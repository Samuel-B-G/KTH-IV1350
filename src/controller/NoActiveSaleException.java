package controller;

/**
 * This class represents the generic exception that is thrown to the view when an exception is caught in the controller or any layer below it.
 */

public class NoActiveSaleException extends Exception {
	
	/**
	 * Is called when an operation is attempted on a sale when the sale is not yet initialized.
	 */
	
	public NoActiveSaleException() {
		super("Operation failed as no sale was active.");
	}
}