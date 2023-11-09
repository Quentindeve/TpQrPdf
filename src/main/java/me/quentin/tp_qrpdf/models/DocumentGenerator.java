package me.quentin.tp_qrpdf.models;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class DocumentGenerator {
	private final int IMAGE_SIZE = 512;

	private QRCodeWriter qrWriter;

	public DocumentGenerator(QRCodeWriter writer) throws WriterException {
		this.qrWriter = writer;
	}

	public File generateDocument(String inputText) throws Exception {
		var document = new Document();
		var outputFile = File.createTempFile("tp_qrpdf", "pdfile");
		var qrCodeBytes = this.getQrCodeBytes(inputText);
		PdfWriter.getInstance(document, new FileOutputStream(outputFile));
		document.open();

		var paragraph = new Paragraph();
		paragraph.add(new Phrase(inputText));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		var image = Image.getInstance(qrCodeBytes);
		image.setAlignment(Element.ALIGN_CENTER);
		document.add(image);

		document.close();

		return outputFile;
	}

	/**
	 * Creates a QR code and returns it in a fancy byte array which is a PNG image.
	 * 
	 * @param inputText The text to encode in the QR code.
	 * @return A byte array containing a QR code in a PNG image.
	 * @throws IOException     If we cannot draw it.
	 * @throws WriterException If we cannot draw it.
	 */
	private byte[] getQrCodeBytes(String inputText) throws IOException, WriterException {
		final var qrCodeMatrix = qrWriter.encode(inputText, BarcodeFormat.QR_CODE, IMAGE_SIZE, IMAGE_SIZE);
		final var imageConfig = new MatrixToImageConfig(0xff00dcdf, 0xff000008);
		final var qrCodeImage = MatrixToImageWriter.toBufferedImage(qrCodeMatrix, imageConfig);

		var baos = new ByteArrayOutputStream();
		ImageIO.write(qrCodeImage, "png", baos);
		return baos.toByteArray();
	}
}
