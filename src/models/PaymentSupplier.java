/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author HP2000
 */
public class PaymentSupplier {
    
    private int supplierID;
    private String invoicenumber;
    private String supplier;
    private String date;
    private String time;
    private double amount;
    private String remarks;
    private int status;

    public PaymentSupplier() {
    }

    public PaymentSupplier(String invoicenumber,String supplier,String date,String time,double amount,String remarks,int status) {
        
        this.invoicenumber = invoicenumber;
        this.supplier = supplier;
        this.date  = date;
        this.time = time;
        this.amount = amount;
        this.remarks = remarks;
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
