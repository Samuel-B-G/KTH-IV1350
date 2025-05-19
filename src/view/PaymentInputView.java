package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import controller.OperationFailedException;

class PaymentInputView {
	Controller contr;
	
	private JPanel panel;
	private TextField amount;
	private JButton payButton;
	
	PaymentInputView(Controller contr, BoundsDTO boundsDTO) {
		this.contr = contr;
		
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		int panelYSize = 70;
		int buttonYSize = 30;
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize,ySize);
		panel.setLocation(boundsDTO.getXPos(),boundsDTO.getYPos());
		panel.setBackground(new Color(230,220,0));
		
		BoundsDTO amountBounds = new BoundsDTO(0,(ySize-panelYSize-buttonYSize)/2,xSize,panelYSize);
		this.amount = new TextField(amountBounds);
		amount.setText("Amount Paid");
		amount.add(panel);
		
		this.payButton = new JButton();
		payButton.setSize(140,buttonYSize);
		payButton.setLocation((xSize-payButton.getWidth())/2,(ySize+panelYSize-buttonYSize)/2);
		payButton.setText("Register Payment");
		payButton.addActionListener(new ButtonListener());
		panel.add(payButton);
	}
	
	void add(JFrame frame) {
		frame.add(panel);
	}

	void remove(JFrame frame) {
		frame.remove(panel);
	}
	
	private void payment() {
		String paymentString = amount.getText();
		double payment = 0;
		
		try {
			payment = Integer.parseInt(paymentString);
		}
		catch (NumberFormatException e) {
			Exception exception = new IncorrectInputException("One or more non-numeric characters were inputted into a field expecting a numeric value.");
			String msg = "Non-numeric characters found in input.";
			contr.writeToLogAndUI(msg, exception);
			return;
		}
		
		try {
			contr.pay(payment);
		} catch (OperationFailedException e) {
			return;
		}
		
		amount.eraseText();
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			payment();
		}
	}
}
