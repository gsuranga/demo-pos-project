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
import models.DIMOStockReturn;
import models.DIMOStockReturnDetails;

/**
 *
 * @author user-pc
 */
public class DIMOPartsReturnController {
    
    public static boolean addDIMOPartsReturnController (DIMOStockReturn dimoStockReturn, ArrayList<DIMOStockReturnDetails> ord) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        boolean checkInsert = false;
        boolean detailcheck = false;
        String sql = "insert into tbl_dimoparts_return(added_date,added_time,return_amount,status,dimo_ret_status) values(?,?,?,?,?)";
        Object data[] = {dimoStockReturn.getAddedDate(),dimoStockReturn.getAddedTime(),dimoStockReturn.getReturnAmount(),dimoStockReturn.getStatus(),dimoStockReturn.getDimoReturnStatus()};
        int setData = DBHandler.setDataToDataBase(data, con, sql);
        if (setData > 0) {
            String sql2 = "select max(returnID) as rid from tbl_dimoparts_return";
            ResultSet rst = DBHandler.getData(sql2);
            rst.next();
            int tcr = rst.getInt("rid");
            for (DIMOStockReturnDetails ors : ord) {
                String detailq = "Insert INTO tbl_dimoparts_return_detalis(returnID,stockID,ItemID,buying_price,return_quantity,status) values(?,?,?,?,?,?)";
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
    
     public static void updateStock(DIMOStockReturnDetails  dimoStockReturnDetails ) throws ClassNotFoundException, SQLException {
        String sql = "update stockdetail set remainingquantity = " + dimoStockReturnDetails.getQuantity() + " where StockID = " + dimoStockReturnDetails.getStockID() + " and ItemID = " + dimoStockReturnDetails.getItemID();
        DBHandler.setData(sql);

    }
    //####################Loard The DIMO stok return parts for Accepted Or Reject
     public static ResultSet getDIMOPartsStockRetdetail(int supplierID) throws ClassNotFoundException, SQLException {
        String queryPart = "and it.supplier_id = " + supplierID;
       String query = "select dsr.returnID, ifnull(dsrd.itemID,'-') as itemID,ifnull(dsrd.stockID,'-') as stockID, ifnull(it.item_part_no,'-')as item_part_no, dsrd.return_quantity, ifnull(dsrd.buying_price,'-')as buying_price, dsr.added_date, dsr.added_time, dsr.return_amount from tbl_dimoparts_return dsr left join tbl_dimoparts_return_detalis dsrd ON dsr.returnID= dsrd .returnID left join item it ON dsrd.itemID=it.ItemID where dsr.status=1 and dsr.dimo_ret_status=0";
        String fullSql = "";
        if (supplierID == 0) {
            fullSql = query;
        } else {
            fullSql = query + queryPart;
        }
        ResultSet rst = DBHandler.getData(fullSql);
        return rst;
    }
    public static int changeDIMOPartStatus(int dimo_ret_status,int returnID) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query="UPDATE `demolanka`.`tbl_dimoparts_return` SET `dimo_ret_status` = '"+dimo_ret_status+"' WHERE `tbl_dimoparts_return`.`returnID` = '"+returnID+"'";
       // String query = "UPDATE `demolanka`.`cheque` SET `cheque_status` = '" + cheque_status + "' WHERE `cheque`.`cheque_no` = '" + cheque_no + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;

    } 
    public static int updateStockDetailQuantityDIMOPart(int requested, int itemID, int stockID) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update stockdetail set remainingquantity = (remainingquantity+?)  where  StockID = ? and ItemID = ? ";
        Object row[] = {requested,  stockID, itemID};
        int value = DBHandler.setDataToDataBase(row, con, query);
        return value;
    }
     
     
    
}

