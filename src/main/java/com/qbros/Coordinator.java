package com.qbros;

import com.qbros.model.Cache;
import com.qbros.model.Context;
import com.qbros.model.OutputEntry;
import com.qbros.model.ResultEntry;
import com.qbros.utils.Logger;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.qbros.utils.ExceptionUtil.handleException;
import static com.qbros.utils.FileUtils.extractFilePathsFrom;
import static java.util.stream.Collectors.groupingBy;

/**
 * This class creates {@link WordCounter} for each file in the {@link Context#rootPath}.
 * Result of each {@link WordCounter#countWords()} is wrapped by a promise {@code CompletableFuture},
 * object.
 *
 * The {@link Coordinator#init} will block until the results of all the count operations
 * are ready, then {@link Coordinator#printCache} will be called. This method will aggregate the contents of
 * the {@link Cache} and prints them.
 */
public class Coordinator {

    private Cache cache;
    private Context context;

    public Coordinator(Cache cache, Context context) {
        this.cache = cache;
        this.context = context;
    }

    /**
     * @param futures all teh future objects that we wait on them
     * @return One future object that wraps all the future objects (Tasks that are sunning concurrently).
     */
    public static <T> CompletableFuture<List<T>> allTasksToFuture(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v -> futures.stream()
                .map(future -> future.join())
                .collect(Collectors.<T>toList())
        );
    }

    public void init() {
        Logger.logDebug("Starting process with config -> " + context);
        Logger.logTime("Response took ", () -> {
            List<CompletableFuture<Void>> wordCountTasks = generateWordCountTaskForEachFileInPath(context.getRootPath());
            CompletableFuture<List<Void>> allFuturesResult = allTasksToFuture(wordCountTasks);
            //Synchronization
            allFuturesResult.join();
            allFuturesResult.thenRun(this::printCache);
        });
    }

    private List<CompletableFuture<Void>> generateWordCountTaskForEachFileInPath(String rootPath) {
        return generateWordCounterForEachFile(rootPath)
                .stream()
                .map(this::generateTaskForEachWordCounter)
                .collect(Collectors.toList());
    }

    /**
     * This method reads all the {@link Cache#getCashEntries()}.
     * Then groups them by word. the the grouped result is converted to {@link OutputEntry} object.
     * Then the converted objects are (reverse)sorted by the total value {@link OutputEntry#getTotal()}
     * and then by alphabetical order of the word {@link OutputEntry#getWord()} and finally the result is
     * printed.
     */
    private void printCache() {
        Logger.logDebug("Counting finished");
        Logger.logTime("Printing cache took ", () -> {

            Map<String, List<ResultEntry>> resultsGroupedByWord = cache.getCashEntries()
                    .stream()
                    .collect(groupingBy(ResultEntry::getWord));

            resultsGroupedByWord.entrySet()
                    .stream()
                    .map(this::getOutputEntry)
                    .sorted(Comparator.comparingLong(OutputEntry::getTotal).reversed()
                            .thenComparing(OutputEntry::getWord))
                    .forEach(outputEntry ->
                            System.out.println(outputEntry.toString())
                    );
        });
    }

    /**
     * creates a separate {@link WordCounter} for each file in the rootPath
     *
     * @param rootPath path to the directory, which contains all the input files
     * @return list of separate {@link WordCounter} for each file.
     */
    private List<WordCounter> generateWordCounterForEachFile(String rootPath) {
        final String fileExtension = ".txt";
        return extractFilePathsFrom(rootPath, fileExtension).stream()
                .map(path -> new FileReader(path, context.isStrict()))
                .map(ParallelWordCounter::new)
                .collect(Collectors.toList());
    }

    /**
     * For each {@link WordCounter} a separate  {@code CompletableFuture} is created so its
     * progress can be tracked.
     *
     * @param wordCounter word counter we want to track
     * @return promise object for the wordCounter
     */
    private CompletableFuture<Void> generateTaskForEachWordCounter(WordCounter wordCounter) {
        return CompletableFuture
                .runAsync(() -> {
                    Collection<ResultEntry> resultEntries = wordCounter.countWords().getEntries();
                    for (ResultEntry resultEntry : resultEntries) {
                        cache.addEntry(resultEntry);
                    }
                })
                .exceptionally(this::handleError);
    }

    /**
     * Reads the map List of  {@link ResultEntry} object related to each word and aggregates them
     * into a {@link OutputEntry} object
     *
     * @param entry
     * @return OutputEntry
     */
    private OutputEntry getOutputEntry(Map.Entry<String, List<ResultEntry>> entry) {
        String word = entry.getKey();
        Long total = entry.getValue().stream()
                .map(ResultEntry::getCount)
                .mapToLong(Integer::intValue).sum();
        String message = entry.getValue().stream()
                .sorted(Comparator.comparing(ResultEntry::getFileId))
                .map(resultEntry -> resultEntry.getCount().toString())
                .collect(Collectors.joining("+"));
        return new OutputEntry(word, total, message);
    }

    private Void handleError(Throwable e) {
        handleException(e, "Exception happened while counting words");
        return null;
    }
}
