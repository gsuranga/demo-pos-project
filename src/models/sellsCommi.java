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
public class sellsCommi {
    private int sellsID;
    private String addedDate;
    private String addedTime;
    private double amount;
    private int status;
    private int paidStatus;
    
     public sellsCommi() {
    }

    public sellsCommi(String addedDate,String addedTime,double amount,int status,int paidStatus) {
        
        
        this.addedDate=addedDate;
        this.addedTime=addedTime;
        this.status = status;
        this.amount=amount;
        this.paidStatus=paidStatus;
        
      
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getSellsID() {
        return sellsID;
    }

    public void setSellsID(int sellsID) {
        this.sellsID = sellsID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }
    
    
    
    
}
