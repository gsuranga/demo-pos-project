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
public class Supplier {

    private int supplierID;
    private String supplierName;
    private String addedDate;
    private String contactNo;
    private String address;
    private int status;

    public Supplier() {
    }

    public Supplier(int supplierID, String supplierName, String address, String contactNo) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.contactNo = contactNo;
        this.address = address;
    }

    public Supplier(String supplierName, String addedDate, String contactNo, String address, int status) {
        this.supplierName = supplierName;
        this.addedDate = addedDate;
        this.status = status;
        this.contactNo = contactNo;
        this.address = address;
    }

    public Supplier(int supplierID) {
        this.supplierID = supplierID;

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
     * @return the supplierName
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * @param supplierName the supplierName to set
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    @Override
    public String toString() {
        if (supplierName == null) {
            return "";
        } else {
            return this.supplierName;
        }
        //To change body of generated methods, choose Tools | Templates.
    }

}
