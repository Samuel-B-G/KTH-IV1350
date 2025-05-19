package model;

/**
 * This class represents a receipt ready to be printed. Instances are immutable.
 */

public final class ReceiptDTO {
	private final String timeOfSale;
	private final ItemDTO[] itemList;
	private final double totalAmount;
	private final double totalVAT;
	private final double paidAmount;
	private final double change;

	/**
	 * Creates a new instance, representing the details of a finished sale.
	 * 
	 * @param saleDTO    The DTO containing the details of the sale and the items
	 *                   linked to it.
	 * @param totalAmount The total amount after any potential discounts.
	 * @param timeOfSale The time at which the sale was completed.
	 * @param paidAmount The total amount that was paid by the customer.
	 * @param change     The total amount that was returned to the customer.
	 */

	ReceiptDTO(SaleDTO saleDTO, double totalAmount, double paidAmount, double change, String timeOfSale) {
		this.timeOfSale = timeOfSale;
		this.change = change;
		this.paidAmount = paidAmount;
		this.itemList = saleDTO.getItemList();
		this.totalAmount = totalAmount;
		this.totalVAT = saleDTO.getTotalVAT();
	}

	/**
	 * Returns the time of the sale stored in the object.
	 * 
	 * @return The time of the sale.
	 */

	public String getTimeOfSale() {
		return timeOfSale;
	}

	/**
	 * Returns the item array stored in the object.
	 * 
	 * @return The item array.
	 */

	public ItemDTO[] getItemList() {
		return itemList;
	}

	/**
	 * Returns the total amount stored in the object.
	 * 
	 * @return The total amount.
	 */

	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Returns the total VAT amount stored in the object.
	 * 
	 * @return The total VAT amount.
	 */

	public double getTotalVAT() {
		return totalVAT;
	}

	/**
	 * Returns the customers payment stored in the object.
	 * 
	 * @return The total paid amount.
	 */

	public double getPaidAmount() {
		return paidAmount;
	}

	/**
	 * Returns the customers change stored in the object.
	 * 
	 * @return The total change.
	 */

	public double getChange() {
		return change;
	}
}
