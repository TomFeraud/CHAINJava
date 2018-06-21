package gui.view;

import java.io.IOException;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class DisplayResultsMapping {

	@FXML
	private TextArea resultsArea;

	// Objet servant de référence à notre classe principale
	// afin de pouvoir récupérer la liste de nos objets.
	private Main_GUI main;

	// Un constructeur par défaut
	public DisplayResultsMapping() {
	}

	// EASIER TO TEST WITH THIS
	@FXML
	private void initialize() {

	}

	// Méthode qui sera utilisée dans l'initialisation de l'IHM
	// dans notre classe principale
	public void setMainApp(Main_GUI mainApp) {
		this.main = mainApp;
	}

	@FXML
	public void nextScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayRepairedQueries.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayRepairedQueriesMapping controllor = loader.getController();
			controllor.setMainApp(this.main);

			controllor.setInitialQuery(this.main.getProjectModel().getQuery().get());
			controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getTestResults()));
			
			controllor.setListQueries();
			
		} catch (

		IOException e) {
			e.printStackTrace();
		}

		// TEST
		System.out.println("REPAIRED QUERIES:");
		System.out.println(this.main.getRun_CHAIn().getRepairedQueries());
		
		System.out.println(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());

	}

	@FXML
	public void back() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/SendQuery.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			SendQueryMapping controllor = loader.getController();
			controllor.setMainApp(this.main);

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BACK !! :)");
	}

	/**
	 * Pass the results computed by the SendQueryMapping controller to this
	 * controller
	 * 
	 * @param results
	 */
	public void setResults(String results) {
		this.resultsArea.setText(results);
	}
}
