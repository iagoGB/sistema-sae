package application;

//http://www.h2database.com/html/main.html
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//import java.io.InputStreamReader;
//import java.net.InetAddress;
//import java.sql.ResultSet;
//import java.sql.SQLException;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.sql.Statement;  

import org.h2.tools.Server;

import java.io.BufferedReader;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.IOException;

public class Main extends Application {  
	public static Stage scene;
	public static Parent root;
	ObservableList<String> data = FXCollections.observableArrayList();
	ListView<String> listView = new ListView<String>();
	//sincronizar
	//INSERT INTO `sincronizador` SELECT * FROM `doacoes` UNION SELECT * FROM `doacoes2`
	//DELETE FROM `doacoes` WHERE 1
	//INSERT INTO `doacoes` SELECT * FROM `sincronizador`
	//DELETE FROM `sincronizador`
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Main.scene = primaryStage;
			Main.root = FXMLLoader.load(getClass().getClassLoader().getResource("telas/Loading.fxml"));
			Main.scene.setTitle("Sistema Emaús de Atendimento");
	        Main.scene.setScene(new Scene(Main.root)); 
	        Main.scene.show(); 
	        Main.scene.setOnCloseRequest(new EventHandler<WindowEvent>() {
	        	@Override
	        	public void handle(WindowEvent we) {
	            	Platform.exit();
	            	try(Connection con = (Connection) new ConnectionFactory().getConnection()){
	                	String db = "drop schema if exists mydb";
	                	Statement acao = (Statement) con.createStatement();
	                	acao.executeUpdate(db);
	                	acao.close();
	            	} catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	            }
	        });  
//	        AnchorPane tabela = (AnchorPane) Main.root.lookup("#tabela");
//			
//			for(int i=0;i<30;i++) {
//				data.add("");
//			}
//	        
//	        listView.setItems(data);
//	        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//	        listView.setPrefSize(983, 487);
//	        listView.setEditable(true);
//	        
//	        listView.setCellFactory(new Callback<ListView<String>, 
//	                ListCell<String>>() {
//	                    @Override 
//	                    public ListCell<String> call(ListView<String> listView) {
//	                        return new ColorRectCell();
//	                    }
//	                }
//	            );
//	        tabela.getChildren().add(listView);
//	        
//			System.out.println(tabela);
	
            Thread t = new Thread(load);
   		 	t.start();
            
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {		
		// start the TCP Server
        Server.createTcpServer().start();
		launch(args);
	}

//	https://www.devmedia.com.br/trabalhando-com-threads-em-java/28780
//	https://docs.oracle.com/javafx/2/api/javafx/concurrent/Task.html
	private static Runnable load = new Runnable(){
        public void run() {
            try{
            	ProgressBar loading = (ProgressBar) Main.root.lookup("#loading");
//        		byte[] ip = InetAddress.getLocalHost().getAddress();
//        	    int timeout = 1000;
//        	    for(int i=1;i<2;i++){
//        	    	ip[3] = (byte)i;
//        	    	InetAddress addr = InetAddress.getByAddress(ip);
//        	    	if (addr.isReachable(timeout)){
////        	    		System.out.println(host + " is reachable");
//        	    		String name = addr.getHostName();
//        	    		System.out.println("name: " + name);
//        	    	}
//        	    	loading.setProgress(1./254 * i);
//        	    	
//        	    	
//        	    }
                // preenche os valores
            	
            	//String path = new File(".").getCanonicalPath();
        		Runtime runtime = Runtime.getRuntime();
        		
//        		File osrm = new File("mapa\\osrm\\osrm-routed.exe");
//            	File fortaleza = new File("mapa\\osrm\\fortaleza\\fortaleza.osrm");
        		       		
        		loading.setProgress(0.14);
        		
//        		try {
//        			String pidInfo = TaskList.lista(); 
//
//        			if(!pidInfo.contains("osrm-routed.exe")) 
//        				try{
//        					runtime.exec("cmd /c " + osrm.getCanonicalPath() + " --algorithm=MLD " + fortaleza.getCanonicalPath());
//        				}
//        			catch (Exception e) {
//        				// TODO Auto-generated catch block
//        	            e.printStackTrace();
//        			}
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
        		
        		loading.setProgress(0.28);
            	
            	loading.setProgress(0.42);
            	try (BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("resources/enderecos.csv").toURI())))) { //getClass().getClassLoader().getResource(
                	Connection con = (Connection) new ConnectionFactory().getConnection();
                	String delete = "drop table if exists enderecos";
                	String db = "create table if not exists enderecos (" +
                		"LON varchar(60) NOT NULL," +
              			"LAT varchar(60) NOT NULL," +
              			"STREET varchar(60) NOT NULL" +
              			")";
                	Statement acao = (Statement) con.createStatement();
                	acao.executeUpdate(delete);
                	acao.executeUpdate(db);
                	acao.close();
                    
                	String line;
                	while ((line = br.readLine()) != null) {
                		String[] split = line.split(",");
                		String sql = "insert into enderecos (LON,LAT,STREET) values (?,?,?)";
                        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
                        stmt.setString(1, split[0].replaceAll("'", ""));
                        stmt.setString(2, split[1].replaceAll("'", ""));
                        stmt.setString(3, split[2].replaceAll("'", ""));
                        stmt.execute();
                        stmt.close();
                	}
            	} catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            	
            	loading.setProgress(0.56);
            	
            	try (BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(new File(getClass().getClassLoader().getResource("resources/bairros.csv").toURI()))))) {
                	Connection con = (Connection) new ConnectionFactory().getConnection();
                	String delete = "drop table if exists bairros";
                	String db = "create table if not exists bairros (" +
                		"NOME varchar(60) NOT NULL" +
              			")";
                	Statement acao = (Statement) con.createStatement();
                	acao.executeUpdate(delete);
                	acao.executeUpdate(db);
                	acao.close();
                    
                	String line;
                	while ((line = br.readLine()) != null) {
                		String[] split = line.split(",");
                		String sql = "insert into bairros (NOME) values (?)";
                        PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
                        stmt.setString(1, split[0].replaceAll("'", ""));
                        stmt.execute();
                        stmt.close();
                	}
            	} catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            	
            	
            	loading.setProgress(0.70);
            	
            	try (Connection con = (Connection) new ConnectionFactory().getConnection()) {
                	String db = "create table if not exists doadores (" +
                		"id int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
						"nome varchar(60) NOT NULL," +
						"instituicao varchar(60) NOT NULL," +
						"email varchar(60) NOT NULL," +
						"telefone varchar(60) NOT NULL," +
						"endereco varchar(60) NOT NULL," +
						"referencia varchar(60) NOT NULL," +
						"bairro varchar(60) NOT NULL," +
						"conhece varchar(60) NOT NULL," +
						"complementares varchar(60) NOT NULL" +
              			")";
                	Statement acao = (Statement) con.createStatement();
                	acao.executeUpdate(db);
                	acao.close();
            	} catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            	
            	loading.setProgress(0.84);
            	
            	try (Connection con = (Connection) new ConnectionFactory().getConnection()) {
                	String db = "create table if not exists doacoes (" +
                		"id int NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                		"iddoador varchar(60) NOT NULL," +
						"conteudo varchar(60) NOT NULL," +
              			"contato varchar(60) NOT NULL," +
              			"coleta varchar(60) NOT NULL" +
              			")";
                	Statement acao = (Statement) con.createStatement();
                	acao.executeUpdate(db);
                	acao.close();
            	} catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            	
            	loading.setProgress(1);
//            	loading.setProgress(1./254 * i);
            	
        	    Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
        				try {
        					Main.root = FXMLLoader.load(getClass().getClassLoader().getResource("telas/Main.fxml"));
        					Main.root.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        					Main.scene.setScene(new Scene(Main.root));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                });
        	    
            } catch (Exception e){}
        }
    };
	
}
