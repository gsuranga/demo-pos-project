/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.*;

/**
 *
 * @author shdinesh.99
 */
public class CustomerReturnDetail {

    private int customerRetDetailID;
    private int custRetID;
    private int itemID;
    private double retQty;
    private double unitPrice;
    private String retReason;
    private int status;

    public CustomerReturnDetail(int itemID, double retQty, double unitPrice, String retReason, int status) {
        this.itemID = itemID;
        this.retQty = retQty;
        this.unitPrice = unitPrice;
        this.retReason = retReason;
        this.status = status;
    }

    public CustomerReturnDetail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the customerRetDetailID
     */
    public int getCustomerRetDetailID() {
        return customerRetDetailID;
    }

    /**
     * @param customerRetDetailID the customerRetDetailID to set
     */
    public void setCustomerRetDetailID(int customerRetDetailID) {
        this.customerRetDetailID = customerRetDetailID;
    }

   
    /**
     * @return the custRetID
     */
    public int getCustRetID() {
        return custRetID;
    }

    /**
     * @param custRetID the custRetID to set
     */
    public void setCustRetID(int custRetID) {
        this.custRetID = custRetID;
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
     * @return the retQty
     */
    public double getRetQty() {
        return retQty;
    }

    /**
     * @param retQty the retQty to set
     */
    public void setRetQty(double retQty) {
        this.retQty = retQty;
    }

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the retReason
     */
    public String getRetReason() {
        return retReason;
    }

    /**
     * @param retReason the retReason to set
     */
    public void setRetReason(String retReason) {
        this.retReason = retReason;
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
