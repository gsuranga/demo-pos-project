/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author shdinesh.99
 */
public class Expence {

    private int expenceID;
    private String description;
    private double amount;
    private String expenceDate;
    private String addedDate;
    private String addedTime;
    private int status;

    public Expence() {
    }

    public Expence(String description, double amount, String expenceDate, String addedDate, String addedTime, int status) {
        this.description = description;
        this.amount = amount;
        this.expenceDate = expenceDate;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
    }

    /**
     * @return the expenceID
     */
    public int getExpenceID() {
        return expenceID;
    }

    /**
     * @param expenceID the expenceID to set
     */
    public void setExpenceID(int expenceID) {
        this.expenceID = expenceID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the expenceDate
     */
    public String getExpenceDate() {
        return expenceDate;
    }

    /**
     * @param expenceDate the expenceDate to set
     */
    public void setExpenceDate(String expenceDate) {
        this.expenceDate = expenceDate;
    }

    /**
     * @return the addedDate
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     * @param addedDate the addedDate to set
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * @return the addedTime
     */
    public String getAddedTime() {
        return addedTime;
    }

    /**
     * @param addedTime the addedTime to set
     */
    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
