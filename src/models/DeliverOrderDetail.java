/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author SHDINESH
 */
public class DeliverOrderDetail {

    private int deleverOrderDetailID;
    private int deliverOrderID;
    private int partID;
    private double amount;
    private double retailVal;
    private double qty;
    private int status;

    public DeliverOrderDetail(int deliverOrderID, int partID, double amount, double retailVal, double qty, int status) {
        this.deliverOrderID = deliverOrderID;
        this.partID = partID;
        this.amount = amount;
        this.retailVal = retailVal;
        this.qty = qty;
        this.status = status;

    }

    public DeliverOrderDetail() {
    }

    /**
     * @return the deleverOrderDetailID
     */
    public int getDeleverOrderDetailID() {
        return deleverOrderDetailID;
    }

    /**
     * @param deleverOrderDetailID the deleverOrderDetailID to set
     */
    public void setDeleverOrderDetailID(int deleverOrderDetailID) {
        this.deleverOrderDetailID = deleverOrderDetailID;
    }

    /**
     * @return the deliverOrderID
     */
    public int getDeliverOrderID() {
        return deliverOrderID;
    }

    /**
     * @param deliverOrderID the deliverOrderID to set
     */
    public void setDeliverOrderID(int deliverOrderID) {
        this.deliverOrderID = deliverOrderID;
    }

    /**
     * @return the partID
     */
    public int getPartID() {
        return partID;
    }

    /**
     * @param partID the partID to set
     */
    public void setPartID(int partID) {
        this.partID = partID;
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
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
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
     * @return the retailVal
     */
    public double getRetailVal() {
        return retailVal;
    }

    /**
     * @param retailVal the retailVal to set
     */
    public void setRetailVal(double retailVal) {
        this.retailVal = retailVal;
    }

}
