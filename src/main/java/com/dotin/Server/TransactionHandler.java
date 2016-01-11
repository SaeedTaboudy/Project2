package com.dotin.Server;

import com.dotin.deposits.Deposit;
import com.dotin.deposits.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Saeed Taboudy
 */
public class TransactionHandler {
    public List<Transaction> executeQuery(List<Transaction> transactions, Map<String, Deposit> depositList) throws IOException {
        return transaction(transactions, depositList);
    }

    /*
        this method receive a list of transactions and also a list map of the deposits then
            check all transactions for their deposit accounts and then take defined action
            return list of transactions
     */
    public List<Transaction> transaction(List<Transaction> transactions, Map<String, Deposit> depositList) {
        for (Transaction transaction : transactions) {
            Deposit deposit = depositList.get(transaction.getDepositId().toString());
            deposit.depositAction(deposit, transaction);
        }
        return transactions;
    }
}
