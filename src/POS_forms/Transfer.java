package POS_forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import POS_classes.DB;
import POS_classes.DBTableModel;
import POS_classes.UIPanels;

public class Transfer extends UIPanels {

	Connection connection = null;

	public Transfer() {

		DB DB;
		try {
			DB = new DB();
			connection = DB.conn;

			JButton btnWithdrawl = new JButton("Withdrawl");
			btnWithdrawl.setVerticalAlignment(SwingConstants.TOP);
			btnWithdrawl.setHorizontalAlignment(SwingConstants.LEFT);
			btnWithdrawl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					try {
						String query = "";
						PreparedStatement pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						table.setModel(model);
						pst.close();
						rs.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			buttons_panel.add(btnWithdrawl);

			withdrawlAMT = new JTextField();
			buttons_panel.add(withdrawlAMT);
			withdrawlAMT.setColumns(10);

			JButton btnDeposit = new JButton("Deposit");
			btnDeposit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						String query = "";
						PreparedStatement pst;
						pst = connection.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						table.setModel(model);
						pst.close();
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			});
			buttons_panel.add(btnDeposit);

			depositAMT = new JTextField();
			buttons_panel.add(depositAMT);
			depositAMT.setColumns(10);

			btnViewCurrentAmounts = new JButton("View Current Amounts");
			btnViewCurrentAmounts.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String query = "select * from running_totals";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();

				}
			});
			buttons_panel.add(btnViewCurrentAmounts);

			table = new JTable();
			pnl_table.add(table);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private static final long serialVersionUID = 6047803356240229042L;

	private DBTableModel model;
	private JTextField withdrawlAMT;
	private JTextField depositAMT;
	private JButton btnViewCurrentAmounts;
	private JTable table;

	@Override
	public void setTableInfo() throws ClassNotFoundException {

	}
}
