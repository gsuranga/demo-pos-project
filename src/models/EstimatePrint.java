/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author HP2000
 */
public class EstimatePrint {
    
    private int EstimateId;
    private String estimateorderno;
    private String estimatedate;
    private int customerid;
    private String time;
    private int userid;
    private Double amount;
    private Double labouramont;
    private int employeeid;
    private Double totaldiscount;
   private int status;
   
     public EstimatePrint() {
    }

    public int getEstimateId() {
        return EstimateId;
    }

    public void setEstimateId(int EstimateId) {
        this.EstimateId = EstimateId;
    }

   public EstimatePrint(String estimateorderno,String estimatedate,int customerid,String time,int userid,Double amount,Double labouramont,int employeeid,Double totaldiscount, int status) {
       
        this.estimateorderno = estimateorderno;
        this.estimatedate = estimatedate;
        this.customerid = customerid;
        this.time = time;
        this.userid = userid;
        this.amount = amount;
        this.labouramont = labouramont;
        this.employeeid = employeeid;
        this.totaldiscount = totaldiscount;
        this.status = status;
    }

    public String getEstimateorderno() {
        return estimateorderno;
    }

    public void setEstimateorderno(String estimateorderno) {
        this.estimateorderno = estimateorderno;
    }

    

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getEstimatedate() {
        return estimatedate;
    }

    public void setEstimatedate(String estimatedate) {
        this.estimatedate = estimatedate;
    }

    

    public Double getLabouramont() {
        return labouramont;
    }

    public void setLabouramont(Double labouramont) {
        this.labouramont = labouramont;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTotaldiscount() {
        return totaldiscount;
    }

    public void setTotaldiscount(Double totaldiscount) {
        this.totaldiscount = totaldiscount;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
     
}
