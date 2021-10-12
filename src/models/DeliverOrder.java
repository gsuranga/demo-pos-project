/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;

/**
 *
 * @author SHDINESH
 */
public class DeliverOrder {

    private int deliverOrderID;
    private String invoicedDate;
    private String invoicedTime;
    private double amount;
    private String invoiceNo;
    private String wipNo;
    private String dueDate;
    private String timeStamp;
    private int adminDeliverOrderID;
    private int status;

    public DeliverOrder(String invoicedDate, String invoicedTime, double amount, String invoiceNo, String wipNo, String dueDate, String timeStamp, int status) {
        this.invoicedDate = invoicedDate;
        this.invoicedTime = invoicedTime;
        this.amount = amount;
        this.invoiceNo = invoiceNo;
        this.wipNo = wipNo;
        this.dueDate = dueDate;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public DeliverOrder() {
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
     * @return the invoicedDate
     */
    public String getInvoicedDate() {
        return invoicedDate;
    }

    /**
     * @param invoicedDate the invoicedDate to set
     */
    public void setInvoicedDate(String invoicedDate) {
        this.invoicedDate = invoicedDate;
    }

    /**
     * @return the invoicedTime
     */
    public String getInvoicedTime() {
        return invoicedTime;
    }

    /**
     * @param invoicedTime the invoicedTime to set
     */
    public void setInvoicedTime(String invoicedTime) {
        this.invoicedTime = invoicedTime;
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
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the wipNo
     */
    public String getWipNo() {
        return wipNo;
    }

    /**
     * @param wipNo the wipNo to set
     */
    public void setWipNo(String wipNo) {
        this.wipNo = wipNo;
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
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the adminDeliverOrderID
     */
    public int getAdminDeliverOrderID() {
        return adminDeliverOrderID;
    }

    /**
     * @param adminDeliverOrderID the adminDeliverOrderID to set
     */
    public void setAdminDeliverOrderID(int adminDeliverOrderID) {
        this.adminDeliverOrderID = adminDeliverOrderID;
    }

}
