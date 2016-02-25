package POS_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SessionData {

	// how many different permissions
	private final int USRPRM = 8;

	int[] permissions = new int[USRPRM];

	String username;

	String first_name;

	// final boolean;
	// HashMap<String, Integer> permissions = new HashMap<>();

	public SessionData(String username) {

		/*
		 * Use the username to receive the data
		 */

		this.username = username;

		DB DB = new DB();

		try {
			Statement stmt = DB.conn.createStatement();

			ResultSet results = stmt.executeQuery("SELECT usr_fname FROM Users WHERE usr_id = '" + username + "';");

			if (results.next()) {
				first_name = results.getString(1);
			} else {
				first_name = "Error";
			}

			stmt = DB.conn.createStatement();

			results = stmt.executeQuery(
					"SELECT usr_prm_warehouse, usr_prm_messages, usr_prm_plans, usr_prm_shipments, usr_prm_products, usr_prm_workorders, usr_prm_restock, usr_prm_admin From Users where usr_id = '"
							+ username + "';");

			if (results.next()) {

				for (int i = 0; i < USRPRM; i++) {
					permissions[i] = results.getInt(i + 1);
				}

			} else {
				System.out.println("Error with permission gathering.");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DB.closeDB();
	}

	public String getFirstName() {
		return first_name;
	}

	public int getHubPermissions() {

		return permissions[1];

	}

	public int getPlansPermissions() {

		return permissions[2];
	}

	public int getWarehousePermissions() {

		return permissions[0];
	}

	public int getShipmentsPermissions() {

		return permissions[3];
	}

	public int getProductsPermissions() {

		return permissions[4];
	}

	public int getWorkOrdersPermissions() {
		return permissions[5];
	}

	public int getRestockPermissions() {
		return permissions[6];
	}

	public int getUsersPermissions() {
		return permissions[7];
	}

}
