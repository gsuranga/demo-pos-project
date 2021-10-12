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
public class SearchCategory {

    private int categoryID;
    private String categoryName;
    private String description;
    private String addedDate;
    private String addedTime;
    private int status;

    public SearchCategory() {
    }

    public SearchCategory(String categoryName, String description, String addedDate, String addedTime, int status) {
        this.categoryName = categoryName;
        this.description = description;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
    }

    /**
     * @return the categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    @Override
    public String toString() {
        if (categoryName == null) {
            return "";
        } else {
            return this.categoryName;
        }
        //To change body of generated methods, choose Tools | Templates.
    }

}
