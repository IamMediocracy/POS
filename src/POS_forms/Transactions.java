package POS_forms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import POS_classes.UIPanels;

public class Transactions extends UIPanels {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	/**
	 * Create the panel.
	 */
	
	
	
	public Transactions() {
		super();
		
		JButton btnVoid = new JButton("VOID");
		btnVoid.setBounds(this.buttons_panel.getBounds().width / 2 - 150, this.buttons_panel.getBounds().height / 2 - 200, 100, 100);
		buttons_panel.add(btnVoid);
		
		JButton btnPay = new JButton("PAY");
		buttons_panel.add(btnPay);
		btnPay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttons_panel.removeAll();
				JButton btnCash = new JButton("Cash");
				buttons_panel.add(btnCash);
				
				JButton btnCheck = new JButton("Check");
				buttons_panel.add(btnCheck);
				
				JButton btnEft = new JButton("Credit/Debit Card");
				buttons_panel.add(btnEft);
				
				buttons_panel.validate();
				buttons_panel.repaint();
			}
		});
		JButton btnOverride = new JButton("OVERRIDE");
		buttons_panel.add(btnOverride);

	}

	private void refresh() {
		/*
		 * TODO Gets messages again after pressing refresh button, not used yet
		 */
	}

	public void disposeChildFrames() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTableInfo() {
		// This data is solely for testing purposes only
		String[] columnNames = { "Item Name", "UPC", "QTY", "Cost" };

		Object[][] data = { { "Hammer", "127846166244", new Integer(5), "15.97"},
				{ "Utility Knife", "0864365341523464", new Integer(3), "9.99"},
				{ "Screwdriver", "3476432043856", new Integer(2), "4.96"},
				{ "Nail", "12874562694501", new Integer(20), "5.27"},
				{ "Screw", "277492973558287364", new Integer(10), "4.79"} };
		
//		this.table = null;
		table = new JTable(data, columnNames);
		pnl_table.removeAll();
		tablepane = null;
		tablepane = new JScrollPane(table);
		
		pnl_table.add(tablepane);
		pnl_table.validate();
		pnl_table.repaint();
	}

}
