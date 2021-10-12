/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author HP2000
 */
public class OtherSupplierReturn {
    
      private int otherSupplierReturnID;
       private String returnDate;
    private String returnTime;
    private int otherReturnStatus;
    private int status;
    private double returnAmount;
       
   
   
    
    
     public OtherSupplierReturn() {
    }

    public OtherSupplierReturn(String returnDate,String returnTime,double returnAmount,int status,int otherReturnStatus) {
        
        
        this.returnDate=returnDate;
        this.returnTime=returnTime;
        this.otherReturnStatus=otherReturnStatus;
        this.status = status;
        this.returnAmount=returnAmount;
      
    }

    public double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(double returnAmount) {
        this.returnAmount = returnAmount;
    }

   
   
    public int getOtherReturnStatus() {
        return otherReturnStatus;
    }

    public void setOtherReturnStatus(int otherReturnStatus) {
        this.otherReturnStatus = otherReturnStatus;
    }

    public int getOtherSupplierReturnID() {
        return otherSupplierReturnID;
    }

    public void setOtherSupplierReturnID(int otherSupplierReturnID) {
        this.otherSupplierReturnID = otherSupplierReturnID;
    }

    

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

   

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
