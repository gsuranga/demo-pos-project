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
import javax.swing.table.DefaultTableModel;
import models.PaymentSupplier;
/**
 *
 * @author HP2000
 */
public class PaymentSupplierController {
    
     public static int addSupplierPayment(PaymentSupplier payment) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_paymentsupplier (invoice_no,supplier_name,date,time,amount,remarks,status) values (?,?,?,?,?,?,?)";
        Object[] row = {payment.getInvoicenumber(),payment.getSupplier(),payment.getDate(),payment.getTime(),payment.getAmount(),payment.getRemarks(),payment.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    
    
    public static void loadAllSupplierPayment(DefaultTableModel payementSuppModelDtm) throws ClassNotFoundException, SQLException {

        String sql = "select supplier_ID, invoice_no, supplier_name,date,time, round(amount,2) as amount, remarks from tbl_paymentsupplier where status = 1 order by supplier_ID asc";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[7];
        int rowCount =payementSuppModelDtm.getRowCount();
        if (rowCount >= 0) {
            payementSuppModelDtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 7; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            payementSuppModelDtm.addRow(tableRow);

        }

    }
    
     public static int deleteSupplierToPayment(PaymentSupplier payment) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update tbl_paymentsupplier set status = 0 where supplier_ID = ?";
        Object[] row = {payment.getSupplierID()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
      public static int updateSupplierToPayment(PaymentSupplier payment) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update tbl_paymentsupplier set invoice_no = ?, supplier_name = ?, date =?, amount = ?, remarks = ? where status = 1 and supplier_ID = ?";
      //  String query = "update tbl_paymentsupplier set invioce_no = ?,supplier_name = ?,date = ?,amount = ?,remarks = ? where status = 1 and supplier_ID = ?";
        Object[] row =  {payment.getInvoicenumber(),payment.getSupplier(),payment.getDate(),payment.getAmount(),payment.getRemarks(),payment.getSupplierID()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
}
