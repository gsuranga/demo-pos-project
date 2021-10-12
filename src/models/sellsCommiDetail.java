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
public class sellsCommiDetail {
     private int detailID;
     private int sellsID;
     private int ItemID;
     private int orderID;
    private int empId;
    private double commision;
    private int status;
    
     public sellsCommiDetail() {
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public sellsCommiDetail(int ItemID,int orderID,int empId,double commision,int status) {
        this.ItemID=ItemID;
        this.orderID=orderID;
        this.empId=empId;
        this.commision=commision;
        this.status = status;
      
    }
   

    

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

  
    public int getSellsID() {
        return sellsID;
    }

    public void setSellsID(int sellsID) {
        this.sellsID = sellsID;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int ItemID) {
        this.ItemID = ItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    

    

    public double getCommision() {
        return commision;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
