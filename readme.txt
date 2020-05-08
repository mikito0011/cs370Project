Project Title
Phase3: Database Management System for the website, Open Textbook Library

Introduction
This program allows user to search textbooks and store the data online and to access the data with offline queries.
Users can also perform data manipulation of inserting, modifying, deleting records. This program requires users to register for the account to utilize functionalities of this
database system fully. A guest user only can search textbooks online but cannot store records. For a use account and admin, they can search online, store records, and manipulate the records.
In addition, they can report the records data via email. Only admin users can access all users' records and log files.

Requirements
JDK 13.02 (other version should work fine.)
Terminal/Command line application
jar files - activation.jar and javax.mail.jar for email feature.

Installment
Download java files. Then, create a project file and src file to put all java files in the source file in the project file.
For the email report feature, a user need to download activation.jar file (https://www.oracle.com/technetwork/java/javase/jaf-136260.html)
and javax.mail.jar file (https://javaee.github.io/javamail/#Download_JavaMail_Release).

How to run the code
This program runs from the command line and accepts following commands.
-i input file path -o output file path -p (just to process input file and write on the output file)
-g (to initiate GUI application)
First, change the directory where the java files are saved, and you need to include the file paths of the jar files for javac and java:
javac -cp .:/filePath/jaf-1.0.2/activation.jar:/filePath/javax.mail.jar main.java URLSearch.java DB.java BookInfo.java LogInSystem.java Email.java GUI.java LoginWindow.java
Then, type:
java -cp .:/filePath/jaf-1.0.2/activation.jar:/filePath/javax.mail.jar main -i input filePath -o outputFilePath -p / -g

If you include the commands of -i, -o, and -p, it will execute the program without initiating the GUI application and produce the result on the output file.
If you want to initiate the GUI, output files will be unnecessary since the program creates a folder for each user to store output files if you register the account.
When you include an input file path with GUI command, the GUI program processes the file and stores records.

EX.
javac -cp .:/Users/mikitotakeshima/Downloads/jaf-1.0.2/activation.jar:/Users/mikitotakeshima/Downloads/javax.mail.jar
main.java URLSearch.java DB.java BookInfo.java LogInSystem.java Email.java GUI.java LoginWindow.java
java -cp .:/Users/mikitotakeshima/Downloads/jaf-1.0.2/activation.jar:/Users/mikitotakeshima/Downloads/javax.mail.jar main -i Desktop/javaProject/src/inputFile.txt -o Desktop/javaProject/src/output.txt
 -p (For only input file processing option)
or
java -cp .:/Users/mikitotakeshima/Downloads/jaf-1.0.2/activation.jar:/Users/mikitotakeshima/Downloads/javax.mail.jar main -i Desktop/javaProject/src/inputFile.txt (optional) -g (for GUI option)

When the command line format is wrong, an error message will pop up and you need to restart the program.
After the user successfully runs the program, the program will print "the program completed." The output file, a log file,
and saved files will be placed on the user's directory if you register for the account. Guest users can only use the function of online search but cannot store data and produce any output files.


