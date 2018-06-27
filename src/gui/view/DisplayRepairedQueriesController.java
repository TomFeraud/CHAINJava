package gui.view;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * * The controller of the DisplayRepairedQueries view
 * 
 * @author Tom Feraud
 *
 */
public class DisplayRepairedQueriesController {

	@FXML
	private ListView<String> listRepairedQueries;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TextArea results;

	// Reference to the main class
	private Main_GUI main;

	/**
	 * Default constructor
	 */
	public DisplayRepairedQueriesController() {
	}

	@FXML
	private void initialize() {

	}

	/**
	 * Use to link the controllor with the main class
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Main_GUI mainApp) {
		this.main = mainApp;
	}

	@FXML
	public void nextScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayMatches.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayMatchesController controllor = loader.getController();
			controllor.setMainApp(this.main);

			controllor.setInitialQuery(this.main.getProjectModel().getInitialQuery().get());
			// THIS WILL CHANGE
			controllor.setRepairedQuery(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());
			//////
			// THIS WILL CHANGE TOO
			ArrayList<String[]> matchComponents = this.main.getRun_CHAIn().getRepairedQueries().get(0)
					.getMatchComponents();
			String matchesStr = new String();
			String simScore = Double.toString(this.main.getRun_CHAIn().getRepairedQueries().get(0).getSimValue());
			matchesStr = "";
			matchesStr += "Similarity score: " + simScore + "\n\n";
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

			DisplayResultsController controllor = loader.getController();
			controllor.setMainApp(this.main);
			controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getResultsFromARepairedQuery()));

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

	public ListView<String> getListRepairedQueries() {
		return listRepairedQueries;
	}

	public void setListRepairedQueries() {
		// From this example we can work out how a datasource org. member can select one
		// or
		// several queries to retun (by putting ".MULTIPLE")
		this.listRepairedQueries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		for (int i = 0; i < this.main.getRun_CHAIn().getRepairedQueries().size(); i++) {
			listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueries().get(i).getQuery());
		}

		// listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());
		// listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());
	}

}
