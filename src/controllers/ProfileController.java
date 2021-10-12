/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.DealerProfile;

/**
 *
 * @author shdinesh.99
 */
public class ProfileController {

    public static ResultSet getDealerDetails() throws ClassNotFoundException, SQLException {
        String sql = "select * from tbl_dealer_profile where status = 1";
        ResultSet data = DBHandler.getData(sql);
        return data;
    }

    public static int updateProfile(DealerProfile dealerProfile) throws ClassNotFoundException, SQLException {
        String query = "update tbl_dealer_profile set  dealer_name = '" + dealerProfile.getDealerName() + "', dealer_mobile_no = '" + dealerProfile.getDealerMobileNo() + "',email= '" + dealerProfile.getEmailAddress() + "',dealer_address='" + dealerProfile.getAddress() + "' where  dealer_id = '" + dealerProfile.getDealerID() + "'";
        int value = DBHandler.setData(query);
        return value;
    }

}
