package application;

import java.io.File;

import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class CreateMap {
	private Scene scene;
	private Stage stage;
	public void create() {
		stage = new Stage();
		stage.setTitle("Web View");
        scene = new Scene(new Browser(),750,500, Color.web("#666970"));
        stage.setScene(scene);     
        stage.show();
	}
}

class Browser extends Region {
	 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page http://a.tile.osm.org/{z}/{x}/{y}{r}.png
        File path = new File("mapa\\html\\router.html");
        File tiles = new File("mapa\\tiles");
        webEngine.load(path.toURI().toString());
//        webEngine.setUserStyleSheetLocation(getClass().getResource("src/web.css").toExternalForm());
        webEngine.getLoadWorker().stateProperty().addListener((observableValue1, state, state2) -> {
            if (state2 == State.SUCCEEDED) {
                webEngine.executeScript(
                		"var map = L.map('map', {" +
                        	"minZoom: 12," +
                        	"maxZoom: 15" +
                        "});"+
                		"var corner1 = L.latLng(-3.646113, -38.669128);" +
                		"var corner2 = L.latLng(-3.948987, -38.31984);" +
                		"var bounds = L.latLngBounds(corner1, corner2);" +
                		"map.setMaxBounds(bounds);" +
                		"map.on('drag', function() {" +
                		    "map.panInsideBounds(bounds, { animate: false });" +
                		"});"+
                		"L.tileLayer('" + tiles.toURI().toString() +"/{z}/{x}/{y}{r}.png', {" + 
                			"attribution: '© OpenStreetMap contributors'" + 
                		"}).addTo(map);" +  
                		"L.Routing.control({" + 
                			"waypoints: [" + 
                				"L.latLng(-3.727398, -38.545189)," + 
                				"L.latLng(-3.755491, -38.522701)," +
                				"L.latLng(-3.724871, -38.516436)"  +
                			"]," + 
                			"router: new L.Routing.OSRMv1({" + 
                				"language: 'pt'," +
                				"serviceUrl: 'http://localhost:5000/route/v1'" + 
                			"})," + 
                			"routeWhileDragging: true," +
                			"language: 'pt'" +
                		"}).addTo(map);"
                		);
            }
        });

        getChildren().add(browser); 
    }
}
