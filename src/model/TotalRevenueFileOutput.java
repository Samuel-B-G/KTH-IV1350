package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class represents the object that prints the total revenue to a text file.
 */

class TotalRevenueFileOutput implements TotalRevenueObserver {
	private static final String LOG_FILE_NAME = "total-revenue.txt";
	private PrintWriter logFile;
	
	/**
	 * Creates a new instance and creates the text file which will be printed to.
	 * 
	 * @throws IOException if the directory cannot be found or the process failed.
	 */
	
	public TotalRevenueFileOutput() throws IOException {
		this.logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME), true);
	}
	
	/**
	 * Prints to the text file created by this object.
	 * 
	 * @param amount The current total revenue to be printed to the file.
	 */
	
	private void printToFile(double amount) {
		logFile.println(amount);
	}

	@Override
	public void updateTotalRevenue(double amount) {
		printToFile(amount);
	}
}