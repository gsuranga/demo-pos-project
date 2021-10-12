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
public class ServiceDetail {

    private int serviceDetailID;
    private int salesOrderID;
    private int serviceID;
    private int discountType;
    private double discountAmount;
    private double currentPrice;
    private double serviceAmount;
    private String doneBy;
    private double serviceQty;
    private double discountTotal;
    private int status;

    public ServiceDetail() {
    }

    public ServiceDetail(int salesOrderID, int serviceID, int discountType, double discountAmount, double currentPrice, double serviceAmount, String doneBy, double serviceQty, double discountTotal, int status) {
        this.salesOrderID = salesOrderID;
        this.serviceID = serviceID;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
        this.currentPrice = currentPrice;
        this.serviceAmount = serviceAmount;
        this.doneBy = doneBy;
        this.serviceQty = serviceQty;
        this.discountTotal = discountTotal;
        this.status = status;
    }

    /**
     * @return the serviceDetailID
     */
    public int getServiceDetailID() {
        return serviceDetailID;
    }

    /**
     * @param serviceDetailID the serviceDetailID to set
     */
    public void setServiceDetailID(int serviceDetailID) {
        this.serviceDetailID = serviceDetailID;
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
     * @return the serviceID
     */
    public int getServiceID() {
        return serviceID;
    }

    /**
     * @param serviceID the serviceID to set
     */
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * @return the discountType
     */
    public int getDiscountType() {
        return discountType;
    }

    /**
     * @param discountType the discountType to set
     */
    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    /**
     * @return the discountAmount
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount the discountAmount to set
     */
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return the currentPrice
     */
    public double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice the currentPrice to set
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * @return the serviceAmount
     */
    public double getServiceAmount() {
        return serviceAmount;
    }

    /**
     * @param serviceAmount the serviceAmount to set
     */
    public void setServiceAmount(double serviceAmount) {
        this.serviceAmount = serviceAmount;
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
     * @return the doneBy
     */
    public String getDoneBy() {
        return doneBy;
    }

    /**
     * @param doneBy the doneBy to set
     */
    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    /**
     * @return the serviceQty
     */
    public double getServiceQty() {
        return serviceQty;
    }

    /**
     * @param serviceQty the serviceQty to set
     */
    public void setServiceQty(double serviceQty) {
        this.serviceQty = serviceQty;
    }

    /**
     * @return the discountTotal
     */
    public double getDiscountTotal() {
        return discountTotal;
    }

    /**
     * @param discountTotal the discountTotal to set
     */
    public void setDiscountTotal(double discountTotal) {
        this.discountTotal = discountTotal;
    }

}
