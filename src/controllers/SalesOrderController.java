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
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.SalesOrder;
import models.SalesOrderDetail;
import models.ServiceDetail;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class SalesOrderController {
    
/***add sales order**********/
    
    public static int addNewSalesOrder(SalesOrder salesOrder) throws ClassNotFoundException, SQLException {
        String query = "insert into salesorder (sales_order_no,OrderDate,CustomerID,vehicle_id,time,userid,amount,paid_amount,labour_amount,current_meter_reading,next_service_due,employee_id,total_discount,other_comments,Status,garge_commission) values ('" + salesOrder.getSalesorderNo() + "','" + salesOrder.getOrderDate() + "','" + salesOrder.getCustID() + "','" + salesOrder.getVehicleID() + "','" + salesOrder.getTime() + "','" + salesOrder.getUserID() + "','" + salesOrder.getAmount() + "','" + salesOrder.getPaidAmount() + "','" + salesOrder.getLabourAmount() + "','" + salesOrder.getCurrentMeterReading() + "','" + salesOrder.getNextServiceDue() + "','" + salesOrder.getEmployeeID() + "','" + salesOrder.getTotalDiscount() + "','" + salesOrder.getOtherComments() + "','" + salesOrder.getStatus() + "','" + salesOrder.getGarageCommission() + "' )";
        int value = DBHandler.setData(query);
        return value;
    }

    /******update sales order********/
    
    public static int updateSalesOrder(SalesOrder salesOrder) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update salesorder set sales_order_no = ?, OrderDate = ?, CustomerID = ?, vehicle_id = ?, time = ?, userid = ?, amount = ?, labour_amount = ?, paid_amount = ?, current_meter_reading = ?, next_service_due = ?, employee_id = ?, total_discount = ?,other_comments = ? ,status = ? where orderid = ?";
        Object row[] = {salesOrder.getSalesorderNo(), salesOrder.getOrderDate(), salesOrder.getCustID(), salesOrder.getVehicleID(), salesOrder.getTime(), salesOrder.getUserID(), salesOrder.getAmount(), salesOrder.getLabourAmount(), salesOrder.getPaidAmount(), salesOrder.getCurrentMeterReading(), salesOrder.getNextServiceDue(), salesOrder.getEmployeeID(), salesOrder.getTotalDiscount(), salesOrder.getOtherComments(), salesOrder.getStatus(), salesOrder.getOrderID()};
        int value = DBHandler.setDataToDataBase(row, con, query);
        return value;
    }

    /*****update sales order details*******/
    public static int updateSalesOrderDetail(SalesOrderDetail orderDetail) throws ClassNotFoundException, SQLException {
        String query = "update salesorder set quantity = '" + orderDetail.getQuantity() + "', discount = '" + orderDetail.getDiscount() + "', amount = '" + orderDetail.getAmount() + "' where itemid = '" + orderDetail.getItemID() + "' and orderid = '" + orderDetail.getSalesOrderID() + "'";
        int val = DBHandler.setData(query);
        return val;

    }

    
    public static int getMaxSalesOrderID() throws ClassNotFoundException, SQLException {
        //String query = "select max(orderID) from salesorder";
        String query = "select last_insert_id() as last_id from salesorder";
        ResultSet rst = DBHandler.getData(query);
        boolean next = rst.next();
    //    System.out.println(next);
        int salesOrderID = rst.getInt(1);
        return salesOrderID;
    }

    public static int removeSalesOrderDetail(SalesOrderDetail orderDetail) throws ClassNotFoundException, SQLException {
        String query = "update salesorderdetail set status = '0' where orderid = '" + orderDetail.getSalesOrderID() + "' and itemid = '" + orderDetail.getItemID() + "'";
        int val = DBHandler.setData(query);
        return val;
    }

    public static void loadAllSalesOrders(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        //String sql = "select so.orderid,so.sales_order_no,so.orderdate,so.time,cus.customer_name,cus.customer_acc_no,coalesce(v.vehicle_reg_no,'') as vehicle_reg_no, us.username,round(so.amount,2) as amount,round(so.labour_amount,2) service_cost,round(so.paid_amount,2) as paid, round(coalesce(@tot:=(db.debtamount-coalesce((select sum(paidamount) from debtdetail where debtid=db.debtid group by db.debtid),0)),0),2) as total_pending, coalesce(@tot,0) as total_pending_1,db.debtid,so.vehicle_id,coalesce(so.current_meter_reading, 0) as current_meter_reading,coalesce(so.next_service_due, 0) as next_service_due, cus.contact_no,so.employee_id,em.employee_name,so.total_discount,other_comments from salesorder as so inner join customer as cus on so.customerid = cus.id inner join tbl_user as us on so.userid = us.id left join debt db on so.sales_order_no = db.salesorderid left join customer_vehicles v on so.vehicle_id = v.vehicle_id inner join tbl_employee em ON so.employee_id = em.employee_id where so.status = 1 or so.status=2 order by cus.id";
        String sql = "select  so.orderid, so.sales_order_no, so.orderdate, so.time, cus.customer_name, cus.customer_acc_no, coalesce(v.vehicle_reg_no, '') as vehicle_reg_no, us.username, @tot_amount:=round(so.amount - (select  ifnull(sum((sod.unit_price) - (sod.discount_amount / sod.quantity) * tcrd.return_quantity), 0) from tbl_customer_return tcr inner join tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id inner join salesorderdetail sod ON tcrd.item_id = sod.itemid and sod.orderid = tcr.sales_order_id where tcr.status = 1 and tcr.sales_order_id = so.orderid), 2) as amount, round(so.labour_amount, 2) service_cost, @paid_amount:=round(so.paid_amount, 2) as paid, @pending_amount:=ifnull(db.debtamount - ifnull((select  sum(paidamount) from debtdetail where debtid = db.debtid group by db.debtid), 0), 0) as total_pending, @pending_amount as total_pending_1, db.debtid, so.vehicle_id, coalesce(so.current_meter_reading, 0) as current_meter_reading, coalesce(so.next_service_due, 0) as next_service_due, cus.contact_no, so.employee_id, em.employee_name, so.total_discount,other_comments,ifnull(so.garge_commission, 0) as grage_commision from salesorder as so inner join customer as cus ON so.customerid = cus.id inner join tbl_user as us ON so.userid = us.id left join debt db ON so.OrderID = db.salesorderid left join customer_vehicles v ON so.vehicle_id = v.vehicle_id inner join tbl_employee em ON so.employee_id = em.employee_id where so.status = 1 or so.status = 2 or so.status=4 order by cus.id";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[23];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 23; i++) {
                if (i == 12) {
                    tableRow[i] = (rst.getDouble(i + 1) > 0) ? "Pending" : "Paid";
                } else {
                    tableRow[i] = rst.getString(i + 1);
                }
            }

            dtm.addRow(tableRow);
        }

    }
    
    
   
    public static void loadAllSavedSalesOrders(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select so.orderid,so.sales_order_no,so.orderdate,so.time,cus.customer_name,cus.customer_acc_no,coalesce(v.vehicle_reg_no,'') as vehicle_reg_no, us.username,round(so.amount,2) as amount,round(so.labour_amount,2) service_cost,round(so.paid_amount,2) as paid,so.vehicle_id,coalesce(so.current_meter_reading, 0) as current_meter_reading,coalesce(so.next_service_due, 0) as next_service_due, cus.contact_no,so.employee_id,em.employee_name,so.total_discount from salesorder as so inner join customer as cus on so.customerid = cus.id inner join tbl_user as us on so.userid = us.id left join debt db on so.sales_order_no = db.salesorderid left join customer_vehicles v on so.vehicle_id = v.vehicle_id inner join tbl_employee em ON so.employee_id = em.employee_id where so.status=3 order by cus.id";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[18];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 18; i++) {
//                if (i == 12) {
//                    tableRow[i] = (rst.getDouble(i + 1) > 0) ? "Pending" : "Paid";
//                } else {
                tableRow[i] = rst.getString(i + 1);
                //}

            }

            dtm.addRow(tableRow);
        }

    }

    public static int removeSalesOrder(int OrderID) throws ClassNotFoundException, SQLException {
        String query = "update salesorder set status = '0' where OrderID = '" + OrderID + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static int removeSalesOrderDetail(int OrderID) throws ClassNotFoundException, SQLException {
        String query = "update salesorderdetail set status = '0' where OrderID = '" + OrderID + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static ArrayList getSalesOrderDetailsToSend(int salesOrderID) throws ClassNotFoundException, SQLException, JSONException {
        String sql = "select it.item_part_no,sod.quantity,sod.unit_price,so.OrderDate,sod.discount,sod.status,it.addeddate, (select COALESCE(sum(remainingquantity) ,0) from stockdetail  where itemid = sod.ItemId and (status = 1 or status = 2)) as remaining_qty,(select StockDate from stock where StockID= (select max(stockid) from stockdetail where itemid=sod.ItemId and (status = 1 or status = 2)))as last_stock_date from salesorderdetail sod inner join item it on sod.itemid= it.itemID inner join salesorder so on sod.orderid=so.OrderID   where so.OrderID=" + salesOrderID + " and (so.status = 1 or so.status = 2) and it.BrandID=1";

        ResultSet rst = DBHandler.getData(sql);
        ArrayList jsonArray = new ArrayList();
        JSONObject jSONObject = null;
        while (rst.next()) {
            jSONObject = new JSONObject();
            jSONObject.putOpt("part_no", rst.getString("item_part_no"));
            jSONObject.putOpt("qty", rst.getDouble("quantity"));
            jSONObject.put("unit_price", rst.getDouble("unit_price"));
            jSONObject.put("date", rst.getString("OrderDate"));
            jSONObject.put("status", 1);
            jSONObject.put("discount", rst.getDouble("discount"));
            jSONObject.put("remaining_stock_qty", rst.getDouble("remaining_qty"));
            jSONObject.put("last_stock_date", rst.getString("last_stock_date"));
            jSONObject.put("first_stock_date", rst.getString("addeddate"));
            jsonArray.add(jSONObject);

        }
        return jsonArray;

    }

    public static ResultSet loadAllUnAssignedSalesOrders() throws ClassNotFoundException, SQLException {
        String query = "select * from salesorder where status = 2";
        ResultSet data = DBHandler.getData(query);
        return data;
    }

    public static void updateSalesOrderStatus(int salesOrderID) throws ClassNotFoundException, SQLException {
        String query = "update salesorder set status = 1 where OrderID =" + salesOrderID;
        DBHandler.setData(query);

    }

    ///***********************service details***********///
    public static int addNewServiceDetail(ServiceDetail sd) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_service_detail (sales_order_id,service_id,discount_type,discount_amount,current_price,service_amount,done_by,service_qty,discount_total,status) values (?,?,?,?,?,?,?,?,?,?)";
        Object[] row = {sd.getSalesOrderID(), sd.getServiceID(), sd.getDiscountType(), sd.getDiscountAmount(), sd.getCurrentPrice(), sd.getServiceAmount(), sd.getDoneBy(), sd.getServiceQty(), sd.getDiscountTotal(), sd.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static void loadAllSalesOrderServices(int salesOrderID, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
       String query ="select sd.service_id, sv.service_code, sv.service_name, round(sd.service_qty,1), round(sd.discount_total, 2), round(sd.current_price, 2), round(sd.service_amount, 2), done_by from tbl_service_detail sd inner join tbl_service sv ON sd.service_id = sv.service_id where sd.sales_order_id = " + salesOrderID + " and sd.status = 1";
       // String query = "select sd.service_id, sv.service_code, sv.service_name, round(sd.service_qty,1)select sd.service_id, sv.service_code, sv.service_name, round(sd.servi, round(sd.discount_total, 2), round(sd.current_price, 2), round(sd.service_amount, 2), done_by from tbl_service_detail sd inner join tbl_service sv ON sd.service_id = sv.service_id where sd.sales_order_id = " + salesOrderID + " and sd.status = 1";
        ResultSet rst = DBHandler.getData(query);
        String tableRow[] = new String[8];
        dtm.setRowCount(0);

        while (rst.next()) {
            for (int i = 0; i < 8; i++) {
                tableRow[i] = rst.getString(i + 1);

            }

            dtm.addRow(tableRow);
        }

    }

    // iresha
    public static void loadAllVehicleWiseSalesOrderParts(DefaultTableModel dtm, String s) throws ClassNotFoundException, SQLException {

        String sql = "select so.sales_order_no,so.OrderDate,i.item_part_no,i.Description,sod.quantity,cv.vehicle_reg_no,cv.customer_id   from    customer_vehicles cv INNER JOIN salesorder so ON cv.customer_id = so.CustomerID INNER JOIN salesorderdetail sod ON so.OrderID = sod.orderid   INNER JOIN item i ON sod.itemid = i.ItemID where cv.vehicle_reg_no ='" + s + "'";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    

    
   

   }
    public static void loadAllVehicleWiseSalesOrderService(DefaultTableModel dtm, String s) throws ClassNotFoundException, SQLException {
        String sql = "select  so.sales_order_no,so.OrderDate,ts.service_name,cv.vehicle_reg_no, cv.customer_id, sod.quantity from customer_vehicles cv INNER JOIN salesorder so ON cv.customer_id = so.CustomerID INNER JOIN salesorderdetail sod ON so.OrderID = sod.orderid INNER JOIN tbl_service_detail tsd ON tsd.sales_order_id = so.sales_order_no        INNER JOIN    tbl_service ts ON tsd.service_id = ts.service_id where cv.vehicle_reg_no = '" + s + "'";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
            }
            dtm.addRow(tableRow);
        }

    }

    public static ResultSet getTotalQuantity(String vehicleNo) throws ClassNotFoundException, SQLException {
        String sql = "select sum(sod.quantity) as total_quantity from  customer_vehicles cv INNER JOIN  salesorder so ON cv.customer_id = so.CustomerID  INNER JOIN salesorderdetail sod ON so.OrderID = sod.orderid  INNER JOIN  item i ON sod.itemid = i.ItemID where cv.vehicle_reg_no = '" + vehicleNo + "'";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static ResultSet getSalesOrderDataForUpdate(int orderID) throws ClassNotFoundException, SQLException {
        String sql = "select so.orderid,so.sales_order_no,so.orderdate,so.time,cus.customer_name,cus.customer_acc_no,coalesce(v.vehicle_reg_no,'') as vehicle_reg_no, us.username,round(so.amount,2) as amount,round(so.labour_amount,2) service_cost,round(so.paid_amount,2) as paid, db.debtid,so.vehicle_id,coalesce(so.current_meter_reading, 0) as current_meter_reading,coalesce(so.next_service_due, 0) as next_service_due, cus.contact_no,so.employee_id,em.employee_name,so.total_discount from salesorder as so inner join customer as cus on so.customerid = cus.id inner join tbl_user as us on so.userid = us.id left join debt db on so.sales_order_no = db.salesorderid left join customer_vehicles v on so.vehicle_id = v.vehicle_id inner join tbl_employee em ON so.employee_id = em.employee_id where so.orderid = " + orderID + " and so.status = 3";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

}
