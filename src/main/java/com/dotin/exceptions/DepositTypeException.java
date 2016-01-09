package com.dotin.exceptions;

/**
 * @author Maral Khojasteh
 */
public class DepositTypeException extends Exception {
    private String message = null;

    public DepositTypeException() {
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
