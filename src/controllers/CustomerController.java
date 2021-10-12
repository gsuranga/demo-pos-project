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
import models.Customer;

/**
 *
 * @author SHDINESH
 */
public class CustomerController {

    public static int addNewCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into customer (customer_name,contact_no,address,customer_type,customer_acc_no,added_date,added_time,status) values (?,?,?,?,?,?,?,?)";
        Object[] row = {customer.getCustomerName(), customer.getContactNo(), customer.getAddress(), customer.getCustomerType(), customer.getAccountNo(), customer.getAddedDate(), customer.getAddedTime(), customer.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static void loadAllCustomers(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select c.id,c.customer_name,c.customer_acc_no,c.contact_no,c.address, ct.customer_type_name from customer c  left join tbl_customer_type ct ON c.customer_type = ct.customer_type_id where c.status = '1'";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
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

    public static int deleteCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        String sql = "update customer set status = '0' where id = '" + customer.getCustomerID() + "'";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static int getLastInsertedCustomerID() throws ClassNotFoundException, SQLException {
        String sql = "select last_insert_id() as last_id from customer";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    public static int updateCustomer(Customer customer) throws ClassNotFoundException, SQLException {
        String query = "update customer set  customer_name = '" + customer.getCustomerName() + "', customer_acc_no = '" + customer.getAccountNo() + "',contact_no= '" + customer.getContactNo() + "',address='" + customer.getAddress() + "',customer_type='" + customer.getCustomerType() + "' where  id = '" + customer.getCustomerID() + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static ResultSet getCustomerDetails(String where, String value) throws ClassNotFoundException, SQLException {
        String sql = "select * from customer where " + where + " = '" + value + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);

        return rst;
    }

    public static ResultSet getCustomertypes() throws ClassNotFoundException, SQLException {
        String sql = "select customer_type_name from tbl_customer_type where status=1";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static int getCustomertypeForName(String custTypeName) throws ClassNotFoundException, SQLException {
        String sql = "select customer_type_id from tbl_customer_type where customer_type_name = '" + custTypeName + "' and status=1";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        int typeID = rst.getInt(1);
        return typeID;
    }

    public static void fillCustomerNames(JComboBox cmb) throws ClassNotFoundException, SQLException {
        String query = "select customer_name,id from customer where status = '1'";
        ResultSet rstItem = DBHandler.getData(query);
        cmb.removeAllItems();
        while (rstItem.next()) {

            cmb.addItem(rstItem.getString(1));
            //lbl.setText(rstItem.getString(2));

        }
    }

    public static ResultSet setCustidbyname(String s) throws ClassNotFoundException, SQLException {
        String query = "select id from customer where customer_name ='" + s + "'";
        ResultSet rst = DBHandler.getData(query);
        return rst;

    }

    public static ResultSet setmodelid(String s) throws ClassNotFoundException, SQLException {
        String query = "select vehicle_model_id from tbl_vehicle_models where vehicle_model_name ='" + s + "'";
        ResultSet rst = DBHandler.getData(query);
        return rst;

    }
}
