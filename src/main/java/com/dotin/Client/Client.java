package com.dotin.Client;

import com.dotin.exceptions.FileFormatException;
import com.dotin.exceptions.FileNotFoundExcep;

import java.io.IOException;

/**
 * @author Saeed Taboudy
 */
public class Client {
    // create a variable to initialize new threads with
    private static Thread sampleThread = null;

    public static void main(String[] args) throws IOException, FileNotFoundExcep, FileFormatException {
        Client client = new Client();
        for (int i = 0; i < 2; i++) {
            sampleThread = new Thread(new ClientThread());
            sampleThread.start();
        }
    }
}
