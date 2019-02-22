package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class PopUp {
	PopUp(String name){
		try {
			Parent page = FXMLLoader.load(getClass().getClassLoader().getResource(name));
			page.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UTILITY);
//			stage.setTitle("Sistema Emaús de Atendimento");
			Scene popup = new Scene(page);
			stage.setScene(popup);     
			stage.setResizable(false);    
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
