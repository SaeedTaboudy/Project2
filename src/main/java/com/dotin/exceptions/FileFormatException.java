package com.dotin.exceptions;

/**
 * @author Saeed Taboudy
 */
public class FileFormatException extends Exception {
    private String message = null;

    public FileFormatException() {
    }

    @Override
    public String toString() {
        return "FileFormatException{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return "FileFormatException{" +
                "message='" + message + '\'' +
                '}';
    }
}
