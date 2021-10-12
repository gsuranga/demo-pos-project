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
public class ServerDetails {
    private String vat;
    private String disscount;

    public ServerDetails() {
    }

    public ServerDetails(String vat, String disscount) {
        this.vat = vat;
        this.disscount = disscount;
    }

    /**
     * @return the vat
     */
    public String getVat() {
        return vat;
    }

    /**
     * @param vat the vat to set
     */
    public void setVat(String vat) {
        this.vat = vat;
    }

    /**
     * @return the disscount
     */
    public String getDisscount() {
        return disscount;
    }

    /**
     * @param disscount the disscount to set
     */
    public void setDisscount(String disscount) {
        this.disscount = disscount;
    }
    
    
}
