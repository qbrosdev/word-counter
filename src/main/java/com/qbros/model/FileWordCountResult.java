package com.qbros.model;

import com.qbros.WordCounter;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Calling {@link WordCounter#countWords} method will result in series of {@link ResultEntry} objects
 * which are stored in {@link FileWordCountResult} object.
 */
public class FileWordCountResult {

    private Set<ResultEntry> entries;

    public FileWordCountResult(Set<ResultEntry> entries) {

        this.entries = entries;
    }

    public Collection<ResultEntry> getEntries() {

        return Collections.unmodifiableSet(entries);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileWordCountResult result = (FileWordCountResult) o;
        return Objects.equals(entries, result.entries);
    }

    @Override
    public int hashCode() {

        return Objects.hash(entries);
    }
}
