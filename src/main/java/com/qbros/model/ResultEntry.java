package com.qbros.model;

import java.util.Objects;

/**
 * This class represents the number of occurrence of a particular word in a particular file
 */
public class ResultEntry {

    private String word;
    private Integer count;
    private String fileId;

    public ResultEntry(String word, Integer count, String fileId) {
        this.word = word;
        this.count = count;
        this.fileId = fileId;
    }

    public Integer getCount() {
        return count;
    }

    public String getFileId() {
        return fileId;
    }

    public String getWord() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultEntry that = (ResultEntry) o;
        return Objects.equals(fileId, that.fileId) &&
                Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fileId, word);
    }
}
