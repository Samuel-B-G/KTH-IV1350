package integration;

import model.DiscountDTO;

/**
 * This class represents a function to find a suitable discount based on the total sum of a sale.
 */

public class DiscountTotalAmount implements DiscountStrategy {

	@Override
	public double findDiscount(DiscountDTO discountDTO) {
		double amount = discountDTO.getTotalAmount();
		double discount = 0;
		if (amount >= 1000)
			discount = 0.15;
		else if (amount >= 500)
			discount = 0.1;
		else if (amount >= 200)
			discount = 0.05;
		return discount;
	}

}
