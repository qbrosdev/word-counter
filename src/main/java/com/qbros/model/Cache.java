package com.qbros.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores all the {@link ResultEntry} Objects in a thread safe collection
 */
public class Cache {

    private Set<ResultEntry> fileCounts = ConcurrentHashMap.newKeySet();

    /**
     * @param resultEntry the entry that needs to be added to the cache
     */
    public void addEntry(ResultEntry resultEntry) {
        fileCounts.add(resultEntry);
    }

    public List<ResultEntry> getCashEntries() {
        return new ArrayList<>(fileCounts);
    }
}
