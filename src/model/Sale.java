package model;

import java.util.ArrayList;
import java.util.List;

import integration.AccountingSystem;
import integration.DiscountDatabase;
import integration.InventorySystem;
import integration.ItemInfoDTO;
import integration.Printer;
import integration.Register;

/**
 * This class represents an active sale.
 */

public class Sale {
	private double totalAmount;
	private double discountedAmount;
	private double totalVAT;
	private List<Item> itemList;
	private Payment payment;
	private Receipt receipt;

	/**
	 * Creates a new instance, representing the details of the sale.
	 */

	public Sale() {
		this.totalAmount = 0;
		this.discountedAmount = 0;
		this.totalVAT = 0;
		this.itemList = new ArrayList<Item>();
	}

	/**
	 * Attempts to search and add an item as specified in the requestedItemDTO from
	 * the inventorySystem to an active sale.
	 * 
	 * @param inventorySystem  The <code>InventorySystem</code> containing all
	 *                         items.
	 * @param requestedItemDTO The <code>RequestedItemDTO</code> containing the
	 *                         specifications of the item to add.
	 * 
	 * @return The item that was added by the function.
	 */

	public ItemAndRunningTotalDTO addItem(InventorySystem inventorySystem, RequestedItemDTO requestedItemDTO) {
		String itemIdentifier = requestedItemDTO.getItemIdentifier();
		int quantity = requestedItemDTO.getQuantity();

		Item item = findItem(itemIdentifier);

		if (item == null)
			item = addNewItem(inventorySystem, itemIdentifier);
		if (item == null)
			return null;

		increaseQuantity(item, quantity);
		updateSaleTotalAmounts(item, quantity);

		ItemAndRunningTotalDTO newItemDTO = new ItemAndRunningTotalDTO(item.getItemInfo(), quantity, totalAmount,
				totalVAT);
		return newItemDTO;
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

	private Item addNewItem(InventorySystem inventorySystem, String itemIdentifier) {
		ItemInfoDTO itemInfo = inventorySystem.getItemInfo(itemIdentifier);
		if (itemInfo == null)
			return null;
		Item item = new Item(itemInfo);
		itemList.add(item);
		return item;
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
	 */

	public void checkDiscount(DiscountDatabase discountDatabase, String customerID) {
		SaleDTO saleDTO = createSaleDTO();

		discountDatabase.addDiscount(customerID, saleDTO);
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
		double change = payment.calculateChange(totalAmount);

		SaleDTO saleDTO = createSaleDTO();

		this.receipt = new Receipt(saleDTO, payment.getPaidAmount(), payment.getChange(), payment.getTimeOfSale());

		return change;
	}

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
		double priceAfterVAT = item.calculateIndividualPriceAfterVAT();
		int quantity = item.getQuantity();
		ItemDTO itemDTO = new ItemDTO(name, itemIdentifier, priceAfterVAT, quantity);
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
}
