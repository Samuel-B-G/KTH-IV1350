package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ItemAndRunningTotalDTO;
import model.SaleObserver;

class SaleStatusView implements SaleObserver {
	private JPanel panel;
	private JLabel saleStatusLabel;

	SaleStatusView(BoundsDTO boundsDTO) {
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize, ySize);
		panel.setLocation(boundsDTO.getXPos(), boundsDTO.getYPos());
		
		this.saleStatusLabel = new JLabel();
		saleStatusLabel.setHorizontalAlignment(JLabel.CENTER);
		saleStatusLabel.setSize(xSize, ySize);
		saleStatusLabel.setLocation(0, 0);
		saleStatusLabel.setForeground(Color.BLACK);
		panel.add(saleStatusLabel);
		
		payment(0);
	}
	
	void add(JFrame frame) {
		frame.add(panel);
	}
	/*
	@Override
	public void noSale() {
		panel.setBackground(new Color(230,60,60));
		saleStatusLabel.setText("No Active Sale");
	}

	@Override
	public void newSale() {
		panel.setBackground(new Color(100,100,230));
		saleStatusLabel.setText("Ongoing Sale");
	}

	@Override
	public void payment() {
		panel.setBackground(new Color(230,220,0));
		saleStatusLabel.setText("Payment");
	}
*/
	public void discount() {
		panel.setBackground(new Color(60,230,60));
		saleStatusLabel.setText("Discount");
	}
	
	@Override
	public void newItem(ItemAndRunningTotalDTO itemDTO) {
		panel.setBackground(new Color(100,100,230));
		saleStatusLabel.setText("Ongoing Sale");
	}

	@Override
	public void endSale(double amount) {
		panel.setBackground(new Color(230,220,0));
		saleStatusLabel.setText("Payment");
	}

	@Override
	public void payment(double change) {
		panel.setBackground(new Color(230,60,60));
		saleStatusLabel.setText("No Active Sale");
	}
	
	void newSale() {
		panel.setBackground(new Color(100,100,230));
		saleStatusLabel.setText("Ongoing Sale");
	}
}
