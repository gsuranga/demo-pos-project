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
import models.Expence;

/**
 *
 * @author shdinesh.99
 */
public class ExpenceController {

    public static int addNewExpence(Expence expence) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_expences (description,amount,expence_date,added_date,added_time,status) values (?,?,?,?,?,?)";
        Object[] row = {expence.getDescription(), expence.getAmount(), expence.getExpenceDate(), expence.getAddedDate(), expence.getAddedTime(), expence.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static int updateExpence(Expence expence) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update tbl_expences set description = ?, amount = ?, expence_date = ? where status = 1 and expences_id = ?";
        Object[] row = {expence.getDescription(), expence.getAmount(), expence.getExpenceDate(), expence.getExpenceID()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static void SearchExpences(DefaultTableModel dtm, String startDate, String endDate) throws ClassNotFoundException, SQLException {

        String sql = "select expences_id, description, round(amount,2) as amount, expence_date, added_date from tbl_expences where expence_date between '" + startDate + "' and '" + endDate + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[5];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 5; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static void loadAllExpences(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select expences_id, description,  round(amount,2) as amount, expence_date, added_date from tbl_expences where status = 1 order by expences_id desc";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[5];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 5; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static int deleteExpence(Expence expence) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update tbl_expences set status = 0 where expences_id = ?";
        Object[] row = {expence.getExpenceID()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static double calculateTotalExpence(String date) throws ClassNotFoundException, SQLException {
        String sql = "select round(coalesce(sum(amount),0),2) as total_expence from tbl_expences where expence_date = '" + date + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        double value = rst.getDouble(1);
        return value;
    }
}
