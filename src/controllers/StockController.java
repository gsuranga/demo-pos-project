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
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import models.Stock;
import models.StockDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.JSonJava;

/**
 *
 * @author user
 */
public class StockController {

    
    public static void loadAllStockDetail(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select st.stockid, sp.SupplierID,sp.SupplierName, st.stockdate, st.deliverdtime from stock st inner join supplier sp on st.SupplierID = sp.SupplierID where st.status = 1 or st.status = 2";
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

    public static int addNewStock(Stock stock) throws ClassNotFoundException, SQLException {
        String sql = "insert into stock (SupplierID,StockDate, DeliverdTime,deliver_order_id,Status) values ('" + stock.getSupplierID() + "','" + stock.getStockDate() + "','" + stock.getDeliverdTime() + "','" + stock.getDeliverOrderId() + "','" + stock.getStatus() + "')";
        int value = DBHandler.setData(sql);
        return value;
    }

    public static int addNewStockDetail(StockDetail stockDetail) throws ClassNotFoundException, SQLException {
        String sql = "insert into stockdetail (StockID,ItemID,StockQuantity,remainingquantity,buying_price,selling_price,status) values ('" + stockDetail.getStockID() + "','" + stockDetail.getItemID() + "','" + stockDetail.getStockQuontity() + "','" + stockDetail.getRemainingQty() + "','" + stockDetail.getBuyingPrice() + "','" + stockDetail.getSellingPrice() + "','" + stockDetail.getStatus() + "')";
        int value = DBHandler.setData(sql);
        return value;
    }

    public static int getMaxStockID() throws ClassNotFoundException, SQLException {
        String query = "select max(stockid) from stock where status=1 or status = 2";
        ResultSet rst = DBHandler.getData(query);
        rst.next();
        int stockid = rst.getInt(1);
        return stockid;
    }

    public static int updateStock(Stock stock) throws ClassNotFoundException, SQLException {
        String query = "update stock set SupplierID = '" + stock.getSupplierID() + "' where stockID = '" + stock.getStockID() + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static int deleteStock(Stock stock) throws ClassNotFoundException, SQLException {
        String query = "update stock set status = '0' where stockID = '" + stock.getStockID() + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static void loadAllStockDetails(DefaultTableModel stockDetailmodel, int stockID) throws SQLException, ClassNotFoundException {
        String query = "select it.itemid,it.item_part_no, it.Description,std.StockQuantity,std.remainingquantity, round(std.buying_price,2),round(std.selling_price,2) from stockdetail std inner join item it on std.ItemID=it.ItemID where std.StockID =" + stockID + " and (std.status=1 or std.status=2);";
        ResultSet rst = DBHandler.getData(query);
        int rowCount = stockDetailmodel.getRowCount();
        Object tableRow[] = new Object[7];
        if (rowCount >= 0) {
            stockDetailmodel.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 7; i++) {
                tableRow[i] = rst.getString(i + 1);

            }

            stockDetailmodel.addRow(tableRow);
        }

    }

    public static void loadAllStockDetailsForUpdate(DefaultTableModel stockDetailmodel, int stockID) throws SQLException, ClassNotFoundException {
        String query = "select it.itemid,it.item_part_no, it.Description,std.StockQuantity,std.remainingquantity, round(std.buying_price,2), round(std.selling_price,2),0,0 from stockdetail std inner join item it on std.ItemID=it.ItemID where std.StockID =" + stockID + " and (std.status=1 or std.status=2);";
        ResultSet rst = DBHandler.getData(query);
        int rowCount = stockDetailmodel.getRowCount();
        Object tableRow[] = new Object[9];
        if (rowCount >= 0) {
            stockDetailmodel.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 9; i++) {
                tableRow[i] = rst.getString(i + 1);

            }
            stockDetailmodel.addRow(tableRow);
        }

    }

    public static int deleteStockDetail(StockDetail sDetail) throws ClassNotFoundException, SQLException {
        String qurey = "update stockdetail set status = 0 where stockid = '" + sDetail.getStockID() + "' and itemID = '" + sDetail.getItemID() + "'";
        int value = DBHandler.setData(qurey);
        return value;
    }

    public static int updateStockDetail(StockDetail sDetail) throws ClassNotFoundException, SQLException {

        String qurey = "update stockdetail set StockQuantity = " + sDetail.getStockQuontity() + ", remainingquantity = " + sDetail.getRemainingQty() + ", buying_price = " + sDetail.getBuyingPrice() + ", selling_price = " + sDetail.getSellingPrice() + " where StockID = " + sDetail.getStockID() + " and itemid =" + sDetail.getItemID() + " and (status =1 or status = 2)";
        int value = DBHandler.setData(qurey);
        return value;
    }

    public static int getTotalRemainingStockQuantityOfaItem(int itemId) throws ClassNotFoundException, SQLException {
        String query = "select sum(remainingquantity) from stockdetail where itemid = '" + itemId + "' and (status = '1' or '2')";
        ResultSet rst = DBHandler.getData(query);
        rst.next();
        int quantity = rst.getInt(1);
        return quantity;

    }

    public static ResultSet getMinimumStockDetailForItem(int itemID) throws ClassNotFoundException, SQLException {
        String query = "select detailid, remainingquantity from stockdetail where itemid = '" + itemID + "' and remainingquantity>0 and (status = '1' or status = '2') order by 1 asc limit 1";
        ResultSet rst = DBHandler.getData(query);
        return rst;
    }

    /* OLD---------------------------------------
     public static int updateStockDetailQuantity(int detailID, int remainingQty) throws ClassNotFoundException, SQLException {
     String query = "update stockdetail set remainingquantity = '" + remainingQty + "', status = '2' where DetailID = '" + detailID + "'";
     int value = DBHandler.setData(query);
     return value;
     }*///NEw-------------
    public static int updateStockDetailQuantity(double requested, int itemID, int stockID) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update stockdetail set remainingquantity = (remainingquantity-?), status = ?  where  StockID = ? and ItemID = ? ";
        Object row[] = {requested, 2, stockID, itemID};
        int value = DBHandler.setDataToDataBase(row, con, query);
        return value;
    }

    public static void getItemStockDetails(DefaultTableModel dtm, int itemID) throws SQLException, ClassNotFoundException {
        String query = "select (select item.item_part_no from item where item.itemid = stockdetail.itemid) as item_part_no, StockQuantity,  remainingquantity from stockdetail where itemid = '" + itemID + "' and (status = '1' or status = '2')";
        ResultSet rst = DBHandler.getData(query);
        int rowCount = dtm.getRowCount();
        Object tableRow[] = new Object[3];
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 3; i++) {
                tableRow[i] = rst.getString(i + 1);

            }

            dtm.addRow(tableRow);
        }

    }

    public static int getLastInsertedStockID() throws ClassNotFoundException, SQLException {
        String sql = "select last_insert_id() as last_id from stock";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    //------------------------------category stock for loass sales-----------------------------------// 
    public static ResultSet getCategoryRemainingStock(int categoryID) throws ClassNotFoundException, SQLException {
        String sql = "select it.ItemID, it.item_part_no, coalesce(sum(std.remainingquantity), 0) as remaining_stock from stockdetail std right join item it ON std.ItemID = it.ItemID and (std.status = 1 or std.status = 2) where it.BrandID = 1 and it.key_id = " + categoryID;
        ResultSet data = DBHandler.getData(sql);
        return data;
    }

    public static ArrayList loadAllUnAssignedStocks() throws ClassNotFoundException, SQLException, JSONException {
        //select st.StockID,it.item_part_no, COALESCE(sum(std.remainingquantity) ,0) as remaining_qty,(select StockDate from stock where StockID= (select max(stockid) from stockdetail where itemid=it.ItemID  and (status = 1 or status = 2)))as last_stock_date,st.Status from stock st left join stockdetail std on st.StockID= std.StockID inner join item it on std.ItemID = it.ItemID where st.status=2 and (std.status=1 or std.status=2) and it.BrandID=1 group by std.ItemID
        //query prev = "select it.ItemID, it.item_part_no, it.Description, coalesce(sum(sd.remainingquantity),0) as remainingquantity, (select StockDate from stock where StockID = (select min(stockid) from stockdetail where itemid = sd.ItemId and (status = 1 or status = 2))) as first_stock_date,(select StockDate from stock where StockID = (select max(stockid) from stockdetail where itemid = sd.ItemId and (status = 1 or status = 2))) as last_stock_date from stockdetail sd inner join item it ON sd.ItemID = it.ItemID where sd.status = 1 or sd.status = 2 and it.BrandID = 1 group by sd.itemID";

        String query = "select it.ItemID,replace(it.item_part_no, ' ', '') as item_part_no,it.Description,coalesce((select round((sum(remainingquantity)), 0) from stockdetail where ItemID = it.ItemID and (status = 1 or status = 2) group by ItemID), 0) as remainingquantity,coalesce((select StockDate from stock where StockID = (select min(stockid) from stockdetail where itemid = it.ItemId and (status = 1 or status = 2))), '0000-00-00') as first_stock_date, coalesce((select StockDate from stock where StockID = (select max(stockid) from stockdetail where itemid = it.ItemId and (status = 1 or status = 2))), '0000-00-00') as last_stock_date from item it where it.status = 1 and it.BrandID = 1";
        ResultSet rst = DBHandler.getData(query);
        ArrayList jsonArray = new ArrayList();
        JSONObject jSONObject = null;
        while (rst.next()) {
            jSONObject = new JSONObject();
            jSONObject.putOpt("part_no", rst.getString(2));
            jSONObject.put("remaining_stock_qty", rst.getDouble(4));
            jSONObject.put("first_stock_date", rst.getString(5));
            jSONObject.put("last_stock_date", rst.getString(6));
            jsonArray.add(jSONObject);
        }
        return jsonArray;
    }

    public static synchronized int sendStockDetailsAsaJson(ArrayList arrayList) {

        int aInt = 0;
        HashSet<Integer> hs = new HashSet<Integer>();
        try {
            ResultSet rst = ProfileController.getDealerDetails();
            rst.next();
            String accountNo = rst.getString(3);
            String serverURL = rst.getString(9);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dealer_acc_no", accountNo);

            ArrayList<JSONObject> jsonArrayList = arrayList;
            JSONArray jSONArray = new JSONArray(jsonArrayList);
            jSONArray.put(jsonObject);

            String replaceAll = jSONArray.toString().replaceAll("\\s", "%20");
            String replace = replaceAll.replaceAll("\\n", "%30");
            String url = serverURL + "dimo_lanka/pos_services/addDealerStockDetails";
            new JSonJava().postJSONArray(jSONArray, url, "stock_args");
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        } catch (Exception ex) {
        }
        return aInt;
    }

    public static int updateStockStatus(HashSet<Integer> hs) throws ClassNotFoundException, SQLException {
        int setData = 0;
        Connection con = DBConnection.getConnection();
        Iterator<Integer> iterator = hs.iterator();
        String query = "update stock set status =1 where StockID=?";
        for (int i = 0; i < hs.size(); i++) {
            Object[] row = {iterator.next()};
            setData = DBHandler.setDataToDataBase(row, con, query);
        }

        return setData;
    }

    public static int getStockIDForDeliverOrder(int deliverOrderID) throws ClassNotFoundException, SQLException {
        String sql = "select StockID from stock where deliver_order_id = " + deliverOrderID + " and status = 1";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt(1);
        return id;

    }

    public static int updateStockONStockBalance(StockDetail stockDetail) throws ClassNotFoundException, SQLException {
        String sql = "update stockdetail set StockQuantity = StockQuantity + " + stockDetail.getStockQuontity() + " where StockID = " + stockDetail.getStockID() + " and ItemID = " + stockDetail.getItemID();
        int data = DBHandler.setData(sql);
        String sql2 = "SELECT ROW_COUNT()";
        ResultSet rst = DBHandler.getData(sql2);
        rst.next();
        int rowCount = rst.getInt(1);
        return rowCount;
    }
    //oter returns
     public static void loadAllOtherStockDetails( int stockID,DefaultTableModel otherRetTableModel) throws SQLException, ClassNotFoundException {
        String query = "select std.StockID,it.itemid,it.item_part_no, it.Description,std.remainingquantity as Quantity, round(std.buying_price,2) from stockdetail std inner join item it on std.ItemID=it.ItemID where std.StockID =" + stockID + " and (std.status=1 or std.status=2);";
      // String query="select it.itemid,it.item_part_no,it.Description,std.StockQuantity as Quantity,round(std.buying_price, 2),false from stockdetail stdinner join item it ON std.ItemID = it.ItemID where std.StockID =" + stockID + " and (std.status = 1 or std.status = 2);";
         ResultSet rst = DBHandler.getData(query);
     
         
        int rowCount = otherRetTableModel.getRowCount();
        Object tableRow[] = new Object[6];
        if (rowCount >= 0) {
            otherRetTableModel.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);

            }

            otherRetTableModel.addRow(tableRow);
        }

    } 
    
    //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
public static int getStockIDForReturn(double UnitPrice,int ItemID) throws ClassNotFoundException, SQLException {
        String sql = "select std.StockID from stockdetail std where std.selling_price="+UnitPrice+" and std.ItemID="+ItemID+"";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        int inv= rst.getInt(1);
        return inv;
    }
 public static int updateStockDetailQuantit(double requested, int itemID, int stockID) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update stockdetail set remainingquantity = (remainingquantity+?)  where  StockID = ? and ItemID = ? ";
        Object row[] = {requested,  stockID, itemID};
        int value = DBHandler.setDataToDataBase(row, con, query);
        return value;
    }
}
