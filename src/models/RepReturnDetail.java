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
public class RepReturnDetail {

    private int returnRepDetailID;
    private int returnRepID;
    private int itemID;
    private double repAcceptedQty;
    private String repRemarks;
    private int status;

    public RepReturnDetail() {
    }

    /**
     * @return the returnRepDetailID
     */
    public int getReturnRepDetailID() {
        return returnRepDetailID;
    }

    /**
     * @param returnRepDetailID the returnRepDetailID to set
     */
    public void setReturnRepDetailID(int returnRepDetailID) {
        this.returnRepDetailID = returnRepDetailID;
    }

    /**
     * @return the returnRepID
     */
    public int getReturnRepID() {
        return returnRepID;
    }

    /**
     * @param returnRepID the returnRepID to set
     */
    public void setReturnRepID(int returnRepID) {
        this.returnRepID = returnRepID;
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
     * @return the repAcceptedQty
     */
    public double getRepAcceptedQty() {
        return repAcceptedQty;
    }

    /**
     * @param repAcceptedQty the repAcceptedQty to set
     */
    public void setRepAcceptedQty(double repAcceptedQty) {
        this.repAcceptedQty = repAcceptedQty;
    }

    /**
     * @return the repRemarks
     */
    public String getRepRemarks() {
        return repRemarks;
    }

    /**
     * @param repRemarks the repRemarks to set
     */
    public void setRepRemarks(String repRemarks) {
        this.repRemarks = repRemarks;
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
