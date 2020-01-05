package com.qbros;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.qbros.utils.ExceptionUtil.handleException;

/**
 * This class is responsible for extracting words from the file
 */

public class FileReader extends InputReader {

    private final String SPACE_BETWEEN_WORDS_REGEX = "\\s+";

    public FileReader(Path sourceFilePath, boolean isStrict) {

        this.sourceFilePath = sourceFilePath;
        this.isStrict = isStrict;
    }

    @Override
    public Stream<String> getWordsStream() {

        return extractLines().stream()
                .map(String::trim)
                .flatMap(trimmedLine -> Stream.of(trimmedLine.split(SPACE_BETWEEN_WORDS_REGEX)))
                .parallel()
                .filter(this::isWord);
    }

    @Override
    public String getInputId() {

        return sourceFilePath.getFileName().toString();
    }

    private List<String> extractLines() {

        try (BufferedReader br = Files.newBufferedReader(Paths.get(sourceFilePath.toUri()), StandardCharsets.UTF_8)) {
            return br.lines().collect(Collectors.toList());

        } catch (Exception e) {
            handleException(e, FILE_OPEN_ERR_MSG, sourceFilePath.toString());
        }
        return Collections.emptyList();
    }
}
