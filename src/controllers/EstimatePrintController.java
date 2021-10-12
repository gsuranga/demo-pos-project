/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.EstimatePrint;



/**
 *
 * @author HP2000
 */
public class EstimatePrintController {
    
    public static int AddEstimatePrint(EstimatePrint estimatePrint) throws ClassNotFoundException, SQLException {
        String query = "insert into tbl_estimatePrint (Estimate_id,estimate_order_no,estimate_date,customer_id,time,user_id,Amount,labour_amount,employee_id,total_discount,status) values ('"+ estimatePrint.getEstimateId() +"','"+ estimatePrint.getEstimateorderno() +"','"+ estimatePrint.getEstimatedate() +"', '"+ estimatePrint.getCustomerid() +"','"+estimatePrint.getTime()+"','"+ estimatePrint.getUserid() +"','"+ estimatePrint.getAmount() +"','"+ estimatePrint.getLabouramont() +"','"+ estimatePrint.getEmployeeid() +"','"+ estimatePrint.getTotaldiscount() +"','"+estimatePrint.getStatus()+"')";
        int value = DBHandler.setData(query);
        return value;
    }
  public static int getMaxEstimateID() throws ClassNotFoundException, SQLException {
        String query = "select last_insert_id() as last_id from tbl_estimatePrint";
        ResultSet rst = DBHandler.getData(query);
        boolean next = rst.next();
        int EstimateId = rst.getInt(1);
        return EstimateId;
    }

    
    
}
