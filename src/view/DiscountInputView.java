package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import controller.OperationFailedException;

class DiscountInputView {
	Controller contr;
	
	private JPanel panel;
	private TextField customerIDPanel;
	private JButton checkDiscount;
	
	DiscountInputView(Controller contr, BoundsDTO boundsDTO) {
		this.contr = contr;
		
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		int panelYSize = 70;
		int buttonYSize = 30;
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize,ySize);
		panel.setLocation(boundsDTO.getXPos(),boundsDTO.getYPos());
		panel.setBackground(new Color(60,230,60));
		
		BoundsDTO amountBounds = new BoundsDTO(0,(ySize-panelYSize-buttonYSize)/2,xSize,panelYSize);
		this.customerIDPanel = new TextField(amountBounds);
		customerIDPanel.setText("Customer Identification");
		customerIDPanel.add(panel);
		
		this.checkDiscount = new JButton();
		checkDiscount.setSize(120,buttonYSize);
		checkDiscount.setLocation((xSize-checkDiscount.getWidth())/2,(ySize+panelYSize-buttonYSize)/2);
		checkDiscount.setText("Add Discount");
		checkDiscount.addActionListener(new ButtonListener());
		panel.add(checkDiscount);
	}
	
	private void checkDiscount() {
		String customerID = customerIDPanel.getText();
		
		try {
			contr.checkDiscount(customerID);
		} catch (OperationFailedException e) {
			return;
		}
		
		customerIDPanel.eraseText();
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			checkDiscount();
		}
	}
	
	void add(JFrame frame) {
		frame.add(panel);
	}

	void remove(JFrame frame) {
		frame.remove(panel);
	}
}
