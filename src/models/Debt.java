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
public class Debt {

    private int debtID;
    private int custID;
    private int vehicleID;
    private int salesOrderID;
    private String debtDate;
    private String endDate;
    private double debtAmount;
    private int status;
   // private int gargeCommission;

    public Debt() {
    }

    public Debt(int custID, int vehicleID, int salesOrderID, String debtDate, String endDate, double debtAmount, int status) {
        this.custID = custID;
        this.vehicleID = vehicleID;
        this.salesOrderID = salesOrderID;
        this.debtDate = debtDate;
        this.endDate = endDate;
        this.debtAmount = debtAmount;
        this.status = status;
      //  this.gargeCommission = gargeCommission;
    }

    /**
     * @return the debtID
     */
    public int getDebtID() {
        return debtID;
    }

    /**
     * @param debtID the debtID to set
     */
    public void setDebtID(int debtID) {
        this.debtID = debtID;
    }

    /**
     * @return the custID
     */
    public int getCustID() {
        return custID;
    }
//
//    public int getGargeCommission() {
//        return gargeCommission;
//    }
//
//    public void setGargeCommission(int gargeCommission) {
//        this.gargeCommission = gargeCommission;
//    }

    /**
     * @param custID the custID to set
     */
    public void setCustID(int custID) {
        this.custID = custID;
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
     * @return the debtDate
     */
    public String getDebtDate() {
        return debtDate;
    }

    /**
     * @param debtDate the debtDate to set
     */
    public void setDebtDate(String debtDate) {
        this.debtDate = debtDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the debtAmount
     */
    public double getDebtAmount() {
        return debtAmount;
    }

    /**
     * @param debtAmount the debtAmount to set
     */
    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
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

}
