package com.dotin.deposits;

/**
 * @author Saeed Taboudy
 */

import com.dotin.Parser.customLogger;

import java.math.BigDecimal;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class Deposit {

    private BigDecimal id;
    private String customer;
    private BigDecimal depositBalance;
    private DepositType depositType;
    private BigDecimal initialBalance;
    private BigDecimal upperBound;


    public Deposit() {
    }

    public Deposit(BigDecimal id, String customer, BigDecimal initialBalance, BigDecimal upperBound) {
        this.id = id;
        this.customer = customer;
        this.initialBalance = initialBalance;
        this.upperBound = upperBound;
    }


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }


    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(BigDecimal upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    public boolean equals(Object obj) {
        Transaction transaction = (Transaction) obj;
        if (this.getId() == ((Transaction) obj).getDepositId())
            return true;
        else
            return false;
    }

    public void depositAction(Deposit deposit, Transaction transaction) {
        if (deposit != null) {
            synchronized (this) {
                if (transaction.getDepositId().equals(deposit.getId())) {
                    BigDecimal depositBalance = deposit.getInitialBalance().add(transaction.getAmount());
                    BigDecimal withdrawBalance = deposit.getInitialBalance().subtract(transaction.getAmount());
                    if (transaction.getType().equals("deposit") && deposit.getUpperBound().compareTo(depositBalance) == 1) {
                        deposit.setDepositBalance(depositBalance);
                        transaction.setResult("Ok");
                        System.out.println(transaction.getDepositId() + "----" + transaction.getResult());
                        customLogger.log("CommandExecutor", transaction.getDepositId() + "  Result: Ok");
                    } else if (transaction.getType().equals("withdraw") && deposit.getInitialBalance()
                            .add(deposit.getInitialBalance()).compareTo(transaction.getAmount()) == 1) {
                        deposit.setDepositBalance(withdrawBalance);
                        transaction.setResult("Ok");
                        customLogger.log("CommandExecutor", transaction.getDepositId() + "  Result: Ok");
                        System.out.println(transaction.getDepositId() + "----" + transaction.getResult());

                    } else {
                        transaction.setResult("Failed");
                        customLogger.log("CommandExecutor", transaction.getDepositId() + "  Result: Failed. Illegal request... ");
                        System.out.println(transaction.getDepositId() + "----" + transaction.getResult());
                    }
                }
            }
        } else {
            transaction.setResult("Failed");
            //  customLogger.log("CommandExecutor", transaction.getDepositId() + "  Result: Failed. deposit not found... ");
            System.out.println(transaction.getDepositId() + "----" + transaction.getResult());
        }

        //  return deposit;
    }

}
