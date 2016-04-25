package POS_forms;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import POS_classes.DB;
import POS_classes.UIPanels;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class Users extends UIPanels {

	private static final long serialVersionUID = 7686999830925783105L;
	private JTextField textField;

	public Users() {
		
		
		
		ButtonGroup bg = new ButtonGroup();
		
		JButton btnSupervisor = new JButton("Supervisor");
		buttons_panel.add(btnSupervisor);
		
		JButton btnManager = new JButton("Manager");
		buttons_panel.add(btnManager);
		
		JRadioButton rdMgrPromote = new JRadioButton("Promote");
		buttons_panel.add(rdMgrPromote);
		
		JRadioButton rdMgrDemote = new JRadioButton("Demote");
		buttons_panel.add(rdMgrDemote);
		
		JLabel lblCashierId = new JLabel("Cashier ID");
		buttons_panel.add(lblCashierId);
		
		textField = new JTextField();
		buttons_panel.add(textField);
		textField.setColumns(10);

		bg.add(rdMgrDemote);
		bg.add(rdMgrPromote);
		
		
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
