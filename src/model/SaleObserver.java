package model;

/**
 * This class represents the observer which observes an active sale.
 */

public interface SaleObserver {
	
	/**
	 * Is called whenever a new item is added to a sale.
	 * 
	 * @param itemDTO The {@link ItemDTO} containing all relevant information about the new item.
	 */
	
	void newItem(ItemAndRunningTotalDTO itemDTO);
	
	/**
	 * Is called whenever a sale is ended.
	 * 
	 * @param amount The total amount for the sale.
	 */
	
	void endSale(double amount);
	
	/**
	 * Is called whenever a payment is made.
	 * 
	 * @param change The total change to be returned to the customer.
	 */
	
	void payment(double change);
}