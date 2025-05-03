package integration;

/**
 * This class is responsible for creating and storing all the required databases.
 */

public class DatabaseCreator {
	AccountingSystem accountingSystem;
	InventorySystem inventorySystem;
	DiscountDatabase discountDatabase;
	
	/**
	 * Creates and stores the required databases in the object.
	 */
	
	public DatabaseCreator() {
		this.accountingSystem = new AccountingSystem();
		this.inventorySystem = new InventorySystem();
		this.discountDatabase = new DiscountDatabase();
	}
	
	/**
	 * Temporary function to fill the inventory with predefined items.
	 */
	
	public void fillInventorySystem() {
		inventorySystem.fillDatabase();
	}
	
	/**
	 * Returns the {@link AccountingSystem} stored in the object.
	 * 
	 * @return The accounting system.
	 */
	
	public AccountingSystem getAccountingSystem() {
		return accountingSystem;
	}
	
	/**
	 * Returns the {@link InventorySystem} stored in the object.
	 * 
	 * @return The inventory system.
	 */
	
	public InventorySystem getInventorySystem() {
		return inventorySystem;
	}
	
	/**
	 * Returns the {@link DiscountDatabase} stored in the object.
	 * 
	 * @return The discount database.
	 */
	
	public DiscountDatabase getDiscountDatabase() {
		return discountDatabase;
	}
}
