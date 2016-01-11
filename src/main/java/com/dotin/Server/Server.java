package com.dotin.Server;

import com.dotin.Parser.MyFileParser;
import com.dotin.exceptions.FileFormatException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Saeed Taboudy
 */
public class Server {


    public static void main(String[] args) throws
            FileFormatException, IOException, InterruptedException {

        String serverPort = MyFileParser.getJsonObject().get("port").toString();
        ServerSocket socket = new ServerSocket(Integer.valueOf(serverPort));
        System.out.println("Server listening on port: " + serverPort);
        while (true) {
            //customLogger.log("Server", "Server listening on port: " + serverPort);
            Socket client = socket.accept();
            Thread thrd = new Thread(new ServerThread(client));
            thrd.sleep(100);
            thrd.start();

        }

    }
}
