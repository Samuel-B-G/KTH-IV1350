package controller;

import java.util.ArrayList;
import java.util.List;

import integration.AccountingSystem;
import integration.DatabaseCreator;
import integration.DiscountDatabase;
import integration.InventorySystem;
import integration.ItemDoesNotExistException;
import integration.NewReceiptObserver;
import integration.NoDatabaseConnectionException;
import integration.Printer;
import integration.Register;
import model.ItemAndRunningTotalDTO;
import model.RequestedItemDTO;
import model.Sale;
import model.SaleObserver;
import model.TotalRevenue;
import model.TotalRevenueObserver;

/**
 * This class represents the main controller which handles calls to functions in
 * other layers.
 */

public class Controller {
	private List<SaleObserver> saleObservers = new ArrayList<SaleObserver>();
	
	AccountingSystem accountingSystem;
	InventorySystem inventorySystem;
	DiscountDatabase discountDatabase;
	Printer printer;
	Register register;
	
	TotalRevenue totalRevenue;
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
		this.sale = null;
		this.totalRevenue = new TotalRevenue();
	}
	
	/**
	 * Adds an object implementing {@link TotalRevenueObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param totalRevenueObserver The object implementing {@link TotalRevenueObserver}.
	 */
	
	public void addTotalRevenueObserver(TotalRevenueObserver totalRevenueObserver) {
		totalRevenue.addTotalRevenueObserver(totalRevenueObserver);
	}
	
	/**
	 * Adds an object implementing {@link NewReceiptObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param newReceiptObserver The object implementing {@link NewReceiptObserver}.
	 */
	
	public void addNewReceiptObserver(NewReceiptObserver newReceiptObserver) {
		printer.addNewReceiptObserver(newReceiptObserver);
	}
	
	/**
	 * Adds an object implementing {@link SaleObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param saleObserver The object implementing {@link SaleObserver}.
	 * 
	 * @throws OperationFailedException if sale is not initialised.
	 */
	
	public void addSaleObserver(SaleObserver saleObserver) {
		saleObservers.add(saleObserver);
	}

	/**
	 * Initializes a new sale.
	 */

	public void newSale() {
		this.sale = new Sale();
		sale.addSaleObservers(saleObservers);
	}

	/**
	 * Accepts a {@link RequestedItemDTO} object to add an item to an existing sale.
	 * 
	 * @param requestedItemDTO DTO containing the details of the requested item.
	 * 
	 * @return The item which was added.
	 * 
	 * @throws OperationFailedException if sale is not initialised or if no matching item was found.
	 * @throws NoActiveSaleException 
	 */

	public ItemAndRunningTotalDTO addItem(RequestedItemDTO requestedItemDTO) throws OperationFailedException, NoActiveSaleException {
		if (sale == null)
			throw new NoActiveSaleException();
		
		ItemAndRunningTotalDTO itemAndRunningTotalDTO = null;
		try {
			itemAndRunningTotalDTO = sale.addItem(inventorySystem, requestedItemDTO);
		} catch (ItemDoesNotExistException e) {
			String msg = "Item was not found.";
			throw new OperationFailedException(msg, e);
		}
		return itemAndRunningTotalDTO;
	}

	/**
	 * Ends an existing sale and presents the total amount of the sale.
	 * 
	 * @return The total amount of the finished sale.
	 * 
	 * @throws OperationFailedException if sale is not initialised.
	 * @throws NoActiveSaleException 
	 */

	public double endSale() throws OperationFailedException, NoActiveSaleException {
		if (sale == null)
			throw new NoActiveSaleException();
		
		double totalAmount = sale.endSale();
		return totalAmount;
	}

	/**
	 * Sends a {@link DiscountDatabase} object to an active sale to be scanned for
	 * possible discounts.
	 * 
	 * @param customerID The ID of the customer.
	 * 
	 * @throws OperationFailedException if sale is not initialised or if the discount database can not be found.
	 * @throws NoActiveSaleException 
	 */

	public void checkDiscount(String customerID) throws OperationFailedException, NoActiveSaleException {
		if (sale == null)
			throw new NoActiveSaleException();
		
		try {
			sale.checkDiscount(discountDatabase, customerID);
		} catch (NoDatabaseConnectionException e) {
			String msg = "Could not contact the database.";
			throw new OperationFailedException(msg, e);
		}
	}

	/**
	 * Accepts a payment from a customer and updates the inventory and accounting
	 * systems, then prints receipt and presents change.
	 * 
	 * @param paidAmount The amount that was paid by the customer.
	 * 
	 * @return The total change to be given to the customer.
	 * 
	 * @throws OperationFailedException if sale is not initialised.
	 * @throws NoActiveSaleException 
	 */

	public double pay(double paidAmount) throws OperationFailedException, NoActiveSaleException {
		if (sale == null)
			throw new NoActiveSaleException();
		
		sale.updateInventory(inventorySystem);
		sale.updateAccounting(accountingSystem);
		double change = sale.pay(paidAmount);
		sale.addToRegister(register);
		sale.printReceipt(printer);
		double finalAmount = sale.calculateAmountToPay();
		totalRevenue.addToRevenue(finalAmount);
		this.sale = null;
		return change;
	}
}
