package com.dotin.Client;

import  com.dotin.Parser.Logger;
import  com.dotin.Parser.MyFileParser;
import  com.dotin.deposits.Transaction;
import  com.dotin.exceptions.FileFormatException;
import  com.dotin.exceptions.FileNotFoundExcep;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.beans.XMLEncoder;
import java.io.*;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Saeed Taboudy
 *         Use this class to  read transaction from client side and send them to the server
 */
public class ClientThread extends Thread {
    private long id;
    private String type;
    public Socket socket = null;
    private String serverIp;
    private int serverPort;
    private ObjectOutputStream outputStream = null;
    private OutputStream transactionCounter = null;
    private static final String TERMINAL_ADDRESS = "src//main//resources//terminal.xml";
    private List<Transaction> transactionList = new ArrayList<>();
    private ObjectInputStream inStream = null;
    private final static String RESPONSE_PATH = "src//main//resources//Response.xml";
    private FileOutputStream fos = null;
    BufferedOutputStream bos = null;


    //initial terminal parameter such as server port and ip
    // and load transaction list from terminal
    public ClientThread() throws IOException, FileNotFoundExcep, FileFormatException {
        terminalInitializing(TERMINAL_ADDRESS);
        getTransactions(TERMINAL_ADDRESS);
        fos = new FileOutputStream(RESPONSE_PATH);
        bos = new BufferedOutputStream(fos);
        start();

    }

    @Override
    public void run() {
        synchronized (this) {
            int transactionListSize = transactionList.size();
            try {
                socket = new Socket(serverIp, serverPort);
                Logger.log("ClientThread", "Connecting to " + socket.toString());
                transactionCounter = socket.getOutputStream();
                //send transactions number to server
                transactionCounter.write(transactionList.size());
                //send transaction objects to sever
                for (int i = 0; i < transactionListSize; i++) {
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(transactionList.get(i));
                }
                //----
                outputStream.reset();
                List<Transaction> resultList = new ArrayList<>();
                //read the transaction result from server and save them to response.xml file
                for (int i = 0; i < transactionListSize; i++) {
                    inStream = new ObjectInputStream(socket.getInputStream());
                    resultList.add((Transaction) inStream.readObject());
                    System.out.println(resultList.size());
                }
                response(resultList);
            } catch (IOException e) {
                Logger.log("ClientThread", e.getMessage());
            } catch (ClassNotFoundException e) {
                Logger.log("ClientThread", e.getMessage());
            }
        }
    }

    /*
        we use this method to initial terminal configuration
     */
    private void terminalInitializing(String terminalAddress) throws FileNotFoundExcep, FileFormatException {
        MyFileParser fileParser = new MyFileParser();
        Document document = fileParser.getXmlObject(terminalAddress);
        NodeList serverTag = document.getElementsByTagName("server");
        Element eElement = (Element) serverTag.item(0);
        serverIp = eElement.getAttribute("ip");
        serverPort = Integer.valueOf(eElement.getAttribute("port"));
        id = Long.valueOf(document.getFirstChild().getAttributes().getNamedItem("id").getNodeValue());
        type = document.getFirstChild().getAttributes().getNamedItem("type").getNodeValue();
    }

    /*
        this method is used to read transactions from xml file and return a list of transactions
            return transactionList
     */
    private List<Transaction> getTransactions(String terminalAddress) throws IOException, FileNotFoundExcep, FileFormatException {
        MyFileParser fileParser = new MyFileParser();
        Document document = fileParser.getXmlObject(terminalAddress);
        NodeList transactionTag = document.getElementsByTagName("transaction");
        for (int i = 0; i < transactionTag.getLength(); i++) {
            Transaction transaction = new Transaction();
            Node nNode = transactionTag.item(i);
            Element eElement = (Element) nNode;
            transaction.setAmount(BigDecimal.valueOf(Double.valueOf(eElement.getAttribute("amount").toString())));
            transaction.setType(eElement.getAttribute("type").toString());
            transaction.setId(Long.valueOf(eElement.getAttribute("id").toString()));
            transaction.setDepositId(BigDecimal.valueOf(Double.valueOf(eElement.getAttribute("deposit").toString())));
            transactionList.add(transaction);
        }
        return transactionList;
    }

    /*
        this method is used to save transactions result into a xml file
     */
    public void response(List<Transaction> transaction) throws FileNotFoundException {
        XMLEncoder xmlEncoder = new XMLEncoder(bos);
        xmlEncoder.writeObject(transaction);
        xmlEncoder.close();
    }

}
