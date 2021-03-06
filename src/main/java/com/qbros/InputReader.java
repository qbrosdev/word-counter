package com.qbros;

import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * bBy implementing {@link this#getWordsStream} the concrete implementations of this class,
 * will provide method for extracting the file contents and presenting
 * them as {@code Stream<String>}.
 *
 * Implementation of {@link this#getInputId} will provide a unique id of each file
 */
public abstract class InputReader {

    private final static Pattern wordPattern = Pattern.compile("\\w+");
    protected final static String FILE_OPEN_ERR_MSG = "Error in opening the file";

    /**
     * Path to the source file, form which we extract words
     */
    protected Path sourceFilePath;
    /**
     *
     */
    protected boolean isStrict;


    abstract Stream<String> getWordsStream();

    abstract String getInputId();

    protected boolean isWord(String input) {
        if (isStrict) {
            return wordPattern.matcher(input).matches();
        } else {
            return input.length() > 0;
        }
    }

}
