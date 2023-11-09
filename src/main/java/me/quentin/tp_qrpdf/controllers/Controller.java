package me.quentin.tp_qrpdf.controllers;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.google.zxing.WriterException;
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
		this.view = new MainView();

		try {
			this.documentGenerator = new DocumentGenerator(new QRCodeWriter());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error: cannot instantiate Document Generator: " + e.getMessage(),
					"Error", 0);
		}
		this.view.setVisible(true);
		this.view.setButtonEventListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var text = view.getText();
				try {
					var document = documentGenerator.generateDocument(text);
					var tempFile = File.createTempFile("temp-pdf", "pdf");
					document.save(tempFile);
					Desktop.getDesktop().open(tempFile);
				} catch (WriterException | IOException | IllegalArgumentException exception) {
					JOptionPane.showMessageDialog(null, "Error: cannot generate PDF: " + exception.getMessage(),
							"Error", 0);
				}
			}
		});
	}
}
