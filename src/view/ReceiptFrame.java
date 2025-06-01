package view;

import integration.NewReceiptObserver;
import model.ReceiptDTO;

class ReceiptFrame implements NewReceiptObserver {

	@Override
	public void newReceipt(ReceiptDTO receipt) {
		new ReceiptFrameView(receipt);
	}
}
