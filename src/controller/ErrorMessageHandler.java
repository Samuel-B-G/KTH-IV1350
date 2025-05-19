package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the error message handler which notifies the relevant components in the User Interface about caught exceptions.
 */

public class ErrorMessageHandler {
	private List<ExceptionObserver> exceptionObservers = new ArrayList<ExceptionObserver>();
	
	/**
	 * Constructs the message that will be shown in the console and sends the passed string to the User Interface.
	 * 
	 * @param msg The message that will be shown in the User Interface and the console.
	 */
	
	void showErrorMsg(String msg) {
		StringBuilder errorMsgBuilder = new StringBuilder();
		errorMsgBuilder.append(createTime());
		errorMsgBuilder.append(", ERROR: ");
		errorMsgBuilder.append(msg);
		notifyObserversException(msg);
		System.out.println(errorMsgBuilder);
	}
	
	private String createTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return now.format(formatter);
	}
	
	/**
	 * Passes on the message to all objects which stored in the {@link ExceptionObserver} list.
	 * 
	 * @param msg The message to be passed on.
	 */
	
	public void notifyObserversException(String msg) {
		for (ExceptionObserver obs : exceptionObservers) {
			obs.exceptionCaught(msg);
		}
	}
	
	/**
	 * Adds an object implementing {@link ExceptionObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param exceptionObserver The object implementing {@link ExceptionObserver}.
	 */
	
	public void addExceptionObserver(ExceptionObserver exceptionObserver) {
		exceptionObservers.add(exceptionObserver);
	}
}
