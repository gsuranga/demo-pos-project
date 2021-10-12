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
public class ReturnsSales {
    private String salesorderid;
    
    private int item_id;
    private String part_nu;
    private String description;
    private double unit_price;
    private double discount;
    private double qty;
    
    private String date;
    private String time;
    
    private String reason;
    
    private int cus_id;
    private String cus_name;
    private String cus_account_nu;
    
    private double amount;
    
    public ReturnsSales(){
        
    }

    public ReturnsSales(String salesorderid, int item_id, String part_nu, String description, double unit_price, double discount,double qty) {
        this.salesorderid = salesorderid;
        this.item_id = item_id;
        this.part_nu = part_nu;
        this.description = description;
        this.unit_price = unit_price;
        this.discount = discount;
        this.qty=qty;
    }
    
    

    public ReturnsSales(String salesorderid) {
        this.salesorderid = salesorderid;
    }
    

    /**
     * @return the salesorderid
     */
    public String getSalesorderid() {
        return salesorderid;
    }

    /**
     * @param salesorderid the salesorderid to set
     */
    public void setSalesorderid(String salesorderid) {
        this.salesorderid = salesorderid;
    }

    /**
     * @return the item_id
     */
    public int getItem_id() {
        return item_id;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    /**
     * @return the part_nu
     */
    public String getPart_nu() {
        return part_nu;
    }

    /**
     * @param part_nu the part_nu to set
     */
    public void setPart_nu(String part_nu) {
        this.part_nu = part_nu;
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
     * @return the unit_price
     */
    public double getUnit_price() {
        return unit_price;
    }

    /**
     * @param unit_price the unit_price to set
     */
    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(double discount) {
        this.discount = discount;
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
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the cus_id
     */
    public int getCus_id() {
        return cus_id;
    }

    /**
     * @param cus_id the cus_id to set
     */
    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }

    /**
     * @return the cus_name
     */
    public String getCus_name() {
        return cus_name;
    }

    /**
     * @param cus_name the cus_name to set
     */
    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    /**
     * @return the cus_account_nu
     */
    public String getCus_account_nu() {
        return cus_account_nu;
    }

    /**
     * @param cus_account_nu the cus_account_nu to set
     */
    public void setCus_account_nu(String cus_account_nu) {
        this.cus_account_nu = cus_account_nu;
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
    
    
}
