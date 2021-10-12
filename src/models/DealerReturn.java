/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 deliver order status
 3- pending
 2-updated
 1-completed
 0-deleted
 */
package models;

/**
 *
 * @author SHDINESH
 */
public class DealerReturn {

    private int returnID;
    private String returnNumber;
    private int deliverOrderID;
    private String returnDate;
    private String returnTime;
    private String timeStamp;
    private int admintReturnID;
    private int status;

    public DealerReturn(int deliverOrderID, String returnDate, String returnTime, int status) {
        this.deliverOrderID = deliverOrderID;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.status = status;
    }

    public DealerReturn() {
    }

    /**
     * @return the returnID
     */
    public int getReturnID() {
        return returnID;
    }

    /**
     * @param returnID the returnID to set
     */
    public void setReturnID(int returnID) {
        this.returnID = returnID;
    }

    /**
     * @return the returnNumber
     */
    public String getReturnNumber() {
        return returnNumber;
    }

    /**
     * @param returnNumber the returnNumber to set
     */
    public void setReturnNumber(String returnNumber) {
        this.returnNumber = returnNumber;
    }

    /**
     * @return the deliverOrderID
     */
    public int getDeliverOrderID() {
        return deliverOrderID;
    }

    /**
     * @param deliverOrderID the deliverOrderID to set
     */
    public void setDeliverOrderID(int deliverOrderID) {
        this.deliverOrderID = deliverOrderID;
    }

    /**
     * @return the returnDate
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the returnTime
     */
    public String getReturnTime() {
        return returnTime;
    }

    /**
     * @param returnTime the returnTime to set
     */
    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * @return the admintReturnID
     */
    public int getAdmintReturnID() {
        return admintReturnID;
    }

    /**
     * @param admintReturnID the admintReturnID to set
     */
    public void setAdmintReturnID(int admintReturnID) {
        this.admintReturnID = admintReturnID;
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
     * @return the timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
