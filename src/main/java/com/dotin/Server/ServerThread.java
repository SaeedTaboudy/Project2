package com.dotin.Server;

import com.dotin.Parser.MyFileParser;
import com.dotin.Parser.customLogger;
import com.dotin.deposits.Deposit;
import com.dotin.deposits.Transaction;
import com.dotin.exceptions.FileFormatException;
import com.dotin.exceptions.FileNotFoundExcep;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Saeed Taboudy
 */
public class ServerThread extends Thread {

    private static Socket socket = null;


    List<Transaction> transactionList = new ArrayList<>();
    Map<String, Deposit> depositList = new HashMap<>();


    public ServerThread(Socket client) {
        socket = client;
    }

    public void run() {
        ObjectInputStream inStream;
        InputStream counter;
        ObjectOutputStream outputResult;
        //Read from Client
        try {
            getServerDepositInfo();
            customLogger.log("ServerThread", "Accepted connection...");
            counter = socket.getInputStream();
            //First of all we read transactions count from client
            int transactionCount =counter.read();
            //In this step we receive transactions from terminals
            for (int i = 0; i < transactionCount; i++) {
                inStream = new ObjectInputStream(socket.getInputStream());
                transactionList.add((Transaction) inStream.readObject());
            }
            customLogger.log("ServerThread", "Data gathered from client....");
            //We use this method to take appropriate action on transactions
          //  TransactionHandler.executeQuery(transactionList, depositList);
            new TransactionHandler().executeQuery(transactionList, depositList);
            for (Transaction transaction : transactionList) {
                outputResult = new ObjectOutputStream(socket.getOutputStream());
                outputResult.writeObject(transaction);
            }
            //----
        } catch (IOException | ClassNotFoundException | FileFormatException
                | FileNotFoundExcep e) {
            e.printStackTrace();
        }

    }

    /*
    this method is used to initial server deposit information
    load from core.json file
     */
    private void getServerDepositInfo() throws FileNotFoundExcep, FileFormatException {

        // customLogger.log("ServerThread", "Loading deposit file.");
        JSONObject jsonObject = MyFileParser.getJsonObject();
        JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");
        for (Object iteratoObject: jsonArray) {
            Deposit deposit = new Deposit();
            jsonObject = (JSONObject)iteratoObject;
            deposit.setCustomer((String) jsonObject.get("customer"));
            deposit.setId(BigDecimal.valueOf(Double.valueOf(jsonObject.get("id").toString())));
            deposit.setInitialBalance(BigDecimal.valueOf(Double.parseDouble(jsonObject.get("initialBalance").toString())));
            deposit.setUpperBound(BigDecimal.valueOf(Double.valueOf(jsonObject.get("upperBound").toString())));
            depositList.put(deposit.getId().toString(), (deposit));
        }
    }

}
