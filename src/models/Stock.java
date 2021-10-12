/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Dinesh
 */
public class Stock {

    private int stockID;
    private int supplierID;
    private String stockDate;
    private String deliverdTime;
    private int deliverOrderId;
    private int status;

    public Stock() {
    }

    public Stock(int stockID, int supplierID, String stockDate, String deliverdTime, int status) {
        this.stockID = stockID;
        this.supplierID = supplierID;
        this.stockDate = stockDate;
        this.deliverdTime = deliverdTime;
        this.status = status;
    }

    public Stock(int supplierID, String stockDate, String deliverdTime, int status) {
        this.supplierID = supplierID;
        this.stockDate = stockDate;
        this.deliverdTime = deliverdTime;
        this.status = status;
    }

    public Stock(int stockID) {
        this.stockID = stockID;
    }

    public Stock(int stockID, int supplierID) {
        this.stockID = stockID;
        this.supplierID = supplierID;
    }

    /**
     * @return the stockID
     */
    public int getStockID() {
        return stockID;
    }

    /**
     * @param stockID the stockID to set
     */
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    /**
     * @return the supplierID
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * @param supplierID the supplierID to set
     */
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * @return the stockDate
     */
    public String getStockDate() {
        return stockDate;
    }

    /**
     * @param stockDate the stockDate to set
     */
    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
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
     * @return the deliverdTime
     */
    public String getDeliverdTime() {
        return deliverdTime;
    }

    /**
     * @param deliverdTime the deliverdTime to set
     */
    public void setDeliverdTime(String deliverdTime) {
        this.deliverdTime = deliverdTime;
    }

    /**
     * @return the deliverOrderId
     */
    public int getDeliverOrderId() {
        return deliverOrderId;
    }

    /**
     * @param deliverOrderId the deliverOrderId to set
     */
    public void setDeliverOrderId(int deliverOrderId) {
        this.deliverOrderId = deliverOrderId;
    }
}
