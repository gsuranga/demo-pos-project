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
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import models.PurchesOrder;
import models.purchesorderdetail;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author insaf
 */
public class PurchesOrderController {

    public static int addPurchesOrder(PurchesOrder purchesOrder) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into purcheorder (date,total,time,name,current_discount,current_vat,status,final_amount) values (?,?,?,?,?,?,?,?)";
        Object row[] = {purchesOrder.getDate(), purchesOrder.getTotal(), purchesOrder.getTime(), purchesOrder.getUser(), purchesOrder.getCurrentDealerDiscount(), purchesOrder.getCurrentVatAmount(), purchesOrder.getStatus(), purchesOrder.getFinalAmount()};
        int setDataToDataBase = DBHandler.setDataToDataBase(row, con, query);
        return setDataToDataBase;

    }

    public static int getMaxPurchaseOrderID() throws ClassNotFoundException, SQLException {
        String sql = "select last_insert_id() as last_id from purcheorder";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();

        int purchesOrderID = rst.getInt(1);
        return purchesOrderID;
    }

    public static int getMaxPurchaseOrderDetailID() throws ClassNotFoundException, SQLException {
        String sql = "select max(id) from purchesorderdetail";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        int purchesOrderID = rst.getInt(1);
        return purchesOrderID;
    }

    public static int addPerchuseOrderDetail(ArrayList<purchesorderdetail> pod) throws ClassNotFoundException, SQLException {

        int setData = 0;
        for (purchesorderdetail p : pod) {
            String sql = "insert into purchesorderdetail (orderId,itemid,quantity,status,unitprice) values('" + p.getOrderid() + "','" + p.getItem() + "','" + p.getQty() + "','" + p.getStatus() + "','" + p.getUnitprice() + "') ";
            setData = DBHandler.setData(sql);
            if (setData == 0) {
                break;
            }
        }

        return setData;

    }

    public static ArrayList<PurchesOrder> getAllPercherseOrder() throws ClassNotFoundException, SQLException {
        //String sql = "Select id,purchase_order_number,name,date,time, coalesce((@total_with_discount:=(total - (total*current_discount/100))),0) as tot_with_discount,round(coalesce((@total_with_discount+(@total_with_discount*current_vat/100)),0),2) as total_amount from purcheorder where status= 1 order by id";
        String sql = "Select id, purchase_order_number, name, date, time, final_amount from purcheorder where status = 1 order by id";
        ResultSet data = DBHandler.getData(sql);
        ArrayList<PurchesOrder> allOrder = new ArrayList<>();
        while (data.next()) {
            PurchesOrder purchesOrder = new PurchesOrder();
            purchesOrder.setId(data.getInt("id"));
            purchesOrder.setDate(data.getString("date"));
            purchesOrder.setFinalAmount(data.getDouble("final_amount"));
            purchesOrder.setUser(data.getString("name"));
            purchesOrder.setTime(data.getString("time"));
            purchesOrder.setPurchaseOrderNumber(data.getString("purchase_order_number"));
            allOrder.add(purchesOrder);
        }
        return allOrder;
    }

    public static int getall(int oid, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        dtm.setRowCount(0);
        double tot = 0;
        String sql = "select i.ItemID,i.item_part_no,i.buyingprice,pod.quantity,pod.unitprice FRom item i INNER JOIN purchesorderdetail pod ON i.ItemID =pod.itemid where pod.orderId='" + oid + "' and pod.status=1";

        ResultSet data = DBHandler.getData(sql);
        while (data.next()) {

            Object row[] = {data.getString("item_part_no"), data.getInt("ItemID"), data.getInt("quantity"), data.getDouble("unitprice"), data.getInt("quantity") * data.getDouble("unitprice")};

            dtm.addRow(row);

        }
        for (int i = 0; i < dtm.getRowCount(); i++) {
            tot += Double.parseDouble(dtm.getValueAt(i, 4).toString());
        }
        return 1;
    }

    public static int getallDeliveredPurchaseOrderDetail(int oid, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        dtm.setRowCount(0);
        double tot = 0;
        String sql = "select i.ItemID,i.item_part_no,i.Description,pod.quantity,i.buyingprice,round((coalesce((pod.quantity*i.buyingprice),0)),2) as amount 	FRom item i INNER JOIN purchesorderdetail pod ON i.ItemID =pod.itemid where pod.orderId=" + oid + " and pod.status=1";

        ResultSet data = DBHandler.getData(sql);
        while (data.next()) {

            Object row[] = {data.getInt(1), data.getString(2), data.getString(3), data.getDouble(4), data.getDouble(5), data.getDouble(6)};
            dtm.addRow(row);

        }
        for (int i = 0; i < dtm.getRowCount(); i++) {
            tot += Double.parseDouble(dtm.getValueAt(i, 4).toString());
        }
        return 1;
    }

    public static int removeItem(int oid, String icode) throws ClassNotFoundException, SQLException {

        String sql = "update purchesorderdetail set status='" + 0 + "' where orderId='" + oid + "' and itemid='" + icode + "'";
        int setData = DBHandler.setData(sql);
        return setData;

    }

    public static int updatePurchesOrderDetail(int oid, String icode, int qty) throws ClassNotFoundException, SQLException {
        String sql = "update purchesorderdetail set quantity='" + qty + "' where itemid='" + icode + "' and orderId='" + oid + "' ";
        int setData = DBHandler.setData(sql);
        return setData;
    }

    public static void updatePurchesOrder(int oid, String lastDate, double parseDouble) throws ClassNotFoundException, SQLException {
        String sql = "update purcheorder set lastupdatedate='" + lastDate + "',total='" + parseDouble + "' where id='" + oid + "'";
        DBHandler.setData(sql);
    }

    public static int removeorder(String oid) throws ClassNotFoundException, SQLException {
        String sql = "Update purcheorder set status='" + 0 + "' where id='" + oid + "'";
        int setData = DBHandler.setData(sql);
        return setData;
    }

    public static int getCompleteorNotPurchaseOrder(int oid) throws ClassNotFoundException, SQLException {
        String sql = "select complete from purcheorder where id='" + oid + "'";
        ResultSet data = DBHandler.getData(sql);
        int co = 0;
        if (data.next()) {
            co = data.getInt("complete");
        }
        return co;
    }

    public static int addAccepetOrder(int oid, String date, String time) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE purcheorder set accepttodatabasetime='" + time + "',accepttodatabasedate='" + date + "',accepttodatabase='" + 1 + "' where id='" + oid + "'";
        int setData = DBHandler.setData(sql);
        return setData;

    }

//    public static void autoCreatePurchaseOrder(DefaultTableModel dtm, JLabel totalLabel) throws ClassNotFoundException, SQLException {
//        String sql = "SELECT itemid ,sum(quantity)from salesorderdetail where status='" + 1 + "' and date>=(select accepttodatabasedate from purcheorder where complete='" + 1 + "' and accepttodatabase='" + 1 + "' and id=(select max(id) from purcheorder) )  and itemId in(select itemId from item where BrandID='" + 1 + "') group by itemid";
//
//        ResultSet data = DBHandler.getData(sql);
//        while (data.next()) {
//            int itemCode = data.getInt("itemid");
//            String itemName = ItemController.getItemNameforID(data.getInt("itemid"));
//            double itemUnitPrice = ItemController.getItemUnitPrice(itemName);
//            int qty = data.getInt("sum(quantity)");
//            //System.out.println(qty + "hjf");
//            double amount = itemUnitPrice * qty;
//            Object[] row = {itemCode, itemName, itemUnitPrice, qty, amount};
//            //System.out.println(row[0] + "" + row[1] + row[2] + row[3]);
//            dtm.addRow(row);
//            double tot2 = 0;
//            for (int j = 0; j < dtm.getRowCount(); j++) {
//                tot2 += Double.parseDouble(dtm.getValueAt(j, 4).toString());
//            }
//            totalLabel.setText(String.valueOf(tot2));
//
//        }
//    }
    public static void autoCreatePurchaseOrder(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        //String sql = "select i.itemID, i.item_part_no,i.description,i.buyingprice,(std.StockQuantity - std.remainingquantity) as purchaseQty,(round(i.buyingprice * (std.StockQuantity - std.remainingquantity),2)) as amount from stockdetail std inner join stock st ON std.stockID = st.stockID inner join item i ON std.itemID = i.itemID inner join itembrand br ON i.BrandID = br.BrandID where std.status = 2 and br.BrandID = 1 and st.StockID = (select  max(StockID) from stockdetail where itemid = i.itemid)";
        String sql = "select i.itemID, i.item_part_no,i.description,i.buyingprice,@remaining_qty:= coalesce((select sum(remainingquantity) from stockdetail where itemid = std.itemID and (status = 1 or 2)),0) as remaining_qty,round(coalesce((select sum(quantity)/(SELECT round(DATEDIFF(curdate(), ((select min(stk.StockDate) from stockdetail stdt inner join stock stk on stdt.StockID=stk.StockID where stdt.itemid = std.itemID and (stdt.status=1 or stdt.status=2)))) / 30.436875)) from salesorderdetail where itemid=std.itemID and status=1),0),2) as avg_monthly_sale,@last_30_days_sale:=coalesce((select sum(sod.quantity) from salesorderdetail sod inner join salesorder so on sod.orderid=so.OrderID where sod.itemid=std.itemID and  OrderDate BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE() and sod.status=1),0) as last_30_days_sale, @last_30_days_loss_sale:= coalesce((select sum(loss_qty) from tbl_loss_sales_detail where part_id = std.itemID and loss_type = 1 and loss_date BETWEEN CURDATE() - INTERVAL 30 DAY AND CURDATE() and status = 1),0) as last_30_days_loss_sales, @avg_daily_demand:= round((@last_30_days_sale + @last_30_days_loss_sale)/30,2) as avg_daily_demand, 10, round((@avg_daily_demand*10)-@remaining_qty,2) as suggested_qty, (std.StockQuantity - std.remainingquantity) as purchaseQty from stockdetail std inner join stock st ON std.stockID = st.stockID inner join item i ON std.itemID = i.itemID inner join itembrand br ON i.BrandID = br.BrandID where std.status = 2 and br.BrandID = 1 and st.StockID = (select  max(StockID) from stockdetail where itemid = i.itemid) order by i.ItemID";
        ResultSet data = DBHandler.getData(sql);
        int rowCount = dtm.getRowCount();
        Object tableRow[] = new Object[12];
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (data.next()) {
            for (int i = 0; i < 12; i++) {
                tableRow[i] = data.getString(i + 1);

            }

            dtm.addRow(tableRow);
        }

    }

    public static PurchesOrder getAcceptedPurchaseOrder() throws ClassNotFoundException, SQLException {
        String sql = "select * from purcheorder where id=(select max(id) from purcheorder )";
        ResultSet data = DBHandler.getData(sql);
        PurchesOrder purchesOrder = new PurchesOrder();
        if (data.next()) {
            purchesOrder.setComplete(data.getInt("complete"));
            purchesOrder.setAccepttodatabase(data.getInt("accepttodatabase"));
        }
        return purchesOrder;
    }

    public static double getPurchaseOrderAmount(int purchaseOrderID) throws ClassNotFoundException, SQLException {
        String sql = "select coalesce((total - (total*current_discount/100))+(total*current_vat/100),0) as total from purcheorder where id=" + purchaseOrderID + " and (status=1 or status=2)";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        double amount = rst.getDouble(1);
        return amount;

    }

//    public static ArrayList getPurchaseOrderDetails(int purchaseOrderID) throws ClassNotFoundException, SQLException, JSONException {
//
//        String sql = "select pod.id,i.item_part_no,pod.quantity,pod.status,pod.unitprice,pod.orderId from purchesorderdetail as pod inner join item as i on pod.itemid=i.itemid where pod.orderID='" + purchaseOrderID + "' and pod.status=1";
//
//        ResultSet rst = DBHandler.getData(sql);
//        ArrayList jsonArray = new ArrayList();
//        JSONObject jSONObject = null;
//        while (rst.next()) {
//            jSONObject = new JSONObject();
//            jSONObject.putOpt("detailID", rst.getInt(1));
//            jSONObject.put("partNumber", rst.getString(2));
//            jSONObject.put("quantity", rst.getDouble(3));
//            jSONObject.put("status", rst.getInt(4));
//            jSONObject.put("unitprice", rst.getDouble(5));
//            jSONObject.put("orderId", purchaseOrderID);
//            jsonArray.add(jSONObject);
//        }
//        return jsonArray;
//
//    }
    public static void loadAllUnAssignedPurchaseOrders(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "Select id, purchase_order_number, name, date, time, final_amount from purcheorder where status = 2 order by id";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[7];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 6; i++) {
                String val = rst.getString(i + 1);

                tableRow[i] = val;

            }

            dtm.addRow(tableRow);
        }

    }

    public static ResultSet getAllUnAssignedPurchaseOrdersToSend() throws ClassNotFoundException, SQLException {
        String sql = "select po . *, pod . *, it.ItemID, it.item_part_no, it.Description from purcheorder po inner join purchesorderdetail pod ON po.id = pod.orderId inner join item it ON pod.itemid = it.ItemID and it.status = 1 where po.status = 2 and pod.status = 1";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static int saveAdminPurchaseOrderID(PurchesOrder po) throws ClassNotFoundException, SQLException {
        String sql = "update purcheorder set admin_purchase_order_id='" + po.getAdminPuchaseOrderID() + "',status='" + po.getStatus() + "', purchase_order_number='" + po.getPurchaseOrderNumber() + "'where id='" + po.getId() + "'";
        int value = DBHandler.setData(sql);
        return value;

    }

    public static int updatePurchaseOrderStatus(PurchesOrder po) throws ClassNotFoundException, SQLException {
        String sql = "update purcheorder set complete = '" + po.getComplete() + "' where id='" + po.getId() + "'";
        int value = DBHandler.setData(sql);
        return value;
    }

    public static ResultSet getAllIncompletePurchaseOrders() throws ClassNotFoundException, SQLException {
        String sql = "select * from purcheorder where complete = 0 and status = 1";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static ResultSet getOrderData(int orderID) throws ClassNotFoundException, SQLException {
        String query = "select pod . *, it.ItemID, it.item_part_no, it.Description from purcheorder po inner join purchesorderdetail pod ON po.id = pod.orderId inner join item it ON pod.itemid = it.ItemID and it.status = 1 where po.status = 2 and pod.status = 1 and po.id = " + orderID + " order by po.id";
        ResultSet rst = DBHandler.getData(query);
        return rst;
    }
}
