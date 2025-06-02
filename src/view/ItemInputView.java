package view;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import controller.NoActiveSaleException;
import controller.OperationFailedException;
import model.RequestedItemDTO;

class ItemInputView {
	Controller contr;
	SaleSystemFrame saleSystemFrame;
	
	private JPanel panel;
	private TextField itemID, quantity;
	private JButton addItemButton;
	
	ItemInputView(Controller contr, SaleSystemFrame saleSystemFrame, BoundsDTO boundsDTO) {
		this.contr = contr;
		this.saleSystemFrame = saleSystemFrame;
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		int panelYSize = 70;
		int buttonYSize = 30;
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize,ySize);
		panel.setLocation(boundsDTO.getXPos(),boundsDTO.getYPos());
		panel.setBackground(new Color(100,100,230));
		
		BoundsDTO itemIDBounds = new BoundsDTO(0,(ySize-panelYSize-buttonYSize)/2,xSize/2,panelYSize);
		this.itemID = new TextField(itemIDBounds);
		itemID.setText("Item Identifier");
		itemID.add(panel);
		
		BoundsDTO quantityBounds = new BoundsDTO(xSize/2,(ySize-panelYSize-buttonYSize)/2,xSize/2,panelYSize);
		this.quantity = new TextField(quantityBounds);
		quantity.setText("Quantity");
		quantity.add(panel);
		
		this.addItemButton = new JButton();
		addItemButton.setSize(100,buttonYSize);
		addItemButton.setLocation((xSize-addItemButton.getWidth())/2,(ySize+panelYSize-buttonYSize)/2);
		addItemButton.setText("Add Item");
		addItemButton.addActionListener(new ButtonListener());
		panel.add(addItemButton);
	}
	
	void add(JFrame frame) {
		frame.add(panel);
	}

	void remove(JFrame frame) {
		frame.remove(panel);
	}
	
	private void addItem() throws OperationFailedException, NoActiveSaleException {
		String itemIdentifier = itemID.getText();
		String quantityString = quantity.getText();
		RequestedItemDTO requestedItemDTO;
		try {
			int itemQuantity = Integer.parseInt(quantityString);
			requestedItemDTO = new RequestedItemDTO(itemIdentifier,itemQuantity);
		}
		catch (Exception e) {
			requestedItemDTO = new RequestedItemDTO(itemIdentifier);
		}
		
		contr.addItem(requestedItemDTO);
		
		itemID.eraseText();
		quantity.eraseText();
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				addItem();
				fail("Exception was not caught as expected.");
			} catch (OperationFailedException e) {
				saleSystemFrame.writeToLogAndUI(e.getMessage(), (Exception) e.getCause());
			} catch (NoActiveSaleException e) {
				saleSystemFrame.writeToLogAndUI(e.getMessage(), e);
			}
		}
	}
}
