
/**
 * This main class is executed with command line. User needs to put coomand line
 * argument, following the syntax. If user type -g command, the program
 * initiates the GUI application. Code from the book _Java in a Nutshell_ by
 * David Flanagan. This code is written by David Flanagan. Copyright (c) 1996
 * O'Reilly & Associates.
 * 
 * @author mikitotakeshima
 * @version 1.2
 * @since 05-06-2020
 * 
 */
public class main {
	// Default input and output files not specified yet
	static String inputFilePath = "";
	// Default directory Path
	static String outputFilePath = "";

	public static void main(String[] args) throws Exception {

		// Command line parsing Go through all user command line of args.
		if (args.length >= 1) {
			int i = 0; // variable for the index of args.
			while (i < args.length) {
				// When args start with -i for input file path.
				if (args[i].equals("-i")) {
					inputFilePath = args[++i];

					// When file path is quoted remove quotation marks.
					if (inputFilePath.startsWith("\"") && inputFilePath.endsWith("\""))
						inputFilePath = inputFilePath.substring(0, inputFilePath.length());
				}

				// When args start with -i for output file path.
				else if (args[i].equals("-o")) {
					outputFilePath = args[++i];

					// if the name does not include .txt extention add .txt.
					if (outputFilePath.startsWith("\"") && outputFilePath.endsWith("\"")) {
						outputFilePath = outputFilePath.substring(0, inputFilePath.length());
					} // if

				} // else if

				// This command processes data from input file and stored processes data on the
				// output file a use specifies without initiating the GUI application.
				else if (args[i].equals("-p")) {

					DB database = new DB(); // Create an instance of database class.
					database.readFile(inputFilePath); // Reads input file and stores data in the instance.
					database.writeFile(outputFilePath); // writes the stored data on the output file.
					System.out.println("Program complete.");

					// Terminates the program.
					System.exit(0);

					// This command starts the GUI application, calling LoginWindow for
					// registration.
				} else if (args[i].equals("-g")) {

					LoginWindow frame = new LoginWindow(inputFilePath);
					frame.setVisible(true);

				}
				i++; // increment i to go through other command lines.

			} // while
		} // if
			// When the number of parameter is inadequate.
		else {
			System.err.println("Invalid Command Line.\n"
					+ "Please restart the program and type -i inputFile path -o outputFile path, -p (if you just want to process the input file in output file)/-g (if you want to initiate GUI Application");
			System.exit(0);
		} // else

	}// main
}// main
