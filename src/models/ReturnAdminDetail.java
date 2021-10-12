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
public class ReturnAdminDetail {

    private int returnAdminDetailID;
    private int returnAdminID;
    private int itemID;
    private double adminAcceptedQty;
    private String adminRemarks;
    private int status;

    public ReturnAdminDetail() {
    }

    /**
     * @return the returnAdminDetailID
     */
    public int getReturnAdminDetailID() {
        return returnAdminDetailID;
    }

    /**
     * @param returnAdminDetailID the returnAdminDetailID to set
     */
    public void setReturnAdminDetailID(int returnAdminDetailID) {
        this.returnAdminDetailID = returnAdminDetailID;
    }

    /**
     * @return the returnAdminID
     */
    public int getReturnAdminID() {
        return returnAdminID;
    }

    /**
     * @param returnAdminID the returnAdminID to set
     */
    public void setReturnAdminID(int returnAdminID) {
        this.returnAdminID = returnAdminID;
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
     * @return the adminAcceptedQty
     */
    public double getAdminAcceptedQty() {
        return adminAcceptedQty;
    }

    /**
     * @param adminAcceptedQty the adminAcceptedQty to set
     */
    public void setAdminAcceptedQty(double adminAcceptedQty) {
        this.adminAcceptedQty = adminAcceptedQty;
    }

    /**
     * @return the adminRemarks
     */
    public String getAdminRemarks() {
        return adminRemarks;
    }

    /**
     * @param adminRemarks the adminRemarks to set
     */
    public void setAdminRemarks(String adminRemarks) {
        this.adminRemarks = adminRemarks;
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
