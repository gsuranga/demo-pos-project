/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import models.Supplier;

/**
 *
 * @author user
 */
public class SupplierController {

    public static int addNewSupplier(Supplier supplier) throws ClassNotFoundException, SQLException {

        String query = "insert into supplier (SupplierID,SupplierName,AddedDate,Address,ContactNo,Status) values ('" + supplier.getSupplierID() + "','" + supplier.getSupplierName() + "','" + supplier.getAddedDate() + "','" + supplier.getAddress() + "','" + supplier.getContactNo() + "','" + supplier.getStatus() + "')";
        int value = DBHandler.setData(query);
        return value;
    }

    public static int deleteSupplier(Supplier supplier) throws ClassNotFoundException, SQLException {
        String sql = "update supplier set status = '0' where supplierid = '" + supplier.getSupplierID() + "'";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static void loadAllSuppliers(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select * from supplier where status = '1'";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[5];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {
            for (int i = 0; i < 5; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static int updateSupplier(Supplier supplier) throws ClassNotFoundException, SQLException {
        String query = "update supplier set  SupplierName = '" + supplier.getSupplierName() + "', Address = '" + supplier.getAddress() + "',ContactNo= '" + supplier.getContactNo() + "' where  supplierid = '" + supplier.getSupplierID() + "'";
        int value = DBHandler.setData(query);
        return value;
    }
//cmb sup
    public static int getSupplierIDforName(String supplierName) throws ClassNotFoundException, SQLException {

        String query = "select supplierid from supplier where SupplierName ='" + supplierName + "'";
        ResultSet rst = DBHandler.getData(query);
        int supplierID = 0;
        while (rst.next()) {
            supplierID = rst.getInt(1);
        }
        return supplierID;
    }
    
     //loard for ABC
    public static int getSupplierIDforABC(String supplierName) throws ClassNotFoundException, SQLException {

        String query = "select supplierid from supplier where SupplierName ='" + supplierName + "'";
        ResultSet rst = DBHandler.getData(query);
        int supplierID = 0;
        while (rst.next()) {
            supplierID = rst.getInt(1);
        }
        return supplierID;
    }
    
    ////Movement
    public static int getSupplierIDforNameMovementSummarySupplier(String supplierName) throws ClassNotFoundException, SQLException {

        String query = "select supplierid from supplier where SupplierName ='" + supplierName + "'";
        ResultSet rst = DBHandler.getData(query);
        int supplierID = 0;
        while (rst.next()) {
            supplierID = rst.getInt(1);
        }
        return supplierID;
    }
    
    
    
    /////
    
    
    ////fast /*
//    public static int getSupplierIDforNameFastMoving() throws ClassNotFoundException, SQLException {
//
//        String query = "select supplierid from supplier where SupplierName ='" + supplierName + "'";
//        ResultSet rst = DBHandler.getData(query);
//        int supplierID = 0;
//        while (rst.next()) {
//            supplierID = rst.getInt(1);
//        }
//        return supplierID;
//    }
    
    ////
    
    
   

    public static String getSupplierNameforID(int supplierid) throws ClassNotFoundException, SQLException {

        String query = "select supplierName from supplier where supplierid ='" + supplierid + "'";
        ResultSet rst = DBHandler.getData(query);
        String supplierName = "";
        while (rst.next()) {
            supplierName = rst.getString(1);
        }
        return supplierName;
    }
//cmb sup
    public static ResultSet loadAllSuppliers() throws ClassNotFoundException, SQLException {
        String query = "select SupplierID,SupplierName from supplier where status=1 order by SupplierID";
        ResultSet rst = DBHandler.getData(query);
        return rst;

    }
//cmb sup
    public static void fillSupplierNames(JComboBox cmb) throws ClassNotFoundException, SQLException {
        ResultSet rstSupplier = SupplierController.loadAllSuppliers();
        cmb.removeAllItems();
        while (rstSupplier.next()) {
            Supplier supplier = new Supplier();
            cmb.addItem(rstSupplier.getString(2));
        }
        
    }
    ///loard supier name for ABC
    public static void fillSupplierrNamesABC(JComboBox cmb) throws ClassNotFoundException, SQLException {
        String query = "select SupplierName,SupplierID from supplier where status=1 order by SupplierID";
                       
        ResultSet rstItem = DBHandler.getData(query);
        cmb.removeAllItems();
        while (rstItem.next()) {

            cmb.addItem(rstItem.getString(1));
            //lbl.setText(rstItem.getString(2));

        }
    }

}
