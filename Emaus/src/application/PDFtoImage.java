package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class PDFtoImage {
    public ArrayList<Image> converter(String file) throws InvalidPasswordException, IOException {
    	ArrayList<Image> listOfImages = new ArrayList<Image>();
    	
    	PDDocument document = PDDocument.load(new File(file));
    	PDFRenderer pdfRenderer = new PDFRenderer(document);
    	for (int page = 0; page < document.getNumberOfPages(); page++)
    	{ 
    	    listOfImages.add( SwingFXUtils.toFXImage(pdfRenderer.renderImageWithDPI(page, 55, ImageType.GRAY), null) );
    	    System.out.println(page);
    	}
    	document.close();
    	
    	return listOfImages;
    }
}
