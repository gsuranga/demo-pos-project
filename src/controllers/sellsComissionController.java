/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.sellsCommi;
import models.sellsCommiDetail;

/**
 *
 * @author user-pc
 */
public class sellsComissionController {
   
    public static boolean addNewSellsCommision(sellsCommi sellscommi, ArrayList<sellsCommiDetail> scd) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

       
        boolean detailcheck = false;
        String sql = "insert into tbl_sellscommision(added_date,added_time,amount,status,paid_status) values(?,?,?,?,?)";
        Object data[] = {sellscommi.getAddedDate(),sellscommi.getAddedTime(),sellscommi.getAmount(),sellscommi.getStatus(),sellscommi.getPaidStatus()};
        int setData = DBHandler.setDataToDataBase(data, con, sql);
        if (setData > 0) {
            String sql2 = "select max(sells_id) as sid from tbl_sellscommision";
            ResultSet rst = DBHandler.getData(sql2);
            rst.next();
            int tcr = rst.getInt("sid");
            for (sellsCommiDetail ors : scd) {
                String detailq = "Insert INTO tbl_sellscommisiondetails(sells_id,ItemID,OrderID,employee_id,commision,status)values(?,?,?,?,?,?)";
                Object detailData[] = {tcr,ors.getItemID(),ors.getOrderID(),ors.getEmpId(),ors.getCommision(),ors.getStatus()};
                int setData1 = DBHandler.setDataToDataBase(detailData, con, detailq);
                if (setData1 > 0) {
                    detailcheck = true;
                } else {
                    detailcheck = false;
                    break;
                }

            }
            return detailcheck;
        } else {
            return false;
        }

    }
    
     public static void loadAllPendingSellsCommi1(DefaultTableModel pendingSellsComi) throws ClassNotFoundException, SQLException {
        String sql = "select it.ItemID,sod.OrderID,so.employee_id,it.item_part_no,it.Description,emp.employee_name,sod.date,sod.time,round(ifnull((sod.amount * it.commision / 100),0),2)as commision,false,sod.detailid from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join item it ON sod.itemid = it.ItemID inner join tbl_employee emp ON so.employee_id = emp.employee_id where  sod.salesComi_status= 0 and  emp.status=1 order by sod.date asc";
        ResultSet rst = DBHandler.getData(sql);
          pendingSellsComi.setRowCount(0);

        while (rst.next()) {
            Object obj[] = {rst.getInt(1), rst.getInt(2), rst.getInt(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getString(7),rst.getString(8),rst.getString(9), false,rst.getInt(11)};
            pendingSellsComi.addRow(obj);
        }
        
     }
         
    
    //search btn
    public static void SearchSellsCommi(DefaultTableModel pendingSellsComi ,String startDateComi, String endDateComi) throws ClassNotFoundException, SQLException {
        String sql = "select it.ItemID,sod.OrderID,so.employee_id,it.item_part_no,it.Description,emp.employee_name,sod.date,sod.time,round(ifnull((sod.amount * it.commision / 100),0),2)as commision,false,sod.detailid from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join item it ON sod.itemid = it.ItemID inner join tbl_employee emp ON so.employee_id = emp.employee_id where sod.date between '" + startDateComi + "' and '" + endDateComi + "' and sod.salesComi_status= 0 and  emp.status=1 order by sod.date asc";
       // String sql="select it.ItemID,sod.OrderID,so.employee_id,it.item_part_no,it.Description,emp.employee_name,sod.date,sod.time,round(ifnull((sod.amount * it.commision / 100), 0),2)as commision,false,sod.detailid  from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join item it ON sod.itemid = it.ItemID inner join tbl_employee emp ON so.employee_id = emp.employee_id where sod.date between '" + startDateComi + "' and '" + endDateComi + "' and sod.salesComi_status= 0 and  emp.status=1 order by sod.date asc";
        ResultSet rst = DBHandler.getData(sql);
        pendingSellsComi.setRowCount(0);

        while (rst.next()) {
            Object obj[] = {rst.getInt(1), rst.getInt(2), rst.getInt(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getString(7),rst.getString(8),rst.getString(9), false,rst.getInt(11)};
            pendingSellsComi.addRow(obj);
        }
//        String tableRow[] = new String[9];
//        int rowCount = dtm.getRowCount();
//        if (rowCount >= 0) {
//            dtm.setRowCount(0);
//        }
//
//        while (data.next()) {
//            for (int i = 0; i < 9; i++) {
//
//                tableRow[i] = data.getString(i + 1);
//            }
//
//            dtm.addRow(tableRow);
//        }

    }
    public static int updateChequeStatusByChequeNumber(int cheque_status, String cheque_id) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "UPDATE `demolanka`.`cheque` SET `cheque_status` = '" + cheque_status + "' WHERE `cheque`.`id` = '" + cheque_id + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;
    }

public static int UpdatePaidStatusSellComi(int salesComi_Status,int detailid ) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection(); 
        String query ="UPDATE `demolanka`.`salesorderdetail` SET `salesComi_Status` = '" + salesComi_Status+ "' WHERE `salesorderdetail`.`detailid` = '" + detailid + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;

    }

    
}
