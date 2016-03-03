package POS_forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import POS_classes.InactivityListener;
import POS_classes.SessionData;

public class POSMain {

	Stack<JFrame> extraWindows = new Stack<JFrame>();

	SessionData data;

	// The Frame of the main program
	JFrame pos_frame = new JFrame();

	// Menu Bar
	JLabel lbl_transaction = new JLabel();
	JLabel lbl_payment = new JLabel();
	JLabel lbl_transfer = new JLabel();
	JLabel lbl_inventory = new JLabel();
	JLabel lbl_users = new JLabel();

	// Current User -> options
	JLabel lbl_logout = new JLabel();

	// The main encompassing panel, all other elements
	// Are placed inside this panel
	JPanel panel = new JPanel();

	// Top Panel, where the Crash button and future title and logo
	// will be.
	JPanel top_panel = new JPanel();

	// Side Panel, holds the side bar
	JPanel side_panel = new JPanel();

	// Keeps track of the current tab selected, such as the HUB pane
	private int currentPanel = 0;

	// A variable used as a temp variable for tab switching
	private int lastPanel;

	// Panel that is set as the JScrollView's viewport
	private final JPanel viewport_panel = new JPanel();

	// The scroll pane, holds the non-static hubs
	JScrollPane scrollPane = new JScrollPane();

	private final JPanel panel_1 = new JPanel();

	Dimension maxsize = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();

	// Creates the Combo Box for logout and settings.
	JComboBox<String> comboBox = new JComboBox<String>();

	private int position = 0;

	// Calls the build function for the JFrame
	public POSMain(String username) {
		data = new SessionData(username);
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("static-access")
	private void initialize() {

		pos_frame.setLocationRelativeTo(null);
		pos_frame.setTitle("POS"); // Title is InStock
		pos_frame.setMaximumSize(new Dimension(maxsize.width, maxsize.height));
		pos_frame.setBounds(0, 0, maxsize.width, maxsize.height); // The
																	// windowed
																	// size
																	// of
																	// the
																	// Jframe
		pos_frame.setSize(maxsize);
		pos_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pos_frame.getContentPane().setLayout(new BoxLayout(pos_frame.getContentPane(), BoxLayout.Y_AXIS));

		// Sets background color
		panel.setBackground(Color.LIGHT_GRAY);

		// Sets the panel to take all available content space
		pos_frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Sets the constraints for the top_pannel

		top_panel.setMinimumSize(new Dimension(maxsize.width, 53));
		top_panel.setMaximumSize(new Dimension(maxsize.width, 53));
		top_panel.setPreferredSize(new Dimension(maxsize.width, 53));

		top_panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		top_panel.setBackground(Color.BLUE);
		top_panel.setLayout(null);
		panel.add(top_panel);

		// Sets the label for logout
		lbl_logout.setIcon(new ImageIcon(POSMain.class.getResource("/media/logout.png")));
		lbl_logout.setBounds(1482, 7, 70, 40);
		comboBox.setForeground(Color.WHITE);
		comboBox.setBackground(Color.BLUE);
		// top_panel.add(lbl_logout);

		comboBox.setBounds(maxsize.width - 200, 7, 150, 40);
		comboBox.addItem("Options");
		comboBox.addItem("Logout");
		// comboBox.setRenderer(new
		// NameSelectionOptions(data.getFirstName()));
		comboBox.setRenderer(new DefaultListCellRenderer() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				setBackground(Color.BLUE);
				setForeground(Color.WHITE);
				super.paint(g);

			}

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {

				JLabel lbllolol = (JLabel) this;

				if (index == -1 && value == null) {
					lbllolol.setText(data.getFirstName());
				} else {
					setText(value.toString());
				}
				return lbllolol;

			}
		});
		comboBox.setSelectedIndex(-1);

		top_panel.add(comboBox);

		// ComboBox action listener

		comboBox.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String selection = (String) cb.getSelectedItem();

				handleSelection(selection);

				return;

			}
		});

		/*
		 * Working on replacement for logut button, EXPERIMENTAL
		 * options_button.setBounds(1482, 7, 70, 40);
		 * top_panel.add(options_button);
		 */

		// Adds a mouse listener for clicks and hovering
		lbl_logout.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				logout_pressed();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				logout_select(1);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				logout_select(0);
			}
		});

		panel.add(panel_1);

		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
//		side_panel.setMaximumSize(new Dimension(maxsize.width - scrollPane.WIDTH, maxsize.height));
//		side_panel.setMinimumSize(new Dimension(maxsize.width - scrollPane.WIDTH, maxsize.height));
//		side_panel.setPreferredSize(new Dimension(maxsize.width - scrollPane.WIDTH, maxsize.height));
//		side_panel.set

		viewport_panel.setBackground(Color.WHITE);
		scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		viewport_panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		scrollPane.setMaximumSize(new Dimension(400, maxsize.height - 300));
		scrollPane.setMinimumSize(new Dimension(300, maxsize.height - 300));
		scrollPane.setPreferredSize(new Dimension(350, maxsize.height - 300));
		panel_1.add(scrollPane);

		scrollPane.setViewportView(viewport_panel);
		viewport_panel.setLayout(new BorderLayout(0, 0));

		side_panel.setAlignmentX(Component.RIGHT_ALIGNMENT);

		// Sets the side panel constraints
		side_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		side_panel.setBackground(Color.GRAY);
		side_panel.setLayout(null);
		panel_1.add(side_panel);

		Action logout = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3388238663613386686L;

			public void actionPerformed(ActionEvent e) {
				logout_pressed();
			}
		};

		InactivityListener listener = new InactivityListener(pos_frame, logout, 10);
		listener.start();

	}

	protected void handleSelection(String selection) {

		switch (selection) {
		case "Logout":
			logout_pressed();
			break;
		case "Options":

			break;
		}

	}

	// Listener for logout to be pressed -- closes frame
	protected void logout_pressed() {

		pos_frame.dispose();
		LoginWindow login = new LoginWindow();
		login.frmPOS.setVisible(true);

	}

	// Listener for rollover of logout button -- depresses button
	protected void logout_select(int i) {
		if (i == 1) {
			lbl_logout.setIcon(new ImageIcon(POSMain.class.getResource("/media/logout_pressed.png")));
		} else {
			lbl_logout.setIcon(new ImageIcon(POSMain.class.getResource("/media/logout.png")));
		}
	}

	// Listener for sidebar scroll over
	protected void panelControlExit(int i) {
		if (i != currentPanel) {
			switch (i) {
			case 1:
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/hub.png")));
				break;
			case 2:
				lbl_payment.setIcon(new ImageIcon(POSMain.class.getResource("/media/restock.png")));
				break;
			case 3:
				lbl_transfer.setIcon(new ImageIcon(POSMain.class.getResource("/media/plans.png")));
				break;
			case 6:
				lbl_inventory.setIcon(new ImageIcon(POSMain.class.getResource("/media/products.png")));
				break;
			case 8:
				lbl_users.setIcon(new ImageIcon(POSMain.class.getResource("/media/users.png")));
				break;
			}
		}

	}

	// Listener for icon change on scroll over enter
	protected void panelControlEntered(int i) {

		if (i != currentPanel) {
			switch (i) {
			case 1:
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/hub_selected.png")));
				break;
			case 2:
				lbl_payment.setIcon(new ImageIcon(POSMain.class.getResource("/media/restock_selected.png")));
				break;
			case 3:
				lbl_transfer.setIcon(new ImageIcon(POSMain.class.getResource("/media/plans_selected.png")));
				break;
			case 6:
				lbl_inventory.setIcon(new ImageIcon(POSMain.class.getResource("/media/products_selected.png")));
				break;
			case 8:
				lbl_users.setIcon(new ImageIcon(POSMain.class.getResource("/media/users_selected.png")));
				break;
			}
		}
	}

	// Changes the current active pane,
	// AKA the pane in the scroll view
	// TODO, code not yet complete

	protected void setActivePane(int i) {
		if (i != currentPanel) {

			while (!extraWindows.empty()) {
				extraWindows.pop().dispose();
			}

			switch (i) {
			case 1:
				lastPanel = currentPanel;
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/hub_selected_current.png")));
				currentPanel = 1;
				viewport_panel.removeAll();
				viewport_panel.validate();
				// viewport_panel.add(new Hub(data, extraWindows));
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				return;
			case 2:
				lastPanel = currentPanel;

				lbl_payment.setIcon(new ImageIcon(POSMain.class.getResource("/media/restock_selected_current.png")));
				currentPanel = 2;
				viewport_panel.removeAll();
				viewport_panel.validate();
				// viewport_panel.add(new Restock());
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				return;
			case 3:
				lastPanel = currentPanel;
				lbl_transfer.setIcon(new ImageIcon(POSMain.class.getResource("/media/plans_selected_current.png")));
				currentPanel = 3;

				viewport_panel.removeAll();
				viewport_panel.validate();
				// viewport_panel.add(new Plans());
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				return;
			case 6:
				lastPanel = currentPanel;
				lbl_inventory.setIcon(new ImageIcon(POSMain.class.getResource("/media/products_selected_current.png")));
				currentPanel = 6;

				viewport_panel.removeAll();
				viewport_panel.validate();
				// viewport_panel.add(new Products());
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				break;
			case 8:
				lastPanel = currentPanel;
				lbl_users.setIcon(new ImageIcon(POSMain.class.getResource("/media/users_selected_current.png")));
				currentPanel = 8;
				viewport_panel.removeAll();
				viewport_panel.validate();
				// viewport_panel.add(new Users());
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				return;
			}
		}
	}

	// The listener for the side bar scroll over and button press
	private void addListenerForMenuBar(JLabel label, int labelNumber) {

		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				setActivePane(labelNumber);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panelControlEntered(labelNumber);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panelControlExit(labelNumber);
			}
		});
	}

	private int increaseY(int currentHeight) {
		if (currentHeight == 0) {
			position = 2;
			return position;
		}
		position += 47;
		return position;
	}
}