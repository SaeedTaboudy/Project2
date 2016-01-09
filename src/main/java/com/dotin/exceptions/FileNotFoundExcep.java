package com.dotin.exceptions;

/**
 * @author Maral Khojasteh
 */
public class FileNotFoundExcep  extends Exception {
    private String message = null;

    public FileNotFoundExcep(String message) {
        this.message = message;
    }

    public FileNotFoundExcep() {
    }

    @Override
    public String toString() {
        return "FileNotFoundExcep{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return "FileNotFoundExcep{" +
                "message='" + message + '\'' +
                '}';
    }
}
