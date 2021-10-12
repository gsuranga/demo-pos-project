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
public class DebtDetail {

    private int detailID;
    private int debtID;
    private String paymentDate;
    private double paidAmont;
   // private int paidType;
    private int status;
    

    public DebtDetail() {
    }

    public DebtDetail(int debtID, String paymentDate, double paidAmont, int status) {
        this.debtID = debtID;
        this.paymentDate = paymentDate;
        this.paidAmont = paidAmont;
       
        this.status = status;
    }

    

    /**
     * @return the detailID
     */
    public int getDetailID() {
        return detailID;
    }

    /**
     * @param detailID the detailID to set
     */
    public void setDetailID(int detailID) {
        this.detailID = detailID;
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
     * @return the paymentDate
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * @param paymentDate the paymentDate to set
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the paidAmont
     */
    public double getPaidAmont() {
        return paidAmont;
    }

    /**
     * @param paidAmont the paidAmont to set
     */
    public void setPaidAmont(double paidAmont) {
        this.paidAmont = paidAmont;
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
