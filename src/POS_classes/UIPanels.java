package POS_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class UIPanels extends JPanel {

	private static final long serialVersionUID = -4240167914103235089L;

	protected DBTableModel model;

	protected JTable table;
	protected JPanel pnl_super = new JPanel();
	protected JPanel pnl_table_info = new JPanel();
	protected JPanel buttons_panel = new JPanel();
	protected JPanel pnl_table = new JPanel();
	protected JScrollPane tablepane = new JScrollPane();
	protected Object[][] data;
	protected String[] columnNames;
	protected String[] columnType;

	public JFrame child;

	public void disposeChildFrames() {
		child.dispose();
	}

	@SuppressWarnings("static-access")
	public UIPanels() {
		// Sets the layout to box
		setLayout(new GridLayout(1, 2));

		// Creates the Panel which holds the messages
		pnl_super.setBackground(Color.BLUE);
		pnl_super.setLayout(new BoxLayout(pnl_super, BoxLayout.Y_AXIS));

		// Creates a new table and adds the information. Adds table to
		// scrollpane

		// Adds the scrollpane to the container
		pnl_table.add(tablepane);
		pnl_super.add(pnl_table);

		pnl_table_info.setBackground(Color.PINK);
		pnl_table_info.setPreferredSize(new Dimension(pnl_super.WIDTH, pnl_super.HEIGHT / 6));

		pnl_super.add(pnl_table_info);
		pnl_table_info.setLayout(null);

		pnl_super.setMinimumSize(new Dimension(0, 30));
		add(pnl_super);

		// Holds the panel in which the Message title is held
		buttons_panel.setBackground(Color.GRAY);
		add(buttons_panel);
		add(buttons_panel);
		buttons_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}

	public void setTableInfo() throws ClassNotFoundException {

		model = new DBTableModel(data, columnNames);
		table = new JTable(model);

		pnl_table.removeAll();

		tablepane = null;
		tablepane = new JScrollPane(table);

		pnl_table.add(tablepane);
		pnl_table.validate();
		pnl_table.repaint();

	}

	public void executeQuery( PreparedStatement pstmt) throws SQLException {
		
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int colcount = rsmd.getColumnCount();
		this.columnNames = new String[colcount];
		this.columnType = new String[colcount];
		if (rs != null) {
			rs.last();
			this.data = new Object[rs.getRow()][colcount];
			rs.beforeFirst();
		}
		for (int k = 0; k < colcount; k++) {
			columnNames[k] = rs.getMetaData().getColumnLabel(k + 1);
			columnType[k] = rs.getMetaData().getColumnClassName(k + 1);
		}
		for (int i = 0; rs.next(); i++) {
			for (int j = 0; j < colcount; j++) {
				this.data[i][j] = rs.getObject(j + 1);
				System.out.println(this.data[i][j]);
			}
		}
		return;

	}

}
