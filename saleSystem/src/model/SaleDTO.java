package model;

/**
 * This class represents a finished sale. Instances are immutable.
 */

public final class SaleDTO {
	private final double totalAmount;
	private final double discountedAmount;
	private final double totalVAT;
	private final ItemDTO[] itemList;
	
	/**
	 * Creates a new instance, representing the details of the finished sale.
	 * 
	 * @param totalAmount The final amount of the sale including VAT and discounts.
	 * @param discountedAmount The total amount that was discounted.
	 * @param totalVAT The total VAT amount of the sale.
	 * @param itemList An array containing DTOs of all the items in the sale.
	 */
	
	public SaleDTO(double totalAmount, double discountedAmount, double totalVat, ItemDTO[] itemList) {
		this.totalAmount = totalAmount;
		this.discountedAmount = discountedAmount;
		this.totalVAT = totalVat;
		this.itemList = itemList;
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
	 * Returns the total discounted amount stored in the object.
	 * 
	 * @return The total discounted amount.
	 */

	public double getDiscountedAmount() {
		return discountedAmount;
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
	 * Returns the item array stored in the object.
	 * 
	 * @return The item array.
	 */

	public ItemDTO[] getItemList() {
		return itemList;
	}
}
