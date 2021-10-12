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
public class DealerReturnDetail {

    private int returnDetailID;
    private int returnID;
    private int itemID;
    private double returnQty;
    private String returnReason;
    private int delivered;
    private double unitPriceWithVat;
    private int status;

    public DealerReturnDetail(int returnID, int itemID, double returnQty, String returnReason, int delivered, double unitPriceWithVat, int status) {
        this.returnID = returnID;
        this.itemID = itemID;
        this.returnQty = returnQty;
        this.returnReason = returnReason;
        this.delivered = delivered;
        this.unitPriceWithVat = unitPriceWithVat;
        this.status = status;
    }

    /**
     * @return the returnQty
     */
    public double getReturnQty() {
        return returnQty;
    }

    /**
     * @param returnQty the returnQty to set
     */
    public void setReturnQty(double returnQty) {
        this.returnQty = returnQty;
    }

    /**
     * @return the returnDetailID
     */
    public int getReturnDetailID() {
        return returnDetailID;
    }

    /**
     * @param returnDetailID the returnDetailID to set
     */
    public void setReturnDetailID(int returnDetailID) {
        this.returnDetailID = returnDetailID;
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

    /**
     * @return the itemID
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the returnReason
     */
    public String getReturnReason() {
        return returnReason;
    }

    /**
     * @param returnReason the returnReason to set
     */
    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    /**
     * @return the delivered
     */
    public int getDelivered() {
        return delivered;
    }

    /**
     * @param delivered the delivered to set
     */
    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    /**
     * @return the unitPriceWithVat
     */
    public double getUnitPriceWithVat() {
        return unitPriceWithVat;
    }

    /**
     * @param unitPriceWithVat the unitPriceWithVat to set
     */
    public void setUnitPriceWithVat(double unitPriceWithVat) {
        this.unitPriceWithVat = unitPriceWithVat;
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
