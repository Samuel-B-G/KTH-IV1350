package integration;

/**
 * This class represents the details of an item. Instances are immutable.
 */

public final class ItemInfoDTO {
	private final String itemIdentifier;
	private final String name;
	private final String itemDescription;
	private final double vatRate;
	private final double price;

	/**
	 * Creates a new instance, representing the pricing and description of a new
	 * item.
	 * 
	 * @param itemIdentifier  The identifier used to search for an item.
	 * @param name            The name of the item.
	 * @param itemDescription A description of the item.
	 * @param vatRate         The VAT rate of the item.
	 * @param price           The price of the item before adding VAT.
	 */

	public ItemInfoDTO(String itemIdentifier, String name, String itemDescription, double vatRate, double price) {
		this.itemIdentifier = itemIdentifier;
		this.name = name;
		this.itemDescription = itemDescription;
		this.vatRate = vatRate;
		this.price = price;
	}

	/**
	 * Returns the item identifier stored in the object.
	 * 
	 * @return The item identifier.
	 */

	public String getItemIdentifier() {
		return itemIdentifier;
	}

	/**
	 * Returns the name stored in the object.
	 * 
	 * @return The name of the item.
	 */

	public String getName() {
		return name;
	}

	/**
	 * Returns the item description stored in the object.
	 * 
	 * @return The item description.
	 */

	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * Returns the VAT rate stored in the object.
	 * 
	 * @return The VAT rate.
	 */

	public double getVATRate() {
		return vatRate;
	}

	/**
	 * Returns the price of the item stored in the object.
	 * 
	 * @return The price of the item.
	 */

	public double getPrice() {
		return price;
	}
}
