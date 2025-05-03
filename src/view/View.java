package view;

import controller.Controller;
import model.ItemAndRunningTotalDTO;
import model.RequestedItemDTO;

/**
 * This class represents the view.
 */

public class View {
	Controller contr;

	/**
	 * Creates a new instance and runs a test adding several items to a sale and
	 * then printing a receipt.
	 * 
	 * @param contr The {@link Controller} object.
	 */

	public View(Controller contr) {
		this.contr = contr;

		testRun();
	}

	private void testRun() {
		newSale();

		String itemIdentifier;
		int quantity;

		itemIdentifier = "abc123";
		quantity = 1;
		addItem(itemIdentifier, quantity);

		itemIdentifier = "def456";
		quantity = 2;
		addItem(itemIdentifier, quantity);

		itemIdentifier = "def456";
		quantity = 1;
		addItem(itemIdentifier, quantity);

		itemIdentifier = "ghi789";
		quantity = 4;
		addItem(itemIdentifier, quantity);

		endSale();

		checkDiscount("123");

		payment(500);
	}

	private void newSale() {
		contr.newSale();
	}

	private void addItem(String itemIdentifier, int quantity) {
		RequestedItemDTO requestedItemDTO = new RequestedItemDTO(itemIdentifier, quantity);
		ItemAndRunningTotalDTO item = contr.addItem(requestedItemDTO);

		// System.out.println(priceWithVat);

		System.out.println("Add " + quantity + " item(s) with item ID " + item.getItemIdentifier());
		System.out.println("Item ID: " + item.getItemIdentifier());
		System.out.println("Item name: " + item.getName());
		System.out.println("Item cost: " + item.getPrice() + " SEK");
		System.out.println("VAT: " + item.getVATRate() * 100 + "%");
		System.out.println("Item description: " + item.getItemDescription());

		System.out.println("Total cost (incl VAT): " + item.getTotalPrice() + " SEK");
		System.out.println("Total VAT: " + item.getTotalVAT() + " SEK");
		System.out.println();
	}

	private void endSale() {
		double finalAmount = contr.endSale();
		System.out.println("End sale:");
		System.out.println("Total cost (incl VAT): " + finalAmount + " SEK");
	}

	private void checkDiscount(String customerID) {
		contr.checkDiscount(customerID);
	}

	private void payment(double paidAmount) {
		// Payment payment = new Payment(paidAmount);
		double change = contr.pay(paidAmount);

		System.out.println("Change to give the customer: " + change + " SEK");
	}
}
