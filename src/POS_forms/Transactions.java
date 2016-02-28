package POS_forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Transactions extends JPanel {

	// Generated SVD
	private static final long serialVersionUID = -203858668229154039L;

	// Global private table variable
	private JTable table;

	/**
	 * Create the panel.
	 */
	public Transactions() {
		// Sets the layout to box
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Creates the title Panel, located at top of pane,
		// Empty as we don't have a logo yet.
		JPanel pnl_title = new JPanel();
		pnl_title.setBackground(UIManager.getColor("Button.background"));
		pnl_title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
		pnl_title.setMinimumSize(new Dimension(0, 100));
		add(pnl_title);

		// Holds the panel in which the Message title is held
		JPanel pnl_plans_title = new JPanel();
		pnl_plans_title.setBackground(Color.BLUE);
		pnl_plans_title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		pnl_plans_title.setMinimumSize(new Dimension(0, 30));
		pnl_plans_title.setPreferredSize(new Dimension(100, 40));
		add(pnl_plans_title);
		pnl_plans_title.setLayout(null);

		// Creates label inside of pnl_message_title to hold the title
		JLabel lbl_plans = new JLabel("Active Plans");
		lbl_plans.setFont(new Font("Dialog", Font.BOLD, 19));
		lbl_plans.setForeground(Color.WHITE);
		lbl_plans.setBounds(26, 0, 300, 40);
		pnl_plans_title.add(lbl_plans);
		add(pnl_plans_title);
		pnl_plans_title.setLayout(null);

		// Creates the Panel which holds the messages
		JPanel pnl_table_container = new JPanel();
		pnl_table_container.setBackground(Color.BLUE);
		pnl_table_container.setLayout(new BoxLayout(pnl_table_container, BoxLayout.Y_AXIS));

		// Sets the size of the continer based on the number of messages, box
		// Layout ensures that all elements are spaced equally.
		pnl_table_container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
		pnl_table_container.setMinimumSize(new Dimension(0, 30));
		add(pnl_table_container);

		// Creates a table based on information recieved from the server

		// This data is solely for testing purposes only
		String[] columnNames = { "First Name", "Last Name", "Purchase", "# of Years", "Current Customer" };

		Object[][] data = { { "Kathy", "Smith", "Snowboard", new Integer(5), new Boolean(false) },
				{ "John", "Doe", "Xbox", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "PC", new Integer(2), new Boolean(false) },
				{ "Jane", "White", "44 Mag.", new Integer(20), new Boolean(true) },
				{ "Joe", "Brown", "Pooltable", new Integer(10), new Boolean(false) } };

		// Creates a new table and adds the information. Adds table to
		// scrollpane
		table = new JTable(data, columnNames);
		JScrollPane tablepane = new JScrollPane(table);

		// Creates a search box and lets you sort by row header in ascending or
		// descending order
		// theres a bug with the ordering in numbers as it may sort 1, 10, 15,
		// 2, 20, 24, 26, 3, 30 etc...
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
		JTextField jtfFilter = new JTextField();
		// JButton jbtFilter = new JButton("Filter");

		table.setRowSorter(rowSorter);

		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

		});

		// Adds the scrollpane to the container
		JLabel lbl_search = new JLabel("Search:");
		lbl_search.setBounds(500, 11, 100, 26);
		lbl_search.setFont(new Font("Dialog", Font.BOLD, 19));
		lbl_search.setForeground(Color.WHITE);

		jtfFilter.setBounds(600, 11, 300, 26);

		pnl_plans_title.add(lbl_search);
		pnl_plans_title.add(jtfFilter);

		// Adds the scrollpane to the container
		pnl_table_container.add(tablepane);

	}

	private void refresh() {
		/*
		 * TODO Gets messages again after pressing refresh button, not used yet
		 */
	}

	public void disposeChildFrames() {
		// TODO Auto-generated method stub

	}

}
