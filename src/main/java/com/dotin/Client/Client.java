package com.dotin.Client;

import  com.dotin.exceptions.FileFormatException;
import  com.dotin.exceptions.FileNotFoundExcep;

import java.io.IOException;

/**
 * @author Saeed Taboudy
 */
public class Client {
    // create a variable to initialize new threads with
    private static Thread thrd = null;

    public static void main(String[] args) throws IOException, FileNotFoundExcep, FileFormatException {
        System.out.println("**************");
        Client client = new Client();
        for (int i = 0; i < 2; i++) {
            thrd = new Thread(new ClientThread());
            thrd.start();
        }
    }
}
