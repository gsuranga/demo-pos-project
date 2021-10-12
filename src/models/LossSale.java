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
public class LossSale {

    private int partID;
    private double lossQty;
    private int lossType;
    private String lossDate;
    private int status;

    public LossSale(int partID, double lossQty, int lossType, String lossDate, int status) {
        this.partID = partID;
        this.lossQty = lossQty;
        this.lossType = lossType;
        this.lossDate = lossDate;
        this.status = status;
    }

    public LossSale() {
    }

    /**
     * @return the partID
     */
    public int getPartID() {
        return partID;
    }

    /**
     * @param partID the partID to set
     */
    public void setPartID(int partID) {
        this.partID = partID;
    }

    /**
     * @return the lossQty
     */
    public double getLossQty() {
        return lossQty;
    }

    /**
     * @param lossQty the lossQty to set
     */
    public void setLossQty(double lossQty) {
        this.lossQty = lossQty;
    }

    /**
     * @return the lossType
     */
    public int getLossType() {
        return lossType;
    }

    /**
     * @param lossType the lossType to set
     */
    public void setLossType(int lossType) {
        this.lossType = lossType;
    }

    /**
     * @return the lossDate
     */
    public String getLossDate() {
        return lossDate;
    }

    /**
     * @param lossDate the lossDate to set
     */
    public void setLossDate(String lossDate) {
        this.lossDate = lossDate;
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
