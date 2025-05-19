package integration;

import model.DiscountDTO;

/**
 * This class represents a function to find a suitable discount based on a customer identification string.
 */

public class DiscountCustomerIdentification implements DiscountStrategy  {

	@Override
	public double findDiscount(DiscountDTO discountDTO) {
		String customerID = discountDTO.getCustomerID();
		double discount = 0;
		if (customerID.equalsIgnoreCase("money"))
			discount = 0.1;
		if (customerID.equalsIgnoreCase("discount"))
			discount = 0.05;
		return discount;
	}

}
