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
import models.Brand;

/**
 *
 * @author user
 */
public class BrandController {

    public static int addNewBrand(Brand brand) throws ClassNotFoundException, SQLException {

        Connection con = DBConnection.getConnection();
        con.setAutoCommit(false);
        String query = "insert into itembrand (BrandName, brandCode, Description, status) values ('" + brand.getBrandName() + "','" + brand.getBrandCode() + "','" + brand.getDesc() + "','" + brand.getStatus() + "')";
        int value = DBHandler.setData(query);
        con.commit();
        return value;
    }

    public static int updateBrand(Brand brand) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update itembrand set BrandName = '" + brand.getBrandName() + "',  Description= '" + brand.getDesc() + "',BrandCode = '" + brand.getBrandCode() + "' where BrandID ='" + brand.getBrandID() + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static void loadAllItemBrands(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select * from itembrand where status = '1'";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[4];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 4; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static int deleteItemBrands(Brand brand) throws ClassNotFoundException, SQLException {

        String sql = "update itembrand set status = '0' where brandID = '" + brand.getBrandID() + "'";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static int getBrandIDforBrandName(String itemBrandName) throws ClassNotFoundException, SQLException {

        String query = "select BrandID from ItemBrand where BrandName = '" + itemBrandName + "'";
        ResultSet rst = DBHandler.getData(query);
        int itemBrandID = 0;
        while (rst.next()) {
            itemBrandID = rst.getInt(1);
        }
        return itemBrandID;
    }

    public static String getBrandNameForBrandID(int itemBrandID) throws ClassNotFoundException, SQLException {

        String query = "select BrandName from ItemBrand where BrandID = '" + itemBrandID + "'";
        ResultSet rst = DBHandler.getData(query);
        String itemBrandName = "";
        while (rst.next()) {
            itemBrandName = rst.getString(1);
        }
        return itemBrandName;
    }

    public static ResultSet getAllBrandNames() throws ClassNotFoundException, SQLException {
        String query = "select BrandID,BrandName from itembrand where status = 1 order by BrandID";
        ResultSet rst = DBHandler.getData(query);
        return rst;
    }

}
