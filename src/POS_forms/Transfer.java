package POS_forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import POS_classes.DB;
import POS_classes.DBTableModel;
import POS_classes.UIPanels;
import POS_utils.SelectBuilder;
import net.proteanit.sql.DbUtils;

public class Transfer extends UIPanels {

	Connection connection = null;

	public Transfer() {
		super();
		tablepane.setBounds(111, 5, 2, 2);
		pnl_table.setLayout(null);


		DB DB;
		try {
			DB = new DB();
			connection = DB.conn;
			
			JButton btnViewCurrentAmounts = new JButton("View Current Amounts");
			JButton btnWithdrawl = new JButton("Withdrawl");
			btnWithdrawl.setVerticalAlignment(SwingConstants.TOP);
			btnWithdrawl.setHorizontalAlignment(SwingConstants.LEFT);
			btnWithdrawl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					try {
						
						// Need to change to Select builder
						String query = "update running_totals set money_total = money_total + '"
								+ withdrawlAMT.getText() + "' WHERE location_name ='drawer1' "
								+ "UNION update running_totals set money_total = money_total - '"
								+ withdrawlAMT.getText() + "' WHERE location_name ='safe' ";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Funds withdrawn from Safe");

						pst.close();

					} catch (SQLException e) {
						
						JOptionPane.showMessageDialog(null, "Error: Please Check Withdrawal Amount");
						
						e.printStackTrace();
					}

				}
			});
			
			buttons_panel.add(btnViewCurrentAmounts);
			
			buttons_panel.add(btnWithdrawl);

			withdrawlAMT = new JTextField();
			withdrawlAMT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			withdrawlAMT.setColumns(10);
			buttons_panel.add(withdrawlAMT);

			JButton btnDeposit = new JButton("Deposit");
			btnDeposit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						
						// Need to change to Select builder
						String query = "update running_totals set money_total = money_total - '" + depositAMT.getText()
								+ "' WHERE location_name ='drawer1' "
								+ "UNION update running_totals set money_total = money_total + '" + depositAMT.getText()
								+ "' WHERE location_name ='safe' ";
						PreparedStatement pst;
						pst = connection.prepareStatement(query);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Funds deposited to Safe");
						pst.close();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error: Please Check Deposit Amount");
						e1.printStackTrace();
					}

				}
			});
			buttons_panel.add(btnDeposit);

			depositAMT = new JTextField();
			buttons_panel.add(depositAMT);
			depositAMT.setColumns(10);

			
			btnViewCurrentAmounts.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						
							SelectBuilder sqlBuilder = new SelectBuilder().column("Money_total").column("location_name")
									.from("running_totals").where("location_name = 'safe' AND 'drawer1'");
							PreparedStatement pst = DB.conn.prepareStatement(sqlBuilder.toString());
							executeQuery(pst);
						
						// need to figure out how to see table in panel	
							
							//ResultSet rs = pst.executeQuery();
						//table.setModel(DbUtils.resultSetToTableModel(rs));
						
						JOptionPane.showMessageDialog(null, "Worked");
						pst.close();
						
					//	rs.close();
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error: Cannot Connect to Database");
						e1.printStackTrace();
					}
				}
			});

		} catch(SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
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
