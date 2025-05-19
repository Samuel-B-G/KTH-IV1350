package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import controller.OperationFailedException;

class SaleOptions {
	Controller contr;
	
	private JPanel panel;
	private JButton newSaleButton, endSaleButton, checkDiscountButton;
	
	SaleOptions(Controller contr, BoundsDTO boundsDTO) {
		this.contr = contr;
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize,ySize);
		panel.setLocation(boundsDTO.getXPos(), boundsDTO.getYPos());
		panel.setBackground(new Color(150,150,150));
		
		int buttonXSize = 130;
		int buttonYSize = 30;
		
		this.newSaleButton = new JButton();
		newSaleButton.setSize(buttonXSize,buttonYSize);
		newSaleButton.setLocation(10,(ySize-buttonYSize)/2);
		newSaleButton.setText("New Sale");
		newSaleButton.addActionListener(new NewSaleButtonListener());
		panel.add(newSaleButton);
		
		this.endSaleButton = new JButton();
		endSaleButton.setSize(buttonXSize,buttonYSize);
		endSaleButton.setLocation(10+(10+buttonXSize),(ySize-buttonYSize)/2);
		endSaleButton.setText("End Sale");
		endSaleButton.addActionListener(new EndSaleButtonListener());
		
		this.checkDiscountButton = new JButton();
		checkDiscountButton.setSize(buttonXSize,buttonYSize);
		checkDiscountButton.setLocation(10+(10+buttonXSize)*2,(ySize-buttonYSize)/2);
		checkDiscountButton.setText("Check Discount");
		checkDiscountButton.addActionListener(new CheckDiscountButtonListener());
	}
	
	private class NewSaleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			contr.newSale();
		}
	}
	
	private class EndSaleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				contr.endSale();
			} catch (OperationFailedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private class CheckDiscountButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				contr.discountStage();
			} catch (OperationFailedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	void add(JFrame frame) {
		frame.add(panel);
	}

	public void switchToItemInput() {
		endSaleButton.setText("End Sale");
		panel.add(endSaleButton);
		panel.remove(checkDiscountButton);
	}

	public void switchToPaymentInput() {
		endSaleButton.setText("Payment");
		panel.add(endSaleButton);
		panel.add(checkDiscountButton);
	}

	public void switchToNoSale() {
		panel.remove(endSaleButton);
		panel.remove(checkDiscountButton);
	}
}
