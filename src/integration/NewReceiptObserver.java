package integration;

import model.ReceiptDTO;

/**
 * This class represents the observer interface which other object can implement, 
 * it is only used when a new receipt is created.
 */

public interface NewReceiptObserver {
	
	/**
	 * Is called when a new receipt is created in order to update all observers.
	 * 
	 * @param receipt The receipt object to be passed along to the observers.
	 */
	
	void newReceipt(ReceiptDTO receipt);
}
