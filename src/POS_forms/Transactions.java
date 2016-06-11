package POS_forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import POS_classes.DB;
import POS_classes.UIPanels;
import POS_utils.SelectBuilder;

public class Transactions extends UIPanels {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	private static boolean inquiry = false;

	JButton btnOverride = new JButton(new ImageIcon(POSMain.class.getResource("/media/Override.png")));
	JButton btnVoid = new JButton(new ImageIcon(POSMain.class.getResource("/media/Void.png")));
	JButton btnPay = new JButton(new ImageIcon(POSMain.class.getResource("/media/Pay.png")));

	JButton btnCash = new JButton(new ImageIcon(POSMain.class.getResource("/media/Cash.png")));
	JButton btnCheck = new JButton(new ImageIcon(POSMain.class.getResource("/media/Check.png")));
	JButton btnEFT = new JButton(new ImageIcon(POSMain.class.getResource("/media/CreditDebit.png")));
	JButton btnCancel = new JButton(new ImageIcon(POSMain.class.getResource("/media/Cancel.png")));

	JButton btnPaper = new JButton(new ImageIcon(POSMain.class.getResource("/media/Paper.png")));
	JButton btnEmail = new JButton(new ImageIcon(POSMain.class.getResource("/media/Email.png")));
	JButton btnNone = new JButton(new ImageIcon(POSMain.class.getResource("/media/NoReceipt.png")));

	JButton btnSelTrans = new JButton(new ImageIcon(POSMain.class.getResource("/media/ResumeTransaction.png")));
	JButton btnCancelTrans = new JButton(new ImageIcon(POSMain.class.getResource("/media/CancelTransaction.png")));
	JButton btnNewTrans = new JButton(new ImageIcon(POSMain.class.getResource("/media/NewTransaction.png")));

	JLabel lblName = new JLabel();
	JLabel lblPrice = new JLabel();
	JLabel lblQuantity = new JLabel();

	JFormattedTextField amountField;
	NumberFormat paymentFormat;
	JButton btnItemInquiry = new JButton(new ImageIcon(POSMain.class.getResource("/media/Inquiry.png")));
	JButton btnRefund = new JButton(new ImageIcon(POSMain.class.getResource("/media/Refund.png")));
	private final JTextField txt_identifier = new JTextField();

	private String userID;

	private int trnsID = 0;

	public Transactions(String userID) {
		super();
		this.userID = userID;
		pnl_table.setBackground(Color.LIGHT_GRAY);
		buttons_panel.setBackground(Color.BLUE);

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

		try {
			lookupTransaction(trnsID, false, false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		setButtonProperties();

		selectTransButtons();

		table.requestFocus();
		table.changeSelection(1, 0, false, false);
		table.changeSelection(1, 3, true, true);

	}

	public void setButtonProperties() {
		// Main Transaction buttons
		btnVoid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = table.getSelectedRow();
				if (selRow > -1) {

					model.removeRow(selRow);
					int rc = table.getRowCount();
					if (rc > 0) {
						table.requestFocus();
						if (selRow < table.getRowCount()) {
							table.changeSelection(selRow, 0, false, false);
							table.changeSelection(selRow, 3, true, true);
						} else {
							table.changeSelection(rc - 1, 0, false, false);
							table.changeSelection(rc - 1, 3, true, true);
						}
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

		btnItemInquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (inquiry)
					inquiry = false;
				else
					inquiry = true;
				txt_identifier.requestFocus();
			}
		});

		// payment buttons
		btnOverride.setBorder(null);
		btnVoid.setBorder(null);
		btnPay.setBorder(null);

		btnOverride.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Override_selected.png")));
		btnVoid.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Void_selected.png")));
		btnPay.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Pay_selected.png")));

		btnOverride.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Override_selected_current.png")));
		btnVoid.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Void_selected_current.png")));
		btnPay.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Pay_selected_current.png")));

		// receipt buttons
		btnPaper.setBorder(null);
		btnEmail.setBorder(null);
		btnNone.setBorder(null);

		btnPaper.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Paper_selected.png")));
		btnEmail.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Email_selected.png")));
		btnNone.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/NoReceipt_selected.png")));

		btnPaper.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Paper_selected_current.png")));
		btnEmail.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Email_selected_current.png")));
		btnNone.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/NoReceipt_selected_current.png")));

		addReceiptListener(btnCash);
		addReceiptListener(btnCheck);
		addReceiptListener(btnEFT);

		// transaction manipulation buttons
		btnItemInquiry.setBorder(null);
		btnCash.setBorder(null);
		btnCheck.setBorder(null);
		btnEFT.setBorder(null);
		btnRefund.setBorder(null);
		btnCancel.setBorder(null);

		btnItemInquiry.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Inquiry_selected.png")));
		btnCash.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Cash_selected.png")));
		btnCheck.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Check_selected.png")));
		btnEFT.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/CreditDebit_selected.png")));
		btnRefund.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Refund_selected.png")));
		btnCancel.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/Cancel_selected.png")));

		btnItemInquiry.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Inquiry_selected_current.png")));
		btnCash.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Cash_selected_current.png")));
		btnCheck.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Check_selected_current.png")));
		btnEFT.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/CreditDebit_selected_current.png")));
		btnRefund.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Refund_selected_current.png")));
		btnCancel.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/Cancel_selected_current.png")));

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
		btnSelTrans.setBorder(null);
		btnCancelTrans.setBorder(null);
		btnNewTrans.setBorder(null);

		btnSelTrans.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/ResumeTransaction_selected.png")));
		btnCancelTrans
				.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/CancelTransaction_selected.png")));
		btnNewTrans.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/NewTransaction_selected.png")));

		btnSelTrans.setPressedIcon(
				new ImageIcon(POSMain.class.getResource("/media/ResumeTransaction_selected_current.png")));
		btnCancelTrans.setPressedIcon(
				new ImageIcon(POSMain.class.getResource("/media/CancelTransaction_selected_current.png")));
		btnNewTrans
				.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/NewTransaction_selected_current.png")));

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

		btnCancelTrans.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DB DB;
				try {
					DB = new DB();
					String sql = "DELETE FROM transaction WHERE trns_id =?";
					int trnsID = (int) model.getValueAt(table.getSelectedRow(), 0);
					PreparedStatement pstmt = DB.conn.prepareStatement(sql);
					pstmt.setInt(1, trnsID);
					pstmt.execute();
					DB.closeDB();
					model.removeRow(table.getSelectedRow());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

	public void selectTransButtons() {
		buttons_panel.setLayout(new BoxLayout(buttons_panel, BoxLayout.Y_AXIS));
		buttons_panel.removeAll();

		buttons_panel.add(btnSelTrans);
		buttons_panel.add(Box.createRigidArea(new Dimension(0, 5)));
		buttons_panel.add(btnCancelTrans);
		buttons_panel.add(Box.createRigidArea(new Dimension(100, 5)));
		buttons_panel.add(btnNewTrans);

		buttons_panel.validate();
		buttons_panel.repaint();

	}

	public void setManipButtons() {
		buttons_panel.setLayout(new FlowLayout());
		txt_identifier.requestFocus();
		buttons_panel.removeAll();

		buttons_panel.add(btnOverride);
		buttons_panel.add(btnVoid);
		buttons_panel.add(btnItemInquiry);
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
			if (!inquiry) {
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tablepane.validate();
					tablepane.repaint();
				}
			} else {
				inquiry = false;
				PreparedStatement pstmnt = DB.conn
						.prepareStatement("select itm_name, itm_price, itm_quantity from item where itm_id = ?");
				pstmnt.setString(1, identifier);
				ResultSet rs = pstmnt.executeQuery();
				if (rs.next()) {
					lblName = new JLabel(rs.getString("itm_name"));
					lblPrice = new JLabel(rs.getString("itm_price"));
					lblQuantity = new JLabel(rs.getString("itm_quantity"));
					GridBagConstraints gbc_info = new GridBagConstraints();
					gbc_info.gridx = GridBagConstraints.RELATIVE;
					gbc_info.gridy = 2;
					pnl_table_info.add(lblName,gbc_info);
					pnl_table_info.add(lblPrice,gbc_info);
					pnl_table_info.add(lblQuantity,gbc_info);
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
		DB.closeDB();

	}

	public void setTransaction() throws SQLException {
		trnsID = (int) model.getValueAt(table.getSelectedRow(), 0);
		try {
			SelectBuilder sqlBuilder = new SelectBuilder().column("item.itm_id AS 'UPC'")
					.column("item.itm_name AS 'Name'").column("receipt_line.rct_line_price AS 'Price'").from("item")
					.join("receipt_line").where("item.itm_id = receipt_line.itm_id").where("receipt_line.trns_id=?");
			DB DB = new DB();
			PreparedStatement pstmt = DB.conn.prepareStatement(sqlBuilder.toString());
			pstmt.setInt(1, trnsID);
			executeQuery(pstmt);
			setTableInfo();
			DB.closeDB();
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
		DB.closeDB();

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
