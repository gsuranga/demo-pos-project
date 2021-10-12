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
public class ReturnRep {

    private int returnRepID;
    private int dealerReturnId;
    private String acceptedDate;
    private String acceptedTime;
    private String rep;
    private String timeStamp;
    private int status;
    private String return_note_no;  ///hhh

    public ReturnRep() {
    }

    public String getReturn_note_no() {
        return return_note_no;
    }

    public void setReturn_note_no(String return_note_no) {
        this.return_note_no = return_note_no;
    }

    /**
     * @return the returnRepID
     */
    public int getReturnRepID() {
        return returnRepID;
    }

    /**
     * @param returnRepID the returnRepID to set
     */
    public void setReturnRepID(int returnRepID) {
        this.returnRepID = returnRepID;
    }

    /**
     * @return the dealerReturnId
     */
    public int getDealerReturnId() {
        return dealerReturnId;
    }

    /**
     * @param dealerReturnId the dealerReturnId to set
     */
    public void setDealerReturnId(int dealerReturnId) {
        this.dealerReturnId = dealerReturnId;
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
     * @return the rep
     */
    public String getRep() {
        return rep;
    }

    /**
     * @param rep the rep to set
     */
    public void setRep(String rep) {
        this.rep = rep;
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
    
    
}
