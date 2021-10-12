/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author SHDINESH
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String contactNo;
    private String address;
    private int customerType;
    private String accountNo;
    private String addedDate;
    private String addedTime;
    private int status;
    private String typeName;

    public Customer(String customerName, String contactNo, String address, int customerType, String accountNo, String addedDate, String addedTime, int status) {
        this.customerName = customerName;
        this.contactNo = contactNo;
        this.address = address;
        this.customerType = customerType;
        this.accountNo = accountNo;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
    }

    public Customer() {
    }

    public Customer(int customerID) {
        this.customerID = customerID;
    }

    public Customer(int customerID, String customerName, String address, String contactNo, int customerType, String accountNo) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.contactNo = contactNo;
        this.customerType = customerType;
        this.accountNo = accountNo;
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
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * @param contactNo the contactNo to set
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the customerType
     */
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
     * @return the customerType
     */
    public int getCustomerType() {
        return customerType;
    }

    /**
     * @param customerType the customerType to set
     */
    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    /**
     * @return the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String toString() {
        return this.customerName; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
