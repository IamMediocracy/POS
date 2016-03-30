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

public class Transactions extends UIPanels {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	private DBTableModel model;

	// List<>

	private static boolean priceCheck = false;

	JButton btnOverride = new JButton("OVERRIDE");
	JButton btnVoid = new JButton("VOID");
	JButton btnPay = new JButton("PAY");

	JButton btnCash = new JButton("Cash");
	JButton btnCheck = new JButton("Check");
	JButton btnEFT = new JButton("Credit/Debit Card");
	JButton btnCancel = new JButton("Cancel");

	JLabel lblName = new JLabel();
	JLabel lblPrice = new JLabel();
	JLabel lblQuantity = new JLabel();

	JFormattedTextField amountField;
	NumberFormat paymentFormat;
	private final JButton btnPriceCheck = new JButton("PRICE CHECK");
	private final JButton btnQuantityOnHand = new JButton("QOH\r\n");
	private final JButton btnRefund = new JButton("REFUND");
	private final JTextField txt_identifier = new JTextField();

	public Transactions() {
		super();
		pnl_table.setBackground(Color.LIGHT_GRAY);
		buttons_panel.setBackground(Color.BLUE);

		setManipButtons();

		btnPay.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 1, 1, 1 };
		gridBagLayout.rowHeights = new int[] { 1, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnl_table_info.setLayout(gridBagLayout);
		txt_identifier.setColumns(10);

		txt_identifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				itemScanned(txt_identifier.getText());
				txt_identifier.setText("");
			}
		});

		GridBagConstraints gbc_txt_identifier = new GridBagConstraints();
		gbc_txt_identifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt_identifier.gridx = 0;
		gbc_txt_identifier.gridy = 0;
		pnl_table_info.add(txt_identifier, gbc_txt_identifier);

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
		txt_identifier.requestFocus();
		buttons_panel.removeAll();
		buttons_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnOverride.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttons_panel.add(btnOverride);

		btnVoid.setFont(new Font("Tahoma", Font.BOLD, 12));

		btnVoid.setBounds(this.buttons_panel.getBounds().width / 2 - 150,
				this.buttons_panel.getBounds().height / 2 - 200, 100, 100);

		btnVoid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = table.getSelectedRow();
				if (selRow > -1) {
					model.removeRow(selRow);
				}
			}
		});

		buttons_panel.add(btnVoid);

		btnPriceCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				priceCheck = true;
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
		buttons_panel.add(btnEFT);
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
		amountField.requestFocus();
	}

	public void disposeChildFrames() {
		// TODO Auto-generated method stub

	}

	public void itemScanned(String identifier) {
		try {
			DB DB = new DB();
			if (!priceCheck) {
				String[] fields = { "itm_id AS 'UPC'", "itm_name AS 'Item'", "itm_price AS 'Price'" };
				String[] where = { "itm_id" };
				String[] criteria = { identifier };
				String table = "item";
				Object[] data = addRow(table, fields, where, criteria);
				if (data!=null) {
					model.addRow(data);
				}
			} else {
				PreparedStatement pstmnt = DB.conn
						.prepareStatement("select itm_name, itm_price, itm_quantity from item where itm_id = ?");
				pstmnt.setString(1, identifier);
				ResultSet rs = pstmnt.executeQuery();
				if (rs.next()) {
					lblName.setText(rs.getString("itm_name"));
					lblPrice.setText(rs.getString("itm_price"));
					lblQuantity.setText(rs.getString("itm_quantity"));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setTableInfo() throws ClassNotFoundException {
		// This data is solely for testing purposes only
		String[] columnNames = { "UPC", "Item", "Price" };

		Object[][] data = new Object[0][0];

		model = new DBTableModel(data, columnNames);
		table = new JTable(model);

		pnl_table.removeAll();

		tablepane = null;
		tablepane = new JScrollPane(table);

		pnl_table.add(tablepane);
		pnl_table.validate();
		pnl_table.repaint();

	}

}
