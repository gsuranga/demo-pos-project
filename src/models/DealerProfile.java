/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author shdinesh.99
 */
public class DealerProfile {

    private int dealerID;
    private String dealerName;
    private String dealerAccountNo;
    private double dealerDiscount;
    private double currentVat;
    private String salesOfficerAccountNo;
    private String salesOfficerName;
    private String dealerMobileNo;
    private String serverURL;
    private String address;
    private String emailAddress;
    private double discountPresentage;
    private int status;

    /**
     * @return the dealerID
     */
    public int getDealerID() {
        return dealerID;
    }

    /**
     * @param dealerID the dealerID to set
     */
    public void setDealerID(int dealerID) {
        this.dealerID = dealerID;
    }

    /**
     * @return the dealerName
     */
    public String getDealerName() {
        return dealerName;
    }

    /**
     * @param dealerName the dealerName to set
     */
    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    /**
     * @return the dealerAccountNo
     */
    public String getDealerAccountNo() {
        return dealerAccountNo;
    }

    /**
     * @param dealerAccountNo the dealerAccountNo to set
     */
    public void setDealerAccountNo(String dealerAccountNo) {
        this.dealerAccountNo = dealerAccountNo;
    }

    /**
     * @return the dealerDiscount
     */
    public double getDealerDiscount() {
        return dealerDiscount;
    }

    /**
     * @param dealerDiscount the dealerDiscount to set
     */
    public void setDealerDiscount(double dealerDiscount) {
        this.dealerDiscount = dealerDiscount;
    }

    /**
     * @return the currentVat
     */
    public double getCurrentVat() {
        return currentVat;
    }

    /**
     * @param currentVat the currentVat to set
     */
    public void setCurrentVat(double currentVat) {
        this.currentVat = currentVat;
    }

    /**
     * @return the salesOfficerAccountNo
     */
    public String getSalesOfficerAccountNo() {
        return salesOfficerAccountNo;
    }

    /**
     * @param salesOfficerAccountNo the salesOfficerAccountNo to set
     */
    public void setSalesOfficerAccountNo(String salesOfficerAccountNo) {
        this.salesOfficerAccountNo = salesOfficerAccountNo;
    }

    /**
     * @return the salesOfficerName
     */
    public String getSalesOfficerName() {
        return salesOfficerName;
    }

    /**
     * @param salesOfficerName the salesOfficerName to set
     */
    public void setSalesOfficerName(String salesOfficerName) {
        this.salesOfficerName = salesOfficerName;
    }

    /**
     * @return the dealerMobileNo
     */
    public String getDealerMobileNo() {
        return dealerMobileNo;
    }

    /**
     * @param dealerMobileNo the dealerMobileNo to set
     */
    public void setDealerMobileNo(String dealerMobileNo) {
        this.dealerMobileNo = dealerMobileNo;
    }

    /**
     * @return the serverURL
     */
    public String getServerURL() {
        return serverURL;
    }

    /**
     * @param serverURL the serverURL to set
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
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
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the discountPresentage
     */
    public double getDiscountPresentage() {
        return discountPresentage;
    }

    /**
     * @param discountPresentage the discountPresentage to set
     */
    public void setDiscountPresentage(double discountPresentage) {
        this.discountPresentage = discountPresentage;
    }

}
