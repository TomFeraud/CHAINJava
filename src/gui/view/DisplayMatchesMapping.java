package gui.view;

import java.io.IOException;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class DisplayMatchesMapping {

	@FXML
	private TextArea repairedQuery;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TextArea matchesArea;

	@FXML
	private void initialize() {

	}

	// Objet servant de référence à notre classe principale
	// afin de pouvoir récupérer la liste de nos objets.
	private Main_GUI main;

	// Un constructeur par défaut
	public DisplayMatchesMapping() {
	}

	// Méthode qui sera utilisée dans l'initialisation de l'IHM
	// dans notre classe principale
	public void setMainApp(Main_GUI mainApp) {
		this.main = mainApp;
	}
	
	
	
	
	@FXML
	public void back() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayRepairedQueries.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayRepairedQueriesMapping controllor = loader.getController();
			controllor.setInitialQuery(this.main.getProjectModel().getQuery().get());
			controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getTestResults()));
			controllor.setListQueries();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BACK !! :)");
	}
	
	
	
	
	
	

	////////

	// These methods below may need to change/disappear ('cause at this step we
	// suppose to have every information required /!\ expect which query from the
	// list is selected)
	///////
	/**
	 * Pass the repaired query to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setRepairedQuery(String results) {
		this.repairedQuery.setText(results);
	}

	/**
	 * Pass the query to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setMatches(String q) {
		this.matchesArea.setText(q);
	}

	/**
	 * Pass the matches to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setInitialQuery(String m) {
		this.initialQuery.setText(m);
	}

}
