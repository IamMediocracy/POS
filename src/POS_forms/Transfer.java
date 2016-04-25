package POS_forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import POS_classes.DB;
import POS_classes.UIPanels;
import POS_utils.SelectBuilder;

public class Transfer extends UIPanels {
	
	private static final long serialVersionUID = 6047803356240229042L;

	private JTextField transferAmount;

	JComboBox<Object> withdraw = new JComboBox<Object>();
	JComboBox<Object> deposit = new JComboBox<Object>();
	
	JLabel to = new JLabel(new ImageIcon(POSMain.class.getResource("/media/to.png")));
	JLabel from = new JLabel(new ImageIcon(POSMain.class.getResource("/media/from.png")));

	public Transfer() {
		super();

		DB DB;
		try {
			DB = new DB();
			
			SelectBuilder sqlBuilder = new SelectBuilder().column("Money_total").column("location_name")
					.from("running_totals");
			PreparedStatement pst = DB.conn.prepareStatement(sqlBuilder.toString());
			executeQuery(pst);
			super.setTableInfo();

			for (int i = 0; i < data.length; i++) {
				withdraw.addItem(data[i][1]);
				deposit.addItem(data[i][1]);
			}
			
			buttons_panel.add(from);

			buttons_panel.add(withdraw);

			buttons_panel.add(to);
			buttons_panel.add(deposit);

			JButton btnTransfer = new JButton(new ImageIcon(POSMain.class.getResource("/media/TransferCash.png")));
			btnTransfer.setRolloverIcon(new ImageIcon(POSMain.class.getResource("/media/TransferCash_selected.png")));
			btnTransfer.setPressedIcon(new ImageIcon(POSMain.class.getResource("/media/TransferCash_selected_current.png")));
			btnTransfer.setBorder(null);
			btnTransfer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					try {
						DB DB = new DB();
						
						String query = "update running_totals set money_total = money_total - " + transferAmount.getText()
								+ " WHERE location_name ='"+withdraw.getSelectedItem()+"' ";

						String query2 = "update running_totals set money_total = money_total + " + transferAmount.getText()
								+ " WHERE location_name ='"+deposit.getSelectedItem()+"' ";
						PreparedStatement pst;

						pst = DB.conn.prepareStatement(query);
						pst.execute();
						pst = DB.conn.prepareStatement(query2);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Funds transferred");
						pst.close();
						resetTable();
						DB.closeDB();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error: Please Check Transfer Amount");
						e1.printStackTrace();
					}

				}
			});

			transferAmount = new JTextField();
			buttons_panel.add(transferAmount);
			transferAmount.setColumns(10);
			buttons_panel.add(btnTransfer);

			pst.close();
			DB.closeDB();

		} catch (SQLException | ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	public void resetTable() {

		try {

			DB DB = new DB();

			SelectBuilder sqlBuilder = new SelectBuilder().column("Money_total").column("location_name")
					.from("running_totals").where("location_name = 'safe' OR location_name = 'drawer1'");
			PreparedStatement pst = DB.conn.prepareStatement(sqlBuilder.toString());
			executeQuery(pst);
			setTableInfo();

			pst.close();

			// rs.close();
			DB.closeDB();

		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error: Cannot Connect to Database");
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
