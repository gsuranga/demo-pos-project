/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author insaf
 */
public class Vehicle {

    private int vehicleID;
    private int customerID;
    private String vehicleRegNo;
    private int vehicleModelID;
    private String addedDate;
    private String addedTime;
    private int status;

    private String custName;
    private String modelName;

    public Vehicle() {
    }

    public Vehicle(int customerID, String vehicleRegNo, int vehicleModelID, String addedDate, String addedTime, int status) {
        this.customerID = customerID;
        this.vehicleRegNo = vehicleRegNo;
        this.vehicleModelID = vehicleModelID;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
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

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the vehicleRegNo
     */
    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    /**
     * @param vehicleRegNo the vehicleRegNo to set
     */
    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
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
     * @return the vehicleModelID
     */
    public int getVehicleModelID() {
        return vehicleModelID;
    }

    /**
     * @param vehicleModelID the vehicleModelID to set
     */
    public void setVehicleModelID(int vehicleModelID) {
        this.vehicleModelID = vehicleModelID;
    }

    @Override
    public String toString() {
        return this.vehicleRegNo; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
