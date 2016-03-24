package POS_classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class UIPanels extends JPanel {

	private static final long serialVersionUID = -4240167914103235089L;

	protected JTable table;
	protected JPanel pnl_super = new JPanel();
	protected JPanel pnl_table_info = new JPanel();
	protected JPanel buttons_panel = new JPanel();
	protected JPanel pnl_table = new JPanel();
	protected JScrollPane tablepane = new JScrollPane();

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

		pnl_super.setMinimumSize(new Dimension(0, 30));
		add(pnl_super);

		// Holds the panel in which the Message title is held
		buttons_panel.setBackground(Color.GRAY);
		add(buttons_panel);
		add(buttons_panel);
		buttons_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	
	public abstract void setTableInfo();

}
