import java.awt.image.BufferedImage;//for fetching image from URL
import java.io.BufferedReader; //For reading file
import java.io.BufferedWriter; //For writing file
import java.io.File; //For exception of files
import java.io.FileWriter; //For file writing
import java.io.IOException; //For handling IOException
import java.io.InputStreamReader; //To read URL contents
import java.io.PrintWriter; //For writing file

import java.util.HashMap; //HashMap library

// libraries for URL processing.
import java.net.MalformedURLException;
import java.net.URL;

//For saving images
import javax.imageio.ImageIO;

/**
 * This class works for the URL processing for the website of opentextbooks.
 * 
 * @author mikitotakeshima
 * @version 1.3
 * @since 05-06-2020
 *
 */
public class URLSearch {
	// Default URL string to concatenate with a search term.
	String defaultURL = "https://open.umn.edu/opentextbooks/textbooks?term=";
	String logFile; // Stores logFile path
	String outputDirectory; // Stores user's folder path
	String imageFolderPath; // Stores image folder path.

	URL imageUrl = null; // URL object for image

	// Store image file
	File outputImageFile = null;
	public static BufferedImage image = null;

	/**
	 * 
	 * @param outputDirect
	 */
	public URLSearch(String outputDirect) {
		outputDirectory = outputDirect + '/';
		logFile = outputDirectory + "logFile.txt";

		File downloadedImageFolder = new File(outputDirectory + "downloadimageFolder");
		if (!downloadedImageFolder.exists())
			downloadedImageFolder.mkdir();
		imageFolderPath = downloadedImageFolder.getPath() + '/';
	}

	/**
	 * This function returns URL query with serarh terms.
	 * 
	 * @param searchWord
	 * @return string url to process
	 * @throws MalformedURLException
	 */
	public String getSearchURL(String searchWord) {
		String[] searchWords = searchWord.split(" "); // Stores search terms
		String searchURL = defaultURL; // Assign the url of opentextbooks

		// This loop add the serch term on the default URL
		for (int i = 0; i < searchWords.length; i++) {
			searchURL += '+' + searchWords[i];

		} // for

		return searchURL;
	}// getSearchURL

	/**
	 * This function returns string of URL concatenated with the subject from the
	 * parameter, subject.
	 * 
	 * @param subject
	 * @return
	 */
	public String getSubjectURL(String subject) {
		/*
		 * Stores url of subject to concatenate depending on what subject the user
		 * chooses.
		 */

		String subjectURL = "https://open.umn.edu/opentextbooks/subjects/";

		if (subject == "Accounting and Finance") {
			return subjectURL + "accounting-finance";

		} // if
		else if (subject == "Business") {
			return subjectURL + "business";

		} // else if
		else if (subject == "Computer Science") {
			return subjectURL + "computer-science-information-systems";

		} // else if
		else if (subject == "Economics") {
			return subjectURL + "economics";

		} // else if
		else if (subject == "Education") {
			return subjectURL + "education";

		} // else if
		else if (subject == "Engineering") {
			return subjectURL + "engineering";

		} // else if
		else if (subject == "Humanities") {
			return subjectURL + "humanities";

		} // else if
		else if (subject == "Journalism") {
			return subjectURL + "journalism-media-studies-communications";

		} // else if
		else if (subject == "Mathematics") {
			return subjectURL + "mathematics";

		} // else if
		else if (subject == "Medicine") {
			return subjectURL + "medicine";

		} // else if
		else if (subject == "Law") {
			return subjectURL + "law";

		} // else if
		else if (subject == "Natural Sciences") {
			return subjectURL + "natural-sciences";

		} // else if
		else if (subject == "Social Sciences") {
			return subjectURL + "social-sciences";

		} // else if
		else if (subject == "Student Success") {
			return subjectURL + "student-success";

		} // else if
		else {
			return "none";
		}

	}// getSubjectURL

	/**
	 * This returns an object of bufferedReader to read contents of url. This method
	 * is provided in the lecture.
	 * 
	 * @param url
	 * @return BufferedReader object
	 * @throws Exception
	 */
	public static BufferedReader read(URL url) throws Exception {
		return new BufferedReader(new InputStreamReader(url.openStream()));
	} // read

	/**
	 * This function returns the HashMap instance that stores text book data.
	 * 
	 * @param searchWord
	 * @return searchData
	 * @throws Exception
	 */
	public HashMap<String, BookInfo> searchResult(String searchWord, String userName, boolean saveImage, DB database)
			throws Exception {
		// Declare an instance of hashMap to store the data.
		HashMap<String, BookInfo> searchData = new HashMap<String, BookInfo>();

		String url = getSearchURL(searchWord); // Store the URL string with serarhWord query.

		// read web contents from the url
		BufferedReader reader = read(new URL(url));

		// Declare lFile for writing log File
		if (userName != "Guest") {

			// Declare an array to store text book information.
			String[] textInfo = new String[7];

			String line = reader.readLine(); // Read the first line of the site.

			// This loop keeps reading HTML tag until reaches null.
			while (line != null) {

				// When the tag starts with
				if (line.startsWith("<a alt=")) {
					// Concatenate each detailed text book URL.
					String textURL = "https://open.umn.edu"
							+ line.substring(line.indexOf("href=\"") + 6, line.indexOf("\"><img alt"));

					// Store text book information in an array.
					textInfo = getTextBookInfo(textURL);

					// Store image URL to fetch the image.
					if (saveImage == true) {
						String imageURL = "https://open.umn.edu"
								+ line.substring(line.indexOf("src=\"") + 5, line.indexOf("\" /></a>"));
						saveImageFile(imageURL); // saves the image from the URL

						// Store the name of the image file stored previously.
						textInfo[6] = getImageURLsuffix(imageURL);
					} else {
						textInfo[6] = "null";
					}
//				[0] -ISBN
//				[1] -Book Title
//				[2] -Description
//				[3] -publisher
//				[4] -language
//				[5] -Copyright Year
//              [6] -imageName
					// Create an instance to store text book information, assigning array values.
					BookInfo textBook = new BookInfo(userName, textInfo[0], textInfo[1], textInfo[2], textInfo[3],
							textInfo[4], textInfo[5], textInfo[6]);

					// Store the data with key (isbn) and values into hashmap.
					searchData.put(textInfo[0], textBook);
					database.storeData(textBook);

					// Save log transaction of online search.
					database.writeLogFile(textInfo[0], "Online Search");

				} // if

				line = reader.readLine(); // Read the next line.
			} // while

		} else {
			// Declare an array to store text book information.
			String[] textInfo = new String[7];

			String line = reader.readLine(); // Read the first line of the site.

			// This loop keeps reading HTML tag until reaches null.
			while (line != null) {

				// When the tag starts with
				if (line.startsWith("<a alt=")) {
					// Concatenate each detailed text book URL.
					String textURL = "https://open.umn.edu"
							+ line.substring(line.indexOf("href=\"") + 6, line.indexOf("\"><img alt"));

					// Store text book information in an array.
					textInfo = getTextBookInfo(textURL);

					textInfo[6] = "null";

//							[0] -ISBN
//							[1] -Book Title
//							[2] -Description
//							[3] -publisher
//							[4] -language
//							[5] -Copyright Year
//			              [6] -imageName
					// Create an instance to store text book information, assigning array values.
					BookInfo textBook = new BookInfo(userName, textInfo[0], textInfo[1], textInfo[2], textInfo[3],
							textInfo[4], textInfo[5], textInfo[6]);

					// Store the data with key (isbn) and values into hashmap.
					searchData.put(textInfo[0], textBook);

				} // if

				line = reader.readLine(); // Read the next line.
			} // while
		}

		return searchData; // returns hashMap instance.

	}// searchResult

	/**
	 * This function serves for advanced search result. The function returns a
	 * HashMap instance, which stores an advanced search result.
	 * 
	 * @param subject
	 * @param userName
	 * @return searchData
	 * @throws Exception
	 */
	public HashMap<String, BookInfo> advancedSearchResult(String subject, String userName, boolean saveImage)
			throws Exception {
		// Declare an instance of hashMap to store the data.
		HashMap<String, BookInfo> searchData = new HashMap<String, BookInfo>();

		// Get url string concatenated with the subject from the function parameter.
		String url = getSubjectURL(subject);

		// read web contents from the url
		BufferedReader reader = read(new URL(url));

		// Declare lFile for writing log File
		FileWriter lFile = new FileWriter(logFile, true);
		PrintWriter printLog = new PrintWriter(new BufferedWriter(lFile));

		// Declare an array to store text book information.
		String[] textInfo = new String[7];

		String line = reader.readLine(); // Read the first line of the site.

		// This loop keeps reading HTML tag until reaches null.
		while (line != null) {

			// When the tag starts with
			if (line.startsWith("<a alt=")) {
				// Concatenate each detailed text book URL.
				String textURL = "https://open.umn.edu"
						+ line.substring(line.indexOf("href=\"") + 6, line.indexOf("\"><img alt"));

				// Store text book information in an array.
				textInfo = getTextBookInfo(textURL);

				// Store image URL to fetch the image.
				if (saveImage == true) {
					String imageURL = "https://open.umn.edu"
							+ line.substring(line.indexOf("src=\"") + 5, line.indexOf("\" /></a>"));
					saveImageFile(imageURL); // saves the image from the URL

					// Store the name of the image file stored previously.
					textInfo[6] = getImageURLsuffix(imageURL);
				} else {
					textInfo[6] = "null";
				}
//				[0] -ISBN
//				[1] -Book Title
//				[2] -Description
//				[3] -publisher
//				[4] -language
//				[5] -Copyright Year
//              [6] -imageName
				// Create an instance to store text book information, assigning array values.
				BookInfo textBook = new BookInfo(userName, textInfo[0], textInfo[1], textInfo[2], textInfo[3],
						textInfo[4], textInfo[5], textInfo[6]);

				// Store the data with key (isbn) and values into hashmap.
				searchData.put(textInfo[0], textBook);

				// Save log transaction of online search.
				printLog.print(textBook.toLogString("Online Advanced Search"));
			} // if

			line = reader.readLine(); // Read the next line.
		} // while

		printLog.close(); // Close the log file.

		return searchData; // returns hashMap instance.
	}// advancedSearchResult

	/**
	 * This function returns a HashMap instance which only contains the record that
	 * contains the string, title.
	 * 
	 * @param title
	 * @param subject
	 * @param userName
	 * @param saveImage
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public HashMap<String, BookInfo> advancedSearchWithTitle(String title, String subject, String userName,
			boolean saveImage) throws MalformedURLException, Exception {
		// Declare an instance of hashMap to store the data.
		HashMap<String, BookInfo> searchData = new HashMap<String, BookInfo>();

		// Get url string concatenated with the subject from the function parameter.
		String url = getSubjectURL(subject);

		// read web contents from the url
		BufferedReader reader = read(new URL(url));

		// Declare lFile for writing log File
		FileWriter lFile = new FileWriter(logFile, true);
		PrintWriter printLog = new PrintWriter(new BufferedWriter(lFile));

		// Declare an array to store text book information.
		String[] textInfo = new String[7];

		String line = reader.readLine(); // Read the first line of the site.

		// This loop keeps reading HTML tag until reaches null.
		while (line != null) {

			// When the tag starts with
			if (line.startsWith("<a alt=")) {
				// Concatenate each detailed text book URL.
				String textURL = "https://open.umn.edu"
						+ line.substring(line.indexOf("href=\"") + 6, line.indexOf("\"><img alt"));

				// Store text book information in an array.
				textInfo = getTextBookInfo(textURL);

				// Store image URL to fetch the image.
				if (saveImage == true) {
					String imageURL = "https://open.umn.edu"
							+ line.substring(line.indexOf("src=\"") + 5, line.indexOf("\" /></a>"));
					saveImageFile(imageURL); // saves the image from the URL

					// Store the name of the image file stored previously.
					textInfo[6] = getImageURLsuffix(imageURL);
				} else {
					textInfo[6] = "null";
				}
//						[0] -ISBN
//						[1] -Book Title
//						[2] -Description
//						[3] -publisher
//						[4] -language
//						[5] -Copyright Year
//		              [6] -imageName
				// Create an instance to store text book information, assigning array values.
				if (textInfo[1].contains(title)) {
					BookInfo textBook = new BookInfo(userName, textInfo[0], textInfo[1], textInfo[2], textInfo[3],
							textInfo[4], textInfo[5], textInfo[6]);

					// Store the data with key (isbn) and values into hashmap.
					searchData.put(textInfo[0], textBook);

					// Save log transaction of online search.
					printLog.print(textBook.toLogString("Online Advanced Search"));
				} // if
			} // if

			line = reader.readLine(); // Read the next line.
		} // while

		printLog.close(); // Close the log file.

		return searchData; // returns hashMap instance.

	}

	/**
	 * This functions stores the textbook information on the site and returns the
	 * array which stores the textbook data.
	 * 
	 * @param url
	 * @return TextInfo
	 * @throws IOException
	 */
	public String[] getTextBookInfo(String url) throws IOException {

		// Store each textbook URL.
		URL textUrl = null;

		try {
			// assign the textbook URL.
			textUrl = new URL(url);
		} // try
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // catch
			// Stores textbook information
		String[] TextInfo = new String[7];

//	[0] -ISBN
//	[1] -Book Title
//	[2] -Description
//	[3] -publisher
//	[4] -language
//	[5] -Copyright Year
//  [6] -imageName

		// Read the web content from the URL
		BufferedReader reader = null;
		try {
			reader = read(textUrl);
		} // try
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // catch

		String line = reader.readLine(); // Read the next line.

		// Read the all lines and retrieve textbook data.
		while (line != null) {

			// Retrieve ISBN
			if (line.startsWith("ISBN 13:")) {
				line = reader.readLine();
				TextInfo[0] = line;
			} // if

			// Retrieve title
			if (line.startsWith("<h1>")) {
				TextInfo[1] = line.substring(line.indexOf("<h1>") + 4, line.indexOf("</h1>"));
			} // if

			// Retrieve description
			if (line.endsWith("rel='canonical'>")) {
				line = reader.readLine();
				TextInfo[2] = line.substring(line.indexOf("<meta content='") + 15, line.indexOf("<meta content='") + 50)
						+ "...";

				line = reader.readLine(); // Read the next line
			} // if

			// Retrieve publisher
			if (line.startsWith("Publisher:")) {
				line = reader.readLine();
				TextInfo[3] = line.substring(line.indexOf("\">") + 2, line.indexOf("</a>"));
			} // if

			// Retrieve language
			if (line.startsWith("Language:")) {
				line = reader.readLine();
				TextInfo[4] = line;
			} // if

			// Retrieve copyright year
			if (line.startsWith("Copyright Year:")) {
				line = reader.readLine();
				TextInfo[5] = line;
			} // if

			line = reader.readLine(); // Read the next line

		} // while
			// when the isbnm number is not on the page, assign a random 13 digit number.
		if (TextInfo[0] == null) {
			long randomISBN = (long) Math.floor(Math.random() * 9000000000000L) + 1000000000000L;
			TextInfo[0] = Long.toString(randomISBN);
		} // if
		return TextInfo;

	}// getTextBookInfo

	/**
	 * This function returns suffix of umage URL for the name of the image file.
	 * 
	 * @param imageURL
	 * @return imageURL (substring of the last portion)
	 */
	public String getImageURLsuffix(String imageURL) {
		return imageURL.substring(imageURL.lastIndexOf('/') + 1);
	}// getImageURLsuffix

	/*
	 * This function retrieves imaga data from url and save the data into image
	 * variable which is bufferedImage object instance. This function is referenced
	 * by the code provided in the class.
	 */
	public static void fetchImageFromURL(URL url) throws IOException {
		// read from a URL
		image = ImageIO.read(url);

	}// fetchImageFromURL

	/**
	 * This function saves images file of .jpeg, .jpg and .gif from URL into the
	 * directory user provides on the command line. This function is referenced by
	 * the code provided in the class.
	 * 
	 * @param url
	 * @throws Exception
	 */
	public void saveImageFile(String url) throws Exception {
		URL imageUrl = new URL(url); // An object for an image URL.

		// Take the last portion of URL path as filename.
		String fileName = url.substring(url.lastIndexOf('/') + 1);
		// Add directory path to the file Name to save the file in the directory.
		String filePath = imageFolderPath + fileName;

		// Create an image file as the output.
		File outputImageFile = new File(filePath);

		// call fetchImageFrom URL to retrieve image data from the URL.
		fetchImageFromURL(imageUrl);
		ImageIO.write(image, "png", outputImageFile);

	}// saveImageFile

}// URLSearch
