package POS_forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import POS_classes.DB;
import POS_classes.DBTableModel;
import POS_classes.UIPanels;
import javax.swing.SwingConstants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DepositWithdrawl extends UIPanels {
	
	Connection connection =null; 
	
	public DepositWithdrawl() {
		
		
		connection = DB.conn();
		
		
		
		JButton btnWithdrawl = new JButton("Withdrawl");
		btnWithdrawl.setVerticalAlignment(SwingConstants.TOP);
		btnWithdrawl.setHorizontalAlignment(SwingConstants.LEFT);
		btnWithdrawl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		buttons_panel.add(btnWithdrawl);
		
		withdrawlAMT = new JTextField();
		buttons_panel.add(withdrawlAMT);
		withdrawlAMT.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			
				
			}
		});
		buttons_panel.add(btnDeposit);
		
		depositAMT = new JTextField();
		buttons_panel.add(depositAMT);
		depositAMT.setColumns(10);
		
		btnViewCurrentAmounts = new JButton("View Current Amounts");
		btnViewCurrentAmounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query= "select * from running_totals";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs= pst.executeQuery();
					table.setModel(model);
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		buttons_panel.add(btnViewCurrentAmounts);
		
		table = new JTable();
		pnl_table.add(table);
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
