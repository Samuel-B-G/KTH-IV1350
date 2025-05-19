package controller;

/**
 * This class represents the generic exception that is thrown to the view when an exception is caught in the controller or any layer below it.
 */

public class OperationFailedException extends Exception {
	
	/**
	 * Creates an instance of the object with the passed message.
	 * 
	 * @param msg The message to be passed with the exception.
	 */
	
	public OperationFailedException(String msg) {
		super(msg);
	}
}
