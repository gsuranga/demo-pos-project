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
public class SalesOrder {

    private int salesorderID;
    private String salesorderNo;
    private String salesOrderDate;
    private int custID;
    private int vehicleID;
    private String time;
    private int userID;
    private double amount;
    private double paidAmount;
    private double labourAmount;
    private double currentMeterReading;
    private double nextServiceDue;
    private int employeeID;
    private double totalDiscount;
    private String otherComments;
    
    private double gargeCommission;

    private int status;

    public SalesOrder(int salesorderID, String salesOrderDate, int custID, int status) {
        this.salesorderID = salesorderID;
        this.salesOrderDate = salesOrderDate;
        this.custID = custID;
        this.status = status;
    }

    public SalesOrder(String salesorderNo, String salesOrderDate, int custID, int vehicleID, String time, int userID, double amount, double paidAmount, double labourAmount, double currentMeterReadingint, double nextServiceDue, int employeeID, double totalDiscount, String otherComments, int status,double gargeCommission) {
        this.salesorderNo = salesorderNo;
        this.salesOrderDate = salesOrderDate;
        this.custID = custID;
        this.vehicleID = vehicleID;
        this.time = time;
        this.userID = userID;
        this.amount = amount;
        this.paidAmount = paidAmount;
        this.labourAmount = labourAmount;
        this.currentMeterReading = currentMeterReadingint;
        this.nextServiceDue = nextServiceDue;
        this.employeeID = employeeID;
        this.totalDiscount = totalDiscount;
        this.otherComments = otherComments;
        this.status = status;
        
        this.gargeCommission=gargeCommission;

    }

    public SalesOrder(int custID, int salesorderID) {
        this.custID = custID;
        this.salesorderID = salesorderID;

    }

    public SalesOrder(int salesorderID, int custID, double amount) {
        this.salesorderID = salesorderID;
        this.custID = custID;
        this.amount = amount;
    }

    /**
     * @return the orderID
     */
    public int getOrderID() {
        return getSalesorderID();
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(int salesorderID) {
        this.setSalesorderID(salesorderID);
    }

    /**
     * @return the OrderDate
     */
    public String getOrderDate() {
        return salesOrderDate;
    }

    /**
     * @param OrderDate the OrderDate to set
     */
    public void setOrderDate(String OrderDate) {
        this.salesOrderDate = salesOrderDate;
    }

    /**
     * @return the custID
     */
    public int getCustID() {
        return custID;
    }

    /**
     * @param custID the custID to set
     */
    public void setCustID(int custID) {
        this.custID = custID;
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
     * @return the salesorderID
     */
    public int getSalesorderID() {
        return salesorderID;
    }

    /**
     * @param salesorderID the salesorderID to set
     */
    public void setSalesorderID(int salesorderID) {
        this.salesorderID = salesorderID;
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
     * @return the userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
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
     * @return the labourAmount
     */
    public double getLabourAmount() {
        return labourAmount;
    }

    /**
     * @param labourAmount the labourAmount to set
     */
    public void setLabourAmount(double labourAmount) {
        this.labourAmount = labourAmount;
    }

    /**
     * @return the paidAmount
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * @param paidAmount the paidAmount to set
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * @return the salesorderNo
     */
    public String getSalesorderNo() {
        return salesorderNo;
    }

    /**
     * @param salesorderNo the salesorderNo to set
     */
    public void setSalesorderNo(String salesorderNo) {
        this.salesorderNo = salesorderNo;
    }

    /**
     * @return the vehicleID
     */
    public int getVehicleID() {
        return vehicleID;
    }

    /**
     * @param vehicleID the vehicleID to set
     */
    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    /**
     * @return the currentMeterReading
     */
    public double getCurrentMeterReading() {
        return currentMeterReading;
    }

    /**
     * @param currentMeterReading the currentMeterReading to set
     */
    public void setCurrentMeterReading(double currentMeterReading) {
        this.currentMeterReading = currentMeterReading;
    }

    /**
     * @return the nextServiceDue
     */
    public double getNextServiceDue() {
        return nextServiceDue;
    }

    /**
     * @param nextServiceDue the nextServiceDue to set
     */
    public void setNextServiceDue(double nextServiceDue) {
        this.nextServiceDue = nextServiceDue;
    }

    /**
     * @return the employeeID
     */
    public int getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * @return the totalDiscount
     */
    public double getTotalDiscount() {
        return totalDiscount;
    }

    /**
     * @param totalDiscount the totalDiscount to set
     */
    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    /**
     * @return the otherComments
     */
    public String getOtherComments() {
        return otherComments;
    }

    /**
     * @param otherComments the otherComments to set
     */
    public void setOtherComments(String otherComments) {
        this.otherComments = otherComments;
    }
    
    public Double getGarageCommission(){
        return gargeCommission;
    }
    
    public void setGargeCommission(){
        this.gargeCommission=gargeCommission;
    }
}
