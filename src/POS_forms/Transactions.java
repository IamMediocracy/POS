package POS_forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import POS_classes.DB;
import POS_classes.UIPanels;

public class Transactions extends UIPanels {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	// List<>

	private static boolean priceCheck = false;

	JButton btnOverride = new JButton("OVERRIDE");
	JButton btnVoid = new JButton("VOID");
	JButton btnPay = new JButton("PAY");

	JButton btnCash = new JButton("Cash");
	JButton btnCheck = new JButton("Check");
	JButton btnEft = new JButton("Credit/Debit Card");
	JButton btnCancel = new JButton("Cancel");

	JFormattedTextField amountField;
	NumberFormat paymentFormat;
	private final JButton btnPriceCheck = new JButton("PRICE CHECK");
	private final JButton btnQuantityOnHand = new JButton("QOH\r\n");
	private final JButton btnRefund = new JButton("REFUND");
	private final JTextField txt_identifier = new JTextField();

	public Transactions() {
		super();
		txt_identifier.setColumns(10);
		pnl_table.setBackground(Color.LIGHT_GRAY);
		buttons_panel.setBackground(Color.BLUE);

		setManipButtons();

		btnPay.setFont(new Font("Tahoma", Font.BOLD, 12));

		txt_identifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemScanned(txt_identifier.getText());
				txt_identifier.setText("");
			}
		});

		pnl_table_info.add(txt_identifier);

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
		buttons_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnOverride.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttons_panel.add(btnOverride);
		btnVoid.setFont(new Font("Tahoma", Font.BOLD, 12));

		btnVoid.setBounds(this.buttons_panel.getBounds().width / 2 - 150,
				this.buttons_panel.getBounds().height / 2 - 200, 100, 100);
		buttons_panel.add(btnVoid);
		btnPriceCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnPriceCheck.setForeground(Color.BLACK);
		btnPriceCheck.setFont(new Font("Tahoma", Font.BOLD, 12));

		btnQuantityOnHand.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnQuantityOnHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		buttons_panel.add(btnQuantityOnHand);

		buttons_panel.add(btnPriceCheck);
		btnRefund.setFont(new Font("Tahoma", Font.BOLD, 12));

		buttons_panel.add(btnRefund);
		buttons_panel.add(btnPay);

		buttons_panel.validate();
		buttons_panel.repaint();
	}

	public void setPayButtons() {
		buttons_panel.removeAll();

		buttons_panel.add(btnCash);
		buttons_panel.add(btnCheck);
		buttons_panel.add(btnEft);
		buttons_panel.add(btnCancel);

		paymentFormat = NumberFormat.getNumberInstance();
		paymentFormat.setMinimumFractionDigits(2);
		paymentFormat.setMaximumFractionDigits(2);
		amountField = new JFormattedTextField(paymentFormat);
		amountField.setColumns(10);
		amountField.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {

			}
		});
		buttons_panel.add(amountField);

		buttons_panel.validate();
		buttons_panel.repaint();
	}

	public void disposeChildFrames() {
		// TODO Auto-generated method stub

	}

	public void itemScanned(String identifier) {
		try {
			DB DB = new DB();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
