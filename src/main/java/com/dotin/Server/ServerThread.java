package com.dotin.Server;

import com.dotin.Parser.Logger;
import com.dotin.Parser.MyFileParser;
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
    private ObjectInputStream inStream = null;
    private InputStream counter = null;
    List<Transaction> transactionList = new ArrayList<Transaction>();
    Map<String, Deposit> depositList = new HashMap<>();
    private ObjectOutputStream outputResult = null;


    public ServerThread(Socket client) {
        this.socket = client;
    }

    public void run() {
        //Read from Client
        try {
            getServerDepositInfo();
            Logger.log("ServerThread", "Accepted connection...");
            counter = socket.getInputStream();
            //First of all we read transactions count from client
            int transactionCount = Integer.valueOf(counter.read());
            //In this step we receive transactions from terminals
            for (int i = 0; i < transactionCount; i++) {
                inStream = new ObjectInputStream(socket.getInputStream());
                transactionList.add((Transaction) inStream.readObject());
            }
            Logger.log("ServerThread", "Data gathered from client....");
            //We use this method to take appropriate action on transactions
            CommandExecutor.run(transactionList, depositList);

            for (Transaction transaction : transactionList) {
                outputResult = new ObjectOutputStream(socket.getOutputStream());
                outputResult.writeObject(transaction);
            }
            //----
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundExcep fileNotFoundExcep) {
            fileNotFoundExcep.printStackTrace();
        }
        //  }
    }

    /*
    this method is used to initial server deposit information
    load from core.json file
     */
    private void getServerDepositInfo() throws FileNotFoundExcep, FileFormatException {

        Logger.log("ServerThread", "Loading deposit file.");
        JSONObject jsonObject = MyFileParser.getJsonObject();
        JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");
        for (int i = 0; i < jsonArray.size(); i++) {
            Deposit deposit = new Deposit();
            jsonObject = (JSONObject) jsonArray.get(i);
            deposit.setCustomer((String) jsonObject.get("customer"));
            deposit.setId(BigDecimal.valueOf(Double.valueOf(jsonObject.get("id").toString())));
            deposit.setInitialBalance(BigDecimal.valueOf(Double.parseDouble(jsonObject.get("initialBalance").toString())));
            deposit.setUpperBound(BigDecimal.valueOf(Double.valueOf(jsonObject.get("upperBound").toString())));
            depositList.put(deposit.getId().toString(), (deposit));
        }
    }

}
