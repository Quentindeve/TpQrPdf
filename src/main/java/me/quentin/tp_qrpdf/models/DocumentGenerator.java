package me.quentin.tp_qrpdf.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

public class DocumentGenerator {
	final PDRectangle PAGE_RECTANGLE = PDRectangle.A6;
	final int FONT_WIDTH = 24;
	final int IMAGE_SIZE = 256;
	final PDRectangle IMAGE_RECTANGLE = new PDRectangle(192, 192);

	private QRCodeWriter qrWriter;

	public DocumentGenerator(QRCodeWriter writer) throws WriterException {
		this.qrWriter = writer;
	}

	public PDDocument generateDocument(String inputText) throws WriterException, IOException {

		var portableDocument = new PDDocument();
		var pdPage = new PDPage(PAGE_RECTANGLE);
		portableDocument.addPage(pdPage);
		var pageContents = new PDPageContentStream(portableDocument, pdPage);

		// Writes source inputText to pd
		final var textMiddle = (inputText.length() * FONT_WIDTH) / 2 + ((inputText.length() * FONT_WIDTH) / 4);
		pageContents.beginText();
		pageContents.newLineAtOffset(textMiddle, PAGE_RECTANGLE.getHeight() - (PAGE_RECTANGLE.getHeight() / 4));
		pageContents.setFont(this.helvetica(portableDocument), 24);
		pageContents.showText(inputText);
		pageContents.endText();

		// Draws rendered QR code to pd
		final var imageBytes = this.getQrCodeBytes(inputText);
		var image = PDImageXObject.createFromByteArray(portableDocument, imageBytes, inputText);
		var imageMiddle = (IMAGE_RECTANGLE.getWidth() / 2);
		pageContents.drawImage(image, PAGE_RECTANGLE.getHeight() + imageMiddle, 0);
		pageContents.close();

		return portableDocument;
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

	private PDFont helvetica(PDDocument document) throws IOException {
		return PDType0Font.load(document, getClass().getClassLoader().getResourceAsStream("helvetica.ttf"));
	}
}
