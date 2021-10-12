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
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import models.SearchCategory;

/**
 *
 * @author SHDINESH
 */
public class SearchCategoryController {

    public static ResultSet getAllSearchCategories() throws ClassNotFoundException, SQLException {
        String query = "select search_key_id,key_name from tbl_search_key where status=1 order by search_key_id";
        ResultSet rst = DBHandler.getData(query);
        return rst;

    }
    
    

    public static void loadAllItemSearchKeys(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select * from tbl_search_key where status = 1 order by search_key_id";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[4];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 4; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static int addNewCategory(SearchCategory sc) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_search_key (key_name,description,added_date,added_time,status) values (?,?,?,?,?)";
        Object[] row = {sc.getCategoryName(), sc.getDescription(), sc.getAddedDate(), sc.getAddedTime(), sc.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    
     public static int updateCategory(SearchCategory searchCategory) throws ClassNotFoundException, SQLException {
        String query = "update tbl_search_key set  key_name = '" + searchCategory.getCategoryName()+ "', description = '" + searchCategory.getDescription()+ "' where  search_key_id = '" + searchCategory.getCategoryID()+ "'";
        // System.out.println("query"+query);
        int value = DBHandler.setData(query);
        return value;
    }

    public static int deleteSearchKey(SearchCategory searchCategory) throws ClassNotFoundException, SQLException {

        String sql = "update tbl_search_key set status = '0' where search_key_id = " + searchCategory.getCategoryID() + "";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static int getSearchCategoryIDForName(String key) throws ClassNotFoundException, SQLException {
        String sql = "select search_key_id from tbl_search_key where key_name = '" + key + "' and status=1";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        return data.getInt(1);
    }

    public static void fillSearchKeys(JComboBox cmb) throws ClassNotFoundException, SQLException {

        ResultSet rstSearchCategories = SearchCategoryController.getAllSearchCategories();
        cmb.removeAllItems();
//        SearchCategory scAll = new SearchCategory();
//        scAll.setCategoryID(0);
        cmb.addItem("All");
        while (rstSearchCategories.next()) {
            // SearchCategory sc = new SearchCategory();
//            sc.setCategoryID(rstSearchCategories.getInt(1));
//            sc.setCategoryName(rstSearchCategories.getString(2));
            cmb.addItem(rstSearchCategories.getString(2));
        }
    }

}
