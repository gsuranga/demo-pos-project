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
import models.User;

/**
 *
 * @author insaf
 */
public class UserController {

    public static int addUser(User user) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO tbl_user(name,username,password,privilage,status,date,time,type) values(?,?,(select password(?)),?,?,?,?,?)";
        Object[] row = {user.getName(), user.getUserName(), user.getPasword(), user.getPrivilage(), "0", user.getDate(), user.getTime(), user.getType()};
        int setDataToDataBase = DBHandler.setDataToDataBase(row, connection, sql);
        return setDataToDataBase;
    }

    public static User searchUser(User user) throws ClassNotFoundException, SQLException {

        // Connection connection = DBConnection.getConnection();
        String sql = "Select * From tbl_user Where Username = '" + user.getUserName() + "' AND"
                + " Password = (Select password('" + user.getPasword() + "'))";

        ResultSet data = DBHandler.getData(sql);
        User userde = new User();

        if (data.next()) {

            int privilage = data.getInt("Privilage");
            int id = data.getInt("id");
            userde.setPrivilage(privilage);
            userde.setId(id);
            return userde;

        } else {
            userde.setId(-1);
            return userde;
        }

    }

    public static int updateStateUser(int id) throws ClassNotFoundException, SQLException {
        String sql = "update tbl_user set status='" + 1 + "' where id='" + id + "'";
        int setData = DBHandler.setData(sql);
        return setData;
    }

    public static ArrayList<User> getAllUser() throws ClassNotFoundException, SQLException {
        ArrayList<User> allUser = new ArrayList<>();
        String sql = "SELECT * FROM tbl_user";
        ResultSet data = DBHandler.getData(sql);
        while (data.next()) {

            int id = data.getInt("id");
            String name = data.getString("name");
            String userName = data.getString("username");
            String password = data.getString("password");
            int privilage = data.getInt("privilage");
            int status = data.getInt("status");
            String date = data.getString("date");
            String time = data.getString("time");
            String type = data.getString("type");

            User user = new User(name, userName, password, type, date, time);
            user.setId(id);

            allUser.add(user);

        }
        return allUser;
    }

    public static User getSelectedUser(int lid) throws ClassNotFoundException, SQLException {
        String sql = "select * from tbl_user where id='" + lid + "'";
        ResultSet data = DBHandler.getData(sql);
        User user = new User();
        while (data.next()) {
            user.setName(data.getString("name"));
            user.setDate(data.getString("date"));
            user.setUserName(data.getString("username"));
            user.setType(data.getString("type"));

        }
        return user;

    }

    public static int updateUser(User user, String userName) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "Update tbl_user set name=?,username=?,password=(select password(?)) where username=? ";
        Object[] row = {user.getName(), user.getUserName(), user.getPasword(), userName};
        int setDataToDataBase = DBHandler.setDataToDataBase(row, connection, sql);
        return setDataToDataBase;

    }

    public static int checkIsExit(String userName) throws ClassNotFoundException, SQLException {
        String sql = "select count(*) from tbl_user where username='" + userName + "'";
        ResultSet data = DBHandler.getData(sql);
        int a = 0;
        if (data.next()) {
            a = data.getInt("count(*)");
        }
        return a;
    }

    public static int getCurrentUser() throws SQLException, ClassNotFoundException {
        String sql = "select lid from loginhistroy order by max(id)";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        return rst.getInt(1);
    }
}
