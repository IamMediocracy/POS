package POS_forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import POS_classes.FocusTraversalOnArray;
import POS_classes.AccountValidation;

public class LoginWindow extends AccountValidation {
	// The frame of the application
	public JFrame frmPOS;

	// Password field, will change with
	// regular password field
	private JPasswordField txt_password;
	private JTextField txt_username;

	// Login button
	private JButton btn_login;
	// backspace
	private JButton btn_backspace;
	// slash key changes focus to next element
	private JButton btn_slash;
	
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
	
	Dimension maxsize = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();

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
		// frmPOS.setFocusable(false);
		frmPOS.setLocationRelativeTo(null);
		frmPOS.setMaximumSize(new Dimension(maxsize.width, maxsize.height));
		frmPOS.setBounds(0, 0, maxsize.width, maxsize.height);
		frmPOS.getContentPane().setForeground(Color.WHITE);
		frmPOS.getContentPane().setBackground(Color.WHITE);
		frmPOS.getContentPane().setLayout(null);

		txt_username = new JTextField();
		txt_username.setBounds(frmPOS.getBounds().width / 2 - 150, frmPOS.getBounds().height / 2 - 330, 300, 28);
		frmPOS.getContentPane().add(txt_username);
		txt_username.setColumns(10);
		txt_username.setFocusable(true);
		// txt_username.requestFocusInWindow();
		// txt_username.requestFocus();

		// sets the password field constraints
		txt_password = new JPasswordField();
		txt_password.setBounds(frmPOS.getBounds().width / 2 - 150, frmPOS.getBounds().height / 2 - 300, 300, 28);
		txt_password.setColumns(10);
		txt_password.setFocusable(true);
		frmPOS.getContentPane().add(txt_password);

		Font arial20 = new Font("Arial", Font.BOLD, 20);
		Font arial12 = new Font("Arial", Font.BOLD, 12);

		// The login button
		btn_1 = new JButton("1");
		btn_1.setFont(arial20);
		btn_1.setBounds(frmPOS.getBounds().width / 2 - 150, frmPOS.getBounds().height / 2 - 200, 100, 100);
		btn_1.setFocusable(false);
		frmPOS.getContentPane().add(btn_1);

		// The login button
		btn_2 = new JButton("2");
		btn_2.setFont(arial20);
		btn_2.setBounds(frmPOS.getBounds().width / 2 - 50, frmPOS.getBounds().height / 2 - 200, 100, 100);
		btn_2.setFocusable(false);
		frmPOS.getContentPane().add(btn_2);

		// The login button
		btn_3 = new JButton("3");
		btn_3.setFont(arial20);
		btn_3.setBounds(frmPOS.getBounds().width / 2 + 50, frmPOS.getBounds().height / 2 - 200, 100, 100);
		btn_3.setFocusable(false);
		frmPOS.getContentPane().add(btn_3);

		// The login button
		btn_4 = new JButton("4");
		btn_4.setFont(arial20);
		btn_4.setBounds(frmPOS.getBounds().width / 2 - 150, frmPOS.getBounds().height / 2 - 100, 100, 100);
		btn_4.setFocusable(false);
		frmPOS.getContentPane().add(btn_4);

		// The login button
		btn_5 = new JButton("5");
		btn_5.setFont(arial20);
		btn_5.setBounds(frmPOS.getBounds().width / 2 + 50, frmPOS.getBounds().height / 2 - 100, 100, 100);
		btn_5.setFocusable(false);
		frmPOS.getContentPane().add(btn_5);

		// The login button
		btn_6 = new JButton("6");
		btn_6.setFont(arial20);
		btn_6.setBounds(frmPOS.getBounds().width / 2 - 50, frmPOS.getBounds().height / 2 - 100, 100, 100);
		btn_6.setFocusable(false);
		frmPOS.getContentPane().add(btn_6);

		// The login button
		btn_7 = new JButton("7");
		btn_7.setFont(arial20);
		btn_7.setBounds(frmPOS.getBounds().width / 2 - 150, frmPOS.getBounds().height / 2, 100, 100);
		btn_7.setFocusable(false);
		frmPOS.getContentPane().add(btn_7);

		// The login button
		btn_8 = new JButton("8");
		btn_8.setFont(arial20);
		btn_8.setBounds(frmPOS.getBounds().width / 2 - 50, frmPOS.getBounds().height / 2, 100, 100);
		btn_8.setFocusable(false);
		frmPOS.getContentPane().add(btn_8);

		// The login button
		btn_9 = new JButton("9");
		btn_9.setFont(arial20);
		btn_9.setBounds(frmPOS.getBounds().width / 2 + 50, frmPOS.getBounds().height / 2, 100, 100);
		btn_9.setFocusable(false);
		frmPOS.getContentPane().add(btn_9);

		// The login button
		btn_backspace = new JButton("Backspace");
		btn_backspace.setFont(arial12);
		btn_backspace.setBounds(frmPOS.getBounds().width / 2 - 150, frmPOS.getBounds().height / 2 + 100, 100, 100);
		btn_backspace.setFocusable(false);
		frmPOS.getContentPane().add(btn_backspace);

		// The login button
		btn_0 = new JButton("0");
		btn_0.setFont(arial20);
		btn_0.setBounds(frmPOS.getBounds().width / 2 - 50, frmPOS.getBounds().height / 2 + 100, 100, 100);
		btn_0.setFocusable(false);
		frmPOS.getContentPane().add(btn_0);

		// The login button
		btn_login = new JButton("Login");
		btn_login.setFont(arial12);
		btn_login.setBounds(frmPOS.getBounds().width / 2 + 50, frmPOS.getBounds().height / 2 + 100, 100, 100);
		btn_login.setFocusable(false);
		frmPOS.getContentPane().add(btn_login);

		btn_slash = new JButton("/");
		btn_slash.setFont(arial20);
		btn_slash.setBounds(frmPOS.getBounds().width / 2 - 50, frmPOS.getBounds().height / 2 + 200, 100, 100);
		btn_slash.setFocusable(false);
		frmPOS.getContentPane().add(btn_slash);

		// The label that holds the background
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(LoginWindow.class.getResource("/media/LoginBackground.png")));
		label.setBounds(0, 0, maxsize.width, maxsize.height);
		frmPOS.getContentPane().add(label);
		frmPOS.setResizable(false);

		// Sets the bounds after calculating the insets
		frmPOS.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmPOS.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { txt_username, txt_password }));
		frmPOS.setUndecorated(true);

		Set<AWTKeyStroke> forwardKeys = txt_username.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
		Set newForwardKeys = new HashSet(forwardKeys);
		newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0));
		frmPOS.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);

		btn_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_1);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btn_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_2);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_3);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_4);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_5);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_6);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_7);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_8);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_9);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_0);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_backspace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_BACK_SPACE);

				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_slash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Robot r;
				try {
					r = new Robot();
					r.keyPress(KeyEvent.VK_SLASH);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Handles the Login Button click
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// TODO uncomment after DB is connected
//				if (checkLogin()) {
				if(true){
					loadPOS();
				}
			}
		});

	}

	public boolean checkLogin() {

		String username = txt_username.getText();

		byte[] password = toBytes(txt_password.getPassword());

		byte[] salted = toSalt(username, password);

		byte[] encryp = toEncrypt(salted);

		if (compareLogin(username, encryp)) {
			return true;
		}
		return false;

	}

	// Loads the POS main window after login validation
	// after closing the login window
	public void loadPOS() {

		POSMain main = new POSMain(txt_username.getText());
		main.pos_frame.setVisible(true);
		frmPOS.dispose();

	}
}
