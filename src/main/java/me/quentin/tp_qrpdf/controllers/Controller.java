package me.quentin.tp_qrpdf.controllers;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JOptionPane;

import com.google.zxing.qrcode.QRCodeWriter;

import me.quentin.tp_qrpdf.models.DocumentGenerator;
import me.quentin.tp_qrpdf.views.MainView;

/**
 * The main controller of the application.
 */
public class Controller {
	// View used by the controller
	private MainView view;
	private DocumentGenerator documentGenerator;

	public Controller() {
		// creates the view
		this.view = new MainView();
		try {
			// instanciates the document generator
			this.documentGenerator = new DocumentGenerator();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: cannot instantiate Document Generator: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		this.view.setVisible(true);
		// when the user clicks on the button of the view to generate the PDF
		this.view.setButtonEventListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var text = view.getText();
				try {
					var generationConfig = view.getGenerationConfig();
					File output = documentGenerator.generateDocument(text, generationConfig);
					Desktop.getDesktop().open(output);
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "Error: cannot generate PDF: " + exception.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
				}
			}
		});
	}
}
