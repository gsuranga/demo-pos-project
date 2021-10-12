/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.VatDis;
import static views.userviws.LogingUser_new.account_number;

/**
 *
 * @author Iresha Lakmali
 */
public class VatDisController {

    public static void main(String[] args) {
        VatDis vatDis = new VatDis();
                
                vatDis.setStatus("1");
                vatDis.setAccount_no("721S0002");
        try {
            get_discount(vatDis);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VatDisController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VatDisController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int insertVat(VatDis vatDis) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO `tbl_discount`(`account_code`, `vat`, `discount`, `status`) VALUES ('" + vatDis.getAccount_no() + "','" + vatDis.getVat() + "','" + vatDis.getDiscount() + "','" + vatDis.getStatus() + "')";
        int value = DBHandler.setData(sql);
        return value;

    }

    public static int updateVat(VatDis vatDis) throws ClassNotFoundException, SQLException {
        String query = "update tbl_discount set  vat = '" + vatDis.getVat() + "', discount = '" + vatDis.getDiscount() + "' where  account_code = '" + vatDis.getAccount_no() + "'";
        int value = DBHandler.setData(query);
        return value;
    }
    
    public static boolean vat_exsists(VatDis vatDis) throws ClassNotFoundException, SQLException{
        String sql = "SELECT * FROM `tbl_discount` WHERE account_code='"+vatDis.getAccount_no()+"'";
        ResultSet data = DBHandler.getData(sql);
        boolean next = data.next();
        if(next){
           return true;
        }
       return false;
        
    }
    
    public static VatDis get_discount(VatDis vatDis) throws ClassNotFoundException, SQLException{
        
        String sql = "SELECT * FROM `tbl_discount` WHERE account_code='"+vatDis.getAccount_no()+"'";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        String string_vat = data.getString("vat");
        System.out.println(""+string_vat);
        String string_dis = data.getString("discount");
        VatDis discount = new VatDis();
        discount.setDiscount(string_dis);
        discount.setVat(string_vat);
        return discount;
    }
    
    

}
