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
import java.sql.Statement;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import POS_classes.DB;
import POS_classes.UIPanels;
import POS_utils.SelectBuilder;
import java.awt.GridLayout;

public class Transactions extends UIPanels {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	private static boolean priceCheck = false;

	JButton btnOverride = new JButton("OVERRIDE");
	JButton btnVoid = new JButton("VOID");
	JButton btnPay = new JButton("PAY");

	JButton btnCash = new JButton("Cash");
	JButton btnCheck = new JButton("Check");
	JButton btnEFT = new JButton("Credit/Debit Card");
	JButton btnCancel = new JButton("Cancel");

	JButton btnPaper = new JButton("Paper");
	JButton btnEmail = new JButton("Email");
	JButton btnNone = new JButton("No Receipt");

	JButton btnSelTrans = new JButton(new ImageIcon(POSMain.class.getResource("/media/ResumeTransaction.png")));
	JButton btnCancelTrans = new JButton(new ImageIcon(POSMain.class.getResource("/media/CancelTransaction.png")));
	JButton btnNewTrans = new JButton(new ImageIcon(POSMain.class.getResource("/media/NewTransaction.png")));

	JLabel lblName = new JLabel();
	JLabel lblPrice = new JLabel();
	JLabel lblQuantity = new JLabel();

	JFormattedTextField amountField;
	NumberFormat paymentFormat;
	JButton btnPriceCheck = new JButton(new ImageIcon(POSMain.class.getResource("/media/test.png")));
	JButton btnQuantityOnHand = new JButton("QOH\r\n");
	JButton btnRefund = new JButton("REFUND");
	private final JTextField txt_identifier = new JTextField();

	private String userID;

	private int trnsID = 0;

	public Transactions(String userID) {
		super();
		this.userID = userID;
		pnl_table.setBackground(Color.LIGHT_GRAY);
		buttons_panel.setBackground(Color.BLUE);

		setButtonProperties();

		selectTransButtons();

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
		
		buttons_panel.setLayout(new GridLayout(0, 1, 0, 0));

		try {
			lookupTransaction(trnsID, false, false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void setButtonProperties() {
		// Main Transation buttons
		btnVoid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = table.getSelectedRow();
				if (selRow > -1) {
					model.removeRow(selRow);
					if (selRow < table.getRowCount()) {
						table.requestFocus();
						table.changeSelection(selRow, 0, false, false);
						table.changeSelection(selRow, 3, true, true);
					}
				}
			}
		});

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

		btnPriceCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (priceCheck)
					priceCheck = false;
				else
					priceCheck = true;
			}
		});

		btnQuantityOnHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		btnPriceCheck.setBorder(null);
		btnPriceCheck.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/test_selected.png")));
		btnPriceCheck.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/test_selected_current.png")));

		// Payment Buttons
		addReceiptListener(btnCash);
		addReceiptListener(btnCheck);
		addReceiptListener(btnEFT);

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

		// Previous transactions buttons
		btnSelTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setTransaction();
					setManipButtons();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnCancelTrans.setBorder(null);
		btnCancelTrans.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/test_selected.png")));
		btnCancelTrans.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/test_selected_current.png")));
		btnCancelTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO DELETE FROM transaction WHERE trns_id = Transaction#;
			}
		});

		btnNewTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					startNewTransaction();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void setManipButtons() {
		txt_identifier.requestFocus();
		buttons_panel.removeAll();

		buttons_panel.add(btnOverride);
		buttons_panel.add(btnVoid);
		buttons_panel.add(btnQuantityOnHand);
		buttons_panel.add(btnPriceCheck);
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
		buttons_panel.add(amountField);

		buttons_panel.validate();
		buttons_panel.repaint();
		amountField.requestFocus();
	}

	public void addReceiptListener(JButton button) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}

	public void receipt() {
		buttons_panel.removeAll();

		buttons_panel.add(btnPaper);
		buttons_panel.add(btnEmail);
		buttons_panel.add(btnNone);

		buttons_panel.validate();
		buttons_panel.repaint();
	}

	public void itemScanned(String identifier) {
		try {
			DB DB = new DB();
			if (!priceCheck) {
				SelectBuilder sqlBuilder = new SelectBuilder().column("itm_id AS 'UPC'").column("itm_name AS 'Item'")
						.column("itm_price AS 'Price'").from("item").where("itm_id = ?");
				PreparedStatement pstmt = DB.conn.prepareStatement(sqlBuilder.toString());
				pstmt.setString(1, identifier);
				executeQuery(pstmt);
				if (data != null) {
					try {
						insertRow();
					} catch (SQLException e) {
						DB.conn.rollback();
						// } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tablepane.validate();
					tablepane.repaint();
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
			DB.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void lookupTransaction(int trnsID, boolean hold, boolean finalized) throws SQLException {

		SelectBuilder sqlBuilder = new SelectBuilder().column("trns_id AS 'Transaction#'")
				.column("trns_total AS 'Total'").column("usr_id AS 'User'").from("transaction");

		sqlBuilder.where("trns_on_hold = ?").where("trns_finalized = ?");
		if (trnsID > 0) {
			sqlBuilder.where("trns_id = " + trnsID);

		}
		DB DB = new DB();
		PreparedStatement pstmt = DB.conn.prepareStatement(sqlBuilder.toString());
		pstmt.setBoolean(1, hold);
		pstmt.setBoolean(2, finalized);
		executeQuery(pstmt);
		if (data.length > 0) {
			if (data.length > 1) {
				try {
					setTableInfo();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// setTransaction();
			}
		} else {
			startNewTransaction();
			lookupTransaction(trnsID, hold, finalized);
		}

	}

	public void selectTransButtons() {
		buttons_panel.removeAll();

		buttons_panel.add(btnSelTrans);
		buttons_panel.add(btnCancelTrans);
		buttons_panel.add(btnNewTrans);

		buttons_panel.validate();
		buttons_panel.repaint();
	}

	public void setTransaction() throws SQLException {
		trnsID = (int) model.getValueAt(table.getSelectedRow(), 0);
		try {
			// selectRows(new String[]{"item","receipt_line"/*,"transaction"*/},
			// new String[]{"itm_id"}, new String[]{"rct_line AS 'Line'","itm_id
			// AS 'UPC'",/*"itm_name AS 'Name'",*/"itm_price AS 'Price'"}, new
			// String[]{"trns_id"}, new Object[]{trnsID});
			// String sql = "Select item_id, "
			SelectBuilder sqlBuilder = new SelectBuilder().column("item.itm_id AS 'UPC'")
					.column("item.itm_name AS 'Name'").column("receipt_line.rct_line_price AS 'Price'").from("item")
					.join("receipt_line").where("item.itm_id = receipt_line.itm_id").where("receipt_line.trns_id=?");
			DB DB = new DB();
			PreparedStatement pstmt = DB.conn.prepareStatement(sqlBuilder.toString());
			pstmt.setInt(1, trnsID);
			executeQuery(pstmt);
			setTableInfo();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void startNewTransaction() throws SQLException {

		String sql = "INSERT INTO transaction (trns_subtotal,trns_tax,trns_total,trns_on_hold,trns_finalized,usr_id)"
				+ "VALUES (0,0,0,false,false,'" + userID + "')";
		DB DB = new DB();
		Statement stmt = DB.conn.createStatement();
		stmt.executeQuery(sql);

		DB.conn.commit();

		columnNames = new String[] { "UPC", "Item", "Price" };
		data = new Object[0][0];
		try {
			setTableInfo();
			setManipButtons();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertRow() throws SQLException {
		DB DB = new DB();

		String insert = "INSERT INTO receipt_line (trns_id,itm_id,rct_line_price) VALUES ( ?,?,?);";

		PreparedStatement pstmt = DB.conn.prepareStatement(insert);

		pstmt.setInt(1, trnsID);
		pstmt.setString(2, data[0][0].toString());
		pstmt.setObject(3, data[0][2]);

		pstmt.execute();

		DB.closeDB();

		model.addRow(data[0]);

		return;
	}

}
