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
import models.CustomerReturnDetail;
import models.OtherSupplierReturn;

import models.OtherSupplierReturnDetail;
import models.StockDetail;

/**
 *
 * @author HP2000
 */
public class OtherSupplierReturnController {
   public static boolean addNewOtherSupplierReturn(OtherSupplierReturn othersupplirReturn, ArrayList<OtherSupplierReturnDetail> ord) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        boolean checkInsert = false;
        boolean detailcheck = false;
        String sql = "insert into tbl_othesup_returns(return_date,return_time,return_amount,status,other_return_status) values(?,?,?,?,?)";
        Object data[] = {othersupplirReturn.getReturnDate(),othersupplirReturn.getReturnTime(),othersupplirReturn.getReturnAmount(),othersupplirReturn.getStatus(),othersupplirReturn.getOtherReturnStatus()};
        int setData = DBHandler.setDataToDataBase(data, con, sql);
        if (setData > 0) {
            String sql2 = "select max(return_id) as rid from tbl_othesup_returns";
            ResultSet rst = DBHandler.getData(sql2);
            rst.next();
            int tcr = rst.getInt("rid");
            for (OtherSupplierReturnDetail ors : ord) {
                String detailq = "Insert INTO tbl_othesrup_return_details(return_id,stockID,ItemID,buying_price,return_quantity,status) values(?,?,?,?,?,?)";
                Object detailData[] = {tcr,ors.getStockID(),ors.getItemID(), ors.getBuyingPrice(), ors.getReturnQuantity(),ors.getStatus()};
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
    public static void updateStock(OtherSupplierReturnDetail otherSupplierReturnDetail) throws ClassNotFoundException, SQLException {
        String sql = "update stockdetail set remainingquantity = " + otherSupplierReturnDetail.getQuantity() + " where StockID = " + otherSupplierReturnDetail.getStockID() + " and ItemID = " + otherSupplierReturnDetail.getItemID();
        DBHandler.setData(sql);

    }
    
   public static ResultSet getOtherSupplierReturns(int supplierID) throws ClassNotFoundException, SQLException {
        String queryPart = "and it.supplier_id = " + supplierID;
       String query = "select osr.return_id,ifnull(osrd.itemID,'-') as  itemID,ifnull(it.item_part_no,'-')as item_part_no,osrd.return_quantity,ifnull(osrd.buying_price,'-')as buying_price,osr.return_date,osr.return_time,osr.return_amount from tbl_othesup_returns osr left join tbl_othesrup_return_details osrd ON osr.return_id = osrd.return_id left join item it ON osrd.itemID=it.ItemID where osr.status=1";
        String fullSql = "";
        if (supplierID == 0) {
            fullSql = query;
        } else {
            fullSql = query + queryPart;
        }
        ResultSet rst = DBHandler.getData(fullSql);
        return rst;
    }
}
