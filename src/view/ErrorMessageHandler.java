package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class represents the error message handler which notifies the relevant components in the User Interface about caught exceptions.
 */

public class ErrorMessageHandler {
	
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
		System.out.println(errorMsgBuilder);
	}
	
	private String createTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		return now.format(formatter);
	}
}
