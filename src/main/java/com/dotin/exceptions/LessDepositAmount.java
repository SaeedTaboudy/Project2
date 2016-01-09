package com.dotin.exceptions;

/**
 * @author Saeed Taboudy
 */
public class LessDepositAmount extends Exception {
    private String message = null;

    public LessDepositAmount() {
    }

    @Override
    public String toString() {
        return "LessDepositAmount{" +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }
}
