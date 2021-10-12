/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author insaf
 */
public class ReportSummery {
    private String brandname;
    private int brandid;
    

    public ReportSummery() {
    }

    public ReportSummery(String brandname, int brandid) {
        this.brandname = brandname;
        this.brandid = brandid;
    }

    /**
     * @return the brandname
     */
    public String getBrandname() {
        return brandname;
    }

    /**
     * @param brandname the brandname to set
     */
    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    /**
     * @return the brandid
     */
    public int getBrandid() {
        return brandid;
    }

    /**
     * @param brandid the brandid to set
     */
    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }
    
   
    
}
