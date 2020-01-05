package com.qbros;

import com.qbros.model.FileWordCountResult;
import com.qbros.model.ResultEntry;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

/**
 * An implementation of {@link WordCounter} that uses parallel streams.
 * <br/>
 * <strong>NOTE</strong>This implementation utilizes {@code Stream.parallel()}.
 * Therefore it is more useful when the file is relatively big.
 */
public class ParallelWordCounter extends WordCounter {

    private InputReader inputReader;

    public ParallelWordCounter(InputReader inputReader) {

        this.inputReader = inputReader;
    }

    @Override
    public FileWordCountResult countWords() {

        Map<String, Integer> wordCountMap = generateWordCountMap();
        return generateFileWordCountResult(wordCountMap);
    }

    private Map<String, Integer> generateWordCountMap() {
        return inputReader.getWordsStream()
                .parallel()
                .map(String::toLowerCase)
                .map(word -> new SimpleImmutableEntry<>(word, 1))
                .collect(groupingBy(SimpleImmutableEntry::getKey,
                        reducing(0, e -> 1, Integer::sum)));
    }

    private FileWordCountResult generateFileWordCountResult(Map<String, Integer> wordCountMap) {
        String fileID = inputReader.getInputId();
        Set<ResultEntry> foundResultEntries = wordCountMap.entrySet()
                .stream()
                .parallel()
                .map(wordCountEntry ->
                        new ResultEntry(wordCountEntry.getKey(), wordCountEntry.getValue(), fileID))
                .collect(Collectors.toSet());
        return new FileWordCountResult(foundResultEntries);
    }
}
