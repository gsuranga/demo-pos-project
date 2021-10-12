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
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import models.DeliverOrder;
import models.DeliverOrderDetail;

/**
 *
 * @author insaf
 */
public class DeliverOrderController {

    public static int getall(int deliverOrderID, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        dtm.setRowCount(0);
        double tot = 0;
        //String sql = "select i.ItemID,i.Description,i.item_part_no,pod.qty,pod.unitprice FRom item i INNER JOIN deliverorderdetail pod ON i.ItemID =pod.itemid where pod.admin_purchase_order_id='" + oid + "' and pod.status=1";
        String sql = "select dod.*,i.itemid,i.Description,i.item_part_no from deliverorderdetail dod inner join item i on dod.itemid = i.ItemID where dod.status=1 and dod.deliver_order_id=" + deliverOrderID;
        ResultSet data = DBHandler.getData(sql);
        while (data.next()) {
            Object row[] = {data.getString("Description"), data.getString("item_part_no"), data.getInt("ItemID"), data.getInt("qty"), data.getDouble("amount"), data.getDouble("retail_val"), false, 0, ""};
            dtm.addRow(row);

        }
        for (int i = 0; i < dtm.getRowCount(); i++) {
            tot += Double.parseDouble(dtm.getValueAt(i, 4).toString());
        }
        return 1;
    }

    public static void getDeliverOrder(int oid, JLabel acceptedDateLabel, JLabel adminName) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM deliverorder where admin_deliver_order_id ='" + oid + "' and status='" + 1 + "'";
        ResultSet data = DBHandler.getData(sql);

        if (data.next()) {
            acceptedDateLabel.setText(data.getString("acceptedDate"));
            adminName.setText(data.getString("userAdmin"));
        }

    }

    public static int insertNewDeliverOrder(DeliverOrder deliverOrder) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into deliverorder(invoiced_date,invoiced_time,amount,invoice_no,wip_no,due_date,time_stamp,admin_deliver_order_id,status)values(?,?,?,?,?,?,?,?,?)";
        Object[] row = {deliverOrder.getInvoicedDate(), deliverOrder.getInvoicedTime(), deliverOrder.getAmount(), deliverOrder.getInvoiceNo(), deliverOrder.getWipNo(), deliverOrder.getDueDate(), deliverOrder.getTimeStamp(), deliverOrder.getAdminDeliverOrderID(), deliverOrder.getStatus()};
        int value = DBHandler.setDataToDataBase(row, connection, sql);
        return value;
    }

    public static int insertNewDeliverOrderDetail(DeliverOrderDetail dod) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into deliverorderdetail (deliver_order_id,itemid,amount,retail_val,qty,status) values (?,?,?,?,?,?)";
        Object row[] = {dod.getDeliverOrderID(), dod.getPartID(), dod.getAmount(), dod.getRetailVal(), dod.getQty(), dod.getStatus()};
        int value = DBHandler.setDataToDataBase(row, connection, sql);
        return value;
    }

    public static int getLastInsertedDeliverOrderID(String wipNo) throws ClassNotFoundException, SQLException {
        String sql = "select deliver_order_id as last_id from deliverorder where wip_no = '" + wipNo + "'";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    public static void loadAllDeliverOrders(DefaultTableModel dtm) throws ClassNotFoundException, SQLException, NullPointerException {
        String sql = "select deliver_order_id,invoice_no,wip_no,invoiced_date,invoiced_time,amount,due_date,status from deliverorder where status=2";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[8];
        int rowCount = dtm.getRowCount();

        dtm.setRowCount(0);

        while (rst.next()) {
            for (int i = 0; i < 8; i++) {
                tableRow[i] = rst.getString(i + 1);
            }
            dtm.addRow(tableRow);
        }

    }

//    public static void getAllDeliveredItems(int deliverOrderID, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
//        String sql = "select dod.*,i.itemid,i.Description,i.ItemID from deliverorderdetail dod inner join item i on dod.itemid = i.ItemID where dod.status=1 and dod.deliver_order_id=" + deliverOrderID;
//        ResultSet rst = DBHandler.getData(sql);
//        String tableRow[] = new String[8];
//        int rowCount = dtm.getRowCount();
//        if (rowCount >= 0) {
//            dtm.setRowCount(0);
//        }
//
//        while (rst.next()) {
//            for (int i = 0; i < 8; i++) {
//                tableRow[i] = rst.getString(i + 1);
//            }
//            dtm.addRow(tableRow);
//        }
//    }
    public static int updateDeliverOrderStatus(int deliverOrderID) throws ClassNotFoundException, SQLException {
        String sql = "update deliverorder set status = 1 where deliver_order_id = " + deliverOrderID;
        int setData = DBHandler.setData(sql);
        return setData;
    }

    public static String getLastTimeStamp() throws ClassNotFoundException, SQLException {
        String sql = "select (max(time_stamp)) + '' as time_stamp from deliverorder where status = 1 or status = 2";
        ResultSet rst = DBHandler.getData(sql);
        String timeStamp = null;
        rst.next();
        timeStamp = rst.getString(1);
        if (timeStamp == null || timeStamp == "null") {
            timeStamp = "0";
        } else {

        }

        return timeStamp;
    }
}
