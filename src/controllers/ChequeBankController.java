/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import models.Chequebank;

/**
 *
 * @author user-pc
 */
public class ChequeBankController {
     public static int addNewBank(Chequebank chequebank) throws ClassNotFoundException, SQLException {
  /*add bank */
        Connection con = DBConnection.getConnection();
        String query = "insert into cheque_bank (bank_name,added_date,description,status) values (?,?,?,?)";
        Object[] row = {chequebank.getBankName(), chequebank.getAddedDate(), chequebank.getDescription(), chequebank.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    
      
   /*get detalis*/
      public static ResultSet getChequeDetalis(String where, String value) throws ClassNotFoundException, SQLException {
        String sql = "select * from cheque_bank where " + where + " = '" + value + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);

        return rst;
    }
       
       public static ResultSet getBanksList() throws ClassNotFoundException, SQLException {
        String sql = "select bank_name from cheque_bank where status = 1";
        ResultSet rst = DBHandler.getData(sql);

        return rst;
    } 
       
        public static void fillBankList(JComboBox cmb) throws ClassNotFoundException, SQLException {
        String query = "select bank_name from cheque_bank where status = 1";
        ResultSet rstItem = DBHandler.getData(query);
        cmb.removeAllItems();
        while (rstItem.next()) {
            cmb.addItem(rstItem.getString(1));
        }
    }
}
  
    

