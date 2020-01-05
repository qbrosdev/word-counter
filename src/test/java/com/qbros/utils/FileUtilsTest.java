package com.qbros.utils;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileUtilsTest {

    @Test
    public void extractFilePathsFrom() {
        List<Path> result = FileUtils
                .extractFilePathsFrom(Paths.get(".", "src", "test", "resources")
                                .toAbsolutePath().normalize().toString(),
                        ".txt");

        Assert.assertEquals(1, result.size());
        Assert.assertEquals("test.txt", result.get(0).getFileName().toString());
    }

    @Test
    public void extractFilePathsFromTest2() {
        List<Path> result = FileUtils
                .extractFilePathsFrom(Paths.get(".", "src", "test", "resources")
                                .toAbsolutePath().normalize().toString(),
                        ".abc");

        Assert.assertEquals(2, result.size());

        Assert.assertEquals(Stream.of("test.abc", "test2.abc")
                .collect(Collectors.toSet()), result
                .stream()
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toSet()));
    }

    @Test
    public void extractFilePathsFromTestNoFilesFound() {
        List<Path> result = FileUtils
                .extractFilePathsFrom(Paths.get(".", "src", "test", "resources")
                                .toAbsolutePath().normalize().toString(),
                        ".xyz");

        Assert.assertEquals(0, result.size());

    }


}