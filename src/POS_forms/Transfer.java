package POS_forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import POS_classes.DB;
import POS_classes.UIPanels;
import POS_utils.SelectBuilder;

public class Transfer extends UIPanels {

	public Transfer() {
		super();

		DB DB;
		try {
			DB = new DB();
			
			JButton btnViewCurrentAmounts = new JButton("View Current Amounts");
			JButton btnWithdrawl = new JButton("Withdrawl");
			btnWithdrawl.setVerticalAlignment(SwingConstants.TOP);
			btnWithdrawl.setHorizontalAlignment(SwingConstants.LEFT);
			btnWithdrawl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					try {
						
						String query = "update running_totals set money_total = money_total + "
								+ withdrawlAMT.getText() + " WHERE location_name ='drawer1' "
								+ "UNION update running_totals set money_total = money_total - "
								+ withdrawlAMT.getText() + " WHERE location_name ='safe' ";
						PreparedStatement pst = DB.conn.prepareStatement(query);
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
						
						String query = "update running_totals set money_total = money_total - " + depositAMT.getText()
								+ " WHERE location_name ='drawer1' "
								+ "UNION update running_totals set money_total = money_total + " + depositAMT.getText()
								+ " WHERE location_name ='safe' ";
						PreparedStatement pst;
						pst = DB.conn.prepareStatement(query);
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
									.from("running_totals").where("location_name = 'safe' OR location_name = 'drawer1'");
							PreparedStatement pst = DB.conn.prepareStatement(sqlBuilder.toString());
							executeQuery(pst);
							setTableInfo();
						
						JOptionPane.showMessageDialog(null, "Worked");
						pst.close();
						
					//	rs.close();
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error: Cannot Connect to Database");
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
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

	private JTextField withdrawlAMT;
	private JTextField depositAMT;
	private JButton btnViewCurrentAmounts;

}
