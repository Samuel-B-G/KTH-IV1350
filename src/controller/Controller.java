package controller;

import java.io.IOException;
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
import util.LogHandler;

/**
 * This class represents the main controller which handles calls to functions in
 * other layers.
 */

public class Controller {
	private List<SaleStatusObserver> saleStatusObservers = new ArrayList<SaleStatusObserver>();
	
	ErrorMessageHandler errorMsgHandler;
	LogHandler logger;
	
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
		try {
			this.logger = new LogHandler();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.errorMsgHandler = new ErrorMessageHandler();
		
		printer = new Printer();
		register = new Register();
		accountingSystem = creator.getAccountingSystem();
		inventorySystem = creator.getInventorySystem();
		discountDatabase = creator.getDiscountDatabase();
		this.sale = null;
		this.totalRevenue = new TotalRevenue();
	}
	/*
	public void notifyObserversException(String msg) {
		errorMsgHandler.notifyObserversException(msg);
	}
	*/
	private void notifyObserversNoSale() {
		for (SaleStatusObserver obs : saleStatusObservers) {
			obs.noSale();
		}
	}
	
	private void notifyObserversNewSale() {
		for (SaleStatusObserver obs : saleStatusObservers) {
			obs.newSale();
		}
	}
	
	private void notifyObserversPayment() {
		for (SaleStatusObserver obs : saleStatusObservers) {
			obs.payment();
		}
	}
	
	private void notifyObserversDiscount() {
		for (SaleStatusObserver obs : saleStatusObservers) {
			obs.discount();
		}
	}
	
	/**
	 * Adds an object implementing {@link ExceptionObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param exceptionObserver The object implementing {@link ExceptionObserver}.
	 */
	
	public void addExceptionObserver(ExceptionObserver exceptionObserver) {
		errorMsgHandler.addExceptionObserver(exceptionObserver);
	}
	
	/**
	 * Adds an object implementing {@link SaleStatusObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param saleStatusObserver The object implementing {@link SaleStatusObserver}.
	 */
	
	public void addSaleStatusObserver(SaleStatusObserver saleStatusObserver) {
		saleStatusObservers.add(saleStatusObserver);
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
	
	public void addSaleObserver(SaleObserver saleObserver) throws OperationFailedException {
		if (sale == null)
			throw new OperationFailedException("No active sale.");
		
		sale.addSaleObserver(saleObserver);
	}

	/**
	 * Initializes a new sale.
	 */

	public void newSale() {
		this.sale = new Sale();
		notifyObserversNewSale();
	}

	/**
	 * Accepts a {@link RequestedItemDTO} object to add an item to an existing sale.
	 * 
	 * @param requestedItemDTO DTO containing the details of the requested item.
	 * 
	 * @return The item which was added.
	 * 
	 * @throws OperationFailedException if sale is not initialised or if no matching item was found.
	 */

	public ItemAndRunningTotalDTO addItem(RequestedItemDTO requestedItemDTO) throws OperationFailedException {
		if (sale == null)
			throw new OperationFailedException("No active sale.");
		
		ItemAndRunningTotalDTO itemAndRunningTotalDTO = null;
		try {
			itemAndRunningTotalDTO = sale.addItem(inventorySystem, requestedItemDTO);
		} catch (ItemDoesNotExistException e) {
			String msg = "Item was not found.";
			writeToLogAndUI(msg, e);
			throw new OperationFailedException(msg);
		}
		return itemAndRunningTotalDTO;
	}

	/**
	 * Ends an existing sale and presents the total amount of the sale.
	 * 
	 * @return The total amount of the finished sale.
	 * 
	 * @throws OperationFailedException if sale is not initialised.
	 */

	public double endSale() throws OperationFailedException {
		if (sale == null)
			throw new OperationFailedException("No active sale.");
		
		double totalAmount = sale.endSale();
		notifyObserversPayment();
		return totalAmount;
	}
	
	/**
	 * Notifies the observers that the discount process is in progress.
	 * 
	 * @throws OperationFailedException if sale is not initialised.
	 */
	
	public void discountStage() throws OperationFailedException {
		if (sale == null)
			throw new OperationFailedException("No active sale.");
		
		notifyObserversDiscount();
	}

	/**
	 * Sends a {@link DiscountDatabase} object to an active sale to be scanned for
	 * possible discounts.
	 * 
	 * @param customerID The ID of the customer.
	 * 
	 * @throws OperationFailedException if sale is not initialised or if the discount database can not be found.
	 */

	public void checkDiscount(String customerID) throws OperationFailedException {
		if (sale == null)
			throw new OperationFailedException("No active sale.");
		
		try {
			sale.checkDiscount(discountDatabase, customerID);
		} catch (NoDatabaseConnectionException e) {
			String msg = "Could not contact the database.";
			writeToLogAndUI(msg, e);
			throw new OperationFailedException(msg);
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
	 */

	public double pay(double paidAmount) throws OperationFailedException {
		if (sale == null)
			throw new OperationFailedException("No active sale.");
		
		sale.updateInventory(inventorySystem);
		sale.updateAccounting(accountingSystem);
		double change = sale.pay(paidAmount);
		sale.addToRegister(register);
		sale.printReceipt(printer);
		double finalAmount = sale.calculateAmountToPay();
		totalRevenue.addToRevenue(finalAmount);
		this.sale = null;
		notifyObserversNoSale();
		return change;
	}
	
	/**
	 * Handles the writing of exceptions to both the logs and to the User Interface.
	 * 
	 * @param msg The message to be displayed on the User Interface.
	 * 
	 * @param exception The exception that was caught which shall be logged.
	 */
	
	public void writeToLogAndUI(String msg, Exception exception) {
		errorMsgHandler.showErrorMsg(msg);
		logger.logException(exception);
	}
}
