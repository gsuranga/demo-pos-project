/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author SHdinesh Madushanka
 */
public class AutoGenerateID {

    private static Connection connection;

    public static String generateNextID(String prefix, String table) throws ClassNotFoundException, SQLException {
        String id = getId(table);

        int index = 0;
        int intValue;
        for (int i = id.length() - 1; i > 0; i--) {
            try {
                intValue = Integer.parseInt(id.substring(i - 1));

            } catch (NumberFormatException numberFormatException) {
                index = i + 1;
                break;
            }

        }
        int val = Integer.parseInt(id.substring(index)) + 1;
        String stringValue = String.valueOf(val);
        System.out.println(stringValue);
        String generatedID = "";
        if (stringValue.length() == 1) {
            generatedID = prefix + "0000" + stringValue;
        } else if (stringValue.length() == 2) {
            generatedID = prefix + "000" + stringValue;

        } else if (stringValue.length() == 3) {
            generatedID = prefix + "00" + stringValue;

        } else if (stringValue.length() == 4) {
            generatedID = prefix + "0" + stringValue;

        } else {
            generatedID = prefix + stringValue;
        }
        return generatedID;

    }

    public static String getId(String table) throws ClassNotFoundException, SQLException {
        connection = DBConnection.getConnection();
        String sql = "SELECT * FROM " + table + " where status = 1 or status = 2 or status = 4 ORDER BY 1 DESC LIMIT 1";
        ResultSet resultSet = DBHandler.getData(sql);
        resultSet.next();
        String id = null;
        id = resultSet.getString(2);
        return id;
    }

    public static String generateNextDBIDOnEmptyResultSet(String prefix) {
        String newID = prefix + "00001";
        return newID;
    }
}
