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
public class LoginHistroy {
  
    private int lid;
    private String date;
    private String time;
    private String endTime;

    public LoginHistroy() {
    }

    public LoginHistroy(int id, int lid, String date, String time,String endTime) {
       
        this.lid = lid;
        this.date = date;
        this.time = time;
        this.endTime=endTime;
    }

    /**
     * @return the id
     */
   

    /**
     * @param id the id to set
     */
  
    /**
     * @return the lid
     */
    public int getLid() {
        return lid;
    }

    /**
     * @param lid the lid to set
     */
    public void setLid(int lid) {
        this.lid = lid;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    
    
}
