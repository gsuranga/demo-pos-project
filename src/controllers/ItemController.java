/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import models.Item;
import models.ItemAlternative;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.JSonJava;

/**
 *
 * @author user
 */
public class ItemController {

    public static int addNewItem(Item item) throws ClassNotFoundException, SQLException {
        String query = "insert into item (BrandID,item_part_no,buyingprice,sellingprice,addeddate,image,Description,remarks,commision,re_order_qty,key_id,supplier_id,type_id,rack_no,status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        // String query = "insert into item (BrandID,item_part_no,buyingprice,sellingprice,addeddate,image,Description,remarks,re_order_qty,key_id,supplier_id,type_id,status) values ('" + item.getBrandID() + "','" + item.getItemName() + "','" + item.getBuyingPrice() + "','" + item.getSellingPrice() + "','" + item.getAddedDate() + "','" + item.getImage() + "','" + item.getItemDesc() + "','" + item.getRemarks() + "','" + item.getReOrderQty() + "','" + item.getSearchKeyID() + "','" + item.getSupplierID() + "','" + item.getTypeID() + "','" + item.getStatus() + "')";
        Connection con = DBConnection.getConnection();
        Object row[] = {item.getBrandID(), item.getItemName(), item.getBuyingPrice(), item.getSellingPrice(), item.getAddedDate(), item.getImage(), item.getItemDesc(), item.getRemarks(),item.getCommision(), item.getReOrderQty(), item.getSearchKeyID(), item.getSupplierID(), item.getTypeID(), item.getRackNo(), item.getStatus()};
        int value = DBHandler.setDataToDataBase(row, con, query);
        return value;
    }

    public static int updateItem(Item item) throws ClassNotFoundException, SQLException {
        String query = "update item set BrandID='" + item.getBrandID() + "', item_part_no = '" + item.getItemName() + "',buyingprice='" + item.getBuyingPrice() + "',sellingprice='" + item.getSellingPrice() + "',image='" + item.getImage() + "',Description='" + item.getItemDesc() + "',remarks='" + item.getRemarks() + "',commision='" + item.getCommision() + "',re_order_qty='" + item.getReOrderQty() + "',key_id='" + item.getSearchKeyID() + "',supplier_id='" + item.getSupplierID() + "', type_id='" + item.getTypeID() + "',rack_no='" + item.getRackNo() + "' where ItemID=" + item.getItemID() + " and status =1";
        int value = DBHandler.setData(query);
        return value;
    }

    public static void updateItemWithPrice(Item item) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
//        String query="update item set Description ='"+item.getItemDesc()+"',buyingprice='"+item.getBuyingPrice()+"' where item_part_no='"+item.getItemName()+"' and status = 1 ";
//        int value = DBHandler.setData(query);
        
        String query = "update item set Description = ?,  buyingprice = ? where item_part_no = ? and status = 1";
        Object row[] = {item.getItemDesc(), item.getBuyingPrice(), item.getItemName()};
        DBHandler.setDataToDataBase(row, con, query);
    }

    public static void loadAllItems(DefaultTableModel dtm) throws ClassNotFoundException, SQLException, NullPointerException {

        String sql = "select i.ItemID,br.BrandName,i.item_part_no,i.Description,sk.key_name,vm.vehicle_model_name,sp.SupplierName, i.buyingprice,i.sellingprice,i.addeddate,i.re_order_qty,i.remarks,i.commision,i.rack_no from item i inner join  itembrand br on i.BrandID = br.BrandID  left join tbl_search_key sk on i.key_id = sk.search_key_id left join tbl_vehicle_models vm on i.type_id = vm.vehicle_model_id inner join supplier sp on i.supplier_id = sp.SupplierID where i.status = 1 order by i.ItemID";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[14];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 14; i++) {
                tableRow[i] = rst.getString(i + 1);
            }
            dtm.addRow(tableRow);
        }

    }

    public static int deleteItem(Item item) throws ClassNotFoundException, SQLException {
        String sql = "update item set status = '0' where itemID = '" + item.getItemID() + "'";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static int getItemIDforItemName(String itemName) throws ClassNotFoundException, SQLException {
        String query = "select ItemID from Item where status = 1 and item_part_no = '" + itemName + "'";
        ResultSet rst = DBHandler.getData(query);
        int itemBrandID = 0;
        if (rst.next()) {
            itemBrandID = rst.getInt(1);
        } else {
            itemBrandID = 0;
        }
        return itemBrandID;
    }

    public static double getItemUnitPrice(String itemName) throws ClassNotFoundException, SQLException {
        String query = "select sellingprice from item where item_part_no = '" + itemName + "'";
        ResultSet rst = DBHandler.getData(query);
        double sellingPrice = 0;
        while (rst.next()) {
            sellingPrice = rst.getDouble(1);
        }
        return sellingPrice;
    }

    public static String getItemNameforID(int itemID) throws ClassNotFoundException, SQLException {

        String query = "select item_part_no from item where ItemID = '" + itemID + "'";
        ResultSet rst = DBHandler.getData(query);
        String itemName = "";
        while (rst.next()) {
            itemName = rst.getString(1);
        }
        return itemName;
    }

    public static ArrayList<Item> getAllItem() throws ClassNotFoundException, SQLException {
        String sql = "SELECT i.*, sk.key_name FROM item i inner join tbl_search_key sk on i.key_id = sk.search_key_id where i.status='1' and i.BrandID='1'";
        ArrayList<Item> is = new ArrayList<>();
        ResultSet data = DBHandler.getData(sql);
        while (data.next()) {
            Item item = new Item();
            int itemId = data.getInt("ItemID");
            item.setItemID(itemId);
            item.setItemName(data.getString("item_part_no"));

            item.setBuyingPrice(data.getDouble("buyingprice"));
            item.setItemDesc(data.getString("Description"));
            //item.setSearchKeyID(itemId); // item.setQuantity(data.getInt("Quantity"));

            int totalRemainingStockQuantityOfaItem = StockController.getTotalRemainingStockQuantityOfaItem(itemId);
            item.setRemainingQty(totalRemainingStockQuantityOfaItem);
            item.setReOrderQty(data.getDouble("re_order_qty"));

            is.add(item);
        }
        return is;
    }

    public static boolean checkItemDuplicate(String itemName) throws ClassNotFoundException, SQLException {
        String query = "Select ItemID from Item where item_part_no like '" + itemName + "'";
        ResultSet rst = DBHandler.getData(query);
        return rst.next();

    }

//    public static int updateItemQuantity(Item item) throws ClassNotFoundException, SQLException {
//        String qury = "update Item set quantity = '" + item.getQuantity() + "' where ItemID = '" + item.getItemID() + "' and status = '1'";
//        int value = DBHandler.setData(qury);
//        return value;
//
//    }
    public static String getImageForID(int itemID) throws ClassNotFoundException, SQLException {
        String qury = "select image from item where itemid = '" + itemID + "'";
        ResultSet rst = DBHandler.getData(qury);
        rst.next();
        return rst.getString(1);

    }

    public static int addNewAlternativePart(ItemAlternative alternative) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_item_alternatives (item_id,alternative_item_id,description,added_date,added_time,status) values (?,?,?,?,?,?)";
        Object[] row = {alternative.getItemID(), alternative.getAlternativePartID(), alternative.getDescription(), alternative.getAddedDate(), alternative.getAddedTime(), alternative.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static String getPartNumberForKey(String key) throws ClassNotFoundException, SQLException {
        String qury = "select item_part_no from item where search_key = '" + key + "'";
        ResultSet data = DBHandler.getData(qury);
        data.next();
        String string = data.getString(1);
        return string;
    }

    public static String getKeyForPartNumber(String partNo) throws ClassNotFoundException, SQLException {
        String qury = "select search_key from item where item_part_no = '" + partNo + "'";
        ResultSet data = DBHandler.getData(qury);
        data.next();
        String string = data.getString(1);
        return string;
    }

    public static ResultSet getAlternativesForItem(int itemID) throws ClassNotFoundException, SQLException {
        String query = "select part_no from tbl_item_alternatives where item_id = " + itemID + " and status=1";
        ResultSet data = DBHandler.getData(query);
        return data;
    }

    public static ResultSet getAllPartsForAlternative() throws ClassNotFoundException, SQLException {
        String query = "select item_part_no, search_key from  item where status=1";
        ResultSet data = DBHandler.getData(query);
        return data;

    }

    public static ResultSet getAllAlternativesForPart(int partID) throws ClassNotFoundException, SQLException {
        String query = "select i.item_part_no,Description,sellingprice from tbl_item_alternatives al inner join item i on al.alternative_item_id = i.ItemID where al.item_id = " + partID + " and al.status=1";
        ResultSet data = DBHandler.getData(query);
        return data;

    }

    public static int getBrandForPart(int itemID) throws ClassNotFoundException, SQLException {
        String query = "select BrandID from item where ItemID = " + itemID + " and status=1";
        ResultSet data = DBHandler.getData(query);
        data.next();
        int brandID = data.getInt(1);
        return brandID;
    }
//-----------------------------to select parts in sales order------------------------------------
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<,Including Avg cost & remaning qty<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public static ResultSet getAllPartDataToSelect() throws ClassNotFoundException, SQLException {
         String query="select it.ItemID as aa,br.BrandName,it.item_part_no,it.Description,ifnull((select round(coalesce(selling_price, 0), 2)from stockdetail where DetailID = ifnull((SELECT `DetailID` as dda FROM `stockdetail` sd WHERE sd.itemid =it.ItemID order by `DetailID` desc limit 1,1),(SELECT `DetailID` as dda FROM `stockdetail` sd WHERE sd.itemid =it.ItemID order by `DetailID` desc limit 1))),0.00) as selling_price,it.buyingprice as current_selling_price,ifnull((select round(coalesce(selling_price, 0), 2)from stockdetail where DetailID = (select max(DetailID) from stockdetail where itemid = it.itemid)),'0.00') as stock_price,temp.quantityCost,sk.key_name,ifnull(vm.vehicle_model_name, '-') as vehicle_model_name,sp.SupplierName,it.re_order_qty,(select COALESCE(sum(remainingquantity), 0)from stockdetail where itemid = it.itemid and (status = 1 or status = 2)) as remaining_qty,it.key_id,round(coalesce(std.buying_price, 0), 2) as buying_price,std.StockID,ifnull(it.commision, '-') as Salesman_Commision,ifnull(it.rack_no, '-') as Rack_No from item it inner join(select ti.ItemID as itemID,coalesce((sum(remainingquantity)), 0) as remaining_qty,ifnull(sum(remainingquantity * round(coalesce(std.buying_price, 0), 2)), 0) as sub_tot,round(ifnull(ifnull(sum(remainingquantity * round(coalesce(std.buying_price, 0), 2)), 0) / coalesce((sum(remainingquantity)), 0), 0), 2) as quantityCost from item ti left join stockdetail std ON (ti.ItemID = std.ItemID and std.remainingquantity > 0 and (std.status = 1 or std.status = 2))where ti.status = 1 group by ti.ItemID) as temp ON temp.itemID = it.itemID Left join stockdetail std ON (it.ItemID = std.ItemID and std.remainingquantity > 0 and (std.status = 1 or std.status = 2))left join itembrand br ON it.BrandID = br.BrandID left join tbl_search_key sk ON it.key_id = sk.search_key_id left join tbl_vehicle_models vm ON it.type_id = vm.vehicle_model_id left join supplier sp ON it.supplier_id = sp.SupplierID where it.status = 1 group by it.ItemID";
        // String query = "select i.*,br.BrandName,sk.key_name,ty.item_type_name,sp.SupplierName,(select sum(remainingquantity) from stockdetail where itemid = i.itemid and status = 1) as remaining_qty from item i inner join  itembrand br on i.BrandID = br.BrandID  left join tbl_search_key sk on i.key_id = sk.search_key_id left join tbl_item_type ty on i.type_id = ty.item_type_id left join supplier sp on i.supplier_id = sp.SupplierID where i.status = 1 order by i.ItemID";
        // query 2: "select i.itemid,br.BrandName,i.item_part_no,i.Description,i.sellingprice,sk.key_name,vm.vehicle_model_name,sp.SupplierName,i.re_order_qty, (select COALESCE(sum(remainingquantity),0) from stockdetail where itemid = i.itemid and (status = 1 or status = 2)) as remaining_qty,i.key_id,i.buyingprice from item i inner join  itembrand br on i.BrandID = br.BrandID  left join tbl_search_key sk on i.key_id = sk.search_key_id left join tbl_vehicle_models vm on i.type_id = vm.vehicle_model_id left join supplier sp on i.supplier_id = sp.SupplierID where i.status = 1 order by i.BrandID";
       // String query = "select it.ItemID, br.BrandName, it.item_part_no, it.Description, round(coalesce(std.selling_price, 0), 2) as selling_price, it.buyingprice as current_selling_price, sk.key_name, vm.vehicle_model_name, sp.SupplierName, it.re_order_qty, coalesce((sum(remainingquantity)), 0) as remaining_qty, it.key_id, round(coalesce(std.buying_price, 0), 2) as buying_price,std.StockID,it.commision,it.rack_no from item it left join stockdetail std ON (it.ItemID = std.ItemID and std.remainingquantity > 0 and (std.status = 1 or std.status = 2)) left join itembrand br ON it.BrandID = br.BrandID left join tbl_search_key sk ON it.key_id = sk.search_key_id left join tbl_vehicle_models vm ON it.type_id = vm.vehicle_model_id left join supplier sp ON it.supplier_id = sp.SupplierID where it.status = 1 group by it.ItemID , std.StockID";
        ResultSet data = DBHandler.getData(query);
        return data;
//        String query="select \n" +
//"    it.ItemID,\n" +
//"    br.BrandName,\n" +
//"    it.item_part_no,\n" +
//"    it.Description,\n" +
//"    round(coalesce(std.selling_price, 0), 2) as selling_price,\n" +
//"    it.buyingprice as current_selling_price,\n" +
//"    temp.quantityAvg,\n" +
//"    sk.key_name,\n" +
//"    ifnull(vm.vehicle_model_name,'-') as vehicle_model_name,\n" +
//"    sp.SupplierName,\n" +
//"    it.re_order_qty,\n" +
//"    coalesce((sum(remainingquantity)), 0) as remaining_qty,\n" +
//"    it.key_id,\n" +
//"    round(coalesce(std.buying_price, 0), 2) as buying_price,\n" +
//"    std.StockID,\n" +
//"    ifnull(it.commision,'-')as Salesman_Commision,\n" +
//"    ifnull(it.rack_no,'-')as Rack_No\n" +
//"from\n" +
//"    item it\n" +
//"inner join\n" +
//"(select \n" +
//"	     it.ItemID as itemID,\n" +
//"		coalesce((sum(remainingquantity)), 0) as remaining_qty,\n" +
//"	    ifnull(sum(remainingquantity* round(coalesce(std.buying_price, 0), 2)),0)as sub_tot,\n" +
//"		round(ifnull(ifnull(sum(remainingquantity* round(coalesce(std.buying_price, 0), 2)),0)/coalesce((sum(remainingquantity)), 0),0 ),2)as quantityAvg\n" +
//"      from\n" +
//"          item it\n" +
//"        left join\n" +
//"    stockdetail std ON (it.ItemID = std.ItemID\n" +
//"        and std.remainingquantity > 0\n" +
//"        and (std.status = 1 or std.status = 2))\n" +
//"where\n" +
//"    it.status = 1\n" +
//"group by it.ItemID ) as temp on temp.itemID = it.itemID\n" +
//"        left join\n" +
//"    stockdetail std ON (it.ItemID = std.ItemID\n" +
//"        and std.remainingquantity > 0\n" +
//"        and (std.status = 1 or std.status = 2))\n" +
//"        left join\n" +
//"    itembrand br ON it.BrandID = br.BrandID\n" +
//"        left join\n" +
//"    tbl_search_key sk ON it.key_id = sk.search_key_id\n" +
//"        left join\n" +
//"    tbl_vehicle_models vm ON it.type_id = vm.vehicle_model_id\n" +
//"        left join\n" +
//"    supplier sp ON it.supplier_id = sp.SupplierID\n" +
//"where \n" +
//"    it.status = 1 \n" +
//"\n" +
//"group by it.ItemID , std.StockID";
        
        
       
    }
    
    /////to select stock parts//////
    public static ResultSet getAllPartDataToSelect1() throws ClassNotFoundException, SQLException {
        
        String query = "select it.ItemID,br.BrandName,it.item_part_no,it.Description,round(coalesce(std.selling_price, 0), 2) as selling_price,round(coalesce(std.buying_price, 0), 2) as buying_price,sk.key_name,vm.vehicle_model_name,sp.SupplierName,it.re_order_qty,coalesce((sum(remainingquantity)), 0) as remaining_qty,it.key_id,std.StockID,it.commision,it.rack_no from item it left join stockdetail std ON (it.ItemID = std.ItemID and std.remainingquantity > 0 and (std.status = 1 or std.status = 2))left join itembrand br ON it.BrandID = br.BrandID left join tbl_search_key sk ON it.key_id = sk.search_key_id left join tbl_vehicle_models vm ON it.type_id = vm.vehicle_model_id left join supplier sp ON it.supplier_id = sp.SupplierID where it.status = 1 group by it.ItemID , std.StockID";
        ResultSet data = DBHandler.getData(query);
        return data;
    }

    //-----------------------------to select parts in purchase orders------------------------------------
    public static ResultSet getAllTataPartsForPurchase() throws ClassNotFoundException, SQLException {
        String query = "select it.ItemID, it.item_part_no,it.Description, it.buyingprice, tsk.key_name, tvm.vehicle_model_name, it.re_order_qty, ifnull((select StockDate from stock where StockID = (select max(stockid) from stockdetail where itemid = it.ItemID and (status = 1 or status = 2))), '0000-00-00') as last_stock_date, ifnull((select sum(remainingquantity) from stockdetail std where std.ItemID = it.ItemID and (std.status = 1 or std.status = 2)), 0) as remaining_qty from item it left join tbl_search_key tsk ON it.key_id = tsk.search_key_id and tsk.status = 1 left join tbl_vehicle_models tvm ON it.type_id = tvm.vehicle_model_id and tvm.status = 1 where it.status = 1 and it.BrandID = 1";
        //"Select i.ItemID AS IteID ,i.item_part_no,i.Description,i.buyingprice,sk.key_name,vm.vehicle_model_name,re_order_qty AS minimumStock,(select invoiced_date from deliverorder where  deliver_order_id=(select max(deliver_order_id) from deliverorder) ) AS lastPurchaseDate ,coalesce(round((select sum(quantity) from salesorderdetail where itemid=IteID and status=1)/(select  datediff(CURDATE(),(SELECT addeddate from item where ItemID=IteID))/30),2),0) As movement,coalesce((select sum(quantity) AS issuedQty from salesorderdetail where status=1 and date>=(select invoiced_date from deliverorder where  deliver_order_id=(select max(deliver_order_id) from deliverorder) )  and itemId=IteID),0) AS issuedQty,coalesce((select sum(qty) from deliverorderdetail where deliver_order_detail_id between (select min(deliver_order_detail_id) from deliverorderdetail where itemid=IteID) and  (select max(deliver_order_detail_id) from deliverorderdetail where itemid=IteID)),0) AS demoIssued, (select COALESCE(sum(remainingquantity),0) from stockdetail where itemid = IteID and (status = 1 or status = 2)) as remaining_qty from item i left join tbl_vehicle_models vm on i.type_id = vm.vehicle_model_id left join tbl_search_key sk on i.key_id=sk.search_key_id where i.status=1 and i.BrandID=1";
        ResultSet data = DBHandler.getData(query);
        return data;

    }

    public static ResultSet getAllPartDataToSelect(int partID) throws ClassNotFoundException, SQLException {
        String query = "select i.*,br.BrandName,sk.key_name,vm.vehicle_model_name,sp.SupplierName from item i inner join  itembrand br on i.BrandID = br.BrandID  left join tbl_search_key sk on i.key_id = sk.search_key_id left join tbl_vehicle_models vm on i.type_id = vm.vehicle_model_id left join supplier sp on i.supplier_id = sp.SupplierID where i.ItemID=" + partID + " and i.status = 1";
        ResultSet data = DBHandler.getData(query);
        return data;
    }
//--------remve

    public static ResultSet getAllPartDataToSelect(String select, String like) throws ClassNotFoundException, SQLException {
        String query = "select * from item where " + select + " like '" + like + "%' and status =1";
        ResultSet data = DBHandler.getData(query);
        return data;
    }

    public static ResultSet getAllPartDataForSearchKey(int searchkey) throws ClassNotFoundException, SQLException {
        String query = "select i.itemid,i.item_part_no,i.Description,i.sellingprice,b.brandname from item i inner join itembrand b ON i.BrandID = b.BrandID where i.key_id =" + searchkey + " and i.status = 1 order by i.itemid";
        ResultSet data = DBHandler.getData(query);
        return data;
    }

    public static void updatePartData() throws ClassNotFoundException, SQLException, JSONException, IOException, Exception {
        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String dealerAccount = rst.getString(3);
        String serverURL = rst.getString(9);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("dealer_acc_no", dealerAccount);
        JSONArray ja = new JSONArray();
        ja.put(jSONObject);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(serverURL + "dimo_lanka/pos_services/getUpdatedPartsForDealer?acc_no=" + ja);
        String url = serverURL + "dimo_lanka/pos_services/getUpdatedPartsForDealer";
        //new JSonJava().readJsonFromUrl(serverURL + "dimo_lanka/pos_services/getUpdatedPartsForDealer?acc_no=" + ja);
        JSonJava jSonJava = new JSonJava();
        JSONObject mainJSON = jSonJava.postJSONArray(ja, url, "acc_no");
        JSONObject partData = mainJSON.getJSONObject("part_data");
      System.out.println(partData);

        Iterator it = partData.keys();
        JSONObject subJSON = null;
        while (it.hasNext()) {
            subJSON = partData.getJSONObject(it.next().toString());
            String partNo = subJSON.getString("item_part_no");
            String description = subJSON.getString("description");
            double buyingPrice = Double.parseDouble(subJSON.get("selling_price").toString());
            
          System.out.println(partNo + "     " + buyingPrice+" "+description);
           
            Item item = new Item();
            item.setItemName(partNo);
            item.setItemDesc(description);
            item.setBuyingPrice(buyingPrice);
            ItemController.updateItemWithPrice(item);

        }

    }
//--------remve
//    public static void fillItemNames(JComboBox cmb) throws ClassNotFoundException, SQLException {
//
//        ResultSet rstItem = ItemController.getAllPartDataToSelect();
//        cmb.removeAllItems();
//        while (rstItem.next()) {
//            Item item = new Item();
//            item.setItemID(rstItem.getInt(1));
//            item.setItemName(rstItem.getString(2));
//            item.setSellingPrice(rstItem.getDouble(4));
//            item.setBrandName(rstItem.getString(5));
//            cmb.addItem(item);
//
//        }
//    }
//--------remve
//    public static void fillItemDescriptions(JComboBox cmb) throws ClassNotFoundException, SQLException {
//
//        ResultSet rstItem = ItemController.getAllPartDataToSelect();
//        cmb.removeAllItems();
//        while (rstItem.next()) {
//            SubItem item = new SubItem();
//            item.setItemID(rstItem.getInt(1));
//            item.setItemDesc(rstItem.getString(3));
//            item.setSellingPrice(rstItem.getDouble(4));
//            item.setBrandName(rstItem.getString(5));
//            cmb.addItem(item);
//
//        }
//    }

    public static int updateItemStatus(Item item) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "update item set status = 1 where item_part_no = ?";
        Object ob[] = {item.getItemName()};
        int status = DBHandler.setDataToDataBase(ob, con, sql);
        return status;

    }
}
