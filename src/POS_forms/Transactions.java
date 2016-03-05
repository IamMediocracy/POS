package POS_forms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import POS_classes.UIPanels;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Transactions extends UIPanels {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	/**
	 * Create the panel.
	 */

	JButton btnOverride = new JButton("OVERRIDE");
	JButton btnVoid = new JButton("VOID");
	JButton btnPay = new JButton("PAY");

	JButton btnCash = new JButton("Cash");
	JButton btnCheck = new JButton("Check");
	JButton btnEft = new JButton("Credit/Debit Card");
	JButton btnCancel = new JButton("Cancel");

	public Transactions() {
		super();
		
		setManipButtons();

		btnPay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setPayButtons();
			}
		});

		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setManipButtons();
			}
		});

	}

	public void setManipButtons() {
		buttons_panel.removeAll();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{57, 51, 83, 0};
		gridBagLayout.rowHeights = new int[]{23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		buttons_panel.setLayout(gridBagLayout);
		
		btnVoid.setBounds(this.buttons_panel.getBounds().width / 2 - 150,
				this.buttons_panel.getBounds().height / 2 - 200, 100, 100);
		GridBagConstraints gbc_btnVoid = new GridBagConstraints();
		gbc_btnVoid.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnVoid.insets = new Insets(0, 0, 0, 5);
		gbc_btnVoid.gridx = 0;
		gbc_btnVoid.gridy = 0;
		buttons_panel.add(btnVoid, gbc_btnVoid);
		GridBagConstraints gbc_btnPay = new GridBagConstraints();
		gbc_btnPay.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnPay.insets = new Insets(0, 0, 0, 5);
		gbc_btnPay.gridx = 1;
		gbc_btnPay.gridy = 0;
		buttons_panel.add(btnPay, gbc_btnPay);
		GridBagConstraints gbc_btnOverride = new GridBagConstraints();
		gbc_btnOverride.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOverride.gridx = 2;
		gbc_btnOverride.gridy = 0;
		buttons_panel.add(btnOverride, gbc_btnOverride);
		
		buttons_panel.validate();
		buttons_panel.repaint();
	}

	public void setPayButtons() {
		buttons_panel.removeAll();
		
		buttons_panel.add(btnCash);
		buttons_panel.add(btnCheck);
		buttons_panel.add(btnEft);
		buttons_panel.add(btnCancel);

		buttons_panel.validate();
		buttons_panel.repaint();
	}

	public void disposeChildFrames() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTableInfo() {
		// This data is solely for testing purposes only
		String[] columnNames = { "Item Name", "UPC", "QTY", "Cost" };

		Object[][] data = { { "Hammer", "127846166244", new Integer(5), "15.97" },
				{ "Utility Knife", "0864365341523464", new Integer(3), "9.99" },
				{ "Screwdriver", "3476432043856", new Integer(2), "4.96" },
				{ "Nail", "12874562694501", new Integer(20), "5.27" },
				{ "Screw", "277492973558287364", new Integer(10), "4.79" } };

		// this.table = null;
		table = new JTable(data, columnNames);
		pnl_table.removeAll();
		tablepane = null;
		tablepane = new JScrollPane(table);

		pnl_table.add(tablepane);
		pnl_table.validate();
		pnl_table.repaint();
	}

}
