package com.qbros.model;

/**
 * This class contains the configuration with which the application was ran.
 */
public class Context {

    /**
     * Path to the directory which contains the input files
     */
    private String rootPath = "input";
    /**
     * flag to indicate whether every group of characters are considered a word or not
     * e.g without strict flag, a combination like this "@#$" is considered a word.
     */
    private boolean strict = false;
    /**
     * show debug messages or not
     */
    private boolean debug = true;

    public String getRootPath() {
        return rootPath;
    }

    public boolean isStrict() {
        return strict;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "Context{" +
                "rootPath='" + rootPath + '\'' +
                ", strict=" + strict +
                ", debug=" + debug +
                '}';
    }
}
