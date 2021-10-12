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
public class Item {

    private int itemID;
    private int brandID;
    private String ItemName;
    private double buyingPrice;
    private double sellingPrice;
    private String addedDate;
    private String image;
    private String itemDesc;
    private String remarks;
    private double commision;
    private double reOrderQty;
    private int searchKeyID;
    private int supplierID;
    private int typeID;
    private double remainingQty;
    private int status;

    private String brandName;
    private String rackNo;

    public Item() {
    }

    public Item(int brandID, String ItemName, double buyingPrice, double sellingPrice, String addedDate, String image, String itemDesc, String remarks,double commision, double reOrderQty, int searchKeyID, int supplierID, int typeID, int status, String rackNo) {
        this.brandID = brandID;
        this.ItemName = ItemName;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.addedDate = addedDate;
        this.image = image;
        this.itemDesc = itemDesc;
        this.remarks = remarks;
        this.reOrderQty = reOrderQty;
        this.commision=commision;
        this.searchKeyID = searchKeyID;
        this.supplierID = supplierID;
        this.typeID = typeID;
        this.status = status;
        this.rackNo = rackNo;
    }

    public Item(int itemID, int brandID, String ItemName, double buyingPrice, double sellingPrice, String image, String itemDesc, String remarks,double commision, double reOrderQty, int searchKeyID, int supplierID, int typeID, String rackNo) {
        this.itemID = itemID;
        this.brandID = brandID;
        this.ItemName = ItemName;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.image = image;
        this.itemDesc = itemDesc;
        this.remarks = remarks;
        this.commision=commision;
        this.reOrderQty = reOrderQty;
        this.searchKeyID = searchKeyID;
        this.supplierID = supplierID;
        this.typeID = typeID;
        this.rackNo = rackNo;
    }

    public double getCommision() {
        return commision;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }

//    public Item(int itemID, int brandID, String ItemName, double buyingPrice, double sellingPrice, String image, String itemDesc, String searchKey) {
//        this.itemID = itemID;
//        this.brandID = brandID;
//        this.ItemName = ItemName;
//        this.buyingPrice = buyingPrice;
//        this.sellingPrice = sellingPrice;
//        this.image = image;
//        this.itemDesc = itemDesc;
//        this.searchKey = searchKey;
//
//    }
//
//    public Item(int itemID, int quantity) {
//        this.itemID = itemID;
//        this.quantity = quantity;
//    }
//
    public Item(int itemID) {
        this.itemID = itemID;
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
     * @return the brandID
     */
    public int getBrandID() {
        return brandID;
    }

    /**
     * @param brandID the brandID to set
     */
    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    /**
     * @return the ItemName
     */
    public String getItemName() {
        return ItemName;
    }

    /**
     * @param ItemName the ItemName to set
     */
    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
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
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the itemDesc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * @param itemDesc the itemDesc to set
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the reOrderQty
     */
    public double getReOrderQty() {
        return reOrderQty;
    }

    /**
     * @param reOrderQty the reOrderQty to set
     */
    public void setReOrderQty(double reOrderQty) {
        this.reOrderQty = reOrderQty;
    }

    /**
     * @return the searchKeyID
     */
    public int getSearchKeyID() {
        return searchKeyID;
    }

    /**
     * @param searchKeyID the searchKeyID to set
     */
    public void setSearchKeyID(int searchKeyID) {
        this.searchKeyID = searchKeyID;
    }

    /**
     * @return the supplierID
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * @param supplierID the supplierID to set
     */
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * @return the typeID
     */
    public int getTypeID() {
        return typeID;
    }

    /**
     * @param typeID the typeID to set
     */
    public void setTypeID(int typeID) {
        this.typeID = typeID;
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

    @Override
    public String toString() {
        return this.ItemName; //To change body of generated methods, choose Tools | Templates.
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
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return the rackNo
     */
    public String getRackNo() {
        return rackNo;
    }

    /**
     * @param rackNo the rackNo to set
     */
    public void setRackNo(String rackNo) {
        this.rackNo = rackNo;
    }

}
