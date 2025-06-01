package integration;

import model.DiscountDTO;

/**
 * This class represents the discount database, as no real database exists, this class uses hardcoded variables for determining discounts.
 */

public class DiscountDatabase {
	private DiscountStrategy discountStrategy;

	/**
	 * Returns a discount from the discount database, in order to simulate a {@link NoDatabaseConnectionException}, this function will throw an exception if a certain customerID is input.
	 * 
	 * @param discountDTO The {@link discountDTO} that is used to find potential discounts.
	 * 
	 * @return The amount to be discounted from the total amount of the sale.
	 * 
	 * @throws NoDatabaseConnectionException is thrown if the database cannot be reached.
	 */

	public double getDiscount(DiscountDTO discountDTO) throws NoDatabaseConnectionException {
		if (discountDTO.getCustomerID().equalsIgnoreCase("error"))
			throw new NoDatabaseConnectionException();
		double discount = discountStrategy.findDiscount(discountDTO);
		return discount;
	}
	
	/**
	 * Sets a new strategy to be used when calling functions from this class.
	 * 
	 * @param discountStrategy The specific strategy to be used when calling a function from this class.
	 */
	
	public void setDiscountStrategy(DiscountStrategy discountStrategy) {
		this.discountStrategy = discountStrategy;
	}
}
