package com.dotin.deposits;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Saeed Taboudy
 */
public class Transaction implements Serializable {
    private long id;
    private String type;
    private BigDecimal amount;
    private BigDecimal depositId;
    private String result;

    public Transaction() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDepositId() {
        return depositId;
    }

    public void setDepositId(BigDecimal depositId) {
        this.depositId = depositId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", depositId=" + depositId +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Transaction transaction = (Transaction) obj;
        if (this.depositId == ((Transaction) obj).getDepositId())
            return true;
        else return false;
    }
}
