package model;

/**
 * This class represents an item from a finished sale. Instances are immutable.
 */

public final class ItemDTO {
	private final String name;
	private final String itemIdentifier;
	private final double priceWithVAT;
	private final int quantity;

	/**
	 * Creates a new instance containing all the details of an item from a finished
	 * sale.
	 * 
	 * @param name           The name of the item.
	 * @param itemIdentifier The identifier of the item.
	 * @param priceWithVAT   The price including VAT for the item.
	 * @param quantity       The quantity of the item.
	 */

	public ItemDTO(String name, String itemIdentifier, double priceWithVAT, int quantity) {
		this.name = name;
		this.itemIdentifier = itemIdentifier;
		this.priceWithVAT = priceWithVAT;
		this.quantity = quantity;
	}

	/**
	 * Returns the items name.
	 * 
	 * @return The items name.
	 */

	public String getName() {
		return name;
	}

	/**
	 * Returns the item identifier.
	 * 
	 * @return The item identifier.
	 */

	public String getItemIdentifier() {
		return itemIdentifier;
	}

	/**
	 * Returns the price with VAT stored in the object.
	 * 
	 * @return The price with VAT
	 */

	public double getPriceWithVAT() {
		return priceWithVAT;
	}

	/**
	 * Returns the quantity stored in the object.
	 * 
	 * @return The quantity.
	 */

	public int getQuantity() {
		return quantity;
	}
}
