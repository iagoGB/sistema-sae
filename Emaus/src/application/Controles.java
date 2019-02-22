package application;

import org.apache.commons.io.FileUtils;

//import java.awt.print.PrinterJob;
//import java.io.File;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Timer;
//
//import javax.print.PrintService;
//import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.zeroturnaround.zip.ZipUtil;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.EventHandler;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.test.annotations.WrapToTest;
import com.itextpdf.text.DocumentException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.sql.Statement;  

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

public class Controles{
	public static String detalhado;

	@FXML AnchorPane pageDetalhes;
	
	@FXML TabPane sobreTabs;
	
	@FXML Group objetivos;
	@FXML Group tutorial;
	@FXML Group creditos;
	
	@FXML SVGPath svgCompactar;
	@FXML Label labelCompactar;
	
	@FXML SVGPath svgLimpar;
	@FXML Label labelLimpar;

	@FXML Button upload;
	
	@FXML SVGPath svgDoar;
	@FXML Label labelDoar;
	
	@FXML Button buttonImprimir;
	
	@FXML Button buttonExcluir;
	
	@FXML TextField inputProcurar;
	@FXML Button lupa;
	
	@FXML SplitMenuButton tipoDoacao;
	@FXML SplitMenuButton tipoPessoa;
	@FXML Pane inputConteudo;
	@FXML MenuItem tipoValor;
	@FXML MenuItem tipoObjeto;
	@FXML MenuItem tipoJuridica;
	@FXML MenuItem tipoFisica;
	@FXML GridPane gridInput;
	@FXML GridPane meses;
	
	@FXML DatePicker dataColeta;
	
	@FXML VBox autoNome;
	@FXML TextField nome;
	@FXML VBox autoBairro;
	@FXML TextField bairro;
	@FXML VBox autoRua;
	@FXML TextField rua;
	
	@FXML TextField valor;
	@FXML TextField telefone;
	@FXML TextField celular;
	@FXML TextField referencia;
	@FXML TextField complemento;
	
//	@FXML VBox autoNome;
	@FXML TextField detalhesNome;
//	@FXML VBox autoBairro;
	@FXML TextField detalhesBairro;
//	@FXML VBox autoRua;
	@FXML TextField detalhesRua;
	
	@FXML TextArea  detalhesObjetos;
	@FXML TextField detalhesValor;
	@FXML TextField detalhesTelefone;
	@FXML TextField detalhesCelular;
	@FXML TextField detalhesReferencia;
	@FXML TextField detalhesComplemento;
	
	@FXML TextField detalhesLigacao;
	@FXML TextField detalhesColeta;
	@FXML TextField detalhesFisico;
	
	@FXML VBox completador;
	
	@FXML AnchorPane tabela;
	
	@FXML GridPane mesesCompactar;
	@FXML GridPane mesesLimpar;
	
//	Timer timer;
	ListView<String> listView = new ListView<String>();
	ArrayList<String> list = new ArrayList<String>();
	String mesSelecionado = (Calendar.getInstance().get(Calendar.MONTH) + 1 < 10) ? 
							"0" + Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1): 
								  Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1);
	TextArea objeto = null;
	
	static ArrayList<Integer> listCompactar = new ArrayList<Integer>();
	static ArrayList<Integer> listLimpar = new ArrayList<Integer>();
	static ObservableList<String> selecionados = null;
	//SELECT * FROM `doacoes` WHERE coleta LIKE '%7%' || contato LIKE '%7%' pesquisa
	
	//eventos dos botoes
	public void Feedback(Event e) throws Exception{
		System.out.println("okay");

		if(e.getSource() == svgCompactar || e.getSource() == labelCompactar) {
			new PopUp("telas/feedbackconfirmarCompactar.fxml");
//			System.out.println(mesesCompactar);
			listCompactar = null;
			listCompactar = new ArrayList<Integer>();
			int cont = 1;
			for (Node i : mesesCompactar.getChildren()) {
				if (i instanceof CheckBox){
					CheckBox check = (CheckBox) i;
					if(check.isSelected()){
						listCompactar.add(cont);
						System.out.println(cont);
					}
					cont++;
			    }
		    }
		}
		if(e.getSource() == svgLimpar    || e.getSource() == labelLimpar) {
			new PopUp("telas/feedbackConfirmarExcluir.fxml");
			System.out.println(mesesLimpar);
			listLimpar = null;
			listLimpar = new ArrayList<Integer>();
			int cont = 1;
			for (Node i : mesesLimpar.getChildren()) {
				if (i instanceof CheckBox){
					CheckBox check = (CheckBox) i;
					if(check.isSelected()){
						listLimpar.add(cont);
						System.out.println(cont);
					}
					cont++;
			    }
		    }
		}
		if(e.getSource() == buttonExcluir) {
			selecionados = null;
			while(selecionados == null) selecionados = listView.getSelectionModel().getSelectedItems();
			
			new PopUp("telas/feedbackConfirmarExcluirHistorico.fxml");
		}
		if(e.getSource() == upload) {
			///https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
			final FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory( new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Downloads")); 
        	File file = fileChooser.showOpenDialog(Main.scene);
        	if (file != null) uploadFile(file.toString());
		}	//new PopUp("telas/FeedbackLoadingArchive.fxml");
		if(e.getSource() == svgDoar      || e.getSource() == labelDoar)             doar(); //new PopUp("telas/FeedbackRegistrou.fxml");
		if(e.getSource() == tipoDoacao)
			if(inputConteudo.getChildren().get(0)  instanceof TextArea) {
				createValor();
				tipoDoacao.setText("Valor");
			}
			else if(inputConteudo.getChildren().get(0)  instanceof TextField) {
				createObjeto(); 
				tipoDoacao.setText("Objeto");
			}
		
		if(e.getSource() == tipoValor)  createValor();
		if(e.getSource() == tipoObjeto) createObjeto();
		if(e.getSource() == tipoJuridica) tipoPessoa.setText("Pessoa Juridica");
		if(e.getSource() == tipoFisica)  tipoPessoa.setText("Pessoa Fisica");
		if(e.getSource() == tipoPessoa) {
			if(tipoPessoa.getText() == "Pessoa Fisica" || tipoPessoa.getText() == "Tipo de Pessoa")   
				tipoPessoa.setText("Pessoa Juridica"); else
			if(tipoPessoa.getText() == "Pessoa Juridica") 
				tipoPessoa.setText("Pessoa Fisica");
			System.out.println(tipoPessoa.getText() == "Tipo de Pessoa");
		}		
		if(e.getSource() == buttonImprimir) imprimir();
	}
	
	private void createValor() {
		inputConteudo.getChildren().clear();
		gridInput.getRowConstraints().get(1).setPrefHeight(42);
		
		TextField input = new TextField();
		input.setPromptText("Valor");
		input.setTranslateY(10);
		input.getStyleClass().add("inputForm");
		inputConteudo.getChildren().add(input);
		tipoDoacao.setText("Valor");
		objeto = null;
		valor = input;
		valor.textProperty().addListener((observable, oldValue, newValue) -> {
			if(valor.getText().length() > 0) {
				valor.setText("R$ " + newValue.replaceAll("[^\\d.]", ""));
			}
			else {
				valor.setText("");
			}
		});
	}
	
	private void createObjeto() {
		inputConteudo.getChildren().clear();
		gridInput.getRowConstraints().get(1).setPrefHeight(120);
		
		TextArea input = new TextArea();
//		input.setText("Objetos");
		input.setPromptText("Objetos");
		input.setPrefSize(573, 102);
		input.setTranslateY(10);
		input.getStyleClass().add("inputForm");
		inputConteudo.getChildren().add(input);
		tipoDoacao.setText("Objeto");
		objeto = input;
		valor = null;
	}
	//codigo --> http://www.java2s.com/Code/Java/JavaFX/DraganddropfiletoScene.htm
	//quando estiver sobre o input do arquivo
//	public void dragFile(DragEvent event){
//		System.out.println("over");
//    }
	
	public void dragFile(DragEvent event) throws SQLException{
		System.out.println("drag");
		Dragboard db = event.getDragboard();
        boolean success = false;
        
        if (db.hasFiles()){
            success = true;
            String filePath = null;
            for (File file:db.getFiles()) {
                filePath = file.getAbsolutePath();
                System.out.println(filePath);
                uploadFile(filePath);
            }
        }
        
        event.setDropCompleted(success);
        event.consume();
	}
	
	//recebe o arquivo
	public void uploadFile(String filePath) throws SQLException{
		System.out.println("upload");
		
        //lê arquivo de entrada
        Doacoes doacao = ReadXMLFile.getFile(filePath);
        System.out.println(doacao);
        
        putText(doacao);
	}
	
	public void doar() throws SQLException {
		Doacoes doacao = new Doacoes(
				nome.getText(),
				tipoPessoa.getText(),//instituicao.getText(),
				celular.getText(),//email.getText(),
				telefone.getText(),
				rua.getText(),
				referencia.getText(),
				bairro.getText(),
				valor == null ? objeto.getText() : valor.getText(),
				"",//conhece.getText(),
				complemento.getText(),
				"0",
				dataColeta.getValue().toString());
				System.out.println(valor == null ? objeto.getText() : valor.getText());
		
        System.out.println(dataColeta.getValue().toString());
        
        Sender envio = new Sender();
        envio.send(doacao);
        
        new PopUp("telas/FeedbackRegistrou.fxml");
	}
	
	public void excluir(InputEvent e) throws SQLException {
		System.out.println("Selecionado" + selecionados);
		for(String item:selecionados) new Excluir().execute(item);
		
		final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	public void imprimir() throws Exception {
		ObservableList<String> selecionados = null;
		
		while(selecionados == null) selecionados = listView.getSelectionModel().getSelectedItems();
		ObservableList<Doacoes> data = FXCollections.observableArrayList();
		
		for(String item:selecionados) data.addAll(new Recebedor().getDoacoes("WHERE id=" + item));
		
		boolean newDirectory = new File("impressos").mkdirs();
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
        File file = new CreatePDF().setup("impressos/" + date + ".pdf", data);
		 
        new PDFViewer().create(file);
		
		createTable("WHERE contato LIKE " + "'%-" + mesSelecionado + "-%'");
		mesDestacado(mesSelecionado);
	}
	
	public void pesquisar() throws SQLException {
		System.out.println("pesquisei");
		
		String id = "";
		System.out.println("Até aqui foi");
		Connection con = (Connection) new ConnectionFactory().getConnection();
        // cria um preparedStatement
	    String sql = "SELECT * FROM doadores WHERE " + // contato LIKE '%-06-%'" +
	    		"nome LIKE '%"                 + inputProcurar.getText() +
	    		"%' OR instituicao LIKE '%"    + inputProcurar.getText() +
	    		"%' OR email LIKE '%"          + inputProcurar.getText() +
	    		"%' OR telefone LIKE '%"       + inputProcurar.getText() +
	    		"%' OR endereco LIKE '%"       + inputProcurar.getText() +
	    		"%' OR referencia LIKE '%"     + inputProcurar.getText() +
	    		"%' OR bairro LIKE '%"         + inputProcurar.getText() +
	    		"%' OR conhece LIKE '%"        + inputProcurar.getText() +
	    		"%' OR complementares LIKE '%" + inputProcurar.getText() + "%'"; 	
	    System.out.println(sql);
	    Statement stmt = (Statement) con.createStatement();
	    ResultSet resultado = stmt.executeQuery(sql);
	    
	    int cont = 0;
	    
		while(resultado.next()) {
			if(cont > 0)  id += " OR ";
			if(cont == 0) id = "AND (("; 
			id += "iddoador ='" + resultado.getString("id") + "'";
	    	cont++;
	    }
		
		if(id != "") { id += ") OR (conteudo LIKE '%" + inputProcurar.getText() + "%'" 
				+ " OR contato LIKE '%" + inputProcurar.getText() + "%'"
				+ " OR coleta LIKE '%" + inputProcurar.getText() + "%'))";
		}
		else {
			id = " AND (conteudo LIKE '%" + inputProcurar.getText() + "%'" 
				+ " OR contato LIKE '%" + inputProcurar.getText() + "%'"
				+ " OR coleta LIKE '%" + inputProcurar.getText() + "%')";
		}
		
		System.out.println(id);
	    
		stmt.close();
        resultado.close();
        
		createTable("WHERE contato LIKE " + "'%-" + mesSelecionado + "-%' " + id );
	}
	
	public void compactar(Event e) throws SQLException, IOException, DocumentException {
		System.out.println(listCompactar);
		ObservableList<Doacoes> doacoes = FXCollections.observableArrayList();
		String date = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		new File("compactados").mkdirs();
		new File("compactados/" + date).mkdirs();
		
		for(int i=0; i<listCompactar.size();i++) {
			if(listCompactar.get(i) < 10) {
				doacoes = new Recebedor().getDoacoes("WHERE contato LIKE '%-0" + Integer.toString(listCompactar.get(i)) + "-%'");
				new File("compactados/" + date + "/0" + listCompactar.get(i)).mkdirs();
				for(Doacoes doacao : doacoes) {
					ObservableList<Doacoes> aux = FXCollections.observableArrayList();
					aux.add(doacao);
			        File file = new CreatePDF().setup("compactados/" + date + "/0" + listCompactar.get(i) + "/" + doacao.getIdDoacao() + ".pdf", aux); 
				}
			}
			else{
				doacoes = new Recebedor().getDoacoes("WHERE contato LIKE '%-" + Integer.toString(listCompactar.get(i)) + "-%'");
				new File("compactados/" + date + "/" + listCompactar.get(i)).mkdirs();
				for(Doacoes doacao : doacoes) {
					ObservableList<Doacoes> aux = FXCollections.observableArrayList();
					aux.add(doacao);
			        File file = new CreatePDF().setup("compactados/" + date + "/" + listCompactar.get(i) + "/" + doacao.getIdDoacao() + ".pdf", aux); 
				}
			}
		}
		
		ZipUtil.pack(new File("compactados/" + date), new File("compactados/" + date + ".zip"));
		
		FileUtils.deleteDirectory(new File("compactados/" + date));
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("compactados")); 
    	File file = fileChooser.showOpenDialog(Main.scene);
    	
    	final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	public void limpar(Event e) throws SQLException {
		System.out.println(listLimpar);
		for(int i=0; i<listLimpar.size();i++) {
			if(listLimpar.get(i) < 10) {
				new Excluir().limpar("WHERE contato LIKE '%-0" + Integer.toString(listLimpar.get(i)) + "-%'");
			}
			else{
				new Excluir().limpar("WHERE contato LIKE '%-" + Integer.toString(listLimpar.get(i)) + "-%'");
			}
		}	
		final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
	public void newWindow() {
		new CreateMap().create();
	}
	
	public void hover(Event e) {
		System.out.println("hover");
		Group grupo = (Group) e.getSource();
		for(Node i : grupo.getChildren()) {
			if(i instanceof SVGPath) {
				SVGPath svg = (SVGPath) i;
				svg.setStyle("-fx-fill: #00bacf");
			}
			if(i instanceof Label) {
				Label label = (Label) i;
				label.setStyle("-fx-text-fill: white");
			}
		}
	}
	
	public void hup(Event e) {
		Group grupo = (Group) e.getSource();
		for(Node i : grupo.getChildren()) {
			if(i instanceof SVGPath) {
				SVGPath svg = (SVGPath) i;
				svg.setStyle("-fx-fill: white");
			}
			if(i instanceof Label) {
				Label label = (Label) i;
				label.setStyle("-fx-text-fill: #00bacf");
			}
		}
	}
	
	@FXML
    public void initialize() throws SQLException{
		if(nome != null) {
			TextField[] inputs = {nome, bairro, rua, referencia, complemento};
			for(TextField input:inputs){
				input.textProperty().addListener((observable, oldValue, newValue) -> {
	//		    	System.out.println("TextField Text Changed (newValue: " + newValue + ")");
					input.setText(newValue.toUpperCase());
					try { new AutoComplete(input).Completar(); } 
					catch (SQLException e) { e.printStackTrace(); }
				}); 
				input.focusedProperty().addListener(new Clear(input));
			}
			
			System.out.println(mesesCompactar);
			
			if(valor != null) {
				valor.textProperty().addListener((observable, oldValue, newValue) -> {
					if(valor.getText().length() > 0) {
						valor.setText("R$ " + newValue.replaceAll("[^\\d.]", ""));
					}
					else {
						valor.setText("");
					}
				});
			}
			
			Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	Stage stage = (Stage) nome.getScene().getWindow();
		        	stage.focusedProperty().addListener(new ChangeListener<Boolean>(){
		        		@Override
		        		public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        			if (newPropertyValue){
		        				try {
		        					createTable("WHERE contato LIKE " + "'%-" + mesSelecionado + "-%'");
		        					mesDestacado(mesSelecionado);
		        				} catch (SQLException e) {
		        					//TODO Auto-generated catch block
		        					e.printStackTrace();
		        				}
		        			}
		        		}
		        	});		            
		        }
		    });
			
			if(tabela != null) {
				createTable("WHERE contato LIKE " + "'%-" + mesSelecionado + "-%'");
				mesDestacado(mesSelecionado);
			}
			
			tipoPessoa.setText("Tipo de Pessoa"); 
			
			System.out.println(mesSelecionado);
			
			SingleSelectionModel<Tab> selectionModel = sobreTabs.getSelectionModel();
			String a = null;
			String b = null;
			String c = null;
		
			selectionModel.select(0);			
			a = "white";
			b = "#ced9de";
			c = "#ced9de";
			
			for(Node i : objetivos.getChildren()) 
				if(i instanceof SVGPath) {
					SVGPath svg  = (SVGPath) i;  
					svg.setStyle("-fx-fill: " + a);
				}
			for(Node i : tutorial.getChildren()) 
				if(i instanceof SVGPath) {
					SVGPath svg  = (SVGPath) i;  
					svg.setStyle("-fx-fill: " + b);
				}
			for(Node i : creditos.getChildren()) 
				if(i instanceof SVGPath) {
					SVGPath svg  = (SVGPath) i;  
					svg.setStyle("-fx-fill: " + c);
				}	
			
			for (Node i : meses.getChildren()) {
				if (i instanceof Group){
					int cont = GridPane.getRowIndex(i) == null ? 1 : GridPane.getRowIndex(i) + 1;
		            String mes = cont < 10 ? "0" + Integer.toString(cont) : Integer.toString(cont);
					Group grupo = (Group) i;
					for (Node j : grupo.getChildren()) {
						if (j instanceof SVGPath){
							SVGPath svg = (SVGPath) j; 
							svg.setOnMouseClicked((event) -> {
								try {
									createTable("WHERE contato LIKE " + "'%-" + mes + "-%'");
									mesSelecionado = mes;
									mesDestacado(mes);
									System.out.println(mes);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
					    }
						if (j instanceof Label){
							Label label = (Label) j; 
							label.setOnMouseClicked((event) -> {
								try {
									createTable("WHERE contato LIKE " + "'%-" + mes + "-%'");
									mesSelecionado = mes;
									mesDestacado(mes);
									System.out.println(mes);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							});
					    }
				    }
			    }
		    }
		}
		if(pageDetalhes != null) {
			ObservableList<Doacoes> doacoes = new Recebedor().getDoacoes("WHERE id=" + detalhado);
			System.out.println(doacoes.get(0).getConteudo());
			
			detalhesNome.setText(       doacoes.get(0).getNome());
			detalhesNome.setDisable(true);
			
			detalhesBairro.setText(     doacoes.get(0).getBairro());
			detalhesBairro.setDisable(true);
			
			detalhesRua.setText(        doacoes.get(0).getEndereco());
			detalhesRua.setDisable(true);
			
			detalhesTelefone.setText(   doacoes.get(0).getTelefone());
			detalhesTelefone.setDisable(true);
			
			detalhesCelular.setText(    doacoes.get(0).getEmail());
			detalhesCelular.setDisable(true);
			
			detalhesReferencia.setText( doacoes.get(0).getReferencia());
			detalhesReferencia.setDisable(true);
			
			detalhesComplemento.setText(doacoes.get(0).getComplementares());
			detalhesComplemento.setDisable(true);
			
			detalhesLigacao.setText(    doacoes.get(0).getContato());
			detalhesLigacao.setDisable(true);
			
			detalhesColeta.setText(     doacoes.get(0).getColeta());
			detalhesColeta.setDisable(true);
			
			detalhesFisico.setText(     doacoes.get(0).getInstituicao());
			detalhesFisico.setDisable(true);
			
			if(doacoes.get(0).getConteudo().contains("R$")) {
				detalhesValor.setText(  doacoes.get(0).getConteudo());
			}
			else {
				detalhesObjetos.setText(doacoes.get(0).getConteudo());
			}
			
			detalhesValor.setDisable(true);
			detalhesObjetos.setDisable(true);
		
			TextField[] inputs = {detalhesNome, detalhesBairro, detalhesRua, detalhesReferencia, detalhesComplemento};
			for(TextField input:inputs){
				input.textProperty().addListener((observable, oldValue, newValue) -> {
	//		    	System.out.println("TextField Text Changed (newValue: " + newValue + ")");
					input.setText(newValue.toUpperCase());
					try { new AutoComplete(input).Completar(); } 
					catch (SQLException e) { e.printStackTrace(); }
				}); 
				input.focusedProperty().addListener(new Clear(input));
			}
		}
	}
	
	public void createTable(String mes) throws SQLException {
		tabela.getChildren().clear();
		listView = new Tabela().setup(mes);
    	tabela.getChildren().add(listView);
	}
	
	String nomeInicial = null;
	
	public void update(Event e) throws SQLException {
		Group grupo = null;
		SVGPath svg = null;
		Label label = null;
				
		if(e.getSource() instanceof SVGPath) {
			SVGPath clicked = (SVGPath) e.getSource();
			grupo = (Group) clicked.getParent();
		}
		if(e.getSource() instanceof Label) {
			Label clicked = (Label) e.getSource();
			grupo = (Group) clicked.getParent();
		}
		for(Node i : grupo.getChildren()) {
			if(i instanceof SVGPath) svg = (SVGPath) i;
			if(i instanceof Label) label = (Label) i;
		}
		
		System.out.println(label.getText());
		
		if(label.getText() != "Confirmar") {
			nomeInicial = detalhesNome.getText().toString();
			label.setText("Confirmar");
			label.setTranslateX(-16);
			detalhesNome.setDisable(false);
			detalhesBairro.setDisable(false);
			detalhesRua.setDisable(false);
			detalhesTelefone.setDisable(false);
			detalhesCelular.setDisable(false);
			detalhesReferencia.setDisable(false);
			detalhesComplemento.setDisable(false);
			detalhesLigacao.setDisable(false);
			detalhesColeta.setDisable(false);
			detalhesFisico.setDisable(false);		
			detalhesValor.setDisable(false);
			detalhesObjetos.setDisable(false);
		}
		else {
			Doacoes doacao = new Doacoes(
					nomeInicial,
					detalhesFisico.getText(),//instituicao.getText(),
					detalhesCelular.getText(),//email.getText(),
					detalhesTelefone.getText(),
					detalhesRua.getText(),
					detalhesReferencia.getText(),
					detalhesBairro.getText(),
					detalhesValor == null ? detalhesObjetos.getText() : detalhesValor.getText(),
					"",//conhece.getText(),
					detalhesComplemento.getText(),
					detalhesLigacao.getText(),
					detalhesColeta.getText().toString());
		
			System.out.println(nomeInicial + "," + detalhesNome.getText());
			
        	new Updater().change(doacao, detalhado, detalhesNome.getText());
        
        	final Node source = (Node) e.getSource();
        	final Stage stage = (Stage) source.getScene().getWindow();
        	stage.close();
		}
	}
	
	void mesDestacado(String mesAtivo) {
		for (Node i : meses.getChildren()) {
			if (i instanceof Group){
				int cont = GridPane.getRowIndex(i) == null ? 1 : GridPane.getRowIndex(i) + 1;
	            String mes = cont < 10 ? "0" + Integer.toString(cont) : Integer.toString(cont);
				Group grupo = (Group) i;
				if(grupo.getStyleClass().contains("mes-ativado")) {
					grupo.setTranslateX(0);
					for(Node j : grupo.getChildren()) 
						if(j instanceof Label) {
							Label label = (Label) j; 
							label.setTranslateY(0);
							label.setTranslateX(12);
						}
				}
				grupo.getStyleClass().clear();
				System.out.println("'" + mesAtivo + "'" + "," + "'" + mes + "'");
				System.out.println(mesAtivo.equals(mes));
				if(mesAtivo.equals(mes)) {
					grupo.getStyleClass().add("mes-ativado");
					grupo.setTranslateX(18);
					System.out.println("yeah");
					for(Node j : grupo.getChildren()) 
						if(j instanceof Label) {
							Label label = (Label) j; 
							label.setTranslateY(-4);
							label.setTranslateX(-6);
						}
				}
			}
	    }
	}
	
	class Clear implements ChangeListener<Boolean>{
		TextField input;
		VBox autoComplete;
		Clear(TextField arg0){
			this.input = arg0;
			this.autoComplete = (arg0 == nome) ? autoNome : (arg0 == bairro) ? autoBairro : (arg0 == rua) ? autoRua : null;
		}
	    @Override
	    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
	        if (!newPropertyValue) {
	        	ResetComplete(this.input, this.autoComplete);
	        }
//	        else {
//	        	try { 
//	        			if(this.input.getText() != "") new AutoComplete(this.input).Completar(); 
//	        		} 
//				catch (SQLException e) { e.printStackTrace(); }
//	        }
//	        	System.out.println("clear"); 
	    }
	}
	
	
	public void funSobre(Event e) {
		SingleSelectionModel<Tab> selectionModel = sobreTabs.getSelectionModel();
		String a = null;
		String b = null;
		String c = null;
	
		if(e.getSource() == objetivos) {
			selectionModel.select(0);			
			a = "white";
			b = "#ced9de";
			c = "#ced9de";
		}
		if(e.getSource() == tutorial) {
			selectionModel.select(1);
			a = "#ced9de";
			b = "white";
			c = "#ced9de";
		}
		if(e.getSource() == creditos) {
			selectionModel.select(2);
			a = "#ced9de";
			b = "#ced9de";
			c = "white";
		}
		
		for(Node i : objetivos.getChildren()) 
			if(i instanceof SVGPath) {
				SVGPath svg  = (SVGPath) i;  
				svg.setStyle("-fx-fill: " + a);
			}
		for(Node i : tutorial.getChildren()) 
			if(i instanceof SVGPath) {
				SVGPath svg  = (SVGPath) i;  
				svg.setStyle("-fx-fill: " + b);
			}
		for(Node i : creditos.getChildren()) 
			if(i instanceof SVGPath) {
				SVGPath svg  = (SVGPath) i;  
				svg.setStyle("-fx-fill: " + c);
			}	
	}
	
	public void ResetComplete(TextField input, VBox autoComplete) {
		autoComplete.getChildren().clear();
    	autoNome.setSpacing(0); 
    	autoComplete.getChildren().add(input);
	}
	
	private void putText(Doacoes doacao) {
		nome.setText(doacao.getNome());
		telefone.setText(doacao.getTelefone());
		rua.setText(doacao.getEndereco());
		referencia.setText(doacao.getReferencia());
		bairro.setText(doacao.getBairro());
//		"",//conhece.getText(),
		complemento.setText(doacao.getComplementares());
	}
	
	class AutoComplete{
		String[] info = new String[] {"",""};
		TextField buscador = new TextField();
		VBox autoComplete = new VBox();
		AutoComplete(TextField a) {
			if(a == bairro) { info = new String[] {"bairros","NOME"};     buscador = bairro; autoComplete = autoBairro; }
			if(a == rua)    { info = new String[] {"enderecos","STREET"}; buscador = rua;    autoComplete = autoRua; }
			if(a == nome)   { info = new String[] {"doadores","nome"};    buscador = nome;   autoComplete = autoNome; }
		}
		
		public void Completar() throws SQLException{
	        try{
	        	Connection con = (Connection) new ConnectionFactory().getConnection();
	        	// cria um preparedStatement
	        	String sql = "SELECT * FROM " + info[0] + " WHERE " + info[1] + " LIKE " + "'%" + buscador.getText() + "%'";
	//            	System.out.println(sql);
	        	
	        	Statement stmt = (Statement) con.createStatement();
	        	ResultSet resultado = stmt.executeQuery(sql);
	        	
	        	autoComplete.getChildren().clear();
	//            	autoNome.setSpacing(0); 
	        	autoComplete.getChildren().add(buscador);
	        	
	        	int cont = 0;
	     		while(resultado.next() && cont<5 ) { 
	     			Label label = new Label(resultado.getString(info[1]));
	     			label.getStyleClass().add("autoComplete");
	     			label.setPrefWidth(buscador.getWidth());
	//     			label.setOnMouseClicked((event) -> {
	     			label.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    	            @Override
	    	            public void handle(MouseEvent mouseEvent) {
	    	                System.out.println("mouse click detected! " + mouseEvent.getSource());
	    	                buscador.setText(label.textProperty().getValue());
	     	                autoComplete.getChildren().clear();
	     	                autoComplete.setSpacing(0); 
	     	                autoComplete.getChildren().add(buscador);
							if(buscador == nome) {
								try {
									System.out.println("WHERE nome=" +  buscador.getText());
									Doacoes doacao = new Recebedor().getDoador("WHERE nome='" +  buscador.getText() + "'");
									putText(doacao);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
	    	            }
	    	        });
	     			autoComplete.getChildren().add(label);
	     	    	cont++;
	     	    }
	     		autoComplete.toFront();
	     		System.out.println("E isto");
	     		
	//         		label.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
	     	    
	     		stmt.close();
	     		resultado.close();
	        } catch (Exception e){}
	      }
	}
}