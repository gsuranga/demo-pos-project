/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author user-pc
 */
public class DIMOStockReturnDetails {
     private int DIMODeatailID;
    private int reurnID;
    private int StockID;
    private int ItemID;
    private int returnQuantity;
    private double buyingPrice;
    private int status;
    
    private int Quantity;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public DIMOStockReturnDetails(int StockID, int ItemID,double buyingPrice,int returnQuantity,  int status) {
       
        this.StockID = StockID;
        this.ItemID = ItemID;
        this.returnQuantity = returnQuantity;
        this.buyingPrice = buyingPrice;
        this.status = status;
    }
    
     public DIMOStockReturnDetails(int StockID, int ItemID, int Quantity) {
        this.StockID = StockID;
        this.ItemID = ItemID;
        this.Quantity = Quantity;
    }

    public int getDIMODeatailID() {
        return DIMODeatailID;
    }

    public void setDIMODeatailID(int DIMODeatailID) {
        this.DIMODeatailID = DIMODeatailID;
    }

    public int getReurnID() {
        return reurnID;
    }

    public void setReurnID(int reurnID) {
        this.reurnID = reurnID;
    }

    public int getStockID() {
        return StockID;
    }

    public void setStockID(int StockID) {
        this.StockID = StockID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(int returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
    
}
