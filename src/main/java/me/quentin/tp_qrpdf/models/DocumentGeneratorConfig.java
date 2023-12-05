package me.quentin.tp_qrpdf.models;

import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.itextpdf.text.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class contains all informations that are used to
 * generate the QR code. These datas are supposed to be entered
 * by the user.
 */
public class DocumentGeneratorConfig {
	/**
	 * The font of the text over the QR code
	 */
	private Font textFont;

	/**
	 * Color fo the text over the QR code
	 */
	private Color fontColor;

	/**
	 * Color of the foreground of the QR code
	 */
	private int qrCodeForeground;

	/**
	 * Color of the background of the QR code
	 */
	private int qrCodeBackground;

	/**
	 * Alignment of the QR code
	 */
	private AlignedImage.Alignment qrCodeAlignment;

	/**
	 * All the extra images added by the user
	 */
	private ArrayList<AlignedImage> additionalImages;

	public DocumentGeneratorConfig(Font textFont, Color fontColor, Color qrCodeForeground, Color qrCodeBackground, AlignedImage.Alignment qrCodeAlignment, ArrayList<AlignedImage> additionalImages) {
		super();
		this.textFont = textFont;
		this.fontColor = fontColor;
		this.qrCodeForeground = this.colorToARGB(qrCodeForeground);
		this.qrCodeBackground = this.colorToARGB(qrCodeBackground);
		this.qrCodeAlignment = qrCodeAlignment;
		this.additionalImages = additionalImages;
	}

	public com.itextpdf.text.Font getTextFont() {
		return this.textFont;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public MatrixToImageConfig getQrCodeConfig() {
		return new MatrixToImageConfig(this.qrCodeForeground, this.qrCodeBackground);
	}

	public AlignedImage.Alignment getQrCodeAlignment() {
		return this.qrCodeAlignment;
	}

	public ArrayList<AlignedImage> getAdditionalImages() {
		return this.additionalImages;
	}

	private int colorToARGB(Color c) {
		return c.getRed() | c.getGreen() << 8 | c.getBlue() << 16 | c.getAlpha() << 24;
	}
}
