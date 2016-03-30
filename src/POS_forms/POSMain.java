package POS_forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import POS_classes.InactivityListener;
import POS_classes.UserData;

public class POSMain {

	Stack<JFrame> extraWindows = new Stack<JFrame>();

	UserData data;

	// The Frame of the main program
	JFrame pos_frame = new JFrame();

	// Menu Bar
	JLabel lbl_transaction = new JLabel();
	JLabel lbl_payment = new JLabel();
	JLabel lbl_transfer = new JLabel();
	JLabel lbl_inventory = new JLabel();
	JLabel lbl_users = new JLabel();

	// The main encompassing panel, all other elements
	// Are placed inside this panel
	JPanel panel = new JPanel();

	// Top Panel, where the Crash button and future title and logo
	// will be.
	JPanel top_panel = new JPanel();

	// Side Panel, holds the side bar
	JPanel menu_panel = new JPanel();

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

	private int position = 0;
	private final JPanel dynamic_panel = new JPanel();

	// Calls the build function for the JFrame
	public POSMain(String userID) {
//		data = new SessionData(userID);
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("static-access")
	private void initialize() {

		pos_frame.setLocationRelativeTo(null);
		pos_frame.setTitle("POS"); // Title is POS
		pos_frame.setMaximumSize(new Dimension(maxsize.width, maxsize.height));
		pos_frame.setSize(maxsize);
		pos_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pos_frame.getContentPane().setLayout(new BoxLayout(pos_frame.getContentPane(), BoxLayout.Y_AXIS));
		pos_frame.setBounds(0, 0, maxsize.width, maxsize.width);

		
		// Sets background color
		panel.setBackground(Color.LIGHT_GRAY);

		// Sets the panel to take all available content space
		pos_frame.getContentPane().add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Sets the constraints for the top_pannel

		top_panel.setMinimumSize(new Dimension(maxsize.width, maxsize.height/10));
		top_panel.setMaximumSize(new Dimension(maxsize.width, maxsize.height/10));
		top_panel.setPreferredSize(new Dimension(maxsize.width, maxsize.height/10));

		top_panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		top_panel.setBackground(Color.GREEN);
		top_panel.setLayout(null);
		panel.add(top_panel);

//		JButton btn_logout = new JButton(data.getFirstName());
		JButton btn_logout = new JButton("USER_FName");
		btn_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				logout_pressed();
			}
		});
		btn_logout.setBounds(maxsize.width - 150, top_panel.HEIGHT + 20, 89, 23);
		top_panel.add(btn_logout);

		panel.add(panel_1);

		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		// Sets the side panel constraints
		menu_panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		menu_panel.setBounds(0, maxsize.height-maxsize.height/10, maxsize.width/8, maxsize.height-maxsize.height/10);
		menu_panel.setMaximumSize(new Dimension(maxsize.width/8, maxsize.height));
		menu_panel.setMinimumSize(new Dimension(maxsize.width/8, maxsize.height));
		menu_panel.setPreferredSize(new Dimension(maxsize.width/8, maxsize.height));
		menu_panel.setBackground(Color.GRAY);
		panel_1.add(menu_panel);
		menu_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		int first_panel = 1;
		
		// TODO after db connected uncomment if statements and delete if(true)s
//		if(data.getCashier()){
		if(true){
			
			lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/transactions.png")));
			lbl_transaction.setBounds(2, setY(position), 196, 45); // 237
			addListenerForMenuBar(lbl_transaction, 1);

			menu_panel.add(lbl_transaction);
			
//			if (first_panel == 0)
//				;
//			first_panel = 1;
		}
//		if(data.getSupervisor()){
//			lbl_inventory.setIcon(new ImageIcon(POSMain.class.getResource("/media/transactions.png")));
//			lbl_inventory.setBounds(2, setY(position), 196, 45); // 237
//			addListenerForMenuBar(lbl_inventory, 1);
//
//			menu_panel.add(lbl_inventory);
//
//			if(data.getManager()){
//				
//			}
//			
//			if (first_panel == 0)
//				;
//			first_panel = 1;
//		}
		
		panel_1.add(dynamic_panel);
		dynamic_panel.setLayout(new BoxLayout(dynamic_panel, BoxLayout.X_AXIS));
		
		viewport_panel.setBackground(Color.WHITE);
		dynamic_panel.add(scrollPane);
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
				scrollPane.setViewportView(viewport_panel);
				viewport_panel.setLayout(new BoxLayout(viewport_panel, BoxLayout.X_AXIS));

		// set action listener for the inactivity listener
		Action logout = new AbstractAction() {
			private static final long serialVersionUID = 3388238663613386686L;

			public void actionPerformed(ActionEvent e) {
				logout_pressed();
			}
		};
		
		setActivePane(first_panel);

		// set the inactivity listener to check for inactivity not exceeding 5
		// minutes
		// TODO modify to check for status of transaction
		InactivityListener listener = new InactivityListener(pos_frame, logout, 5);
		// start listener
		listener.start();
		
		pos_frame.setUndecorated(true);

	}

	// Listener for logout to be pressed -- closes frame
	protected void logout_pressed() {

		LoginWindow login = new LoginWindow();
		login.frmPOS.setVisible(true);
		pos_frame.dispose();

	}

	// Listener for sidebar scroll over
	protected void panelControlExit(int i) {
		if (i != currentPanel) {
			switch (i) {
			case 1:
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/transactions.png")));
				break;
			}
		}

	}

	// Listener for icon change on scroll over enter
	protected void panelControlEntered(int i) {

		if (i != currentPanel) {
			switch (i) {
			case 1:
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/transactions_selected.png")));
				break;
			}
		}
	}

	// Changes the current active pane,
	protected void setActivePane(int i) {
		if (i != currentPanel) {

			while (!extraWindows.empty()) {
				extraWindows.pop().dispose();
			}

			switch (i) {
			case 1:
				lastPanel = currentPanel;
				lbl_transaction.setIcon(new ImageIcon(POSMain.class.getResource("/media/transactions_selected_current.png")));
				currentPanel = 1;
				viewport_panel.removeAll();
				viewport_panel.validate();
				Transactions transaction = new Transactions();
				try {
					transaction.setTableInfo();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				viewport_panel.add(transaction);
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

	private int setY(int currentHeight) {
		if (currentHeight == 0) {
			position = 2;
			return position;
		}
		position += 47;
		return position;
	}
}