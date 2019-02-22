package application;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PDFViewer {
    private Scene scene;
	private Stage stage;
	public void create(File pdf) throws Exception {
		ArrayList<Image> listOfImages = new PDFtoImage().converter(pdf.getCanonicalPath());
        
		ListView<String> listView = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for(int i=0; i<listOfImages.size(); i++) items.add(Integer.toString(i));
     
        listView.setItems(items);
        listView.setPrefHeight(660);
        
        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                Image image = listOfImages.get(0);
                imageView.setImage(image);
                setGraphic(imageView);
           }
        });        
        Group impressao = new Group();
        
        Label string = new Label("Imprimir");
        string.setStyle("-fx-text-fill: white");
//        string.setTranslateX(24);
//        string.setTranslateY(-8);
        string.setOnMouseClicked(new EventHandler<MouseEvent> ()  {  
            @Override  
            public void handle (MouseEvent c) {  
            	PDDocument documento;
				try {
					documento = PDDocument.load(pdf);
					PrintService servico = PrintServiceLookup.lookupDefaultPrintService();					
					PrinterJob job = PrinterJob.getPrinterJob();
					job.setPageable(new PDFPageable(documento));
					job.setPrintService(servico);
					job.print();
					documento.close();
				} catch (IOException | PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }  
        });
        
        SVGPath button = new SVGPath();
        button.setContent("M91.71 10 27.29 10 15 20.37 15 33 104 33 104 20.37 91.71 10z");
        button.setStyle("-fx-fill: #00b9d0");
        button.setOnMouseClicked(new EventHandler<MouseEvent> ()  {  
            @Override  
            public void handle (MouseEvent c) {  
            	PDDocument documento;
				try {
					documento = PDDocument.load(pdf);
					PrintService servico = PrintServiceLookup.lookupDefaultPrintService();					
					PrinterJob job = PrinterJob.getPrinterJob();
					job.setPageable(new PDFPageable(documento));
					job.setPrintService(servico);
					job.print();
					documento.close();
				} catch (IOException | PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }  
        });  
        
        impressao.getChildren().addAll(button,string);
        
    	button.setTranslateY(5);
    	string.setTranslateY(14);
    	string.setTranslateX(20);
        
//    	VBox barra = new VBox(impressao);
    	
    	VBox box = new VBox(listView,impressao);
        box.setPrefSize(484,660);
        box.setAlignment(Pos.CENTER);
    	
        stage = new Stage();
		stage.setTitle("Sistema Emaús de Atendimento");
        scene = new Scene(box,484,676);
        stage.initStyle(StageStyle.UTILITY);
		stage.setResizable(false);    
        stage.setScene(scene);     
        stage.show();
    }
}