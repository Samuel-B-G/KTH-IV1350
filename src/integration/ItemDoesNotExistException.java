package integration;

/**
 * This class represents an exception which is thrown when an item cannot be found in the inventory system.
 */

public class ItemDoesNotExistException extends Exception {
	String itemIdentifier;
	
	/**
	 * Creates an instance of the object with the passed item identifier.
	 * 
	 * @param itemIdentifier The item identifier to be passed with the exception.
	 */
	
	public ItemDoesNotExistException(String itemIdentifier) {
		super("No item with the item identifier '"+itemIdentifier+"' exists in the inventory system.");
	}
}
