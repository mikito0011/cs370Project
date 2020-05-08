import java.io.BufferedReader; //Buffered reader for reading file and get data.
import java.io.BufferedWriter; //Buffered reader for reading file and get data.
import java.io.File; // For the argument of BufferedReader and BufferedWriter.
import java.io.FileNotFoundException; //For the exception when a file is not found.
import java.io.FileReader; //For reading a file and get data.
import java.io.FileWriter;//For writing a lines into a output file.
import java.io.IOException; //IO exception for try and catch block.
import java.io.PrintWriter; //To write data into a file.
import java.sql.Timestamp; //For the time of transaction.
import java.text.SimpleDateFormat; //For format of the Timestamp
import java.util.HashMap; //To create hashMap object to store hashMap data as the storage.

/**
 * This class works as a database system with hashMap. This class gets records
 * from BookInfo class And holds functions for data manipulation features.
 * 
 * @author Mikito Takeshima
 * @version 1.1
 * @since 2020-04-26
 */
public class DB {
	// A static instance to hashmap to store data from List class.
	public HashMap<String, BookInfo> bookDB = new HashMap<String, BookInfo>();
	String outputDirectory; // Store outputDirectory path
	String ouputFile;
	// dafeult logfile
	String logFilePath;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

	// default user
	String userName;

	/**
	 * A default constructor for the guest Account.
	 */
	public DB() {
		userName = "Guest";
	}// DB

	/**
	 * Database constructor which takes logFilepath as a parameter to store the path
	 * of logFile
	 * 
	 * @param logFilePath
	 * @throws IOException
	 */
	public DB(String outputDirect, String user, String inputFilePath) throws IOException {
		if (user != "Guest") {
			outputDirectory = outputDirect;
			userName = user;
			logFilePath = outputDirectory + '/' + "logFile.txt";

			if (!inputFilePath.isEmpty())
				readFile(inputFilePath);
		} else {
			userName = "Guest";
		}
	}// DB

	/**
	 * This method map the name of items and the other information from ItemInfo
	 * class.
	 * 
	 * @param x this is an instance of ItemInfo which stores all data of a product.
	 * @throws IOException
	 */
	void storeData(BookInfo x) throws IOException {
		bookDB.put(x.getISBNNum(), x);
		if (userName != "Guest")
			updateOutputFile();
	}// storeData

	/**
	 * This method stores stores all HashMap data.
	 * 
	 * @param copy
	 * @throws IOException
	 */
	void storeData(HashMap<String, BookInfo> copy) throws IOException {
		bookDB.putAll(copy);
		if (userName != "Guest")
			this.updateOutputFile();

	}// storeData

	void copyData(HashMap<String, BookInfo> copy) throws IOException {

		for (String key : copy.keySet()) {
			bookDB.put(key, copy.get(key));
		}
		System.out.println(getStorage());
		if (userName != "Guest")
			this.updateOutputFile();

	}// storeData

	/**
	 * This function deletes the all record of hash map.
	 */

	void deleteDB() {
		bookDB.clear();
	}// deleteDB

	/**
	 * Returns boolean value if the isbn number exists.
	 * 
	 * @param isbn
	 * @return
	 */
	public boolean keyExist(String isbn) {
		return bookDB.containsKey(isbn);
	}// keyExist

	/**
	 * This returns the line of storage field for the first line of the output file
	 * and log File.
	 * 
	 * @return string of record Fields
	 */
	public String getRecordFields() {
		return "Transaction Time|User Name|ISBN13|Title|Description|Publisher|Language|Copyright Year|Saved Image Name \n";
	}// getRecordFields

	/**
	 * This returns the fields for log files including transaction type.
	 * 
	 * @return returns the string of fields.
	 */
	public String getLogFields() {
		return "Transaction Time|Transaction Type|User Name|ISBN13|Title|Description|Publisher|Language|Copyright Year|Saved Image Name \n";
	}// getLogFields

	/**
	 * This method modifies string format of each product for the output file.
	 * 
	 * @return modified string line of product information.
	 */
	public String getStorage() {
		String line = bookDB.toString();
		line = line.replaceAll(".+=", ""); // Take out string after = sign.
		line = line.replace("}", ""); // Remove the last character }.
		return line;
	}// getStorage

	/**
	 * This function returns a current date with a specified format when this
	 * function is called. This code is referenced from Mkyong.com provided in the
	 * class.
	 * 
	 * @return current time.
	 */
	public String getCurrentTime() {
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());

		return sdf.format(currentTimeStamp);
	}// getCurrentTime

	/**
	 * Reads a file from an argument and store data on the file into the storage.
	 * 
	 * @param fileName The path of input file.
	 * @throws IOException
	 */
	public void readFile(String fileName) throws IOException {
		// an array to hold each lines of the input file.
		String[] inputArray = new String[1000];

		int i = 0; // Store number of lines on input File.

		// Exception block. Catch an error when file is unreadable.
		try {
			// Read the input file.
			File file = new File(fileName);
			BufferedReader br = new BufferedReader(new FileReader(file));

			// Skip the first line since the first line is fields.
			br.readLine();
			// Store each line of the file into the array, inputArray, until there is no
			// more lines.
			while ((inputArray[i] = br.readLine()) != null) {
				i++;
			} // while

			br.close();// Close bufferedReader.

		} // try

		// Catch an error when the file is not found.
		catch (FileNotFoundException e) {
			System.out.println(e);
		} // catch

		// Catch an error when IO exception happens
		catch (IOException e) {
			System.out.println(e);
		} // catch

		i--; // Decrease i to avoid read an empty line.

		// Go through each line on input file and store data into the storage.
		while (i >= 0) {
			// creates an array holds elements of each lines, separated by a vertical line.
			String inputElements[] = inputArray[i].split("[|]");

			/*
			 * [0]- ISBNNum [1]- title [2]- description [3]- publisher [4]- language [5]-
			 * copyRightYear [6]- savedImageName
			 */
			// For each set of elements of each line, creates ItemInfo objects to store
			// data.
			BookInfo textBook = new BookInfo(inputElements[0], inputElements[1], inputElements[2], inputElements[3],
					inputElements[4], inputElements[5], inputElements[6]);

			// Store each records into the hasmMap.
			this.storeData(textBook);

			if (userName != "Guest")
				writeLogFile(inputElements[0], "InputFile");
			--i; // decrement i for the loop to go through each line.
		} // while

		if (userName != "Guest")
			updateOutputFile(); // update the backup file for the database

	} // readFile

	/**
	 * This function reconstructs database from db_back.txt file which updates every
	 * time database info changes.
	 * 
	 * @throws IOException
	 */
	public void reconstructDB() throws IOException {
		// an array to hold each lines of the input file.
		String[] inputArray = new String[1000];
		String filePath = outputDirectory + "/" + userName + ".txt";
		int i = 0; // Store number of lines on input File.

		// Exception block. Catch an error when file is unreadable.
		try {
			// Read the backup file.
			File backUpfile = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(backUpfile));

			// Store each line of the file into the array, inputArray, until there is no
			// more lines.
			while ((inputArray[i] = br.readLine()) != null) {
				i++;
			} // while

			br.close();// Close bufferedReader.

		} // try

		// Catch an error when the file is not found.
		catch (FileNotFoundException e) {
			System.out.println(e);
		} // catch

		// Catch an error when IO exception happens
		catch (IOException e) {
			System.out.println(e);
		} // catch

		i--; // Decrease i to avoid read an empty line.

		// Go through each line on input file and store data into the storage.
		while (i >= 0) {
			// creates an array holds elements of each lines, separated by a vertical line.
			String inputElements[] = inputArray[i].split("[|]");

			// Capitalize the first letter of each value.
			for (int j = 0; j < inputElements.length; j++) {

				// When the value is empty, assigns -1 as Null.
				if (inputElements[j].isBlank())
					inputElements[j] = "-1";

				// Capitalize the first letter
				inputElements[j] = inputElements[j].substring(0, 1).toUpperCase() + inputElements[j].substring(1);
			} // for

			/*
			 * [0]- transactionTime [1]- userName [2]- ISBNNum [3]- title [4]- description
			 * [5]- publisher [6]- language [7]- copyRightYear [8]- savedImageName
			 */
			// For each set of elements of each line, creates ItemInfo objects to store
			// data.
			BookInfo textBook = new BookInfo(inputElements[0], inputElements[1], inputElements[2], inputElements[3],
					inputElements[4], inputElements[5], inputElements[6], inputElements[7], inputElements[8]);

			// Store each records into the hasmMap.
			this.storeData(textBook);

			// Updates the log file with the transaction type of "Reconstruct".
			writeLogFile(inputElements[2], "Reconstruct");
			--i; // decrement i for the loop to go through each line.
		} // while
	}// reconstructDB

	/**
	 * This method writes the storage data into the output File passed as an
	 * argument.
	 * 
	 * @param fileName The output file path.
	 */
	public void writeFile(String fileName) {
		// Try and catch block to catch an error when IOException occurs.
		try {
			// file allows printWriter to write data.
			FileWriter file = new FileWriter(fileName);
			// Takes file as the argument and can write storage data in the output file.
			PrintWriter printW = new PrintWriter(new BufferedWriter(file));
			// Declare readFile to check if the file already has contents.
			File readFile = new File(fileName);

			if (readFile.length() == 0) // when the output file is empty, write the record info.
				printW.print(this.getRecordFields());
			// Write the storage data into the output file.
			printW.print(this.getStorage());
			printW.close(); // Close the file.

		} // try

		catch (IOException e) {
			System.out.println(e);
		} // catch

	}// writeFile

	/**
	 * This function writes the length of the file on the file, passed in the
	 * parameter and called for log file. Code from
	 * https://mkyong.com/java/how-to-get-file-size-in-java/ and provided in the
	 * class.
	 * 
	 * @param filePath
	 * @throws IOException
	 */
	public void writeFileSize(String filePath) throws IOException {
		// Declare file object to read the file from the parameter, filePath.
		File file = new File(filePath);

		if (file.exists()) {
			// Store the size of the file in bytes.
			double bytes = file.length();
			// Converts the size of the file in kilobytes by dividing by 1024.
			double kilobytes = (bytes / 1024);

			// Declare a file object to write on the file
			FileWriter outFile = new FileWriter(filePath, true);
			PrintWriter writeFile = new PrintWriter(new BufferedWriter(outFile));

			// Write the size of the file in bytes and kilobytes.
			writeFile.println("");
			writeFile.println("\nFile size in Bytes: " + bytes + " Bytes");
			writeFile.println("File size in KB: " + String.format("%.2f", kilobytes) + " KB");
			writeFile.close(); // Close the connection of FileWriter.
		} // if
		else {
			System.out.println("File does not exist");
		} // else
	}// writeFileSize

	/**
	 * This function writes transaction history on the log file with the parameters
	 * of ISBN for the key and logType for the transaction type.
	 * 
	 * @param isbn
	 * @param logType
	 * @throws IOException
	 */
	public void writeLogFile(String isbn, String logType) throws IOException {
		FileWriter lFile = new FileWriter(logFilePath, true);
		PrintWriter printLog = new PrintWriter(new BufferedWriter(lFile));
		// Update the logFile writing the transaction type
		printLog.print(bookDB.get(isbn).toLogString(logType));
		printLog.close(); // Close the connection of printWriter
	}// writeLogFile

	/**
	 * This function stores the current storage data for the backup purpose.
	 * 
	 * @throws IOException
	 */
	public void updateOutputFile() throws IOException {
		// File path of db_backup.txt file
		String filePath = outputDirectory + "/" + userName + ".txt";

		// file allows printWriter to write data.
		FileWriter file = new FileWriter(filePath);
		// Takes file as the argument and can write storage data in the output file.
		PrintWriter printW = new PrintWriter(new BufferedWriter(file));

		// Write the storage data into the output file.
		printW.print(this.getStorage());
		printW.close(); // Close the file.

	}// updateDatabaseBackUp

	/**
	 * This method insert a record in the storage from the parameters provided by a
	 * user.
	 * 
	 * @param user
	 * @param isbn
	 * @param textTitle
	 * @param descript
	 * @param publisherName
	 * @param textLanguage
	 * @param copyRight
	 * @param imageName
	 * @throws IOException
	 */
	public void insertRecord(String user, String isbn, String textTitle, String descript, String publisherName,
			String textLanguage, String copyRight, String imageName) throws IOException {
		// Declare the instance of BookInfo to store the textbook data into the
		// database.
		BookInfo newTextbook = new BookInfo(user, isbn, textTitle, descript, publisherName, textLanguage, copyRight,
				imageName);
		storeData(newTextbook);

		writeLogFile(isbn, "Insert"); // Updates the log file with the transaction type of insert.
		updateOutputFile(); // update the backup file for the database

	}// insertRecord

	/**
	 * This function takes an array of text book data and modifies the record values
	 * based on what values the user puts.
	 * 
	 * @throws IOException
	 */
	public void modifyRecord(String[] bookData) throws IOException {
		String isbn = bookData[1]; // Stores the key of a hashmap.

		bookDB.get(isbn).setUserName(bookData[0]);// Set the user's name.s
		// When the isbn exists.
		if (bookDB.containsKey(isbn)) {
			// change the value of title
			if (!bookData[2].isEmpty()) {
				bookDB.get(isbn).setTitle(bookData[1]);
			} // if
				// change the value of description
			if (!bookData[3].isEmpty()) {
				bookDB.get(isbn).setDescription(bookData[2]);
			} // if
				// change the value of publisher
			if (!bookData[4].isEmpty()) {
				bookDB.get(isbn).setPublisher(bookData[3]);
			} // if
				// change the value of language
			if (!bookData[5].isEmpty()) {
				bookDB.get(isbn).setLanguage(bookData[4]);
			} // if
				// change the value of copyRight year
			if (!bookData[6].isEmpty()) {
				bookDB.get(isbn).setCopyrightYear(bookData[5]);
			} // if

			writeLogFile(bookData[1], "Modify"); // Updates the log file with the transaction type of modify.
			updateOutputFile(); // update the backup file for the database
		} // if

	}// modifyRecord

	/**
	 * This method allows users to search record from the key and return the fields
	 * and record associated with the key.
	 * 
	 * @param isbn
	 * @return Record fields and the record.
	 * @throws IOException
	 */
	public String searchRecord(String isbn) throws IOException {
		if (bookDB.get(isbn) != null) {
			writeLogFile(isbn, "Search");

			return getRecordFields() + bookDB.get(isbn).toString();

		} // if
		else {
			return "This ISBN does not exist on the storage.";
		} // else
	}// searchRecord

	/**
	 * From given the start and end time, this function returns queries within the
	 * time range.
	 * 
	 * @param start
	 * @param end
	 * @return queries of database within the range of time.
	 */
	public String searchRecordOnTimeRange(Timestamp start, Timestamp end) {
		// First add the string of record filed to the variable.
		String queries = getRecordFields();

		/*
		 * This loop goes through all hash map element and check the condition if the
		 * time is within the range.
		 */
		for (String key : bookDB.keySet()) {
			// Check the data of timestamp is after the start and before the end data.
			if (bookDB.get(key).timeStamp.after(start) && bookDB.get(key).timeStamp.before(end))
				queries += bookDB.get(key).toString(); // Add the record data to the string var, queries.
		} // for
		return queries;
	}// searchRecordOnTimeRange

	/**
	 * This function allows users to delete a record from the primary key. Users can
	 * see the whole storage and see the set of all primary keys.
	 * 
	 * @param isbn
	 * @return boolean value depending on success of deleting a record.
	 * @throws IOException
	 */
	public boolean deleteRecord(String isbn) throws IOException {
		// Check if the key exist in the hashmap data.
		if (bookDB.get(isbn) != null) {

			bookDB.remove(isbn); // delete the record linked with the key.

			writeLogFile(isbn, "Delete"); // Updates the log file with the transaction type, delete.
			updateOutputFile(); // update the backup file for the database

			return true;
		} // if

		else {
			return false;
		} // else

	}// deleteRecord

	/**
	 * This function stores all lines of all users' output files which hold user's
	 * database data. And the function returns the saved line.
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getAllDatabase() throws IOException {

		String line = ""; // A string to store all lines of log files
		String tempLine = "";// a temporary string

		// Store the number of users in the userlist file.
		int size = LogInSystem.userDB.keySet().size();
		// Create string array which holds usernames from the userlist file.
		String[] array = LogInSystem.userDB.keySet().toArray(new String[size]);

		// Loop through all users to retrieve data of each user's output file.
		for (int i = 0; i < size; i++) {
			// Store user name from the array.
			userName = array[i];

			// Read the user's output file
			File file = new File("userFolders/" + userName + '/' + userName + ".txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			line += "User:" + userName + "\n";

			// Until the readline reaches null, add each line to the variable line.
			while (br.readLine() != null) {
				tempLine = br.readLine();
				if (tempLine != null)
					line += br.readLine() + '\n';
			} // while
			br.close(); // close BufferedReader br
		} // for

		return line;
	}// getAllDatabase

	/**
	 * This function stores all lines of all users' log files and returns the saved
	 * line.
	 * 
	 * @return line
	 * @throws IOException
	 */
	public String getAllLogFiles() throws IOException {

		String line = ""; // A string to store all lines of log files
		String tempLine = ""; // a temporary string

		// Store the number of users in the userlist file.
		int size = LogInSystem.userDB.keySet().size();
		// Create string array which holds usernames from the userlist file.
		String[] array = LogInSystem.userDB.keySet().toArray(new String[size]);

		// Loop through all users to retrieve data of each user's log file.
		for (int i = 0; i < size; i++) {
			// Store user name from the array.
			userName = array[i];

			// Read the user's log file
			File file = new File("userFolders/" + userName + '/' + "logFile.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			line += "User:" + userName + "\n";

			// Until the readline reaches null, add each line to the variable line.
			while (br.readLine() != null) {
				tempLine = br.readLine();
				if (tempLine != null)
					line += br.readLine() + '\n';
			} // while
			br.close(); // close BufferedReader br
		} // for

		return line;
	}// getAllLogFiles

}// DB
