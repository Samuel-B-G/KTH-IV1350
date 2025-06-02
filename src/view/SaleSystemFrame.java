package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import model.ItemAndRunningTotalDTO;
import model.SaleObserver;

class SaleSystemFrame implements SaleObserver {
	Controller contr;
	View view;
	
	private JFrame frame;
	
	private SaleStatusView saleStatus;
	private OutputView output;
	
	private JPanel noSale;
	private ItemInputView itemInput;
	private PaymentInputView paymentInput;
	private DiscountInputView discountInput;
	
	private SaleOptions saleOptions;
	
	SaleSystemFrame(Controller contr, View view) {
		this.contr = contr;
		this.view = view;
		
		this.frame = new JFrame();
		frame.setTitle("Sale System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(500,320);
		frame.setResizable(false);
		frame.setLayout(null);
		
		int xSize = calculateContentPaneWidth();
		int ySize = calculateContentPaneHeight();
		
		BoundsDTO titlePanelBounds = new BoundsDTO(0,0,xSize,50);
		TitlePanel titlePanel = new TitlePanel(titlePanelBounds);
		titlePanel.add(frame);
		
		BoundsDTO saleStatusBounds = new BoundsDTO(0,50,3*xSize/5,20);
		this.saleStatus = new SaleStatusView(saleStatusBounds);
		saleStatus.add(frame);
		contr.addSaleObserver(saleStatus);
		
		BoundsDTO outputBounds = new BoundsDTO(0,70,saleStatusBounds.getXSize(),ySize-120);
		this.output = new OutputView(contr, outputBounds);
		output.add(frame);
		contr.addSaleObserver(output);
		
		BoundsDTO inputBounds = new BoundsDTO(outputBounds.getXSize(),50,xSize-outputBounds.getXSize(),ySize-100);
		this.noSale = new JPanel();
		noSale.setBounds(inputBounds.getXPos(),inputBounds.getYPos(),inputBounds.getXSize(),inputBounds.getYSize());
		noSale.setBackground(new Color(230,60,60));
		frame.add(noSale);
		
		this.itemInput = new ItemInputView(contr, this, inputBounds);
		
		this.paymentInput = new PaymentInputView(contr, this, inputBounds);
		
		this.discountInput = new DiscountInputView(contr, this, inputBounds);
		
		BoundsDTO saleOptionsBounds = new BoundsDTO(0,ySize-50,xSize,50);
		this.saleOptions = new SaleOptions(this, contr, saleOptionsBounds);
		saleOptions.add(frame);
	
		frame.setVisible(true);
	}
	
	private int calculateContentPaneWidth() {
		int xSize = frame.getWidth() - frame.getInsets().left - frame.getInsets().right;
		return xSize;
	}
	
	private int calculateContentPaneHeight() {
		int ySize = frame.getHeight() - frame.getInsets().top - frame.getInsets().bottom;
		return ySize;
	}
	
	void switchToItemInput() {
		saleStatus.newSale();
		output.switchToItemInput();
		frame.remove(noSale);
		paymentInput.remove(frame);
		discountInput.remove(frame);
		itemInput.add(frame);
		saleOptions.switchToItemInput();
		contr.addSaleObserver(output);
		update();
	}
	
	private void switchToPaymentInput() {
		itemInput.remove(frame);
		discountInput.remove(frame);
		paymentInput.add(frame);
		saleOptions.switchToPaymentInput();
		update();
	}
	
	void switchToDiscountInput() {
		itemInput.remove(frame);
		paymentInput.remove(frame);
		discountInput.add(frame);
		saleStatus.discount();
		update();
	}
	
	private void removeInputs() {
		itemInput.remove(frame);
		paymentInput.remove(frame);
		discountInput.remove(frame);
		frame.add(noSale);
		saleOptions.switchToNoSale();
		update();
	}
	
	private void update() {
		frame.revalidate();
		frame.repaint();
	}
	
	@Override
	public void newItem(ItemAndRunningTotalDTO itemDTO) {
		
	}

	@Override
	public void endSale(double amount) {
		switchToPaymentInput();
	}

	@Override
	public void payment(double change) {
		removeInputs();
	}
	
	void writeToLogAndUI(String msg, Exception exception) {
		view.writeToLogAndUI(msg, exception);
	}
	
	void showMsg(String msg) {
		output.displayMessage(msg);
	}
	
}
