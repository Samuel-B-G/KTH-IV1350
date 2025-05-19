package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class represents the object that prints exceptions to a file.
 */

public class LogHandler {
	private static final String LOG_FILE_NAME = "sale-log.txt";
	private PrintWriter logFile;
	
	/**
	 * Creates a new instance and creates the text file which will be printed to.
	 * 
	 * @throws IOException if the directory cannot be found or the process failed.
	 */
	
	public LogHandler() throws IOException {
		logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
	}
	
	/**
	 * Constructs the message which will be printed to the text file and prints the message.
	 * 
	 * @param exception The exception to be printed to the file.
	 */
	
	public void logException(Exception exception) {
		StringBuilder logMsgBuilder = new StringBuilder();
		logMsgBuilder.append(createTime());
		logMsgBuilder.append(", Exception was thrown: ");
		logMsgBuilder.append(exception.getMessage());
		logFile.println(logMsgBuilder);
		exception.printStackTrace(logFile);
	}
	
	private String createTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.
		ofLocalizedDateTime(FormatStyle.MEDIUM);
		return now.format(formatter);
	}
}
