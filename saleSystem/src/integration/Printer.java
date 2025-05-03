package integration;

import model.ItemDTO;
import model.Receipt;

/**
 * This class represents a physical printer.
 */

public class Printer {
	
	/**
	 * Prints out all the details of a {@link Receipt} object which is passed to it.
	 * 
	 * @param receipt The receipt to be printed.
	 */
	
	public void printReceipt(Receipt receipt) {
		System.out.println("------------------ Begin receipt -------------------");
		System.out.println("Time of Sale: "+receipt.getTimeOfSale());
		System.out.println();
		for (int i = 0; i < receipt.getItemList().length; i++) {
			ItemDTO item = receipt.getItemList()[i];
			System.out.print(item.getName()+", ");
			System.out.print(item.getQuantity()+" x "+item.getPriceWithVAT()+", ");
			System.out.println(item.getPriceWithVAT()*item.getQuantity()+" SEK");
		}
		System.out.println();
		System.out.println("Total: "+receipt.getTotalAmount()+" SEK");
		System.out.println("VAT: "+receipt.getTotalVAT()+" SEK");
		System.out.println();
		System.out.println("Cash: "+receipt.getPaidAmount()+" SEK");
		System.out.println("Change: "+receipt.getChange()+" SEK");
		System.out.println("------------------ End receipt ---------------------");
	}
}
