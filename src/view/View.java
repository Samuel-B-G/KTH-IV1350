package view;

import java.io.IOException;

import controller.Controller;
import util.LogHandler;

/**
 * This class represents the view.
 */

public class View {
	Controller contr;
	SaleSystemFrame saleSystemFrame;
	
	ErrorMessageHandler errorMsgHandler;
	LogHandler logger;
	
	/**
	 * Creates a new instance and create the frame which holds all the UI elements,
	 * also creates the class which handles the receipt frame. Adds both of these elements to relevant observer lists.
	 * 
	 * @param contr The {@link Controller} object.
	 */

	public View(Controller contr) {
		
		try {
			this.logger = new LogHandler();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.errorMsgHandler = new ErrorMessageHandler();
		
		this.contr = contr;
		this.saleSystemFrame = new SaleSystemFrame(contr, this);
		contr.addSaleObserver(saleSystemFrame);
		
		ReceiptFrame receiptFrame = new ReceiptFrame();
		contr.addNewReceiptObserver(receiptFrame);
	}
	
	/**
	 * Handles the writing of exceptions to both the logs and to the User Interface.
	 * 
	 * @param msg The message to be displayed on the User Interface.
	 * 
	 * @param exception The exception that was caught which shall be logged.
	 */
	
	void writeToLogAndUI(String msg, Exception exception) {
		saleSystemFrame.showMsg(msg);
		errorMsgHandler.showErrorMsg(msg);
		logger.logException(exception);
	}
}
