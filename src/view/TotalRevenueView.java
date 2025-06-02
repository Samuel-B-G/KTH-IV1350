package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import util.TotalRevenueTemplate;

/**
 * This class represents the view which shows the total revenue.
 */

class TotalRevenueView extends TotalRevenueTemplate {
	private JPanel panel;
	private JLabel totalRevenue;
	
	/**
	 * Creates a new instance, representing the view which shows the total revenue.
	 */
	
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
	
	/**
	 * Adds the panel from this class to the passed {@link JPanel} object.
	 * 
	 * @param frame The {@link JPanel} object that the view should be added to.
	 */
	
	void add(JPanel frame) {
		frame.add(panel);
	}

	@Override
	protected void displayTotalIncome(double amount) {
		String msg = "Total Revenue: " + amount + " SEK";
		System.out.println(msg);
		totalRevenue.setText(msg);
	}

	@Override
	protected void handleErrors(Exception e) {
		e.printStackTrace();
	}
}
