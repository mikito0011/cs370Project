
//These libraries are for GUI and JFrame
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;

/**
 * This class extends JFrame for GUI of login panel and provides the
 * registration system of login and signup. Depending on the user's status, the
 * constructor calls the main GUI class for the database management system.
 * 
 * @author mikito takeshima
 *
 */
public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JLayeredPane layeredPane;
	private JTextField login_userNameField;
	private JTextField login_passwordField;
	private JTextField login_messageTextField;

	private JTextField signup_userNameTextField;
	private JTextField signup_passwordTextField;
	private JTextField signup_messageTextField;

	/**
	 * This function takes JPanel variable to change the current panel to the panel
	 * of the parameter.
	 * 
	 * @param addedPanel
	 */
	public void changePanel(JPanel addedPanel) {
		layeredPane.removeAll(); // Remove all panels
		layeredPane.add(addedPanel); // Add the panel of the parameter to display.
		layeredPane.repaint();
		layeredPane.revalidate();

	}// changePanel

	/**
	 * This class is parameterized constructor that accepts directoryFile path and
	 * creates the frame when this constructor is called.
	 * 
	 * @throws IOException
	 */
	public LoginWindow(String inputFilePath) throws IOException {

		LogInSystem login = new LogInSystem(); // Declare LogInSystem instance for the registration system.

		String inputFile = inputFilePath;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 668, 409);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(135, 0, 533, 387);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel loginPanel = new JPanel();
		layeredPane.add(loginPanel, "name_5259812443761");
		loginPanel.setBackground(Color.LIGHT_GRAY);
		loginPanel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setBounds(200, 16, 98, 16);
		loginPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_4 = new JLabel("userName:");
		lblNewLabel_4.setBounds(68, 83, 88, 16);
		loginPanel.add(lblNewLabel_4);

		login_userNameField = new JTextField();
		login_userNameField.setBounds(181, 78, 130, 26);
		loginPanel.add(login_userNameField);
		login_userNameField.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Password:");
		lblNewLabel_5.setBounds(68, 147, 88, 16);
		loginPanel.add(lblNewLabel_5);

		login_passwordField = new JTextField();
		login_passwordField.setBounds(181, 142, 130, 32);
		loginPanel.add(login_passwordField);

		login_messageTextField = new JTextField();
		login_messageTextField.setBackground(Color.LIGHT_GRAY);
		login_messageTextField.setBounds(109, 201, 275, 26);
		loginPanel.add(login_messageTextField);
		login_messageTextField.setColumns(10);

		JButton login_loginButton = new JButton("Login");
		login_loginButton.addActionListener(new ActionListener() {

			/**
			 * This button action stores the userName and password data from the text field
			 * and initiates the main GUI program after a user successfully logs in.
			 */
			public void actionPerformed(ActionEvent e) {
				String userName = login_userNameField.getText(); // Store the user name.
				String password = login_passwordField.getText(); // Store the password.
				String userStatus = "userAccount"; // The default user status.

				/*
				 * checks if the username and password matches, calling the function of
				 * LoginSystem.
				 */
				try {
					if (login.doesFileExist()) {
						if (login.checkUserNameAndPass(userName, password)) {

							String userDirectory = login.getUserFolderPath(userName);

							EventQueue.invokeLater(new Runnable() {
								/**
								 * This run method initiate the main GUI program, passing the parameters of
								 * directorypath, userName, and UserStatus.
								 */
								public void run() {
									try {
										if (login.IsAmdin(userName)) {
											GUI frame = new GUI(userDirectory, userName, "Admin", inputFile);
											frame.setVisible(true);
										} else {

											GUI frame = new GUI(userDirectory, userName, userStatus, inputFile);
											frame.setVisible(true);
										}
									} // try
									catch (Exception e) {
										e.printStackTrace();
									} // catch
								}// run
							});
						} // if
						/*
						 * When the userName and passWord does not match, set the message textField of
						 * the login panel.
						 */
						else {
							login_messageTextField.setText("Login failed. The username and password does not match.");
						}
					} else {
						login_messageTextField.setText("Login failed. The username and password does not match.");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // else

			}// actionPerformed
		});

		login_loginButton.setBounds(181, 226, 117, 29);
		loginPanel.add(login_loginButton);

		JPanel signUpPanel = new JPanel();
		layeredPane.add(signUpPanel, "name_3334422444476");
		signUpPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Sign Up");
		lblNewLabel.setBounds(211, 39, 61, 16);
		signUpPanel.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Username:");
		lblNewLabel_2.setBounds(97, 113, 94, 16);
		signUpPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Password:");
		lblNewLabel_3.setBounds(97, 168, 85, 16);
		signUpPanel.add(lblNewLabel_3);

		signup_userNameTextField = new JTextField();
		signup_userNameTextField.setBounds(230, 108, 130, 26);
		signUpPanel.add(signup_userNameTextField);
		signup_userNameTextField.setColumns(10);

		signup_passwordTextField = new JTextField();
		signup_passwordTextField.setBounds(230, 163, 130, 26);
		signUpPanel.add(signup_passwordTextField);
		signup_passwordTextField.setColumns(10);

		signup_messageTextField = new JTextField();
		signup_messageTextField.setBackground(SystemColor.window);
		signup_messageTextField.setBounds(123, 208, 261, 26);
		signUpPanel.add(signup_messageTextField);
		signup_messageTextField.setColumns(10);

		JButton signup_signupButton = new JButton("SignUp");
		signup_signupButton.setBounds(175, 242, 117, 29);
		signup_signupButton.addActionListener(new ActionListener() {
			/**
			 * This button action stores the userName and password data from the text field
			 * and sign up the user. Then, it initiates the main GUI program after a user
			 * successfully logs in.
			 */
			public void actionPerformed(ActionEvent e) {

				String userName = signup_userNameTextField.getText(); // Store the user name.
				String passWord = signup_passwordTextField.getText(); // Store the password.
				String userStatus = "userAccount"; // Set the default user status.

				// Check if user name already exists.
				if (login.doesUserExist(userName)) {
					signup_messageTextField.setText("The userName already exist.");
				} // if
				else {
					try {
						// When the userList.txt file exist, create an account.
						if (login.doesFileExist()) {
							login.createAccount(userName, passWord, userStatus);

							login.createUserFolder(userName);
							String userDirectory = login.getUserFolderPath(userName);
							EventQueue.invokeLater(new Runnable() {
								/**
								 * Initiate the main GUI Application with the userAccount status passed.
								 */
								public void run() {
									try {
										// Initiates the main GUI program with the usrAccount status.
										GUI frame = new GUI(userDirectory, userName, "userAccount", inputFile);
										frame.setVisible(true);
									} // try
									catch (Exception e) {
										e.printStackTrace();
									} // catch
								}// run
							});

						} // if
						/*
						 * When the userList.txt file does not exist, it assigns the first registered
						 * account as an Admin.
						 */
						else {
							// add an Admin user on the user list
							login.createAccount(userName, passWord, "Admin");
							String userDirectory = login.getUserFolderPath(userName);
							login.createUserFolder(userName);

							EventQueue.invokeLater(new Runnable() {
								/**
								 * Starts the GUI with the user status of Admin.
								 */
								public void run() {
									try {
										// Initiates the main GUI program passing the Admin user status.
										GUI frame = new GUI(userDirectory, userName, "Admin", inputFile);
										frame.setVisible(true);
									} // try
									catch (Exception e) {
										e.printStackTrace();
									} // catch
								}// run
							});

						} // else
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					} // catch

				} // else
			}// actionPerformed
		});
		signup_signupButton.setBounds(181, 226, 117, 29);
		signUpPanel.add(signup_signupButton);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to loginPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(loginPanel);

			}// actionPerformed
		});
		loginButton.setBounds(6, 39, 117, 29);
		contentPane.add(loginButton);

		JButton signUpButton = new JButton("SignUp");
		signUpButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to signUpPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(signUpPanel);
			}// actionPerformed
		});
		signUpButton.setBounds(6, 80, 117, 29);
		contentPane.add(signUpButton);

		JButton guestButton = new JButton("Guest");
		guestButton.addActionListener(new ActionListener() {
			/**
			 * When the Guest button is clicked, it initiates the main GUI program with
			 * Guest user status.
			 */
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							GUI frame = new GUI("", "Guest", "Guest", "");
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		guestButton.setBounds(6, 132, 117, 29);
		contentPane.add(guestButton);
	}// LoginWindow (constructor)
}// LoginWindow
