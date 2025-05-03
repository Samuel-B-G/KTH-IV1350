package model;

import integration.ItemInfoDTO;

/**
 * This class represents an item.
 */

public class Item {
	private ItemInfoDTO itemInfo;
	private int quantity;
	
	/**
	 * Creates a new instance, representing the details of the new item.
	 * 
	 * @param itemInfo The DTO containing all the details of the item.
	 */
	
	public Item(ItemInfoDTO itemInfo) {
		this.itemInfo = itemInfo;
		this.quantity = 0;
	}
	
	/**
	 * Creates a new instance, representing the details of the new item.
	 * 
	 * @param itemInfo The DTO containing all the details of the item.
	 * @param quantity The initial quantity of the item.
	 */
	
	public Item(ItemInfoDTO itemInfo, int quantity) {
		this.itemInfo = itemInfo;
		this.quantity = quantity;
	}
	
	/**
	 * Increases the quantity in the object by the specified amount.
	 * 
	 * @param quantity The amount the quantity is increased by.
	 */
	
	public void increaseQuantity(int quantityToAdd) {
		this.quantity = quantity+quantityToAdd;
	}
	
	/**
	 * Decreases the quantity in the object by the specified amount.
	 * 
	 * @param quantity The amount the quantity is decreased by.
	 */
	
	public void decreaseQuantity(int quantityToSubtract) {
		this.quantity = quantity-quantityToSubtract;
	}
	
	/**
	 * Returns the {@link ItemInfoDTO} stored in the object.
	 * 
	 * @return The itemInfo stored in the item.
	 */
	
	public ItemInfoDTO getItemInfo() {
		return itemInfo;
	}
	
	/**
	 * Returns the quantity stored in the object.
	 * 
	 * @return The quantity of the item.
	 */
	
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Returns the name stored in the object.
	 * 
	 * @return The name of the item.
	 */
	
	public String getName() {
		return itemInfo.getName();
	}
	
	/**
	 * Returns the price stored in the object.
	 * 
	 * @return The price of the item.
	 */
	
	public double getPrice() {
		return itemInfo.getPrice();
	}
	
	/**
	 * Returns the VAT rate stored in the object.
	 * 
	 * @return The VAT rate of the item.
	 */
	
	public double getVATRate() {
		return itemInfo.getVATRate();
	}
	
	/**
	 * Returns the item identifier stored in the object.
	 * 
	 * @return The item identifier.
	 */
	
	public String getItemIdentifier() {
		return itemInfo.getItemIdentifier();
	}
	
	/**
	 * Returns the description stored in the object.
	 * 
	 * @return The description of the item.
	 */
	
	public String getDescription() {
		return itemInfo.getItemDescription();
	}
	
	/**
	 * Calculates the VAT amount of a singular item and returns this value.
	 * 
	 * @return The VAT amount of a singular item.
	 */
	
	public double calculateIndividualVATPrice() {
		double price = itemInfo.getPrice();
		double vatRate = itemInfo.getVATRate();
		double vatPrice = price*vatRate;
		return vatPrice;
	}
	
	/**
	 * Calculates the total price with VAT of a singular item and returns this value.
	 * 
	 * @return The total price with VAT of a singular item.
	 */
	
	public double calculateIndividualPriceAfterVAT() {
		double price = itemInfo.getPrice();
		double vatPrice = calculateIndividualVATPrice();
		double individualPriceAfterVat = vatPrice+price;
		return individualPriceAfterVat;
	}
}
