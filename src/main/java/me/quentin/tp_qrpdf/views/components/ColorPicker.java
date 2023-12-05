package me.quentin.tp_qrpdf.views.components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;

public class ColorPicker extends JButton {
	private Color latestColor;
	
	public ColorPicker(Color defaultColor) {
		this.latestColor = defaultColor;
		this.setText("Choose Color...");
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				latestColor = JColorChooser.showDialog(null, "Color Picker", latestColor);
			}
		});
	}
	
	public Color getColor() {
		return this.latestColor;
	}
}
