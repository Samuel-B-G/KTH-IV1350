package integration;

import model.DiscountDTO;

/**
 * This class represents a function to find a suitable discount based on a list of items, the only function, findDiscount() always returns 0 as this method was not implemented fully.
 */

public class DiscountItemList implements DiscountStrategy  {
	
	@Override
	public double findDiscount(DiscountDTO discountDTO) {
		return 0;
	}

}
