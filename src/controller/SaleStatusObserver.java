package controller;

/**
 * This class represents the observer which observes the stage a sale is in.
 */

public interface SaleStatusObserver {
	
	/**
	 * Is called when a sale is completely ended and a payment has been made.
	 */
	
	void noSale();
	
	/**
	 * Is called when a new sale has begun and item identifiers can be inputted.
	 */
	
	void newSale();
	
	/**
	 * Is called when the sale has ended and a payment can be made.
	 */
	
	void payment();
	
	/**
	 * Is called when the customer requests a discount.
	 */
	
	void discount();

	
}