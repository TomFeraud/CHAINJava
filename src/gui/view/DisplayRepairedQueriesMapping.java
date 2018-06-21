package gui.view;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSetFormatter;

import chain_source.Match_Struc;
import gui.main.Main_GUI;
import gui.model.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class DisplayRepairedQueriesMapping {

	@FXML
	 private ListView<String> listRepairedQueries;
	//private ListView<Match_Struc> listRepairedQueries;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TextArea results;

	// Objet servant de référence à notre classe principale
	// afin de pouvoir récupérer la liste de nos objets.
	private Main_GUI main;

	// Un constructeur par défaut
	public DisplayRepairedQueriesMapping() {
	}

	// Méthode qui initialise notre interface graphique
	// avec nos données métier
	@FXML
	private void initialize() {
		// listRepairedQueries.getItems().addAll("NTM","HOIJP","HOIJP","HOIJP","HOIJP","HOIJP","HOIJP","HOIJP","HOIJP");

		// ObservableList<String> data = FXCollections.observableArrayList("gihoapfhzi",
		// "iohpj");
		// listRepairedQueries.setItems(data);
	}

	// Méthode qui sera utilisée dans l'initialisation de l'IHM
	// dans notre classe principale
	public void setMainApp(Main_GUI mainApp) {
		this.main = mainApp;
	}

	// TEST
	public void setListQueries() {
		// From this example we can work out how a datasource org. member can select one or
		// several queries to retun (by putting ".MULTIPLE")
		this.listRepairedQueries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		for(int i = 0; i < this.main.getRun_CHAIn().getRepairedQueries().size(); i++) {
		listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueries().get(i).getQuery());
		}
		
		//listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());
		//listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());
	}
	
	@FXML
	public void nextScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayMatches.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayMatchesMapping controllor = loader.getController();
			controllor.setMainApp(this.main);

			controllor.setInitialQuery(this.main.getProjectModel().getQuery().get());
			//THIS WILL CHANGE
			controllor.setRepairedQuery(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());
			//////
			//THIS WILL CHANGE TOO
			ArrayList<String[]> matchComponents = this.main.getRun_CHAIn().getRepairedQueries().get(0).getMatchComponents();
			String matchesStr = new String();
			matchesStr = "";
			for (String[] m : matchComponents) {
				matchesStr += "(";
				matchesStr += m[0];
				matchesStr += ")";
				matchesStr += m[1];
				matchesStr += "(";
				matchesStr += m[2];
				matchesStr += ")";
				matchesStr += "\n";
			}			
			controllor.setMatches(matchesStr);
			////////
			
		} catch (

		IOException e) {
			e.printStackTrace();
		}


	}
	
	
	@FXML
	public void back() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayResults.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayResultsMapping controllor = loader.getController();
			controllor.setMainApp(this.main);
			controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getTestResults()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BACK !! :)");
	}
	
	
	

	/**
	 * Pass the results to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setResults(String results) {
		this.results.setText(results);
	}

	/**
	 * Pass the query to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setInitialQuery(String q) {
		this.initialQuery.setText(q);
	}
}
