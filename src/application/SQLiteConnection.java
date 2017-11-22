package application;

import java.sql.*;

/**
 * http://tutorials.jenkov.com/jdbc/overview.html
 * @author Marisa
 *
 */

public class SQLiteConnection {

	//prepare connection with sqlite database via JDBC
	public static Connection Connector() {

		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:template.sqlite");
			return connection;

		} catch (Exception e) {
			return null;

		}
	}
}
