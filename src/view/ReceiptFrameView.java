package view;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.ItemDTO;
import model.ReceiptDTO;

class ReceiptFrameView {
	JFrame frame;
	int xSize, ySize;
	
	ReceiptFrameView(ReceiptDTO receipt) {
		this.frame = new JFrame();
		frame.setTitle("Receipt");
		frame.setResizable(false);
		frame.setLayout(null);
		
		this.xSize = 250;
		this.ySize = 0;
		
		JLabel timeOfSale = createItemLabel();
		timeOfSale.setText("Time of Sale: " + receipt.getTimeOfSale());
		frame.add(timeOfSale);
		this.ySize += 20;
		
		for (int i = 0; i < receipt.getItemList().length; i++) {
			ItemDTO itemDTO = receipt.getItemList()[i];
			JLabel itemLabel = createItemLabel();
			String itemName = itemDTO.getName() + ",  " + itemDTO.getQuantity() + " x " + itemDTO.getPriceWithVAT() + ",  " + itemDTO.getTotalAmount() + " SEK";
			itemLabel.setText(itemName);
			frame.add(itemLabel);
		}
		
		this.ySize += 20;
		
		JLabel totalAmount = createItemLabel();
		totalAmount.setText("Total: "+receipt.getTotalAmount() + " SEK");
		frame.add(totalAmount);
		
		JLabel vatAmount = createItemLabel();
		vatAmount.setText("VAT: " + receipt.getTotalVAT() + " SEK");
		frame.add(vatAmount);
		
		this.ySize += 20;
		
		JLabel cashAmount = createItemLabel();
		cashAmount.setText("Cash: " + receipt.getPaidAmount() + " SEK");
		frame.add(cashAmount);
		
		JLabel changeAmount = createItemLabel();
		changeAmount.setText("Change: " + receipt.getChange() + " SEK");
		frame.add(changeAmount);
		
		frame.pack();
		
		frame.setSize(xSize, ySize + frame.getInsets().top + frame.getInsets().bottom);
		
		frame.setVisible(true);
	}
	
	private JLabel createItemLabel() {
		JLabel label = new JLabel();
		label.setSize(xSize-10, 20);
		label.setLocation(5, ySize);
		this.ySize += 20;
		return label;
	}
}
