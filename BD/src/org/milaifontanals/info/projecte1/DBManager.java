package org.milaifontanals.info.projecte1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static Connection con = null;

	static
	{
		String url = "jdbc:oracle:thin:@//192.168.1.199:1521/XEPDB1";
		String user = "PROJECTE";
		String pass = "alumne";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
        
	public static Connection getConnection()
	{
		return con;
	}
}

