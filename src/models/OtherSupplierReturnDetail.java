/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author HP2000
 */
public class OtherSupplierReturnDetail {
    private int otherSupDeatailID;
    private int reurnID;
    private int StockID;
    private int ItemID;
    private int returnQuantity;
    private double buyingPrice;
    private int status;
    private int Quantity;
    
     public OtherSupplierReturnDetail() {
    }

    public OtherSupplierReturnDetail(int StockID,int ItemID,double buyingPrice,int returnQuantity,int status) {
       
        
        this.StockID=StockID;
        this.ItemID=ItemID;
        this.returnQuantity=returnQuantity;
        this.buyingPrice=buyingPrice;
       // this.otherReturnStatus=otherReturnStatus;
        this.status = status;
      
    }

    public OtherSupplierReturnDetail(int StockID, int ItemID, int Quantity) {
        this.StockID = StockID;
        this.ItemID = ItemID;
        this.Quantity = Quantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getStockID() {
        return StockID;
    }

    public void setStockID(int StockID) {
        this.StockID = StockID;
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

   
    public int getOtherSupDeatailID() {
        return otherSupDeatailID;
    }

    public void setOtherSupDeatailID(int otherSupDeatailID) {
        this.otherSupDeatailID = otherSupDeatailID;
    }

   

    public int getReurnID() {
        return reurnID;
    }

    public void setReurnID(int reurnID) {
        this.reurnID = reurnID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
}
