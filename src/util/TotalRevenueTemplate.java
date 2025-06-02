package util;

import model.TotalRevenueObserver;

/**
 * This class represents a template pattern used by objects observing the {@link TotalRevenue} class, it holds and updates the total revenue amount.
 */

public abstract class TotalRevenueTemplate implements TotalRevenueObserver {
	private double totalRevenue;
	
	/**
	 * Updates the total revenue stored in the object and passes this value to the function of the class using this template.
	 * 
	 * @param amount The amount that should be added to the total revenue.
	 */
	
	public void updateTotalRevenue(double amount) {
		calculateTotalIncome(amount);
		showTotalIncome();
	}
	
	private void calculateTotalIncome(double amount) {
		totalRevenue += amount;
		totalRevenue = (double) Math.round(totalRevenue * 100) / 100;
	}
	
	private void showTotalIncome() {
		try {
			displayTotalIncome(totalRevenue);
		} catch (Exception e) {
			handleErrors(e);
		}
		
	}
	
	protected abstract void displayTotalIncome(double totalRevenue);
	
	protected abstract void handleErrors(Exception e);
}
