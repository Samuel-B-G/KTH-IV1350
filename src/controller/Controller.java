package controller;

import integration.AccountingSystem;
import integration.DatabaseCreator;
import integration.DiscountDatabase;
import integration.InventorySystem;
import integration.Printer;
import integration.Register;
import model.ItemAndRunningTotalDTO;
import model.RequestedItemDTO;
import model.Sale;

/**
 * This class represents the main controller which handles calls to functions in
 * other layers.
 */

public class Controller {
	AccountingSystem accountingSystem;
	InventorySystem inventorySystem;
	DiscountDatabase discountDatabase;
	Printer printer;
	Register register;

	Sale sale;

	/**
	 * Creates a new instance, representing the details of the controller, gathers
	 * the required systems and databases from the {@link DatabaseCreator}.
	 *
	 * @param creator The {@link DatabaseCreator} object used to gather the
	 *                databases.
	 */

	public Controller(DatabaseCreator creator) {
		printer = new Printer();
		register = new Register();
		accountingSystem = creator.getAccountingSystem();
		inventorySystem = creator.getInventorySystem();
		discountDatabase = creator.getDiscountDatabase();
	}

	/**
	 * Initializes a new sale.
	 */

	public void newSale() {
		this.sale = new Sale();
	}

	/**
	 * Accepts a {@link RequestedItemDTO} object to add an item to an existing sale.
	 * 
	 * @param requestedItemDTO DTO containing the details of the requested item.
	 * 
	 * @return The item which was added.
	 */

	public ItemAndRunningTotalDTO addItem(RequestedItemDTO requestedItemDTO) {
		return sale.addItem(inventorySystem, requestedItemDTO);
	}

	/**
	 * Ends an existing sale and presents the total amount of the sale.
	 * 
	 * @return The total amount of the finished sale.
	 */

	public double endSale() {
		return sale.getTotalAmount();
	}

	/**
	 * Sends a {@link DiscountDatabase} object to an active sale to be scanned for
	 * possible discounts.
	 * 
	 * @param customerID The ID of the customer.
	 */

	public void checkDiscount(String customerID) {
		sale.checkDiscount(discountDatabase, customerID);
	}

	/**
	 * Accepts a payment from a customer and updates the inventory and accounting
	 * systems, then prints receipt and presents change.
	 * 
	 * @param paidAmount The amount that was paid by the customer.
	 * 
	 * @return The total change to be given to the customer.
	 */

	public double pay(double paidAmount) {
		sale.updateInventory(inventorySystem);
		sale.updateAccounting(accountingSystem);
		double change = sale.pay(paidAmount);
		sale.addToRegister(register);
		sale.printReceipt(printer);
		return change;
	}
}
