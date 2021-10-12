/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author HP2000
 */
public class EstimateDetails {
    private int detailID;
    private int Estimateid;
    private int itemid;
    private double quantity;
    private double discount;
    private String AddedTime;
    private String date;
    private double unitprice;
    private double buyingprice;
    private double detailAmount ;
    private double discountamount;
    private int status;
    
    public EstimateDetails(){
        
    }
    
    public EstimateDetails(int Estimateid,int itemid,double quantity,Double discount,String AddedTime,String date,Double unitprice,Double buyingprice,Double detailAmount ,Double discountamount,int status){
        this.Estimateid = Estimateid;
        this.itemid = itemid;
        this.quantity = quantity;
        this.discount = discount;
        this.AddedTime = AddedTime;
        this.date = date;
        this.unitprice =unitprice;
        this.buyingprice = buyingprice;
        this.detailAmount  =detailAmount ;
        this.discountamount = discountamount;
        this.status = status;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getEstimateid() {
        return Estimateid;
    }

    public void setEstimateid(int Estimateid) {
        this.Estimateid = Estimateid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getAddedTime() {
        return AddedTime;
    }

    public void setAddedTime(String AddedTime) {
        this.AddedTime = AddedTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public double getBuyingprice() {
        return buyingprice;
    }

    public void setBuyingprice(double buyingprice) {
        this.buyingprice = buyingprice;
    }

    public double getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(double detailAmount) {
        this.detailAmount = detailAmount;
    }

    public double getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(double discountamount) {
        this.discountamount = discountamount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   
    
    
}
