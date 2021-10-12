/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Iresha
 */
public class DBHandler {

    public static int setData(String qurey) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(qurey);
        return result;

    }

    public static ResultSet getData(String qurey) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qurey);
        return resultSet;

    }

    public static int setDataToDataBase(Object[] data, Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < data.length; i++) {
            preparedStatement.setObject((i + 1), data[i]);

        }
        int res = preparedStatement.executeUpdate();

        return res;
    }

}
