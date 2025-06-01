package model;

import java.util.ArrayList;
import java.util.List;

import integration.AccountingSystem;
import integration.DiscountCustomerIdentification;
import integration.DiscountDatabase;
import integration.DiscountItemList;
import integration.DiscountTotalAmount;
import integration.InventorySystem;
import integration.ItemDoesNotExistException;
import integration.ItemInfoDTO;
import integration.NoDatabaseConnectionException;
import integration.Printer;
import integration.Register;

/**
 * This class represents an active sale.
 */

public class Sale {
	private List<SaleObserver> saleObservers = new ArrayList<SaleObserver>();
	
	private double totalAmount;
	private double discountedAmount;
	private double totalVAT;
	private List<Item> itemList;
	private Payment payment;
	private ReceiptDTO receipt;

	/**
	 * Creates a new instance, representing the details of the sale.
	 */

	public Sale() {
		this.totalAmount = 0;
		this.discountedAmount = 0;
		this.totalVAT = 0;
		this.itemList = new ArrayList<Item>();
		//notifyObserversNewSale();
	}

	/**
	 * Attempts to search and add an item as specified in the requestedItemDTO from
	 * the inventorySystem to an active sale.
	 * 
	 * @param inventorySystem  The {@link InventorySystem} containing all
	 *                         items.
	 * @param requestedItemDTO The {@link RequestedItemDTO} containing the
	 *                         specifications of the item to add.
	 * 
	 * @return The item that was added by the function.
	 * 
	 * @throws ItemDoesNotExistException if the searched for item does not exist in the inventory system.
	 */

	public ItemAndRunningTotalDTO addItem(InventorySystem inventorySystem, RequestedItemDTO requestedItemDTO) throws ItemDoesNotExistException {
		String itemIdentifier = requestedItemDTO.getItemIdentifier();
		int quantity = requestedItemDTO.getQuantity();

		Item item = findItem(itemIdentifier);

		if (item == null)
			item = addNewItem(inventorySystem, itemIdentifier);

		increaseQuantity(item, quantity);
		updateSaleTotalAmounts(item, quantity);

		ItemAndRunningTotalDTO newItemDTO = new ItemAndRunningTotalDTO(item.getItemInfo(), quantity, totalAmount, totalVAT);
		notifyObserversNewItem(newItemDTO);
		return newItemDTO;
	}
	
	private void notifyObserversNewItem(ItemAndRunningTotalDTO newItemDTO) {
		for (SaleObserver obs : saleObservers) {
			obs.newItem(newItemDTO);
		}
	}
	
	private void notifyObserversEndSale(double amount) {
		for (SaleObserver obs : saleObservers) {
			obs.endSale(amount);
		}
	}
	
	private void notifyObserversPayment(double change) {
		for (SaleObserver obs : saleObservers) {
			obs.payment(change);
		}
	}
	
	/**
	 * Adds a {@link SaleObserver} object to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param saleObserverList The object to be added to the list.
	 */

	public void addSaleObservers(List<SaleObserver> saleObserverList) {
		saleObservers.addAll(saleObserverList);
	}

	private void updateSaleTotalAmounts(Item item, int quantity) {
		double itemVATAmount = item.calculateIndividualVATPrice();
		double itemPrice = item.getPrice();

		double totalVATAmountToAdd = itemVATAmount * quantity;
		double totalAmountToAdd = itemPrice * quantity + totalVATAmountToAdd;
		increaseTotalVAT(totalVATAmountToAdd);
		increaseTotalAmount(totalAmountToAdd);
	}

	private Item findItem(String itemIdentifier) {
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			if (itemIdentifier.equals(item.getItemIdentifier())) {
				return item;
			}
		}
		return null;
	}

	private Item addNewItem(InventorySystem inventorySystem, String itemIdentifier) throws ItemDoesNotExistException {
		ItemInfoDTO itemInfo = inventorySystem.getItemInfo(itemIdentifier);
		if (itemInfo == null)
			return null;
		Item item = new Item(itemInfo);
		itemList.add(item);
		return item;
	}
	
	/**
	 * Returns the total amount of the sale and notifies all relevant objects of in the view of the ended sale.
	 * 
	 * @return The total amount of the sale.
	 */
	
	public double endSale() {
		double amountToPay = roundToTwoDecimals(calculateAmountToPay());
		notifyObserversEndSale(amountToPay);
		return amountToPay;
	}

	private void increaseQuantity(Item item, int quantity) {
		item.increaseQuantity(quantity);
	}

	private void increaseTotalVAT(double amountToAdd) {
		double unroundedAmount = totalVAT + amountToAdd;
		totalVAT = roundToTwoDecimals(unroundedAmount);
	}

	private void increaseTotalAmount(double amountToAdd) {
		double unroundedAmount = totalAmount + amountToAdd;
		totalAmount = roundToTwoDecimals(unroundedAmount);
	}

	private double roundToTwoDecimals(double toRound) {
		double rounded = (double) Math.round(toRound * 100) / 100;
		return rounded;
	}

	/**
	 * Checks the passed {@link DiscountDatabase} for potential discounts and adds
	 * the total discount to the sale.
	 * 
	 * @param discountDatabase The database to search for discounts.
	 * @param customerID       The customer ID which is passed on to
	 *                         discountDatabase.
	 * 
	 * @return The change to give to the customer.
	 * 
	 * @throws NoDatabaseConnectionException if the database cannot be called.
	 */

	public void checkDiscount(DiscountDatabase discountDatabase, String customerID) throws NoDatabaseConnectionException {
		DiscountDTO discountDTO = createDiscountDTO(customerID);
		
		double newDiscountedAmount = 0;
		
		discountDatabase.setDiscountStrategy(new DiscountCustomerIdentification());
		newDiscountedAmount += totalAmount * discountDatabase.getDiscount(discountDTO);
		discountDatabase.setDiscountStrategy(new DiscountTotalAmount());
		newDiscountedAmount += totalAmount * discountDatabase.getDiscount(discountDTO);
		discountDatabase.setDiscountStrategy(new DiscountItemList());
		newDiscountedAmount += discountDatabase.getDiscount(discountDTO);
		
		this.discountedAmount = newDiscountedAmount;
		
		notifyObserversEndSale(roundToTwoDecimals(calculateAmountToPay()));
	}

	/**
	 * Accepts a payment and creates a payment and receipt object, receives the
	 * total change from payment and returns this value.
	 * 
	 * @param paidAmount The total amount that was paid by the customer.
	 * 
	 * @return The change to give to the customer.
	 */

	public double pay(double paidAmount) {
		this.payment = new Payment(paidAmount);
		double amountToPay = totalAmount - discountedAmount;
		double change = roundToTwoDecimals(payment.calculateChange(amountToPay));

		this.receipt = createReceipt();
		notifyObserversPayment(change);
		return change;
	}
	
	private ReceiptDTO createReceipt() {
		SaleDTO saleDTO = createSaleDTO();
		double finalAmount = roundToTwoDecimals(calculateAmountToPay());
		double paidAmount = roundToTwoDecimals(payment.getPaidAmount());
		double changeAmount = roundToTwoDecimals(payment.getChange());
		ReceiptDTO receipt = new ReceiptDTO(saleDTO, finalAmount, paidAmount, changeAmount, payment.getTimeOfSale());
		return receipt;
	}
	
	/**
	 * Adds the payment in the {@link Sale} object to the passed {@link Printer}
	 * 
	 * @param register The register the payment should be added to.
	 */

	public void addToRegister(Register register) {
		payment.addToRegister(register);
	}

	/**
	 * Prints the receipt which is stored in the sale to the specified printer
	 * 
	 * @param printer The <Code>Printer</Code> to print the receipt to.
	 */

	public void printReceipt(Printer printer) {
		printer.printReceipt(receipt);
	}

	/**
	 * Updates the accounting system with the sale information.
	 * 
	 * @param accountingSystem The <Code>AccountingSystem</Code> to update.
	 */

	public void updateAccounting(AccountingSystem accountingSystem) {
		SaleDTO saleDTO = createSaleDTO();
		accountingSystem.updateAccounting(saleDTO);
	}

	/**
	 * Updates the inventory system with an array of ItemDTOs.
	 * 
	 * @param inventorySystem The <Code>InventorySystem</Code> to update.
	 */

	public void updateInventory(InventorySystem inventorySystem) {
		ItemDTO[] itemDTOArray = createItemDTOArray();
		inventorySystem.updateInventory(itemDTOArray);
	}
	
	private DiscountDTO createDiscountDTO(String customerID) {
		ItemDTO[] itemDTOArray = createItemDTOArray();
		DiscountDTO discountDTO = new DiscountDTO(customerID, totalAmount, itemDTOArray);
		return discountDTO;
	}

	private SaleDTO createSaleDTO() {
		ItemDTO[] itemDTOArray = createItemDTOArray();
		return new SaleDTO(totalAmount, discountedAmount, totalVAT, itemDTOArray);
	}

	private ItemDTO[] createItemDTOArray() {
		ItemDTO[] itemDTOArray = new ItemDTO[itemList.size()];
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			ItemDTO itemDTO = createItemDTO(item);
			itemDTOArray[i] = itemDTO;
		}
		return itemDTOArray;
	}

	private ItemDTO createItemDTO(Item item) {
		String name = item.getName();
		String itemIdentifier = item.getItemIdentifier();
		double priceAfterVAT = roundToTwoDecimals(item.calculateIndividualPriceAfterVAT());
		int quantity = item.getQuantity();
		double totalAmount = roundToTwoDecimals(priceAfterVAT * quantity);
		ItemDTO itemDTO = new ItemDTO(name, itemIdentifier, totalAmount, priceAfterVAT, quantity);
		return itemDTO;
	}

	/**
	 * Returns the total amount stored in the object.
	 * 
	 * @return The total amount.
	 */

	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Returns the total discounted amount stored in the object.
	 * 
	 * @return The total discounted amount.
	 */

	public double getDiscountedAmount() {
		return discountedAmount;
	}

	/**
	 * Returns the total VAT amount stored in the object.
	 * 
	 * @return The total VAT amount.
	 */

	public double getTotalVAT() {
		return totalVAT;
	}

	/**
	 * Returns the item list stored in the object.
	 * 
	 * @return The item list.
	 */

	public List<Item> getItemList() {
		return itemList;
	}
	
	/**
	 * Calculated the current final price.
	 * 
	 * @return the final price.
	 */
	
	public double calculateAmountToPay() {
		double amountToPay = totalAmount - discountedAmount;
		return amountToPay;
	}
}
