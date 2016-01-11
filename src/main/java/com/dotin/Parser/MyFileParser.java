package com.dotin.Parser;

import  com.dotin.exceptions.FileFormatException;
import  com.dotin.exceptions.FileNotFoundExcep;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Saeed Taboudy
 */

/*
    This class is used to read server JSON configuration file and load it to server properties and
    read and load terminals information from client side.
 */
public class MyFileParser {
    private final static String JSON_CONFIG = "src//main//resources//core.json";

    /*
    This Method used to loading json file
    return JSONObject
     */
    public static JSONObject getJsonObject() throws FileNotFoundExcep, FileFormatException {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(JSON_CONFIG));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        } catch (IOException  e) {
            e.printStackTrace();
      //      customLogger.log("MyFileParser", e.getMessage());
            throw new FileNotFoundExcep();
        } catch (ParseException e) {
            e.printStackTrace();
            //customLogger.log("MyFileParser", e.getMessage());
            throw new FileFormatException();
        }
    }

    /*
    read and load terminal xml file in client side and return its document object
    return Document
     */
    public Document getXmlObject(String terminalAddress) throws FileNotFoundExcep, FileFormatException {
        File inputFile = new File(terminalAddress);
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        } catch (ParserConfigurationException | org.xml.sax.SAXException e) {
            customLogger.log("", "Terminal Xml file format exception....");
            throw new FileFormatException();
        } catch (IOException e) {
            customLogger.log("", "Terminal Xml file not found....");
            throw new FileNotFoundExcep();
        }
        doc.getDocumentElement().normalize();
        return doc;
    }

}
