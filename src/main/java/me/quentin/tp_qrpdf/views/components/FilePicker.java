package me.quentin.tp_qrpdf.views.components;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.*;

public class FilePicker extends JButton {
	
	private File file;
	
	public FilePicker(BiConsumer<FilePicker, String> onFilePicked) {
		this.setText("Choose an image...");
		
		var instance = this;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var dialog = new FileDialog((Frame)null, "Choose an image", FileDialog.LOAD);
				JFileChooser chooser = new JFileChooser();
				dialog.setVisible(true);
				file = dialog.getFiles()[0];
				onFilePicked.accept(instance, file.getAbsolutePath());
			}
		});
	}
	
	public BufferedImage readFile(BiConsumer<Long, Long> progressCallback) throws IOException {
		var baos = new ByteArrayOutputStream();
		var inputStream = new FileInputStream(file);
		
		var tempBuffer = new byte[4096];
		
		long accumulator = 0;
		long totalSize = file.length();
		while (inputStream.read(tempBuffer) != -1) {
			baos.write(tempBuffer);
			accumulator += 4096;
			progressCallback.accept(accumulator, totalSize);
		}
		
		return ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
	}

}
