package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class TextField {
	private JPanel panel;
	private JLabel label;
	private JTextField textField;
	
	TextField(BoundsDTO boundsDTO) {
		int xSize = boundsDTO.getXSize();
		int ySize = boundsDTO.getYSize();
		
		this.panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(xSize,ySize);
		panel.setLocation(boundsDTO.getXPos(),boundsDTO.getYPos());
		panel.setOpaque(false);
		
		this.label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setSize(xSize,20);
		label.setLocation(0,ySize/2-25);
		label.setForeground(Color.BLACK);
		panel.add(label);
		
		this.textField = new JTextField();
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.setSize(70,20);
		textField.setLocation((xSize-70)/2, ySize/2);
		textField.setBackground(Color.WHITE);
		panel.add(textField);
	}
	
	void add(JPanel frame) {
		frame.add(panel);
	}
	
	void setText(String text) {
		label.setText(text);
	}
	
	void eraseText() {
		textField.setText("");
	}
	
	String getText() {
		return textField.getText();
	}
}
