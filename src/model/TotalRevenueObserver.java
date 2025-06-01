package model;

/**
 * This class represents the observer which observes the total revenue.
 */

public interface TotalRevenueObserver {
	
	/**
	 * Is called when the total revenue is updated so that the observers can be notified.
	 * 
	 * @param amount The current total revenue.
	 */
	
	void updateTotalRevenue(double amount);
}
