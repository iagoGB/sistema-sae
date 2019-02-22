package application;

import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;


class Linha extends ListCell<String> {
	String id = null;
	String doacaoDetalhada = null;
	@Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        
        if(id == null) id = item;
        
        ColumnConstraints c1 = new ColumnConstraints(); c1.setPercentWidth(30);
        ColumnConstraints c2 = new ColumnConstraints(); c2.setPercentWidth(30);
        ColumnConstraints c3 = new ColumnConstraints(); c3.setPercentWidth(30);
        ColumnConstraints c4 = new ColumnConstraints(); c4.setPercentWidth(10);
        
        GridPane gridpane = new GridPane();
        gridpane.getColumnConstraints().addAll(c1,c2,c3,c4); // each get 50% of width
        
        try {
			ObservableList<Doacoes> doacoes = new Recebedor().getDoacoes("WHERE id=" + id);
			System.out.println("Item: " + id);
	        for(Doacoes doacao:doacoes) {
		        Label nome = new Label(doacao.getNome());
		        Label ligacao = new Label(doacao.getContato());
		        Label coleta = new Label(doacao.getColeta());
		        Group detalhes = new Group();
		        
		        Label string = new Label("+ Detalhes");
		        string.setOnMouseClicked(new EventHandler<MouseEvent> ()  {  
		            @Override  
		            public void handle (MouseEvent c) {  
		            	Controles.detalhado = id;
		            	new PopUp("telas/popupDetalhes.fxml");
		            }  
		        });
		        
		        SVGPath button = new SVGPath();
		        button.setContent("M91.71 10 27.29 10 15 20.37 15 33 104 33 104 20.37 91.71 10z");
		        button.setOnMouseClicked(new EventHandler<MouseEvent> ()  {  
		            @Override  
		            public void handle (MouseEvent c) {  
		            	Controles.detalhado = id;
		            	new PopUp("telas/popupDetalhes.fxml");
		            }  
		        });  
		        
		        detalhes.getChildren().addAll(button,string);
		        
		    	button.setTranslateY(5);
		    	string.setTranslateY(14);
		    	string.setTranslateX(20);

	        	gridpane.add(nome     , 0, 0);
	        	gridpane.add(ligacao  , 1, 0);
	        	gridpane.add(coleta   , 2, 0);
	        	gridpane.add(detalhes , 3, 0);
	        	detalhes.setTranslateY(5);
	            setGraphic(gridpane);
	        }
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
