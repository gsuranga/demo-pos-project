/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.SQLException;
import models.EstimateDetails;

/**
 *
 * @author HP2000
 */
public class EstimateDetailsController {
    
     public static int addNewEstimateDetails(EstimateDetails estimateDetails) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_estimate_details (Estimate_id,itemid,quantity,discount,time,date,unit_price,buying_price,amount,discount_amount,status) values (?,?,?,?,?,?,?,?,?,?,?)";
        Object ob[] = {estimateDetails.getEstimateid(),estimateDetails.getItemid(),estimateDetails.getQuantity(),estimateDetails.getDiscount(),estimateDetails.getAddedTime(),estimateDetails.getDate(),estimateDetails.getUnitprice(),estimateDetails.getBuyingprice(),estimateDetails.getDetailAmount(),estimateDetails.getDiscountamount(),estimateDetails.getStatus()};
        int value = DBHandler.setDataToDataBase(ob, con, query);
        return value;
    }
    
}
