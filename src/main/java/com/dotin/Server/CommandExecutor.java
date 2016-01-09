package com.dotin.Server;

import  com.dotin.Parser.Logger;
import  com.dotin.deposits.Deposit;
import  com.dotin.deposits.Transaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Saeed Taboudy
 */
public class CommandExecutor {
    static List<Transaction> run(List<Transaction> transactions, Map<String, Deposit> depositList) throws IOException {
        List<Transaction> transactionList = transaction(transactions, depositList);
        return transactionList;
    }

    /*
        this method receive a list of transactions and also a list map of the deposits then
            check all transactions for their deposit accounts and then take defined action
            return list of transactions
     */
    public static synchronized List<Transaction> transaction(List<Transaction> transactions, Map<String, Deposit> depositList) {
        for (Transaction transaction : transactions) {
            Deposit deposit = depositList.get(transaction.getDepositId().toString());
            if (deposit != null) {
                if (transaction.getDepositId().equals(deposit.getId())) {
                    BigDecimal depositBalance = deposit.getInitialBalance().add(transaction.getAmount());
                    BigDecimal withdrawBalance = deposit.getInitialBalance().subtract(transaction.getAmount());
                    if (transaction.getType().equals("deposit") && deposit.getUpperBound().compareTo(depositBalance) == 1) {
                        deposit.setDepositBalance(depositBalance);
                        transaction.setResult("Ok");
                        System.out.println(transaction.getDepositId()+"----" +transaction.getResult());
                        Logger.log("CommandExecutor", transaction.getDepositId() + "  Result: Ok");
                    } else if (transaction.getType().equals("withdraw") && deposit.getInitialBalance()
                            .add(deposit.getInitialBalance()).compareTo(transaction.getAmount()) == 1) {
                        deposit.setDepositBalance(withdrawBalance);
                        transaction.setResult("Ok");
                        Logger.log("CommandExecutor", transaction.getDepositId() + "  Result: Ok");
                        System.out.println(transaction.getDepositId()+"----" +transaction.getResult());

                    } else {
                        transaction.setResult("Failed");
                        Logger.log("CommandExecutor", transaction.getDepositId() + "  Result: Failed. Illegal request... ");
                        System.out.println(transaction.getDepositId()+"----" +transaction.getResult());
                    }
                }
            } else {
                transaction.setResult("Failed");
                Logger.log("CommandExecutor", transaction.getDepositId() + "  Result: Failed. deposit not found... ");
                System.out.println(transaction.getDepositId()+"----" +transaction.getResult());
            }
        }
        return transactions;
    }
}
