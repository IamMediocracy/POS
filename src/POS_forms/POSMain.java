package POS_forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import POS_classes.DB;
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

	// Collapse Button
	JLabel lbl_collapse = new JLabel();

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

	// Keeps track of the Side Bar's crash status
	boolean crashed = false;

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
	private void initialize() {

		// Creates a JFrame
		// instock_frame = new JFrame();
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

		// Separate block of code to help with reading, everything below is
		// inside of
		// the initial containers.
		{

			// Sets the constraints for the top_pannel

			top_panel.setMinimumSize(new Dimension(maxsize.width, 53));
			top_panel.setMaximumSize(new Dimension(maxsize.width, 53));
			top_panel.setPreferredSize(new Dimension(maxsize.width, 53));

			top_panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			top_panel.setBackground(Color.BLUE);
			top_panel.setLayout(null);
			panel.add(top_panel);

			// Sets the label to have the push button icon
			lbl_collapse.setIcon(new ImageIcon(POSMain.class.getResource("/media/collapse_unclicked.png")));
			lbl_collapse.setBounds(12, -19, 70, 95);
			top_panel.add(lbl_collapse);

			// Adds a mouse listener for clicks and hovering
			lbl_collapse.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					collapse_pressed();
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					collapse_select(1);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					collapse_select(0);
				}
			});

			// Sets the label for logout
			lbl_logout.setIcon(new ImageIcon(POSMain.class.getResource("/media/logout.png")));
			lbl_logout.setBounds(1482, 7, 70, 40);
			comboBox.setForeground(Color.WHITE);
			comboBox.setBackground(Color.BLUE);
			// top_panel.add(lbl_logout);

			comboBox.setBounds(1482, 7, 150, 40);
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
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {

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

		}
		{
			panel.add(panel_1);
		}
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		int first_panel = 0;

		{

			// Sets the side panel constraints
			side_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			side_panel.setMaximumSize(new Dimension(200, maxsize.height));
			side_panel.setMinimumSize(new Dimension(200, maxsize.height));
			side_panel.setPreferredSize(new Dimension(200, maxsize.height));
			side_panel.setBackground(Color.GRAY);
			side_panel.setLayout(null);
			panel_1.add(side_panel);

			/*
			 * Each of these blocks creates a label, sets the proper image to
			 * the label, and then adds a listener, through the
			 * addListenerForMenuBar method
			 */

			{
				if (data.getHubPermissions() > 0) {
					lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/hub.png")));
					lbl_transaction.setBounds(2, increaseY(position), 196, 45); // 2
					lbl_transaction.setBackground(Color.BLACK);
					addListenerForMenuBar(lbl_transaction, 1);

					side_panel.add(lbl_transaction);

					if (first_panel == 0)
						;
					first_panel = 1;

				}
			}
			{
				if (data.getRestockPermissions() > 0) {
					lbl_payment.setIcon(new ImageIcon(POSMain.class.getResource("/media/restock.png")));
					lbl_payment.setBounds(2, increaseY(position), 196, 45); // 49
					lbl_payment.setBackground(Color.BLACK);

					addListenerForMenuBar(lbl_payment, 2);

					side_panel.add(lbl_payment);

					if (first_panel == 0)
						;
					first_panel = 2;

				}
			}
			{
				if (data.getPlansPermissions() > 0) {
					lbl_transfer.setIcon(new ImageIcon(POSMain.class.getResource("/media/plans.png")));
					lbl_transfer.setBounds(2, increaseY(position), 196, 45); // 96
					addListenerForMenuBar(lbl_transfer, 3);

					side_panel.add(lbl_transfer);

					if (first_panel == 0)
						;
					first_panel = 3;
				}
			}
			{

				if (data.getProductsPermissions() > 0) {
					lbl_inventory.setIcon(new ImageIcon(POSMain.class.getResource("/media/products.png")));
					lbl_inventory.setBounds(2, increaseY(position), 196, 45); // 237
					addListenerForMenuBar(lbl_inventory, 6);

					side_panel.add(lbl_inventory);

					if (first_panel == 0)
						;
					first_panel = 6;
				}
			}
			{
				if (data.getUsersPermissions() > 0) {
					lbl_users.setIcon(new ImageIcon(POSMain.class.getResource("/media/users.png")));
					lbl_users.setBounds(2, increaseY(position), 196, 45); // 331
					addListenerForMenuBar(lbl_users, 8);

					side_panel.add(lbl_users);

					if (first_panel == 0)
						;
					first_panel = 8;
				}
			}
		}

		// At this point I add another panel, but I will
		// straighten this mess up next revision
		{
			viewport_panel.setBackground(Color.BLUE);
			panel_1.add(scrollPane);

			scrollPane.setViewportView(viewport_panel);
		}
		viewport_panel.setLayout(new BorderLayout(0, 0));

		setActivePane(first_panel);

		// Setup menu bar
		JMenuBar menuBar = new JMenuBar();
		pos_frame.setJMenuBar(menuBar);

		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);

		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mnSettings.add(mntmChangePassword);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnSettings.add(mntmLogout);

		JMenu mnAdmin = new JMenu("Admin");
		menuBar.add(mnAdmin);

		JMenuItem mntmResetPasswords = new JMenuItem("Reset Passwords");
		mnAdmin.add(mntmResetPasswords);

		JMenuItem mntmUserPermissions = new JMenuItem("User Permissions");
		mnAdmin.add(mntmUserPermissions);

		JMenuItem mntmAddUsers = new JMenuItem("Manage Users");
		mnAdmin.add(mntmAddUsers);

		JMenuItem mntmNewMessage = new JMenuItem("New Message");
		mnAdmin.add(mntmNewMessage);
		
		Action logout = new AbstractAction()
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 3388238663613386686L;

			public void actionPerformed(ActionEvent e)
		    {
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

	// Collapses the menu when pressed
	// uncollapses when already collapsed
	protected void collapse_pressed() {

		if (crashed) {

			side_panel.setMaximumSize(new Dimension(201, maxsize.height));
			side_panel.setMinimumSize(new Dimension(199, maxsize.height));
			side_panel.setPreferredSize(new Dimension(200, maxsize.height));

			lbl_collapse.setIcon(new ImageIcon(POSMain.class.getResource("/media/collapse_unclicked.png")));

			crashed = false;
			collapse_select(0);

			panel.validate();
			panel.repaint();

			return;
		}

		side_panel.setMaximumSize(new Dimension(41, maxsize.height));
		side_panel.setMinimumSize(new Dimension(39, maxsize.height));
		side_panel.setPreferredSize(new Dimension(40, maxsize.height));

		lbl_collapse.setIcon(new ImageIcon(POSMain.class.getResource("/media/collapse_clicked.png")));

		crashed = true;

		panel.validate();
		panel.repaint();

	}

	// Listener for icon change on scroll over exit
	protected void collapse_select(int i) {
		if (crashed)
			return;

		if (i == 1) {
			lbl_collapse.setIcon(new ImageIcon(POSMain.class.getResource("/media/collapse_highlighted.png")));
		} else {
			lbl_collapse.setIcon(new ImageIcon(POSMain.class.getResource("/media/collapse_unclicked.png")));
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

			while(!extraWindows.empty()){
				extraWindows.pop().dispose();
			}

			switch (i) {
			case 1:
				lastPanel = currentPanel;
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/hub_selected_current.png")));
				currentPanel = 1;
				viewport_panel.removeAll();
				viewport_panel.validate();
//				viewport_panel.add(new Hub(data, extraWindows));
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				return;
			case 2:
				lastPanel = currentPanel;

				lbl_payment
						.setIcon(new ImageIcon(POSMain.class.getResource("/media/restock_selected_current.png")));
				currentPanel = 2;
				viewport_panel.removeAll();
				viewport_panel.validate();
//				viewport_panel.add(new Restock());
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
//				viewport_panel.add(new Plans());
				viewport_panel.validate();
				panelControlExit(lastPanel);

				scrollPane.repaint();

				return;
			case 6:
				lastPanel = currentPanel;
				lbl_inventory
						.setIcon(new ImageIcon(POSMain.class.getResource("/media/products_selected_current.png")));
				currentPanel = 6;

				viewport_panel.removeAll();
				viewport_panel.validate();
//				viewport_panel.add(new Products());
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
//				viewport_panel.add(new Users());
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