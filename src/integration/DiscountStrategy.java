package integration;

import model.DiscountDTO;

/**
 * The interface used by the discount classes, allows the {@link DiscountDatabase} to call different classes using the Strategy method.
 */

public interface DiscountStrategy {
	
	/**
	 * Finds a suitable discount based on several factors and returns this discount.
	 * 
	 * @param discountDTO The {@link DiscountDTO} used to find suitable discounts.
	 * 
	 * @return The total amount or percentage that should be discounted for any particular discount criteria.
	 */
	
	double findDiscount(DiscountDTO discountDTO);
}
