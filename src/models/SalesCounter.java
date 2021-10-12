/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author user-pc
 */
public class SalesCounter {
    private int disID;
    private int employeeID;
    private String addedDate;
    private String addedTime;
    private String Date;
    private double amount;
    private double discount;
    private String Commistion;
   
    
    public SalesCounter(int employeeID, String addedDate, String addedTime,String Date, double amount,double discount,String Commistion) {
        this.employeeID = employeeID;
        this.Date = Date;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.amount = amount;
        this.discount = discount;
        this.Commistion=Commistion;
        
    }

    public int getDisID() {
        return disID;
    }

    public void setDisID(int disID) {
        this.disID = disID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCommistion() {
        return Commistion;
    }

    public void setCommistion(String Commistion) {
        this.Commistion = Commistion;
    }

    
}
