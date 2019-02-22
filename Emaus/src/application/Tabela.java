package application;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

public class Tabela {
	public ListView<String> setup(String mes) throws SQLException{
		ObservableList<String> data = FXCollections.observableArrayList();
		ListView<String> listView = new ListView<String>();
	    
	    listView.setItems(data);
	
	    ObservableList<Doacoes> doacoes = new Recebedor().getDoacoes(mes);
	    System.out.println("lenght: " + doacoes.size());
	    for(Doacoes doacao:doacoes) {
	    	data.add(doacao.getIdDoacao());
	    	System.out.println("ID Doacao: " + doacao.getIdDoacao());
	    }
	    
	    listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    listView.setPrefSize(983, 487);
	    listView.setEditable(true);
	    
	    listView.setCellFactory(new Callback<ListView<String>, 
	        ListCell<String>>() {
	            @Override 
	            public ListCell<String> call(ListView<String> listView) {
	                return new Linha();
	            }
	        }
	    );
	    return listView;
	}
}
