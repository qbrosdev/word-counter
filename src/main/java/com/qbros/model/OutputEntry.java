package com.qbros.model;

import static com.qbros.utils.StringUtils.strAppend;

/**
 * Total count of concurrences of a particular word from different files are represented in this object
 */
public class OutputEntry {

    private String word;
    private Long total;
    private String message;

    public OutputEntry(String word, Long total, String message) {

        this.word = word;
        this.total = total;
        this.message = message;
    }

    public String getWord() {
        return word;
    }

    public Long getTotal() {
        return total;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        //<word> <total occurrences> = <occurrences in file1> + <occurrences in file2>
        return strAppend(word, total.toString(), "=", message);
    }
}
