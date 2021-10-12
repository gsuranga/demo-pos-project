/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author user
 */
public class SalesOrderDetail {

    private int detailID;
    private int salesOrderID;
    private int itemID;
    private double quantity;
    private double discount;
    private String addedDate;
    private String addedTime;
    private double amount;
    private double unitPrice;
    private double buyingPrice;
    private double discountAmount;
    private int stockID;
    private int status;
    private int sellsComiStatus;

    public SalesOrderDetail(int detailID, int salesOrderID, int itemID, double quantity, double discount, int status) {
        this.detailID = detailID;
        this.salesOrderID = salesOrderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.discount = discount;
        this.status = status;
    }

    public int getSellsComiStatus() {
        return sellsComiStatus;
    }

    public void setSellsComiStatus(int sellsComiStatus) {
        this.sellsComiStatus = sellsComiStatus;
    }

    // salesOrderID, itemID, qty, discount, orderDate, deliverdTime, 1
    public SalesOrderDetail(int salesOrderID, int itemID, double quantity, double discount, String addedDate, String addedTime, double unitPrice, double buyingPrice, double amount, double discountAmount, int stockID, int status) {
        this.salesOrderID = salesOrderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.discount = discount;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.status = status;
        this.discountAmount = discountAmount;
        this.stockID = stockID;
        this.buyingPrice = buyingPrice;

    }

    public SalesOrderDetail(int itemID, int salesOrderID) {
        this.itemID = itemID;
        this.salesOrderID = salesOrderID;
    }
    //orderID, itemID, qty, discount, detailAmount, 1

    public SalesOrderDetail(int orderID, int itemID, int qty, int discount, int detailAmount, int status) {
        this.salesOrderID = orderID;
        this.itemID = itemID;
        this.quantity = qty;
        this.discount = discount;
        this.amount = detailAmount;
        this.status = status;
    }

    /**
     * @return the detailID
     */
    public int getDetailID() {
        return detailID;
    }

    /**
     * @param detailID the detailID to set
     */
    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    /**
     * @return the salesOrderID
     */
    public int getSalesOrderID() {
        return salesOrderID;
    }

    /**
     * @param salesOrderID the salesOrderID to set
     */
    public void setSalesOrderID(int salesOrderID) {
        this.salesOrderID = salesOrderID;
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
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
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
     * @return the addedTime
     */
    public String getAddedTime() {
        return addedTime;
    }

    /**
     * @param addedTime the addedTime to set
     */
    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    /**
     * @return the addedDate
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     * @param addedDate the addedDate to set
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
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
     * @return the buyingPrice
     */
    public double getBuyingPrice() {
        return buyingPrice;
    }

    /**
     * @param buyingPrice the buyingPrice to set
     */
    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    /**
     * @return the discountAmount
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discount_amoount the discount_amoount to set
     */
    public void setDiscountAmount(double discount_amoount) {
        this.discountAmount = discount_amoount;
    }

    /**
     * @return the stockID
     */
    public int getStockID() {
        return stockID;
    }

    /**
     * @param stockID the stockID to set
     */
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }
}
