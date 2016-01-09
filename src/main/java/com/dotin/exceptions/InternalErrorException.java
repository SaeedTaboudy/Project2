package com.dotin.exceptions;

/**
 * @author Saeed Taboudy
 */
public class InternalErrorException extends Exception {
    private String message = null;

    public InternalErrorException() {
    }

    public InternalErrorException(String message) {
        super(message);
        message = message;
    }

    @Override
    public String toString() {
        return "UnknownException{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return "UnknownException{" +
                "message='" + message + '\'' +
                '}';
    }
}

