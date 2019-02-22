package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import javafx.collections.ObservableList;

import java.io.File;

public class CreatePDF {
	public File setup(String filename, ObservableList<Doacoes> doacoes)throws IOException, DocumentException {
		File file = new File(filename);
    	// step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        for(int i=0; i<doacoes.size(); i++) {    
        	document.add(createFirstTable(doacoes.get(i)));
        	LineSeparator ls = new LineSeparator();
        	document.add(new Chunk(ls));
        }
        document.newPage();
        // step 5
        document.close();
		
		return file;
	}
	
	 public static PdfPTable createFirstTable(Doacoes doacoes) throws BadElementException, MalformedURLException, IOException {
    	// a table with three columns
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table = newParte(table, doacoes);
        
        return table;
    }
	
	 public static PdfPTable newParte(PdfPTable table, Doacoes doacao) throws BadElementException, MalformedURLException, IOException {
		table = newLine1(table,"Data da Ligaçao: " + doacao.getContato());
		table = newLine1(table,"Data da Coleta: " + doacao.getColeta());
		table = newLine1(table,"Nome: " + doacao.getNome());
		table = newLine1(table,"Telefone: " + doacao.getTelefone());
		table = newLine1(table,"Celular: " + doacao.getTelefone());
		table = newLine1(table,"Rua/Av: " + doacao.getEndereco());
		table = newLine1(table,"Objetos/Valor: " + doacao.getConteudo());
		table = newLine1(table,"Bairro: " + doacao.getBairro());
		table = newLine1(table,"Complemento: " + doacao.getComplementares());
		table = newLine1(table,"Ponto de Referência: " + doacao.getReferencia());
		table = newLineImg(table,"Estado Físico: " + doacao.getInstituicao(), "resources/LogoUnico.png");
		
		return table;
	}
	
	public static PdfPTable newLineImg(PdfPTable table, String ligacao, String path) throws BadElementException, MalformedURLException, IOException {
		table = newCell(table,ligacao);
		Image img = Image.getInstance(Main.class.getClassLoader().getResource(path));
        table.addCell(new PdfPCell(img, true));
		return table;
	} 
	 
	public static PdfPTable newLine2(PdfPTable table, String ligacao, String coleta) {
		table = newCell(table,ligacao);
		table = newCell(table,coleta);
		table = newCell(table,"");
		return table;
	}

	public static PdfPTable newLine1(PdfPTable table, String nome) {
		table = newCell(table,nome);
		table = newCell(table,"");
		return table;
	}
	
	public static PdfPTable newCell(PdfPTable table,String text) {
		PdfPCell cell = new PdfPCell();
        cell.setFixedHeight(24);
        cell.setBorder(Rectangle.NO_BORDER);
        Font f = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, GrayColor.GRAYWHITE);
        cell.addElement(new Phrase(text));
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        return table;
	}
}
