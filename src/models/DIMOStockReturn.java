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
public class DIMOStockReturn {
     private int ReturnID;
       private String addedDate;
    private String addedTime;
    private int DimoReturnStatus;
    private int status;
    private double returnAmount;
    
    
    public DIMOStockReturn() {
    }

    public DIMOStockReturn(String addedDate, String addedTime, int DimoReturnStatus, int status, double returnAmount) {
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.DimoReturnStatus = DimoReturnStatus;
        this.status = status;
        this.returnAmount = returnAmount;
    }

    public int getReturnID() {
        return ReturnID;
    }

    public void setReturnID(int ReturnID) {
        this.ReturnID = ReturnID;
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

    public int getDimoReturnStatus() {
        return DimoReturnStatus;
    }

    public void setDimoReturnStatus(int DimoReturnStatus) {
        this.DimoReturnStatus = DimoReturnStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }
    
    
    
    
    
    
}
