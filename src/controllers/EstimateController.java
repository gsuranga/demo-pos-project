/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author insaf
 */
public class EstimateController {
    public static void getItem(DefaultTableModel dtm) throws ClassNotFoundException, SQLException{
        String sql="SELECT tbl_search_key.key_name,item.item_part_no,item.Description,item.sellingprice FROM tbl_search_key tbl_search_key RIGHT JOIN item item ON tbl_search_key.search_key_id=item.key_id";
        ResultSet data = DBHandler.getData(sql);
        while(data.next()){
            Object row[]={data.getString("key_name"),data.getString("item_part_no"),data.getString("Description"),data.getDouble("sellingprice")};
            dtm.addRow(row);
            //jComboBox.addItem(data.getString("key_name")+"   "+data.getString("item_part_no")+"   "+data.getString("Description")+"   "+data.getDouble("sellingprice")+"   "+"(Rs)");
        }
    }
    public static void getcustomer(JComboBox cuscomb,JComboBox acccomb,Map cusid) throws ClassNotFoundException, SQLException{
       String sql2="SELECT id,customer_name,customer_acc_no FROM customer where status=1";
        ResultSet cusdata = DBHandler.getData(sql2);
       
        int i=0;
        while(cusdata.next()){
            cuscomb.addItem(cusdata.getString("customer_name"));
            acccomb.addItem(cusdata.getString("customer_acc_no"));
            cusid.put(String.valueOf(i), String.valueOf(cusdata.getInt("id")));
            
            i++;
          
        } 
       
        
    }
    public static void insertEstimate(String customerid,String date) throws ClassNotFoundException, SQLException{
        String sql="insert into tbl_estimate(customer_id,estimate_date) values('"+Integer.parseInt(customerid)+"','"+date+"') ";
        DBHandler.setData(sql);
    }
    public static void getEstimate(DefaultTableModel dtm) throws ClassNotFoundException, SQLException{
        String sql="Select c.customer_acc_no,c.customer_name,c.contact_no,e.estimate_date from customer c LEFT JOIN tbl_estimate e ON c.id=e.customer_id where e.status=1";
        ResultSet data = DBHandler.getData(sql);
        while (data.next()) {
            Object row[]={data.getString("estimate_date"),data.getString("customer_name"),data.getString("customer_acc_no"),data.getString("contact_no")};
            dtm.addRow(row);
             
            
        }
    }
}
