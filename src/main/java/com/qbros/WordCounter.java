package com.qbros;

import com.qbros.model.FileWordCountResult;

/**
 * Concrete implementations of this class, by implementing {@link this#countWords}
 * will provide method for getting the count of the words in the input file and present
 * them as {@link FileWordCountResult} object.
 */
public abstract class WordCounter {

    abstract FileWordCountResult countWords();
}
