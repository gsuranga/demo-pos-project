/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dinesh
 */
public class DBConnection {

	private static DBConnection dBConnection;
	private Connection connection;

	private DBConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
               //connection = DriverManager.getConnection("jdbc:mysql://localhost/dimo_clear", "root", "");
		connection = DriverManager.getConnection("jdbc:mysql://localhost/demolanka", "root", "");
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if (dBConnection == null) {
			dBConnection = new DBConnection();
		}

		return dBConnection.connection;

	}
}
