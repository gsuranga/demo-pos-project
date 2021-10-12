/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.SQLException;
import models.SalesCounter;

/**
 *
 * @author user-pc
 */
public class SalesCounterController {
    
     public static int addNewSales(SalesCounter salesCounter)  throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_counter(employee_id,addedDate,addedTime,date,amount,discount,commision) values (?,?,?,?,?,?,?)";
           Object[] row ={salesCounter.getEmployeeID(),salesCounter.getAddedDate(),salesCounter.getAddedTime(),salesCounter.getDate(),salesCounter.getAmount(),salesCounter.getDiscount(),salesCounter.getCommistion()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    
}
