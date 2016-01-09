package com.dotin.exceptions;

/**
 * @author Saeed Taboudy
 */
public class LessDayException extends Exception {
    private String message = null;

    public LessDayException() {

    }

    @Override
    public String toString() {
        return "LessDayException{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }
}
