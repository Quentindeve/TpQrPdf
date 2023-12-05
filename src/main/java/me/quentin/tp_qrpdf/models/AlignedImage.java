package me.quentin.tp_qrpdf.models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * This class represents an image that will go
 * to the output PDF.
 */
public class AlignedImage {

    /**
     * All possible alignments the image can take
     */
    public static enum Alignment {
        LEFT(0), CENTER(1), RIGHT(2);

        public int value;
        private Alignment(int value) {
            this.value = value;
        }
    }

    /**
     * The alignment of the image
     */
    private Alignment imageAlignment;

    /**
     * The image in itself
     */
    private BufferedImage image;

    /**
     * Source file of the image
     *
     */
    private String file;

    public AlignedImage(BufferedImage image, Alignment imagePosition, String file) {
        this.image = image;
        this.imageAlignment = imagePosition;
        this.file = file;
    }

    public Alignment getAlignment() {
        return this.imageAlignment;
    }
    
    public void setAlignment(Alignment a) {
    	this.imageAlignment = a;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    @Override
    public String toString() {
    	return this.file;
    }
    
    public byte[] renderImage() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(this.image, "png", baos);

        return baos.toByteArray();
    }
}
