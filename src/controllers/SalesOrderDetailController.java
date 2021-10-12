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
import models.SalesOrderDetail;

/**
 *
 * @author user
 */
public class SalesOrderDetailController {

    public static int addNewSalesOrderDetail(SalesOrderDetail orderDetail) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into salesOrderDetail (orderid,itemid,quantity,discount,time,date,unit_price,buying_price,amount,discount_amount,stock_id,status,salesComi_status) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object ob[] = {orderDetail.getSalesOrderID(), orderDetail.getItemID(), orderDetail.getQuantity(), orderDetail.getDiscount(), orderDetail.getAddedTime(), orderDetail.getAddedDate(), orderDetail.getUnitPrice(), orderDetail.getBuyingPrice(), orderDetail.getAmount(), orderDetail.getDiscountAmount(), orderDetail.getStockID(), orderDetail.getStatus(),orderDetail.getSellsComiStatus()};
        int value = DBHandler.setDataToDataBase(ob, con, query);
        return value;
    }

    public static int updateSalesOrderDetail(SalesOrderDetail orderDetail) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "update salesorderdetail set orderid = ?, itemid = ?, quantity = ?, discount = ?, time = ?, date = ?, unit_price = ?, buying_price = ?, amount = ?, discount_amount = ?, status = 2 where detailid = 1";
        Object ob[] = {orderDetail.getSalesOrderID(), orderDetail.getItemID(), orderDetail.getQuantity(), orderDetail.getDiscount(), orderDetail.getAddedTime(), orderDetail.getAddedDate(), orderDetail.getUnitPrice(), orderDetail.getBuyingPrice(), orderDetail.getAmount(), orderDetail.getDiscountAmount(), orderDetail.getStatus()};
        int value = DBHandler.setDataToDataBase(ob, con, sql);
        return value;
    }

    public static int deleteSalesOrderDetail(int detailID) throws ClassNotFoundException, SQLException {
        String sql = "update salesorderdetail set status = 2 where detailid = " + detailID;
        int value = DBHandler.setData(sql);
        return value;
    }

    public static void loadAllSalesOrderDetails(DefaultTableModel dtm, int salesOrderID) throws ClassNotFoundException, SQLException {

        String sql = "Select item.ItemID, item.item_part_no, item.Description, round(salesorderdetail.quantity,1), round(salesorderdetail.discount_amount, 2) as discount, round(salesorderdetail.unit_price, 2) as unit_price, round(salesorderdetail.amount, 2) as amount,commision from item inner join salesorderdetail ON item.ItemID = salesorderdetail.ItemID where salesorderdetail.status = 1 and orderid = " + salesOrderID;
        //String sql = "Select item.ItemID, item.item_part_no, item.Description, round(salesorderdetail.quantity, 1), round(salesorderdetail.discount_amount, 2) as discount, round(salesorderdetail.unit_price, 2) as unit_price, round(salesorderdetail.amount, 2) as amount from item inner join salesorderdetail ON item.ItemID = salesorderdetail.ItemID left join (select tcrd.item_id, tcrd.return_quantity from tbl_customer_return tcr inner join tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id where tcr.status = 1 and tcr.sales_order_id = " + salesOrderID + ") tcrds ON salesorderdetail.itemid = tcrds.item_id where salesorderdetail.status = 1 and tcrds.return_quantity is null and orderid = " + salesOrderID;
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[8];
        int rowCount = dtm.getRowCount();
        dtm.setRowCount(0);
        double amount;
       
        while (rst.next()) {
            for (int i = 0; i < 8; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static int deleteAllFromOrderDetail(int salesOrderID) throws ClassNotFoundException, SQLException {
        String query = "delete from salesorderdetail where SalesOrderID = '" + salesOrderID + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static ResultSet getPartsDataToUpdate(int soID) throws ClassNotFoundException, SQLException {
        String sql = "Select sod.detailid,it.ItemID, it.item_part_no, it.Description, round(sod.quantity,1), round(sod.discount_amount,2) as discount_total, round(sod.unit_price, 2) as unit_price, round(sod.amount, 2) as amount,it.BrandID,it.key_id,sod.buying_price,round(sod.discount, 2) as discount,sod.stock_id from item it inner join salesorderdetail sod ON it.ItemID = sod.ItemID where sod.status = 1 and sod.orderid = " + soID;
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static ResultSet getServiceDataToUpdate(int soID) throws ClassNotFoundException, SQLException {
        String sql = "select sd.service_detail_id,sd.service_id, sv.service_code, sv.service_name, round(sd.service_qty,1),round(sd.discount_total,2) , round(sd.current_price, 2), round(sd.service_amount, 2), sd.discount_type,done_by, round(sd.discount_amount, 2) from tbl_service_detail sd inner join tbl_service sv ON sd.service_id = sv.service_id where sd.sales_order_id = " + soID + " and sd.status = 1";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

}
