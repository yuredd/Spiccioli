package it.yuredd.spiccioli.database;

import java.math.BigDecimal;

/**
 * Created by Elia on 03/05/2015.
 */
public class Transaction {
    private int _id;
    private String _description;
    private BigDecimal _amount;

    public Transaction() {

    }

    public Transaction(int id, String description, BigDecimal amount) {
        this._id = id;
        this._description = description;
        this._amount = amount;
    }

    public Transaction(String description, BigDecimal amount) {
        this._description = description;
        this._amount = amount;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

    public BigDecimal getAmount() {
        return _amount;
    }

    public void setAmount(BigDecimal amount) {
        this._amount = amount;
    }
}
