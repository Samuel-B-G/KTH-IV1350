package integration;

import java.util.ArrayList;
import java.util.List;

import model.ItemDTO;
import model.ReceiptDTO;

/**
 * This class represents a physical printer.
 */

public class Printer {
	private List<NewReceiptObserver> newReceiptObservers = new ArrayList<NewReceiptObserver>();

	/**
	 * Prints out all the details of a {@link ReceiptDTO} object which is passed to it.
	 * 
	 * @param receipt The receipt to be printed.
	 */

	public void printReceipt(ReceiptDTO receipt) {
		notifyNewReceiptObservers(receipt);
		System.out.println("------------------ Begin receipt -------------------");
		System.out.println("Time of Sale: " + receipt.getTimeOfSale());
		System.out.println();
		for (int i = 0; i < receipt.getItemList().length; i++) {
			ItemDTO item = receipt.getItemList()[i];
			System.out.print(item.getName() + ", ");
			System.out.print(item.getQuantity() + " x " + item.getPriceWithVAT() + ", ");
			System.out.println(item.getTotalAmount() + " SEK");
		}
		System.out.println();
		System.out.println("Total: " + receipt.getTotalAmount() + " SEK");
		System.out.println("VAT: " + receipt.getTotalVAT() + " SEK");
		System.out.println();
		System.out.println("Cash: " + receipt.getPaidAmount() + " SEK");
		System.out.println("Change: " + receipt.getChange() + " SEK");
		System.out.println("------------------ End receipt ---------------------");
	}
	
	private void notifyNewReceiptObservers(ReceiptDTO receipt) {
		for (NewReceiptObserver obs : newReceiptObservers) {
			obs.newReceipt(receipt);
		}
	}
	
	/**
	 * Adds an object implementing {@link NewReceiptObserver} to the list of objects to be notified by 
	 * the observer.
	 * 
	 * @param newReceiptObserver The object implementing {@link NewReceiptObserver}.
	 */

	public void addNewReceiptObserver(NewReceiptObserver newReceiptObserver) {
		newReceiptObservers.add(newReceiptObserver);
	}
}
