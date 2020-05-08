
import java.sql.Timestamp; //For the time of transaction.
import java.text.SimpleDateFormat; //For format of the Timestamp

/**
 * This class stores the data of each text book for the database and has
 * functionalities for data manipulation and writing output, log files.
 * 
 * @author mikitotakeshima
 * @version 1.3
 * @since 2020-05-06
 */
public class BookInfo {
	// For the format of timeStamp
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

	// Store the current time when the instance is created.
	Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
	private String userName = "Guest"; // default user name.
	private String transactionTime; // stores the transaction time.
	private String ISBNNum; // Stores the ISBN number
	private String title; // Store the title of a textbook
	private String description; // Store description
	private String publisher; // Store publisher
	private String language; // Stores the language
	private String copyRightYear; // Store copyright year
	private String savedImageName; // Store the name of stored image file.

	/**
	 * A constructor that accepts 7 parameters.
	 * 
	 * @param name
	 * @param quantity
	 * @param priceLB
	 * @param language
	 * @param Made
	 * @param purchased
	 * @param exp
	 */
	BookInfo(String isbn, String textTitle, String descript, String publisherName, String textLanguage,
			String copyRight, String ImageName) {
//		this.userName =name;
		transactionTime = sdf.format(timeStamp);
		ISBNNum = isbn;
		title = textTitle;
		description = descript;
		publisher = publisherName;
		language = textLanguage;
		copyRightYear = copyRight;
		savedImageName = ImageName;

	}// BookInfo

	/**
	 * This constructor takes eight parameters to specify the user's name.
	 * 
	 * @param user
	 * @param isbn
	 * @param textTitle
	 * @param descript
	 * @param publisherName
	 * @param textLanguage
	 * @param copyRight
	 * @param ImageName
	 */
	BookInfo(String user, String isbn, String textTitle, String descript, String publisherName, String textLanguage,
			String copyRight, String ImageName) {
//		this.userName =name;
		transactionTime = sdf.format(timeStamp);
		userName = user;
		ISBNNum = isbn;
		title = textTitle;
		description = descript;
		publisher = publisherName;
		language = textLanguage;
		copyRightYear = copyRight;
		savedImageName = ImageName;

	}// BookInfo

	/**
	 * A constructor that accepts 9 parameters when reconstructing the storage from
	 * backup file.
	 * 
	 * @param transactTime
	 * @param user
	 * @param isbn
	 * @param textTitle
	 * @param descript
	 * @param publisherName
	 * @param textLanguage
	 * @param copyRight
	 * @param ImageName
	 */
	BookInfo(String transactTime, String user, String isbn, String textTitle, String descript, String publisherName,
			String textLanguage, String copyRight, String ImageName) {
		transactionTime = transactTime;
		userName = user;
		ISBNNum = isbn;
		title = textTitle;
		description = descript;
		publisher = publisherName;
		language = textLanguage;
		copyRightYear = copyRight;
		savedImageName = ImageName;
	}// BookInfo

	/**
	 * This function returns userName.
	 * 
	 * @return userName;
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * This function returns ISBNNum.
	 * 
	 * @return ISBNNum
	 */
	public String getISBNNum() {
		return ISBNNum;
	}

	/**
	 * This function returns title
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This function returns description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This function returns publisher
	 * 
	 * @return publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * This function returns copyRightYear
	 * 
	 * @return copyRightYear
	 */
	public String getOopyRightYear() {
		return copyRightYear;
	}

	/**
	 * This function returns savedImageName
	 * 
	 * @return savedImageName
	 */
	public String getSavedImageName() {
		return savedImageName;
	}

	/**
	 * set the instance of Timestamp to the current time when a record is updated.
	 */
	public void setCurrentTransactionTime() {
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		this.transactionTime = sdf.format(currentTimeStamp);
	}// setCurrentTransactionTime

	/**
	 * Set the value of user name and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setUserName(String newVal) {
		if (newVal.isBlank())
			this.userName = "-1";
		else
			this.userName = newVal;
	}// setUserName

	/**
	 * Set the value of title and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setTitle(String newVal) {
		if (newVal.isBlank())
			this.title = "-1";
		else
			this.title = newVal;
	}// setTitle

	/**
	 * Set the value of description and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setDescription(String newVal) {
		if (newVal.isBlank())
			this.description = "-1";
		else
			this.description = newVal;
	}// setDescription

	/**
	 * Set the value of publisher and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setPublisher(String newVal) {
		if (newVal.isBlank())
			this.publisher = "-1";
		else
			this.publisher = newVal;
	}// setPublisher

	/**
	 * Set the value of language and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setLanguage(String newVal) {
		if (newVal.isBlank())
			this.language = "-1";
		else
			this.language = newVal;
	}// setLanguage

	/**
	 * Set the value of description and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setCopyrightYear(String newVal) {
		if (newVal.isBlank())
			this.copyRightYear = "-1";
		else
			this.copyRightYear = newVal;
	}// copyRightYear

	/**
	 * Set the value of copyRight year and assign -1 when the input is blank.
	 * 
	 * @param newVal
	 */
	public void setCopyRightYear(String newVal) {
		if (newVal.isBlank())
			this.copyRightYear = "-1";
		else
			this.copyRightYear = newVal;
	}// setExpDates

	/**
	 * This method modifies all elements into a string. The format will be
	 * {name=product name...}
	 * 
	 * @param no parameters
	 * @return Returns one string line which contains all information of string.
	 */
	public String toString() {
		return transactionTime + "|" + userName + "|" + ISBNNum + "|" + title + "|" + description + "|" + publisher
				+ "|" + language + "|" + copyRightYear + "|" + savedImageName + '\n';
	}// toString

	/**
	 * Convert the field values into the string for logFile. This string includes
	 * the transaction type from the user's input.
	 * 
	 * @param logType transaction type.
	 * @return The string for the record for the logFile.
	 */
	public String toLogString(String logType) {
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(currentTimeStamp) + "|" + logType + "|" + userName + "|" + ISBNNum + "|" + title + "|"
				+ description + "|" + publisher + "|" + language + "|" + copyRightYear + "|" + savedImageName + '\n';

	}// toLogString
}// BookInfo
