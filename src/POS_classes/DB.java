package POS_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

	protected Connection conn;
	private String url = "<JDBC URL>";
	private String user = "<USERNAME>";
	private String pass = "<PASSWORD>";

	protected DB() throws SQLException {
			conn = DriverManager.getConnection(url, user, pass);
	}
	
	protected void closeDB() throws SQLException{
			this.conn.close();
		return;
	}

}
