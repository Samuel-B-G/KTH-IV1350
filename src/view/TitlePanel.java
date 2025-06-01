package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class TitlePanel {
	private JPanel panel;
	
	TitlePanel(BoundsDTO boundsDTO) {
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(boundsDTO.getXSize(),boundsDTO.getYSize());
		panel.setLocation(boundsDTO.getXPos(), boundsDTO.getYPos());
		panel.setBackground(new Color(150,150,150));
		
		JLabel titleLabel = new JLabel();
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setSize(boundsDTO.getXSize(),boundsDTO.getYSize());
		titleLabel.setLocation(boundsDTO.getXPos(), boundsDTO.getYPos());
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		titleLabel.setText("☆Amazing Sale System☆");
		panel.add(titleLabel);
	}
	
	void add(JFrame frame) {
		frame.add(panel);
	}
}
