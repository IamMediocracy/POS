package POS_forms;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import POS_classes.DB;
import POS_classes.UIPanels;

public class Users extends UIPanels {

	private static final long serialVersionUID = 7686999830925783105L;

	public Users() {

	}

	public void addCashier() throws SQLException {
		DB DB = new DB();
		PreparedStatement pstmt = DB.conn.prepareStatement(
				"select usr_id, usr_fname, usr_lname, usr_cashier from user where usr_cashier = true");

	}

	@Override
	public void setTableInfo() throws ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
