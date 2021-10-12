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
public class CustomerReturn {

    private int returnID;
    private int salesOrderID;
    private String retNumber;
    private String retDate;
    private String retTime;
    private double retAmount;
    private int status;
    private int returns;

    public CustomerReturn() {
    }

    public CustomerReturn(int salesOrderID,String retNumber, String retDate, String retTime, double retAmount, int status) {
        this.salesOrderID = salesOrderID;
        this.retNumber = retNumber;
        this.retDate = retDate;
        this.retTime = retTime;
        this.retAmount = retAmount;
        this.status = status;
    }

    /**
     * @return the returnID
     */
    public int getReturnID() {
        return returnID;
    }

    /**
     * @param returnID the returnID to set
     */
    public void setReturnID(int returnID) {
        this.returnID = returnID;
    }

    public int getReturns() {
        return returns;
    }

    public void setReturns(int returns) {
        this.returns = returns;
    }
    
    

    /**
     * @return the salesOrderID
     */
    public int getSalesOrderID() {
        return salesOrderID;
    }

    /**
     * @param salesOrderID the salesOrderID to set
     */
    public void setSalesOrderID(int salesOrderID) {
        this.salesOrderID = salesOrderID;
    }

    /**
     * @return the retDate
     */
    public String getRetDate() {
        return retDate;
    }

    /**
     * @param retDate the retDate to set
     */
    public void setRetDate(String retDate) {
        this.retDate = retDate;
    }

    /**
     * @return the retTime
     */
    public String getRetTime() {
        return retTime;
    }

    /**
     * @param retTime the retTime to set
     */
    public void setRetTime(String retTime) {
        this.retTime = retTime;
    }

    /**
     * @return the retAmount
     */
    public double getRetAmount() {
        return retAmount;
    }

    /**
     * @param retAmount the retAmount to set
     */
    public void setRetAmount(double retAmount) {
        this.retAmount = retAmount;
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

    /**
     * @return the retNumber
     */
    public String getRetNumber() {
        return retNumber;
    }

    /**
     * @param retNumber the retNumber to set
     */
    public void setRetNumber(String retNumber) {
        this.retNumber = retNumber;
    }

}
