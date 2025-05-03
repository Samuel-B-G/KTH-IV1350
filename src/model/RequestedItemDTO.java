package model;

/**
 * This class represents the details of a requested item. Instances are
 * immutable.
 */

public final class RequestedItemDTO {
	private final String itemIdentifier;
	private final int quantity;

	/**
	 * Creates a new instance, representing the specified item identifier and
	 * quantity.
	 * 
	 * @param itemIdentifier The item identifier represented by the newly created
	 *                       instance.
	 * @param quantity       The quantity represented by the newly created instance.
	 */

	public RequestedItemDTO(String itemIdentifier, int quantity) {
		this.itemIdentifier = itemIdentifier;
		this.quantity = quantity;
	}

	/**
	 * Creates a new instance, representing the specified item identifier and
	 * quantity, quantity defaults to 1.
	 * 
	 * @param itemIdentifier The item identifier represented by the newly created
	 *                       instance.
	 */

	public RequestedItemDTO(String itemIdentifier) {
		this.itemIdentifier = itemIdentifier;
		this.quantity = 1;
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
	 * Returns the quantity stored in the object.
	 * 
	 * @return The quantity.
	 */

	public int getQuantity() {
		return quantity;
	}
}
