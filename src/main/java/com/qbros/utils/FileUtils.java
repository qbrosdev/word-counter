package com.qbros.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.qbros.utils.ExceptionUtil.handleException;

public class FileUtils {

    private final static String FIND_FILES_ERR_MSG = "error in finding the required file types with extension:";

    /**
     * @param rootPathString string representation of the path of the root directory that contains input files
     * @param fileExtension  the extension of the files that we want to find in the root directory
     * @return List containing the path to all the files found.
     */
    public static List<Path> extractFilePathsFrom(String rootPathString, String fileExtension) {
        try {
            Path rootPath = FileSystems.getDefault().getPath(rootPathString);
            return Files.walk(rootPath, 1)
                    .filter(s -> s.toString().endsWith(fileExtension))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            handleException(e, FIND_FILES_ERR_MSG, fileExtension);
        }
        return Collections.emptyList();
    }
}
