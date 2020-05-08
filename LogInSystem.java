
//Libraries for writing and reading files and file error handling
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap; //HashMap library

/**
 * This class has functions of user registration for users to sign up or login.
 * The class converts the list of user registration data into hashMap to map the
 * username with the values of password and user status.
 * 
 * @author mikito takeshima
 *
 */
public class LogInSystem {

	String userFoldersPath = "userFolders/";

	public static HashMap<String, String[]> userDB = new HashMap<String, String[]>();

	/**
	 * This is a default constructor that creates userFolder if it does not exist.
	 * 
	 * @throws IOException
	 */
	public LogInSystem() throws IOException {
		File userFolders = new File("userFolders");
		if (!userFolders.exists()) {
			userFolders.mkdir();

		}
	}// LogInSystem

	/**
	 * This function returns boolean value if the userName is associated with the
	 * user status, Admin.
	 * 
	 * @param userName
	 * @return true or false
	 * @throws IOException
	 */
	public boolean IsAmdin(String userName) throws IOException {
		convertUserListIntoHashMap();
		String userStat = (String) userDB.get(userName)[1];
		if (userStat.equals("Admin"))
			return true;
		return false;
	}// IsAmdin

	/**
	 * This method creates a new file of userList.
	 * 
	 * @param userName
	 * @param passWord
	 * @param userStatus
	 * @throws IOException
	 */
	private void createNewUserList(String userName, String passWord, String userStatus) throws IOException {
		File userListFile = new File("userList.txt");
		userListFile.createNewFile();
		FileWriter userWrite = new FileWriter("userList.txt");
		userWrite.write("UserName|Password|status \n" + userName + "|" + passWord + "|" + userStatus + '\n');
		userWrite.close();
		convertUserListIntoHashMap();
	}// createNewUserList

	/**
	 * This method adds an user list with user name, password and user status
	 * separated by the vertical line.
	 * 
	 * @param userName
	 * @param passWord
	 * @param userStatus
	 * @throws IOException
	 */
	public void addUserList(String userName, String passWord, String userStatus) throws IOException {
		FileWriter userWrite = new FileWriter("userList.txt", true);

		// add an user list.
		userWrite.write(userName + "|" + passWord + '|' + userStatus + '\n');

		userWrite.close();
		convertUserListIntoHashMap();

	}// addUserList

	/**
	 * This method returns boolean value if the userName exists in the userList
	 * file.
	 * 
	 * @param userName
	 * @return true of false
	 */
	public boolean doesUserExist(String userName) {
		if (userDB.containsKey(userName)) {
			return true;
		} // if
		else {
			return false;
		} // else
	}// doesUserExist

	/**
	 * Returns boolean value if the userList.txt file exists.
	 * 
	 * @return true or false
	 */
	public boolean doesFileExist() {
		File userListFile = new File("userList.txt");
		if (userListFile.exists()) {
			return true;

		} // if
		else {
			return false;
		} // else
	}// doesFileExist

	/**
	 * This method checks if the user name and password matches.
	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws IOException
	 */
	public boolean checkUserNameAndPass(String userName, String passWord) throws IOException {

		convertUserListIntoHashMap();
		// the userName exists.
		if (doesUserExist(userName)) {
			// Parse password associated with the userName into string.
			String pass = (String) userDB.get(userName)[0];
			// Returns true if the pass equals to the password.
			if (pass.equals(passWord)) {

				return true;
			} // if
			else {
				return false;
			} // else
		} // if
		else {
			return false;
		} // else
	}// checkUserNameAndPass

	/**
	 * This method converts the user list into Hashmap data with the key of the
	 * user's name and values of password and user status.
	 * 
	 * @throws IOException
	 */
	public void convertUserListIntoHashMap() throws IOException {
		// Reads the user list.
		File userListFile = new File("userList.txt");
		BufferedReader br = new BufferedReader(new FileReader(userListFile));
		String line = br.readLine();

		String[] userInfo = new String[3];// Declare an array to store each user's data for registration.

		line = br.readLine();
		// Reads each line of userList file.
		while (line != null) {
			// Split each line by the vertical line.
			userInfo = line.split("[|]");
			// Declare an array to store password and userStatus.
			String[] userPassAndStat = new String[2];
			userPassAndStat[0] = userInfo[1];
			userPassAndStat[1] = userInfo[2];

			// Put Hashmap with the key and array values.
			userDB.put(userInfo[0], userPassAndStat);
			line = br.readLine();
		} // while

		br.close();
	}// convertUserListIntoHashMap

	/**
	 * This function returns the user's folder path.
	 * 
	 * @param userName
	 * @return
	 */
	public String getUserFolderPath(String userName) {
		return userFoldersPath + userName + '/';
	}

	/**
	 * This method creates userList text file for user registration.
	 * 
	 * @param userName
	 * @param passWord
	 * @param userStatus
	 * @throws IOException
	 */
	public void createAccount(String userName, String passWord, String userStatus) throws IOException {

		File userListFile = new File("userList.txt");

		// If the userList file exists
		if (userListFile.exists()) {
			addUserList(userName, passWord, "userAccount");
		} // if

		// When the userList.txt file does not exist, the first user will be Admin.
		else {
			createNewUserList(userName, passWord, "Admin");
		} // else

	}// createAccount

	/**
	 * This function creates user folder to store output file for each user.
	 * 
	 * @param userName
	 * @throws IOException
	 */
	public void createUserFolder(String userName) throws IOException {
		// Create a file instance to create the folder for each user account.
		File userFolder = new File(userFoldersPath + userName);
		userFolder.mkdir();

		// Create an output text file with user's name.
		File userOutFile = new File(userFolder.getPath() + "/" + userName + ".txt");
		userOutFile.createNewFile();

	}// createUserFolder

}// LoginSystem
