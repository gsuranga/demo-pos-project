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
public class CustomerType {

    private int customerTypeID;
    private String customerTypeName;
    private String customerTypeCode;
    private String addedDate;
    private String addedTime;
    private int status;

    public CustomerType() {
    }

    public CustomerType(String customerTypeName, String customerTypeCode, String addedDate, String addedTime, int status) {
        this.customerTypeName = customerTypeName;
        this.customerTypeCode = customerTypeCode;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
    }

    /**
     * @return the customerTypeID
     */
    public int getCustomerTypeID() {
        return customerTypeID;
    }

    /**
     * @param customerTypeID the customerTypeID to set
     */
    public void setCustomerTypeID(int customerTypeID) {
        this.customerTypeID = customerTypeID;
    }

    /**
     * @return the customerTypeName
     */
    public String getCustomerTypeName() {
        return customerTypeName;
    }

    /**
     * @param customerTypeName the customerTypeName to set
     */
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    /**
     * @return the customerTypeCode
     */
    public String getCustomerTypeCode() {
        return customerTypeCode;
    }

    /**
     * @param customerTypeCode the customerTypeCode to set
     */
    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
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

}
