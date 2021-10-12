/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author insaf
 */
public class Suggest {
    private int order_no;
    private String sales_officer_name;
    private String date;
    private String time;
    private double amount;
    
    private String partNumber;
    private String description;
    private int qty;
    private int unitPrice;
    private double discount_percentage;
    private double vat;
    

    public Suggest() {
    }

    public Suggest(int order_no, String sales_officer_name, String date, String time, double amount) {
        this.order_no = order_no;
        this.sales_officer_name = sales_officer_name;
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    /**
     * @return the order_no
     */
    public int getOrder_no() {
        return order_no;
    }

    /**
     * @param order_no the order_no to set
     */
    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    /**
     * @return the sales_officer_name
     */
    public String getSales_officer_name() {
        return sales_officer_name;
    }

    /**
     * @param sales_officer_name the sales_officer_name to set
     */
    public void setSales_officer_name(String sales_officer_name) {
        this.sales_officer_name = sales_officer_name;
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
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber the partNumber to set
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * @return the unitPrice
     */
    public int getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the discount_percentage
     */
    public double getDiscount_percentage() {
        return discount_percentage;
    }

    /**
     * @param discount_percentage the discount_percentage to set
     */
    public void setDiscount_percentage(double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    /**
     * @return the vat
     */
    public double getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(double vat) {
        this.vat = vat;
    }
    
    
}
