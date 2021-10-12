/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import models.CustomerType;

/**
 *
 * @author user
 */
public class CustomerTypeController {

    public static int addNewCustomerType(CustomerType cType) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_customer_type (customer_type_name,customer_type_code,added_date,added_time,status) values (?,?,?,?,?)";
        Object[] row = {cType.getCustomerTypeName(), cType.getCustomerTypeCode(), cType.getAddedDate(), cType.getAddedTime(), cType.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    
     public static int updateCustomerType(CustomerType customerType) throws ClassNotFoundException, SQLException {
        String query = "update tbl_customer_type set  customer_type_name = '" + customerType.getCustomerTypeName()+ "', customer_type_code = '" + customerType.getCustomerTypeCode()+ "' where  customer_type_id = '" + customerType.getCustomerTypeID()+ "'";
        
        int value = DBHandler.setData(query);
        return value;
    }

    public static void loadAllCustomerTypes(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tbl_customer_type where status = 1";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[3];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {
            for (int i = 0; i < 3; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static int deleteCustomerType(CustomerType cType) throws ClassNotFoundException, SQLException {
        String sql = "update tbl_customer_type set status = '0' where customer_type_id = '" + cType.getCustomerTypeID() + "'";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static ResultSet getCustomerTypeData(String searchFor, String searchValue) throws ClassNotFoundException, SQLException {
        String sql = "Select * from tbl_customer_type where " + searchFor + "='" + searchValue + "' and status=1";
        ResultSet data = DBHandler.getData(sql);
        return data;
    }
}
