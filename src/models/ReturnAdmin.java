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
public class ReturnAdmin {

    private int returnAdminID;
    private int dealerReturnID;
    private String returnNoteNo;
    private String acceptedDate;
    private String acceptedTime;
    private String timeStamp;
    private int status;

    public ReturnAdmin() {
    }

    /**
     * @return the returnAdminID
     */
    public int getReturnAdminID() {
        return returnAdminID;
    }

    /**
     * @param returnAdminID the returnAdminID to set
     */
    public void setReturnAdminID(int returnAdminID) {
        this.returnAdminID = returnAdminID;
    }

    /**
     * @return the dealerReturnID
     */
    public int getDealerReturnID() {
        return dealerReturnID;
    }

    /**
     * @param dealerReturnID the dealerReturnID to set
     */
    public void setDealerReturnID(int dealerReturnID) {
        this.dealerReturnID = dealerReturnID;
    }

    /**
     * @return the acceptedDate
     */
    public String getAcceptedDate() {
        return acceptedDate;
    }

    /**
     * @param acceptedDate the acceptedDate to set
     */
    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    /**
     * @return the acceptedTime
     */
    public String getAcceptedTime() {
        return acceptedTime;
    }

    /**
     * @param acceptedTime the acceptedTime to set
     */
    public void setAcceptedTime(String acceptedTime) {
        this.acceptedTime = acceptedTime;
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
     * @return the returnNoteNo
     */
    public String getReturnNoteNo() {
        return returnNoteNo;
    }

    /**
     * @param returnNoteNo the returnNoteNo to set
     */
    public void setReturnNoteNo(String returnNoteNo) {
        this.returnNoteNo = returnNoteNo;
    }

}
