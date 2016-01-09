package com.dotin.Parser;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * @author Saeed Taboudy
 */
public class Logger {
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MyLog");
    public static String LOG_PATH = "src//main//resources//MyLog.log";
    public static FileHandler fh;

    private  void  logging(String cls, String str) {
        try {
            fh = new FileHandler(LOG_PATH, 0,1,  true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.info(str);
            fh.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void log(String cls, String str) {
        Logger logger = new Logger();
        logger.logging(cls, str);

    }
}
