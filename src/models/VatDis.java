/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Iresha Lakmali
 */
public class VatDis {
    
    private int id;
    private String account_no;
    private String vat;
    private String discount;
    private String status;
   

    public VatDis() {
    }

    public VatDis(int id, String account_no, String vat, String discount, String status) {
        this.id = id;
        this.account_no = account_no;
        this.vat = vat;
        this.discount = discount;
        this.status = status;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the account_no
     */
    public String getAccount_no() {
        return account_no;
    }

    /**
     * @param account_no the account_no to set
     */
    public void setAccount_no(String account_no) {
        this.account_no = account_no;
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
     * @return the discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }


    
    
}
