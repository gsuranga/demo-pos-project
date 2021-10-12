/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author user-pc
 */
public class Chequebank {
 
    private int modelID;
    private String bankName;
    private String addedDate;
    private String description;
    private int status;
    
     public Chequebank() {
    }

    public Chequebank(String bankName, String addedDate, String description, int status) {
        this.bankName = bankName;
        this.addedDate = addedDate;
        this.description = description;
        this.status = status;
    }

   

    /**
     * @return the modelID
     */
    public int getModelID() {
        return modelID;
    }

    /**
     * @param modelID the modelID to set
     */
    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    /**
     * @return the modelName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
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
     * @return the addedTime
     */
   

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
/*
    @Override
    public String toString() {
        if (bankName == null) {
            return "";
        } else {
            return this.bankName;
        }
    }*/
}

