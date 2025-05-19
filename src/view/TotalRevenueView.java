package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.TotalRevenueObserver;

class TotalRevenueView implements TotalRevenueObserver {
	private JPanel panel;
	private JLabel totalRevenue;
	
	TotalRevenueView(BoundsDTO boundsDTO) {
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize, ySize);
		panel.setLocation(boundsDTO.getXPos(), boundsDTO.getYPos());
		panel.setBackground(new Color(200,200,200));
		
		this.totalRevenue = new JLabel();
		totalRevenue.setSize(xSize, ySize);
		totalRevenue.setLocation(5, 0);
		totalRevenue.setForeground(Color.BLACK);
		totalRevenue.setText("Total Revenue: 0.0 SEK");
		panel.add(totalRevenue);
	}
	
	void add(JPanel frame) {
		frame.add(panel);
	}

	@Override
	public void updateTotalRevenue(double amount) {
		totalRevenue.setText("Total Revenue: " + amount + " SEK");
	}
}
