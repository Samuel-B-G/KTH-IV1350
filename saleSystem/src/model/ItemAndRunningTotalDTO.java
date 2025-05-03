package model;

import integration.ItemInfoDTO;

public final class ItemAndRunningTotalDTO {
	private final ItemInfoDTO itemInfo;
	private final int quantity;
	private final double totalPrice;
	private final double totalVAT;
	
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

	public double getTotalPrice() {
		return totalPrice;
	}

	public double getTotalVAT() {
		return totalVAT;
	}
}
