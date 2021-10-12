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
public class ItemAlternative {

    private int alternativeID;
    private int itemID;
    private int alternativePartID;
    private String description;
    private String addedDate;
    private String addedTime;
    private int status;

    public ItemAlternative(int itemID, int alternativePartID, String description, String addedDate, String addedTime, int status) {
        this.itemID = itemID;
        this.alternativePartID = alternativePartID;
        this.description = description;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
    }

    /**
     * @return the alternativeID
     */
    public int getAlternativeID() {
        return alternativeID;
    }

    /**
     * @param alternativeID the alternativeID to set
     */
    public void setAlternativeID(int alternativeID) {
        this.alternativeID = alternativeID;
    }

    /**
     * @return the itemID
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the partNo
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the alternativePartID
     */
    public int getAlternativePartID() {
        return alternativePartID;
    }

    /**
     * @param alternativePartID the alternativePartID to set
     */
    public void setAlternativePartID(int alternativePartID) {
        this.alternativePartID = alternativePartID;
    }

}
