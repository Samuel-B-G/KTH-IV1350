package integration;

/**
 * This class represents a physical register.
 */

public class Register {
	private double amountStored;
	
	/**
	 * Creates a new instance, representing the details of the register.
	 */
	
	public Register() {
		this.amountStored = 0;
	}
	
	/**
	 * Adds a specified amount of money to the register.
	 * 
	 * @param paidAmount The amount by which to increase the stored amount by.
	 */
	
	public void addToRegister(double paidAmount) {
		this.amountStored += paidAmount;
	}
}
