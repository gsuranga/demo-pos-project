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
public class Estimate {
    private String searchkey;
    private String part_nu;
    private String description;
    private double qty;
    private double unitPrice;

    public Estimate() {
    }

    public Estimate(String searchkey, String part_nu, String description, double qty, double unitPrice) {
        this.searchkey = searchkey;
        this.part_nu = part_nu;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    /**
     * @return the searchkey
     */
    public String getSearchkey() {
        return searchkey;
    }

    /**
     * @param searchkey the searchkey to set
     */
    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
    }

    /**
     * @return the part_nu
     */
    public String getPart_nu() {
        return part_nu;
    }

    /**
     * @param part_nu the part_nu to set
     */
    public void setPart_nu(String part_nu) {
        this.part_nu = part_nu;
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
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the unitPrice
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    
    
}
