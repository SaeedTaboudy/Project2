package com.dotin.deposits;

/**
 * @author Saeed Taboudy
 */

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

}
