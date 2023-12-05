package me.quentin.tp_qrpdf.models;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * The document generator.
 * It does all rendering stuff.
 */
public class DocumentGenerator {
	/**
	 * Width and height of the QR code.
	 */
	public static final int IMAGE_SIZE = 256;

	/**
	 * The QR code writer from zxing
	 */
	private QRCodeWriter qrWriter;

	public DocumentGenerator() throws WriterException {
		this.qrWriter = new QRCodeWriter();
	}

	/**
	 * Generates the PDF.
	 * @param inputText The data to encode in the QR code
	 * @param config All user inputted datas
	 * @return The file object the PDF got outputted to
	 * @throws Exception If anything goes wrong idk
	 */
	public File generateDocument(String inputText, DocumentGeneratorConfig config) throws Exception {
		var document = new Document();
		var outputFile = File.createTempFile("qrpdf", "pdfile");

		var qrCodeMatrix = this.qrWriter.encode(inputText, BarcodeFormat.QR_CODE, IMAGE_SIZE, IMAGE_SIZE);
		var qrCode = new AlignedImage(MatrixToImageWriter.toBufferedImage(qrCodeMatrix, config.getQrCodeConfig()), config.getQrCodeAlignment(), inputText);

		PdfWriter.getInstance(document, new FileOutputStream(outputFile));
		document.open();

		var paragraph = new Paragraph(inputText, config.getTextFont());
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		var image = Image.getInstance(qrCode.renderImage());
		image.setAlignment(Element.ALIGN_CENTER);
		document.add(image);

		config.getAdditionalImages().stream().forEach(additionalImage -> {
			try {
				var instance = Image.getInstance(additionalImage.renderImage());
				instance.setAlignment(additionalImage.getAlignment().value);
				document.add(instance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		document.close();

		return outputFile;
	}
}
