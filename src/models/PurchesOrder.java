/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author insaf
 */
public class PurchesOrder {

    private int id;
    private String date;
    private int status;
    private double total;
    private int complete;
    private String time;
    private int accepttodatabase;
    private String user;
    private int adminPuchaseOrderID;
    private String purchaseOrderNumber;
    private double currentDealerDiscount;
    private double currentVatAmount;
    private double finalAmount;

    public PurchesOrder() {
        
    }

    public PurchesOrder(int id, String date, int status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public PurchesOrder(int id, int adminPuchaseOrderID, String purchaseOrderNumber, int status) {
        this.id = id;
        this.adminPuchaseOrderID = adminPuchaseOrderID;
        this.status = status;
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
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
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the complete
     */
    public int getComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(int complete) {
        this.complete = complete;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the accepttodatabase
     */
    public int getAccepttodatabase() {
        return accepttodatabase;
    }

    /**
     * @param accepttodatabase the accepttodatabase to set
     */
    public void setAccepttodatabase(int accepttodatabase) {
        this.accepttodatabase = accepttodatabase;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the adminPuchaseOrderID
     */
    public int getAdminPuchaseOrderID() {
        return adminPuchaseOrderID;
    }

    /**
     * @param adminPuchaseOrderID the adminPuchaseOrderID to set
     */
    public void setAdminPuchaseOrderID(int adminPuchaseOrderID) {
        this.adminPuchaseOrderID = adminPuchaseOrderID;
    }

    /**
     * @return the purchaseOrderNumber
     */
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    /**
     * @param purchaseOrderNumber the purchaseOrderNumber to set
     */
    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    /**
     * @return the currentDealerDiscount
     */
    public double getCurrentDealerDiscount() {
        return currentDealerDiscount;
    }

    /**
     * @param currentDealerDiscount the currentDealerDiscount to set
     */
    public void setCurrentDealerDiscount(double currentDealerDiscount) {
        this.currentDealerDiscount = currentDealerDiscount;
    }

    /**
     * @return the currentVatAmount
     */
    public double getCurrentVatAmount() {
        return currentVatAmount;
    }

    /**
     * @param currentVatAmount the currentVatAmount to set
     */
    public void setCurrentVatAmount(double currentVatAmount) {
        this.currentVatAmount = currentVatAmount;
    }

    /**
     * @return the finalAmount
     */
    public double getFinalAmount() {
        return finalAmount;
    }

    /**
     * @param finalAmount the finalAmount to set
     */
    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

}
