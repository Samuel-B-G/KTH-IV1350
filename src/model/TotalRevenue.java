package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the total revenue of all sales.
 */

public class TotalRevenue {
	private List<TotalRevenueObserver> totalRevenueObservers = new ArrayList<TotalRevenueObserver>();
	
	/**
	 * Creates a new instance, representing the details of the total revenue.
	 */
	
	public TotalRevenue() {
		try {
			TotalRevenueFileOutput totalRevenueFileOutput = new TotalRevenueFileOutput();
			addTotalRevenueObserver(totalRevenueFileOutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds an amount to the total revenue.
	 * 
	 * @param amount The amount to the added to the total revenue.
	 */
	
	public void addToRevenue(double amount) {
		notifyObservers(amount);
	}
	
	private void notifyObservers(double amount) {
		for (TotalRevenueObserver obs : totalRevenueObservers) {
			obs.updateTotalRevenue(amount);
		}
	}
	
	/**
	 * Adds a {@link TotalRevenueObserver} object to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param totalRevenueObserver The object to be added to the list.
	 */
	
	public void addTotalRevenueObserver(TotalRevenueObserver totalRevenueObserver) {
		totalRevenueObservers.add(totalRevenueObserver);
	}
}
