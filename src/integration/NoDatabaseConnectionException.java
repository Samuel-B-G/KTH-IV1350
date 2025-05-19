package integration;

/**
 * This class represents the exception which is thrown when any database is unable to be reached.
 */

public class NoDatabaseConnectionException extends Exception {
	
	/**
	 * Is called when a database is not able to be reached.
	 */
	
	public NoDatabaseConnectionException() {
		super("Unable to perform operation as database could not be reached.");
	}
}
