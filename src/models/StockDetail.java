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
public class StockDetail {

    private int detailID;
    private int stockID;
    private int itemID;
    private double stockQuontity;
    private double remainingQty;
    private double buyingPrice;
    private double sellingPrice;
   // private int otherSuplierReturns;
    private int status;

    public StockDetail() {
    }

    public StockDetail(int stockID, int itemID, int stockQuontity, int remainingQty, double buyingPrice, double sellingPrice, int status) {
        this.stockID = stockID;
        this.itemID = itemID;
        this.stockQuontity = stockQuontity;
        this.remainingQty = remainingQty;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.status = status;
    }

    public StockDetail(int stockID, int itemID, int status) {
        this.stockID = stockID;
        this.itemID = itemID;
        this.status = status;
    }

    public StockDetail(int itemID, int stockID) {
        this.itemID = itemID;
        this.stockID = stockID;
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
     * @return the stockQuontity
     */
    public double getStockQuontity() {
        return stockQuontity;
    }

    /**
     * @param stockQuontity the stockQuontity to set
     */
    public void setStockQuontity(double stockQuontity) {
        this.stockQuontity = stockQuontity;
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
     * @return the remainingQty
     */
    public double getRemainingQty() {
        return remainingQty;
    }

    /**
     * @param remainingQty the remainingQty to set
     */
    public void setRemainingQty(double remainingQty) {
        this.remainingQty = remainingQty;
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
     * @return the sellingPrice
     */
    public double getSellingPrice() {
        return sellingPrice;
    }

    /**
     * @param sellingPrice the sellingPrice to set
     */
    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

}
