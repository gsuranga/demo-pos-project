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
public class Brand {

    private int brandID;
    private String brandName;
    private String brandCode;
    private String desc;
    private int status;

    public Brand() {
    }

    public Brand(String brandCode, String brandName, String desc, int status, int brandID) {
        this.brandID = brandID;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.desc = desc;
        this.status = status;

    }

    public Brand(String brandCode, String brandName, String desc, int status) {
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.desc = desc;
        this.status = status;
    }

    public Brand(int brandID) {
        this.brandID = brandID;
    }

    /**
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * @return the brandCode
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * @param brandCode the brandCode to set
     */
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return the brandID
     */
    public int getBrandID() {
        return brandID;
    }

    /**
     * @param brandID the brandID to set
     */
    public void setBrandID(int brandID) {
        this.brandID = brandID;
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
        if (brandName == null) {
            return "";
        } else {
            return this.brandName;
        }
        //To change body of generated methods, choose Tools | Templates.
    }

}
