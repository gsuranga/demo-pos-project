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
import models.LoginHistroy;

/**
 *
 * @author insaf
 */
public class LogingHistroyController {
    public static int addLoginHistroy(LoginHistroy loginHistroy) throws ClassNotFoundException, SQLException{
        Connection connection=DBConnection.getConnection();
        String sql="insert into loginhistroy(lid,date,time) values(?,?,?)";
        Object[] row={loginHistroy.getLid(),loginHistroy.getDate(),loginHistroy.getTime()};
        int setDataToDataBase = DBHandler.setDataToDataBase(row,connection, sql);
        return setDataToDataBase;
        
    }
    public static ArrayList<LoginHistroy>getAllLogingHistroy() throws ClassNotFoundException, SQLException{
        String sql="Select * from loginhistroy";
        ResultSet data = DBHandler.getData(sql);
        ArrayList<LoginHistroy> logingh=new ArrayList<>();
        while(data.next()){
            LoginHistroy loginHistroy=new LoginHistroy();
            loginHistroy.setLid(data.getInt("lid"));
            loginHistroy.setDate(data.getString("date"));
            loginHistroy.setTime(data.getString("time"));
            loginHistroy.setEndTime(data.getString("logouttime"));
            logingh.add(loginHistroy);
        }
        return logingh;
    }
    
}
