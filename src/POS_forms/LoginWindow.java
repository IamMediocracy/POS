package POS_forms;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import POS_classes.UserAccounts;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class LoginWindow extends UserAccounts {
	// The frame of the application
	public JFrame frmPOS;

	// Password field, will change with
	// regular password field
	private JPasswordField txt_password;

	// Login button
	private JButton btn_login;
	// backspace
	private JButton btn_backspace;
	// number pad
	private JButton btn_0;
	private JButton btn_1;
	private JButton btn_2;
	private JButton btn_3;
	private JButton btn_4;
	private JButton btn_5;
	private JButton btn_6;
	private JButton btn_7;
	private JButton btn_8;
	private JButton btn_9;

	// The programs main, program starts with a
	// Login button
	public static void main(String[] args) {

		// Starts the login window
		Runnable login = new Runnable() {

			public void run() {
				try {

					// Creates the login window, and sets it to be visible
					LoginWindow window = new LoginWindow();
					window.frmPOS.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		EventQueue.invokeLater(login);

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public LoginWindow() {
		// Creates the Login frame
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// Sets the frame constraints
		frmPOS = new JFrame();
		frmPOS.setTitle("POS - Login");
		frmPOS.getContentPane().setForeground(Color.WHITE);
		frmPOS.getContentPane().setBackground(Color.WHITE);
		frmPOS.getContentPane().setLayout(null);

		// sets the password field constraints
		txt_password = new JPasswordField();
		txt_password.setBounds(193, 27, 213, 28);
		frmPOS.getContentPane().add(txt_password);
		txt_password.setColumns(10);
		
		Font arial20 = new Font("Arial", Font.BOLD, 20);
		Font arial12 = new Font("Arial", Font.BOLD, 12);

		// The login button
		btn_1 = new JButton("1");
		btn_1.setFont(arial20);
		btn_1.setBounds(146, 59, 100, 100);
		frmPOS.getContentPane().add(btn_1);

		// The login button
		btn_2 = new JButton("2");
		btn_2.setFont(arial20);
		btn_2.setBounds(247, 59, 100, 100);
		frmPOS.getContentPane().add(btn_2);

		// The login button
		btn_3 = new JButton("3");
		btn_3.setFont(arial20);
		btn_3.setBounds(346, 59, 100, 100);
		frmPOS.getContentPane().add(btn_3);

		// The login button
		btn_4 = new JButton("4");
		btn_4.setFont(arial20);
		btn_4.setBounds(146, 158, 100, 100);
		frmPOS.getContentPane().add(btn_4);

		// The login button
		btn_5 = new JButton("5");
		btn_5.setFont(arial20);
		btn_5.setBounds(50, 150, 100, 100);
		frmPOS.getContentPane().add(btn_5);

		// The login button
		btn_6 = new JButton("6");
		btn_6.setFont(arial20);
		btn_6.setBounds(346, 158, 100, 100);
		frmPOS.getContentPane().add(btn_6);

		// The login button
		btn_7 = new JButton("7");
		btn_7.setFont(arial20);
		btn_7.setBounds(146, 257, 100, 100);
		frmPOS.getContentPane().add(btn_7);

		// The login button
		btn_8 = new JButton("8");
		btn_8.setFont(arial20);
		btn_8.setBounds(247, 257, 100, 100);
		frmPOS.getContentPane().add(btn_8);

		// The login button
		btn_9 = new JButton("9");
		btn_9.setFont(arial20);
		btn_9.setBounds(346, 257, 100, 100);
		frmPOS.getContentPane().add(btn_9);

		// The login button
		btn_backspace = new JButton("Backspace");
		btn_backspace.setFont(arial12);
		btn_backspace.setBounds(146, 351, 100, 100);
		frmPOS.getContentPane().add(btn_backspace);

		// The login button
		btn_0 = new JButton("0");
		btn_0.setFont(arial20);
		btn_0.setBounds(247, 351, 100, 100);
		frmPOS.getContentPane().add(btn_0);

		// The login button
		btn_login = new JButton("Login");
		btn_login.setFont(arial12);
		btn_login.setBounds(346, 351, 100, 100);
		frmPOS.getContentPane().add(btn_login);

		// The label that holds the background
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(LoginWindow.class.getResource("/media/LoginBackground.png")));
		label.setBounds(0, 0, 640, 480);
		frmPOS.getContentPane().add(label);
		frmPOS.setResizable(false);

		// Sets the bounds after calculating the insets
		Insets i = frmPOS.getInsets();
		frmPOS.setBounds(100, 100, 640 + i.right + i.left, 480 + i.top + i.bottom);
		frmPOS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPOS.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txt_password}));

		// Handles the Login Button click
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				if (checkLogin()) {
					loadPOS();
				}
			}
		});

	}

	public boolean checkLogin() {

		// try {
		// System.out.println("Lines inserted " +
		// CSV.CSVDatabaseUpdate("src/media/_GET_MERCHANT_LISTINGS_DATA_.csv"));
		// } catch (IOException | SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// createTestDB();
		// TestDB.insertTestData();

//		String username = txt_username.getText();

		byte[] password = toBytes(txt_password.getPassword());

//		byte[] salted = toSalt(username, password);

//		byte[] encryp = toEncrypt(salted);

//		if (compareLogin(username, encryp)) {
//			return true;
//		}
		return false;

	}

	// Loads the InStock main window after login validation
	// after closing the login window
	public void loadPOS() {

		frmPOS.dispose();

		// Instock_Main main = new Instock_Main();
//		POSMain main = new POSMain(txt_username.getText());
//		main.pos_frame.setVisible(true);

	}
}
