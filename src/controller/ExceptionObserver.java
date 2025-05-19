package controller;

/**
 * This class represents the observer interface which other object can implement.
 */

public interface ExceptionObserver {
	
	/**
	 * Is called whenever information about a new exception needs to be passed to the User Interface.
	 * 
	 * @param msg The message which will be passed to the observers.
	 */
	
	void exceptionCaught(String msg);
}
