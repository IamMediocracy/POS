package POS_forms;

import java.awt.BorderLayout;

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
		
		JButton btnChk = new JButton("chk");
		buttons_panel.add(btnChk);

		JButton btnVoid = new JButton("VOID");
		btnVoid.setBounds(this.buttons_panel.getBounds().width / 2 - 150, this.buttons_panel.getBounds().height / 2 - 200, 100, 100);
		buttons_panel.add(btnVoid);
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
		
		this.table = null;
		this.table = new JTable(data, columnNames);
		this.pnl_table.removeAll();
		this.tablepane = null;
		this.tablepane = new JScrollPane(this.table);
		
		this.pnl_table.add(this.table);
		this.pnl_table.validate();
		this.pnl_table.repaint();
	}

}
