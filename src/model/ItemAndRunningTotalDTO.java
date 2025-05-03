package model;

import integration.ItemInfoDTO;

/**
 * This class represents a newly added item as well as the current total price
 * and VAT of the sale. Instances are immutable.
 */

public final class ItemAndRunningTotalDTO {
	private final ItemInfoDTO itemInfo;
	private final int quantity;
	private final double totalPrice;
	private final double totalVAT;

	/**
	 * Creates a new instance containing all the details of a newly added item.
	 * 
	 * @param itemInfo   An {@link ItemInfoDTO} object containing the item details.
	 * @param quantity   The quantity that was added.
	 * @param totalPrice The total price of the sale.
	 * @param totalVAT   The total VAT of the sale.
	 */

	public ItemAndRunningTotalDTO(ItemInfoDTO itemInfo, int quantity, double totalPrice, double totalVAT) {
		this.itemInfo = itemInfo;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.totalVAT = totalVAT;
	}

	/**
	 * Returns the item identifier stored in the itemInfo object.
	 * 
	 * @return The item identifier.
	 */

	public String getItemIdentifier() {
		return itemInfo.getItemIdentifier();
	}

	/**
	 * Returns the quantity of the item.
	 * 
	 * @return The quantity.
	 */

	public int getQuantity() {
		return quantity;
	}

	/**
	 * Returns the name stored in the itemInfo object.
	 * 
	 * @return The name of the item.
	 */

	public String getName() {
		return itemInfo.getName();
	}

	/**
	 * Returns the item description stored in the itemInfo object.
	 * 
	 * @return The item description.
	 */

	public String getItemDescription() {
		return itemInfo.getItemDescription();
	}

	/**
	 * Returns the VAT rate stored in the itemInfo object.
	 * 
	 * @return The VAT rate.
	 */

	public double getVATRate() {
		return itemInfo.getVATRate();
	}

	/**
	 * Returns the price of the item stored in the itemInfo object.
	 * 
	 * @return The price of the item.
	 */

	public double getPrice() {
		return itemInfo.getPrice();
	}

	/**
	 * Returns the total price of the sale.
	 * 
	 * @return The total price of the sale.
	 */

	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Returns the total VAT of the sale.
	 * 
	 * @return The total VAT of the sale.
	 */

	public double getTotalVAT() {
		return totalVAT;
	}
}
