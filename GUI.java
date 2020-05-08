
//These libraries are for GUI and JFrame
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.SystemColor;

/**
 * This class establishes JFrame for GUI application and works as an interface
 * of other classes.
 * 
 * @author mikitotakeshima
 *
 */
@SuppressWarnings("unchecked")

public class GUI extends JFrame {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

	private JPanel contentPane;
	private JButton onlineSearchButton;
	private JPanel loginPanel;
	private JPanel onlineSearchPanel;
	private JPanel offlineSearchPanel;
	private JPanel editStoragePanel;
	private JPanel edit_insertPanel;
	private JPanel edit_modifyPanel;
	private JPanel edit_deletePanel;

	private JScrollPane scrollPane;
	private JTextArea online_searchResultArea;

	private JLayeredPane layeredPane;
	private JTextField online_searchField;
	private JComboBox online_subjectComboBox;
	private JTextField offline_startTimeTextField;
	private JTextField insert_isbnTextField;
	private JTextField insert_titleTextField;
	private JTextField insert_publisherTextField;
	private JTextField insert_languageTextField;
	private JTextField insert_copyRightTextField;
	private JTextField modify_isbnTextField;
	private JTextField modify_titleTextField;
	private JTextField modify_publisherTextField;
	private JTextField modify_languageTextField;
	private JTextField modify_copyRightTextField;
	private JTextField delete_isbnTextField;
	private JTextField offline_endTimeTextField;
	private JTextField email_sendToTextField;
	private JTextField textField_1;
	private JTextField email_subjectTextField;
	private JTextField online_searchBySubjectTextField;

	/**
	 * This function takes the variable name of JPanel and change the displaying
	 * panel to the Panel passed by the parameter.
	 * 
	 * @param addedPanel
	 */
	public void changePanel(JPanel addedPanel) {
		layeredPane.removeAll();
		layeredPane.add(addedPanel);
		layeredPane.repaint();
		layeredPane.revalidate();

	}// changePanel

	public void windowClosing(WindowEvent e) {
		System.out.println("WindowClosingDemo.windowClosing");
		System.exit(0);
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public GUI(String UserDirectoryPath, String user, String userStat, String inputFilePath) throws IOException {
		// Creates an URLsearch instance for online search function.
		URLSearch ur = new URLSearch(UserDirectoryPath);
		// Creates an instance of DB class to store records.
		DB database = new DB(UserDirectoryPath, user, inputFilePath);
		String userName = user; // Store's user's name for putting user's name on the database record.
		String userStatus = userStat; // Store's user's status of Guest, userAcount, or Admin.

		if (userStatus != "Guest") {
			database.reconstructDB();
		}

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				try {
					database.writeFileSize(UserDirectoryPath + "logFile.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 668, 409);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel userStatusLabel = new JLabel(userStatus);
		userStatusLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		userStatusLabel.setForeground(SystemColor.activeCaption);
		userStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userStatusLabel.setBounds(0, 6, 136, 27);
		contentPane.add(userStatusLabel);
		userStatusLabel.setText(userStatus);

		layeredPane = new JLayeredPane();
		layeredPane.setBounds(135, 0, 533, 387);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel onlineSearchPanel = new JPanel();
		layeredPane.add(onlineSearchPanel, "name_5990181384172");
		onlineSearchPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("online Search");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(193, 1, 185, 28);
		onlineSearchPanel.add(lblNewLabel);

		JLabel lblNewLabel_6 = new JLabel("Search textbooks:");
		lblNewLabel_6.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_6.setBounds(44, 45, 130, 16);
		onlineSearchPanel.add(lblNewLabel_6);

		online_searchField = new JTextField();
		online_searchField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		online_searchField.setBounds(193, 40, 139, 26);
		onlineSearchPanel.add(online_searchField);
		online_searchField.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(6, 139, 521, 242);
		onlineSearchPanel.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);

		// An array for the list of combo box to choose.
		String[] subjectList = { "Subjects", "Accounting and Finance", "Business", "Computer Science", "Economics",
				"Education", "Engineering", "Humanities", "Journalism", "Law", "Mathematics", "Medicine",
				"Natural Sciences", "Social Sciences", "Student Success" };
		JComboBox online_subjectComboBox = new JComboBox(subjectList);
		online_subjectComboBox.setBounds(213, 94, 139, 29);
		onlineSearchPanel.add(online_subjectComboBox);

		JCheckBox online_downloadImagesCheckBox = new JCheckBox("Download images");
		online_downloadImagesCheckBox.setSelected(true);
		online_downloadImagesCheckBox.setBounds(353, 5, 151, 23);
		onlineSearchPanel.add(online_downloadImagesCheckBox);

		if (userStatus == "Guest")
			online_downloadImagesCheckBox.setEnabled(false);

		JButton online_searchButton = new JButton("Search by Titles");
		online_searchButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		online_searchButton.setBounds(344, 40, 151, 29);
		online_searchButton.addActionListener(new ActionListener() {

			/**
			 * This button action searches record from an ISBN number.
			 */
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				String searchBook = online_searchField.getText(); // Stores ISBN number from the text field.
				try {
					HashMap<String, BookInfo> searchData = ur.searchResult(searchBook, userName,
							online_downloadImagesCheckBox.isSelected(), database);
					DB tempDB = new DB(); // Declare a temporary database instance.
					tempDB.deleteDB();
					tempDB.storeData(searchData); // Stores the search result in the temp instance.

					// Set the searh result in the text field.
					textArea.setText(tempDB.getRecordFields() + tempDB.getStorage());

//					if (userStatus == "Admin" || userStatus == "userAcount") {
//						database.copyData(searchData); // Store the search result into the main database.
//					}

				} // try
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // catch
			}// actionPerformed
		});
		onlineSearchPanel.add(online_searchButton);

		online_searchBySubjectTextField = new JTextField();
		online_searchBySubjectTextField.setBounds(80, 94, 130, 26);
		onlineSearchPanel.add(online_searchBySubjectTextField);
		online_searchBySubjectTextField.setColumns(10);

		JButton online_advancedSearchButton = new JButton("Search by Subject");
		online_advancedSearchButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		online_advancedSearchButton.addActionListener(new ActionListener() {
			/**
			 * This button action searches the textbooks online based on the subject
			 * selected by the user.
			 */
			public void actionPerformed(ActionEvent e) {
				// Parse the comboBox object selected into String type.
				String subject = (String) online_subjectComboBox.getSelectedItem();
				HashMap<String, BookInfo> searchData; // Declare hash map object to store the data from the search.
				String title = online_searchBySubjectTextField.getText();
				boolean saveImage = online_downloadImagesCheckBox.isSelected();
				try {
					// Stores the advanced search result.
					if (title.isEmpty()) {
						searchData = ur.advancedSearchResult(subject, userName, saveImage);
					} else {
						searchData = ur.advancedSearchWithTitle(title, subject, userName, saveImage);
					}
					DB tempDB = new DB(); // a temporary database object.
					tempDB.storeData(searchData); // Store the search result in the tempDB.
					// Shows the search result with record fields.
					textArea.setText(tempDB.getRecordFields() + tempDB.getStorage());

					// delete the data stored in the temporary database instance.
					tempDB.deleteDB();
					// Stores the search result in the database.
					database.storeData(searchData);
				} // try
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // catch

			}// actionPerformed
		});
		online_advancedSearchButton.setBounds(354, 93, 161, 29);
		onlineSearchPanel.add(online_advancedSearchButton);

		JLabel lblNewLabel_24 = new JLabel("Title");
		lblNewLabel_24.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_24.setBounds(44, 99, 48, 28);
		onlineSearchPanel.add(lblNewLabel_24);

		JPanel offlineQueryPanel = new JPanel();
		layeredPane.add(offlineQueryPanel, "name_5990197430372");
		offlineQueryPanel.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Offline Queries");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(194, 17, 146, 26);
		offlineQueryPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_7 = new JLabel("Search textbooks:");
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(6, 77, 135, 16);
		offlineQueryPanel.add(lblNewLabel_7);

		offline_startTimeTextField = new JTextField();
		offline_startTimeTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		offline_startTimeTextField.setText("From");
		offline_startTimeTextField.setBounds(140, 73, 150, 26);
		offlineQueryPanel.add(offline_startTimeTextField);
		offline_startTimeTextField.setColumns(10);

		offline_endTimeTextField = new JTextField();
		offline_endTimeTextField.setText("To");
		offline_endTimeTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		offline_endTimeTextField.setBounds(337, 72, 170, 26);
		offlineQueryPanel.add(offline_endTimeTextField);
		offline_endTimeTextField.setColumns(10);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(6, 143, 521, 238);
		offlineQueryPanel.add(scrollPane_2);

		JTextArea offline_searchResultArea = new JTextArea();
		scrollPane_2.setViewportView(offline_searchResultArea);

		JButton offline_searchButton = new JButton("Search");
		offline_searchButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		offline_searchButton.addActionListener(new ActionListener() {
			/**
			 * This button action retrieves queries within the time range the record is
			 * stored.
			 */
			public void actionPerformed(ActionEvent e) {
				// Stores string of the start time from the text field.
				String startTime = offline_startTimeTextField.getText();
				startTime += ":00.00";
				Timestamp startTimestamp = Timestamp.valueOf(startTime); // Parses the string into Timestamp type.

				// Stores string of the end time from the text field.
				String endTime = offline_endTimeTextField.getText();
				endTime += ":00.00";
				Timestamp endTimestamp = Timestamp.valueOf(endTime);// Parses the string into Timestamp type.

				// Show the queries returned by the function of searchRecordOnTimeRange passing
				// the objects of Timestamp.
				offline_searchResultArea.setText(database.searchRecordOnTimeRange(startTimestamp, endTimestamp));
			}// actionPerformed
		});
		offline_searchButton.setBounds(194, 111, 117, 29);
		offlineQueryPanel.add(offline_searchButton);

		JLabel lblNewLabel_1 = new JLabel("~");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(302, 77, 23, 16);
		offlineQueryPanel.add(lblNewLabel_1);

		JButton admin_showAllUserDBButton = new JButton("Show all user's DB");
		admin_showAllUserDBButton.setEnabled(false);
		if (userStatus == "Admin")
			admin_showAllUserDBButton.setEnabled(true);

		admin_showAllUserDBButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					offline_searchResultArea.setText(database.getAllDatabase());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}// actionPerformed
		});
		admin_showAllUserDBButton.setBounds(361, 112, 146, 29);
		offlineQueryPanel.add(admin_showAllUserDBButton);

		JPanel viewStoragePanel = new JPanel();
		layeredPane.add(viewStoragePanel, "name_20498643419931");
		viewStoragePanel.setLayout(null);

		JLabel lblNewLabel_23 = new JLabel("view storage");
		lblNewLabel_23.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel_23.setBounds(200, 18, 142, 29);
		viewStoragePanel.add(lblNewLabel_23);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(6, 68, 521, 313);
		viewStoragePanel.add(scrollPane_3);

		JTextArea viewStorageTextArea = new JTextArea();
		viewStorageTextArea.setEditable(false);
		scrollPane_3.setViewportView(viewStorageTextArea);

		JPanel edit_insertPanel = new JPanel();
		layeredPane.add(edit_insertPanel, "name_13324345022874");
		edit_insertPanel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Insert Mode");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel_3.setBounds(182, 18, 122, 26);
		edit_insertPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_10 = new JLabel("ISBN:");
		lblNewLabel_10.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(132, 56, 61, 26);
		edit_insertPanel.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Title:");
		lblNewLabel_11.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_11.setBounds(132, 94, 50, 16);
		edit_insertPanel.add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("Description:");
		lblNewLabel_12.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_12.setBounds(78, 122, 95, 16);
		edit_insertPanel.add(lblNewLabel_12);

		JLabel lblNewLabel_13 = new JLabel("Publisher:");
		lblNewLabel_13.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_13.setBounds(92, 182, 78, 16);
		edit_insertPanel.add(lblNewLabel_13);

		JLabel lblNewLabel_14 = new JLabel("Language:");
		lblNewLabel_14.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_14.setBounds(92, 210, 78, 16);
		edit_insertPanel.add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("CopyRight Year:");
		lblNewLabel_15.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_15.setBounds(51, 254, 122, 16);
		edit_insertPanel.add(lblNewLabel_15);

		insert_isbnTextField = new JTextField();
		insert_isbnTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_isbnTextField.setBounds(182, 56, 184, 26);
		edit_insertPanel.add(insert_isbnTextField);
		insert_isbnTextField.setColumns(10);

		insert_titleTextField = new JTextField();
		insert_titleTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_titleTextField.setBounds(182, 89, 184, 26);
		edit_insertPanel.add(insert_titleTextField);
		insert_titleTextField.setColumns(10);

		insert_publisherTextField = new JTextField();
		insert_publisherTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_publisherTextField.setBounds(182, 182, 184, 26);
		edit_insertPanel.add(insert_publisherTextField);
		insert_publisherTextField.setColumns(10);

		insert_languageTextField = new JTextField();
		insert_languageTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_languageTextField.setBounds(182, 211, 184, 26);
		edit_insertPanel.add(insert_languageTextField);
		insert_languageTextField.setColumns(10);

		insert_copyRightTextField = new JTextField();
		insert_copyRightTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_copyRightTextField.setBounds(182, 249, 184, 26);
		edit_insertPanel.add(insert_copyRightTextField);
		insert_copyRightTextField.setColumns(10);

		JTextArea insert_descriptTextArea = new JTextArea();
		insert_descriptTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_descriptTextArea.setBounds(182, 121, 322, 53);
		edit_insertPanel.add(insert_descriptTextArea);

		JTextArea insert_messageTextArea = new JTextArea();
		insert_messageTextArea.setBackground(SystemColor.window);
		insert_messageTextArea.setEditable(false);
		insert_messageTextArea.setBounds(92, 282, 345, 26);
		edit_insertPanel.add(insert_messageTextArea);
		insert_messageTextArea.setVisible(false);

		JButton insert_insertRecordButton = new JButton("Insert Record");
		insert_insertRecordButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		insert_insertRecordButton.addActionListener(new ActionListener() {
			/**
			 * This button action inserts the record with the values user types.
			 */
			public void actionPerformed(ActionEvent e) {
				String isbn = insert_isbnTextField.getText();
				// When the key value does not exist, insert the record.
				if (!database.keyExist(isbn)) {

					try {
						/*
						 * calls the insertRecord function of DB class, passing the values from text
						 * fields.
						 */
						database.insertRecord(userName, insert_isbnTextField.getText(), insert_titleTextField.getText(),
								insert_descriptTextArea.getText(), insert_publisherTextField.getText(),
								insert_languageTextField.getText(), insert_copyRightTextField.getText(), "null");
						// show the message after inserting the record.
						insert_messageTextArea.setText("The record of ISBN: " + isbn + " inserted.");
						insert_messageTextArea.setForeground(Color.black);
						insert_messageTextArea.setVisible(true);

					} // try
					catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // catch

				} // if
					// When the key already exists, shows the message below.
				else {
					insert_messageTextArea.setText("The record of ISBN: " + isbn + " already exist.");
					insert_messageTextArea.setForeground(Color.RED);
					insert_messageTextArea.setVisible(true);

				} // else
			}// actionPerformed

		});
		insert_insertRecordButton.setBounds(172, 310, 140, 36);
		edit_insertPanel.add(insert_insertRecordButton);

		JPanel edit_modifyPanel = new JPanel();
		layeredPane.add(edit_modifyPanel, "name_13396556135645");
		edit_modifyPanel.setLayout(null);

		JLabel lblNewLabel_8 = new JLabel("Modify Mode");
		lblNewLabel_8.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel_8.setBounds(208, 6, 123, 28);
		edit_modifyPanel.add(lblNewLabel_8);

		JLabel lblNewLabel_16 = new JLabel("ISBN:");
		lblNewLabel_16.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_16.setBounds(110, 51, 61, 16);
		edit_modifyPanel.add(lblNewLabel_16);

		JLabel lblNewLabel_17 = new JLabel("Title:");
		lblNewLabel_17.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_17.setBounds(120, 85, 61, 16);
		edit_modifyPanel.add(lblNewLabel_17);

		JLabel lblNewLabel_18 = new JLabel("Description:");
		lblNewLabel_18.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_18.setBounds(100, 134, 92, 16);
		edit_modifyPanel.add(lblNewLabel_18);

		JLabel lblNewLabel_19 = new JLabel("Publisher:");
		lblNewLabel_19.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_19.setBounds(100, 186, 81, 16);
		edit_modifyPanel.add(lblNewLabel_19);

		JLabel lblNewLabel_20 = new JLabel("Language:");
		lblNewLabel_20.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_20.setBounds(100, 221, 81, 16);
		edit_modifyPanel.add(lblNewLabel_20);

		JLabel lblNewLabel_21 = new JLabel("CopyRight Year:");
		lblNewLabel_21.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_21.setBounds(78, 259, 123, 16);
		edit_modifyPanel.add(lblNewLabel_21);

		modify_isbnTextField = new JTextField();
		modify_isbnTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_isbnTextField.setBounds(208, 46, 172, 26);
		edit_modifyPanel.add(modify_isbnTextField);
		modify_isbnTextField.setColumns(10);

		modify_titleTextField = new JTextField();
		modify_titleTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_titleTextField.setBounds(208, 80, 194, 26);
		edit_modifyPanel.add(modify_titleTextField);
		modify_titleTextField.setColumns(10);

		JTextArea modify_descriptTextArea = new JTextArea();
		modify_descriptTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_descriptTextArea.setBounds(208, 111, 247, 58);
		edit_modifyPanel.add(modify_descriptTextArea);

		modify_publisherTextField = new JTextField();
		modify_publisherTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_publisherTextField.setBounds(208, 181, 188, 26);
		edit_modifyPanel.add(modify_publisherTextField);
		modify_publisherTextField.setColumns(10);

		modify_languageTextField = new JTextField();
		modify_languageTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_languageTextField.setBounds(208, 216, 194, 26);
		edit_modifyPanel.add(modify_languageTextField);
		modify_languageTextField.setColumns(10);

		modify_copyRightTextField = new JTextField();
		modify_copyRightTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_copyRightTextField.setBounds(208, 254, 172, 26);
		edit_modifyPanel.add(modify_copyRightTextField);
		modify_copyRightTextField.setColumns(10);

		JTextArea modify_messagetextArea = new JTextArea();
		modify_messagetextArea.setBackground(SystemColor.window);
		modify_messagetextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_messagetextArea.setBounds(138, 287, 252, 16);
		edit_modifyPanel.add(modify_messagetextArea);
		modify_messagetextArea.setVisible(false);

		JButton modify_modifyRecordButton = new JButton("Modify Record");
		modify_modifyRecordButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		modify_modifyRecordButton.addActionListener(new ActionListener() {
			/**
			 * This button action modifies the record from the user's input.
			 */
			public void actionPerformed(ActionEvent e) {
				String[] bookArray = new String[7]; // An array stores textbook data.
				String isbn = modify_isbnTextField.getText();
				bookArray[0] = userName;
				bookArray[1] = isbn;
				bookArray[2] = modify_titleTextField.getText();
				bookArray[3] = modify_descriptTextArea.getText();
				bookArray[4] = modify_publisherTextField.getText();
				bookArray[5] = modify_languageTextField.getText();
				bookArray[6] = modify_copyRightTextField.getText();

				try {
					// if the isbn user enters exist, modifies the record.
					if (database.keyExist(bookArray[1])) {
						database.modifyRecord(bookArray);
						// shows the message when the record is successfully modified.
						modify_messagetextArea.setText("ISBN: " + isbn + " modified.");
						modify_messagetextArea.setForeground(Color.black);
						modify_messagetextArea.setVisible(true);
					} // if
					else {
						// Shows the message when ISBN number does not exist.
						modify_messagetextArea.setText("The ISBN: " + isbn + " does not exist.");
						modify_messagetextArea.setForeground(Color.red);
						modify_messagetextArea.setVisible(true);

					} // else
				} // try
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // catch

			}// actionPerformed
		});
		modify_modifyRecordButton.setBounds(185, 315, 130, 29);
		edit_modifyPanel.add(modify_modifyRecordButton);

		JPanel edit_deletePanel = new JPanel();
		layeredPane.add(edit_deletePanel, "name_13406993076333");
		edit_deletePanel.setLayout(null);

		JLabel lblNewLabel_9 = new JLabel("Delete Mode");
		lblNewLabel_9.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		lblNewLabel_9.setBounds(179, 17, 117, 16);
		edit_deletePanel.add(lblNewLabel_9);

		JLabel lblNewLabel_22 = new JLabel("ISBN:");
		lblNewLabel_22.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_22.setBounds(127, 146, 48, 16);
		edit_deletePanel.add(lblNewLabel_22);

		delete_isbnTextField = new JTextField();
		delete_isbnTextField.setText("Enter ISBN to delete the record.");
		delete_isbnTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		delete_isbnTextField.setBounds(200, 140, 201, 30);
		edit_deletePanel.add(delete_isbnTextField);
		delete_isbnTextField.setColumns(10);

		JTextArea delete_textArea = new JTextArea();
		delete_textArea.setBackground(SystemColor.window);
		delete_textArea.setForeground(Color.RED);
		delete_textArea.setEditable(false);
		delete_textArea.setBounds(105, 207, 285, 16);
		edit_deletePanel.add(delete_textArea);
		delete_textArea.setVisible(false);

		JButton delete_deleteRecordButton = new JButton("Delete Record");
		delete_deleteRecordButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		delete_deleteRecordButton.addActionListener(new ActionListener() {
			/**
			 * This button action delete a record which associates with the isbn number the
			 * user enters.
			 */
			public void actionPerformed(ActionEvent e) {

				String isbn = delete_isbnTextField.getText(); // Stores ISBN number from the text field.
				try {
					if (database.deleteRecord(isbn)) {
						// if the record is successfully deleted, prints out the following message.
						delete_textArea.setText("The record of " + isbn + " deleted.");
						delete_textArea.setVisible(true);
					} // else
					else {
						// if the isbn number does not exist, prints out the following message.
						delete_textArea.setText("The record of " + isbn + " does not exist.");
						delete_textArea.setVisible(true);
					} // else

				} // try
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // catch
			}// actionPerformed
		});
		delete_deleteRecordButton.setBounds(179, 235, 149, 29);
		edit_deletePanel.add(delete_deleteRecordButton);

		JPanel emaiPanel = new JPanel();
		layeredPane.add(emaiPanel, "name_8124194339176");
		emaiPanel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("Send Database Report");
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel_4.setBackground(SystemColor.window);
		lblNewLabel_4.setBounds(173, 17, 198, 28);
		emaiPanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("To:");
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(113, 75, 33, 21);
		emaiPanel.add(lblNewLabel_5);

		JLabel label6 = new JLabel("Subject:");
		label6.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label6.setBounds(103, 148, 71, 16);
		emaiPanel.add(label6);

		email_sendToTextField = new JTextField();
		email_sendToTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		email_sendToTextField.setBounds(186, 70, 185, 26);
		emaiPanel.add(email_sendToTextField);
		email_sendToTextField.setColumns(10);

		email_subjectTextField = new JTextField();
		email_subjectTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		email_subjectTextField.setText("Database Report");
		email_subjectTextField.setBounds(196, 144, 175, 26);
		emaiPanel.add(email_subjectTextField);
		email_subjectTextField.setColumns(10);

		JButton email_SendButton = new JButton("Send email");
		email_SendButton.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		email_SendButton.addActionListener(new ActionListener() {
			/**
			 * This button action sends e-mail report to the address entered in the
			 * email_sendToTextField.
			 */
			public void actionPerformed(ActionEvent e) {
				String sendTo = email_sendToTextField.getText(); // Store the recipient address string.
				String subjectLine = email_subjectTextField.getText(); // Stores the subject line string.
				String reportContent = database.getRecordFields() + database.getStorage(); // Store the database record
																							// as a string.

				try {
					// Calls the function of Email class to send a DB report.
					Email.sendEmailReport(sendTo, subjectLine, reportContent);
				} // try
				catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // catch

			}// actionPerformed
		});
		email_SendButton.setBounds(186, 249, 117, 29);
		emaiPanel.add(email_SendButton);

		JPanel admin_viewLogFilesPanel = new JPanel();
		admin_viewLogFilesPanel.setLayout(null);
		layeredPane.add(admin_viewLogFilesPanel, "name_1401427360876");

		JLabel label = new JLabel("View Log Files");
		label.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		label.setBounds(170, 19, 142, 29);
		admin_viewLogFilesPanel.add(label);

		JTextArea viewLogFilesTextArea = new JTextArea();

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(6, 68, 521, 313);
		admin_viewLogFilesPanel.add(scrollPane_4);
		scrollPane_4.setViewportView(viewLogFilesTextArea);

		onlineSearchButton = new JButton("online");
		onlineSearchButton.setBounds(6, 45, 117, 29);
		onlineSearchButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to onlineSearchPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(onlineSearchPanel);
			}// actionPerformed
		});
		contentPane.add(onlineSearchButton);

		JButton offlineQueryButton = new JButton("Offline Query");
		offlineQueryButton.setBounds(6, 73, 117, 29);
		offlineQueryButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to offlineQueryPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(offlineQueryPanel);
			}// actionPerformed
		});
		contentPane.add(offlineQueryButton);

		// When userStatus is Guest, disables the offlineQuery Button.
		if (userStatus == "Guest") {
			offlineQueryButton.setEnabled(false);
		} // if

		JButton edit_insertRecordButton = new JButton("Insert");
		edit_insertRecordButton.setBounds(6, 197, 117, 29);
		contentPane.add(edit_insertRecordButton);

		// When userStatus is Guest, disables the edit_insertRecord Button.
		if (userStatus == "Guest") {
			edit_insertRecordButton.setEnabled(false);
		} // if

		JButton edit_modifyRecordButton = new JButton("Modify");
		edit_modifyRecordButton.setBounds(6, 229, 117, 29);
		contentPane.add(edit_modifyRecordButton);

		// When userStatus is Guest, disables the edit_modifyRecord Button.
		if (userStatus == "Guest") {
			edit_modifyRecordButton.setEnabled(false);
		} // if

		JButton edit_deleteRecordButton = new JButton("Delete");
		edit_deleteRecordButton.setBounds(6, 259, 117, 29);
		contentPane.add(edit_deleteRecordButton);

		// When userStatus is Guest, disables the edit_deleteRecord Button.
		if (userStatus == "Guest") {
			edit_deleteRecordButton.setEnabled(false);
		} // if

		JButton viewStorageButton = new JButton("View");
		viewStorageButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to viewStoragePanel and shows the database record.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(viewStoragePanel);
				viewStorageTextArea.setText(database.getRecordFields() + database.getStorage());

			}// actionPerformed
		});
		viewStorageButton.setBounds(6, 127, 117, 29);
		contentPane.add(viewStorageButton);

		JButton reconstructDBButton = new JButton("Reconstruct");
		reconstructDBButton.addActionListener(new ActionListener() {
			/**
			 * This button action reconstructs the database from the db_backup.txt file.
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					database.reconstructDB();
				} // try
				catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // catch
			}// acitionPerformed
		});
		reconstructDBButton.setBounds(6, 300, 117, 29);
		contentPane.add(reconstructDBButton);
		// When userStatus is Guest, disables the reconstruct Button.
		if (userStatus == "Guest") {
			reconstructDBButton.setEnabled(false);
		} // if

		// When userStatus is Admin, enables the reconstruct Button.
		if (userStatus == "Admin") {
			reconstructDBButton.setEnabled(true);
		} // if

		JButton sendEmail_button = new JButton("Send Report");
		sendEmail_button.addActionListener(new ActionListener() {
			/**
			 * Change the panel to emaiPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(emaiPanel);

			}// actionPerformed
		});
		sendEmail_button.setBounds(0, 352, 117, 29);
		contentPane.add(sendEmail_button);
		sendEmail_button.setBounds(6, 331, 117, 29);
		contentPane.add(sendEmail_button);

		JButton logFileButton = new JButton("Log File");
		logFileButton.setEnabled(false);
		if (userStatus == "Admin")
			logFileButton.setEnabled(true);
		logFileButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to emaiPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(admin_viewLogFilesPanel);
				try {
					viewLogFilesTextArea.setText(database.getAllLogFiles());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}// actionPerformed
		});
		logFileButton.setBounds(6, 156, 117, 29);
		contentPane.add(logFileButton);

		if (userStatus == "Guest") {
			sendEmail_button.setEnabled(false);
		} // if
		edit_deleteRecordButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to edit_deletePanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(edit_deletePanel);
			}// actionPerformed
		});
		edit_modifyRecordButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to edit_modifyPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(edit_modifyPanel);
			}// actionPerformed
		});
		edit_insertRecordButton.addActionListener(new ActionListener() {
			/**
			 * Change the panel to edit_insertPanel.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanel(edit_insertPanel);

			}// actionPerformed
		});
	}// GUI (constructor)
}// GUI
