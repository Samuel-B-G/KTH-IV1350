package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.ItemAndRunningTotalDTO;
import model.SaleObserver;

class OutputView implements SaleObserver {
	private JPanel panel;
	
	private int xSize, ySize;
	
	private JPanel addItemPanel;
	private JLabel itemID, name, description, price, vat, total;
	
	private JLabel generalMessage;
	
	OutputView(Controller contr, BoundsDTO boundsDTO) {
		this.xSize = boundsDTO.getXSize();
		this.ySize = boundsDTO.getYSize();
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize, ySize);
		panel.setLocation(boundsDTO.getXPos(), boundsDTO.getYPos());
		panel.setBackground(new Color(230,230,230));
		
		createAddItemPanel();
		createPaymentPanel();
		
		BoundsDTO totalRevenueBounds = new BoundsDTO(0,ySize-20,xSize,20);
		TotalRevenueView totalRevenueView = new TotalRevenueView(totalRevenueBounds);
		contr.addTotalRevenueObserver(totalRevenueView);
		totalRevenueView.add(panel);
	}
	
	private void createAddItemPanel() {
		this.addItemPanel = new JPanel();
		addItemPanel.setLayout(null);
		addItemPanel.setSize(xSize,ySize);
		addItemPanel.setOpaque(false);
		
		this.itemID = createLabel(0);
		this.name = createLabel(1);
		this.description = createLabel(2);
		this.price = createLabel(3);
		this.vat = createLabel(4);
		this.total = createLabel(5);
		total.setLocation(5,ySize-total.getHeight()-20);
	}
	
	private JLabel createLabel(int number) {
		JLabel label = new JLabel();
		label.setSize(xSize-10, 20);
		label.setLocation(5, 20*number);
		label.setForeground(Color.BLACK);
		addItemPanel.add(label);
		return label;
	}
	
	private void emptyLabels() {
		itemID.setText("");
		name.setText("");
		description.setText("");
		price.setText("");
		vat.setText("");
		total.setText("");
	}
	
	private void createPaymentPanel() {
		this.generalMessage = new JLabel();
		generalMessage.setSize(xSize, ySize-20);
		generalMessage.setLocation(5, 0);
		generalMessage.setForeground(Color.BLACK);
	}
	
	public void displayMessage(String msg) {
		panel.remove(addItemPanel);
		panel.add(generalMessage);
		generalMessage.setText(msg);
		update();
	}

	void switchToItemInput() {
		emptyLabels();
		panel.remove(generalMessage);
		panel.add(addItemPanel);
		update();
	}
	
	private void update() {
		panel.revalidate();
		panel.repaint();
	}

	public void add(JFrame frame) {
		frame.add(panel);
	}

	@Override
	public void newItem(ItemAndRunningTotalDTO item) {
		panel.remove(generalMessage);
		panel.add(addItemPanel);
		String itemIDString = "Add " + item.getQuantity() + " item(s) with item ID: " + item.getItemIdentifier();
		itemID.setText(itemIDString);
		String nameString = "Item name: " + item.getName();
		name.setText(nameString);
		String priceString = "Item cost: " + item.getPrice() + " SEK";
		price.setText(priceString);
		String descriptionString = "Item description: " + item.getItemDescription();
		description.setText(descriptionString);
		String totalString = "Total cost (incl VAT): " + item.getTotalPrice() + " SEK";
		total.setText(totalString);
		String vatString = "VAT: " + item.getVATRate() * 100 + "%";
		vat.setText(vatString);
		
		System.out.println(itemIDString);
		System.out.println(nameString);
		System.out.println(priceString);
		System.out.println(descriptionString);
		System.out.println(totalString);
		System.out.println(vatString);
		
		update();
	}

	@Override
	public void endSale(double amount) {
		panel.remove(addItemPanel);
		panel.add(generalMessage);
		String msg = "Total cost (incl VAT): " + amount + " SEK";
		generalMessage.setText(msg);
		System.out.println(msg);
	}
	
	@Override
	public void payment(double change) {
		String msg = "Change to give the customer: " + change + " SEK";
		generalMessage.setText(msg);
		System.out.println(msg);
	}
}
