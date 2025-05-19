package view;

import controller.Controller;

/**
 * This class represents the view.
 */

public class View {
	Controller contr;
	
	/**
	 * Creates a new instance and create the frame which holds all the UI elements,
	 * also creates the class which handles the receipt frame. Adds both of these elements to relevant observer lists.
	 * 
	 * @param contr The {@link Controller} object.
	 */

	public View(Controller contr) {
		this.contr = contr;
		SaleSystemFrame frame = new SaleSystemFrame(contr);
		contr.addSaleStatusObserver(frame);
		
		ReceiptFrame receiptFrame = new ReceiptFrame();
		contr.addNewReceiptObserver(receiptFrame);
	}
}
