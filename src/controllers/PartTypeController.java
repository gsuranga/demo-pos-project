/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author SHDINESH
 */
public class PartTypeController {

    public static ResultSet getAllPartTypes() throws ClassNotFoundException, SQLException {
        String query = "select item_type_id,item_type_name from tbl_item_type where status=1 order by item_type_id";
        ResultSet rst = DBHandler.getData(query);
        return rst;

    }

    public static int getPartTypeIDForName(String typeName) throws ClassNotFoundException, SQLException {
        String query = "select item_type_id from tbl_item_type where item_type_name = '" + typeName + "'";
        ResultSet rst = DBHandler.getData(query);
        int itemTypeID = 0;
        while (rst.next()) {
            itemTypeID = rst.getInt(1);
        }
        return itemTypeID;
    }
}
