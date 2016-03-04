package POS_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserData {

	// how many different permissions
	private final int  USRPRM= 3;

	boolean[] permissions = new boolean[USRPRM];

	String id;

	String first_name;

	public UserData(String id) {

		this.id = id;

		try {
			DB DB = new DB();
			Statement stmt = DB.conn.createStatement();

			ResultSet results = stmt.executeQuery("SELECT usr_fname FROM Users WHERE usr_id = '" + id + "';");

			if (results.next()) {
				first_name = results.getString(1);
			} else {
				first_name = "Error";
			}

			stmt = DB.conn.createStatement();

			results = stmt.executeQuery(
					"SELECT usr_cashier, usr_supervisor, usr_manager FROM user where usr_id = '"
							+ id + "';");

			if (results.next()) {

				for (int i = 0; i < USRPRM; i++) {
					permissions[i] = results.getBoolean(i + 1);
				}

			} else {
				System.out.println("Error with permission gathering.");
			}
			DB.closeDB();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getFirstName() {
		return first_name;
	}

	public boolean getCashier() {
		return permissions[0];
	}
	
	public boolean getSupervisor(){
		return permissions[1];
	}

	public boolean getManager() {
		return permissions[2];
	}

}
