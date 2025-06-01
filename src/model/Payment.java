package model;

import java.time.LocalDate;

import integration.Register;

/**
 * This class represents a payment by a customer.
 */

final class Payment {
	private double paidAmount;
	private double change;
	private String timeOfSale;

	/**
	 * Creates a new instance, representing the details of the customers payment.
	 * 
	 * @param paidAmount The total amount that was paid by the customer.
	 */

	Payment(double paidAmount) {
		this.paidAmount = paidAmount;
		this.timeOfSale = setTimeOfSale();
	}

	/**
	 * Subtracts the total amount of the sale from the customers payment and returns
	 * the result.
	 * 
	 * @param totalAmount The total amount of the finished sale.
	 * 
	 * @return The total change to give to the customer.
	 */

	double calculateChange(double totalAmount) {
		this.change = paidAmount - totalAmount;
		return change;
	}
	
	/**
	 * Adds the paid amount stored in the object to the passed {@link Register} object.
	 * 
	 * @param register The register to add the paid amount to.
	 */

	void addToRegister(Register register) {
		register.addToRegister(paidAmount);
	}

	private String setTimeOfSale() {
		LocalDate date = LocalDate.now();
		return date.toString();
	}

	/**
	 * Returns the customers payment stored in the object.
	 * 
	 * @return The total paid amount.
	 */

	double getPaidAmount() {
		return paidAmount;
	}

	/**
	 * Returns the customers change stored in the object.
	 * 
	 * @return The total change.
	 */

	double getChange() {
		return change;
	}

	/**
	 * Returns the time of the sale stored in the object.
	 * 
	 * @return The time of the sale.
	 */

	String getTimeOfSale() {
		return timeOfSale;
	}
}
