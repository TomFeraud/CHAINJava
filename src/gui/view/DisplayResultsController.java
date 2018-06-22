package gui.view;

import java.io.IOException;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * The controller of the DisplayResults view
 * 
 * @author Tom Feraud
 *
 */
public class DisplayResultsController {

	@FXML
	private TextArea resultsArea;

	// TEST
	// This may need to be in the main or model
	private List<QuerySolution> listResults;

	// TEST
	@FXML
	private Text topText;

	// TEST
	@FXML
	private Text bottomText;

	// Reference to the main class
	private Main_GUI main;

	/**
	 * Default constructor
	 */
	public DisplayResultsController() {
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
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayRepairedQueries.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayRepairedQueriesController controllor = loader.getController();
			controllor.setMainApp(this.main);

			controllor.setInitialQuery(this.main.getProjectModel().getInitialQuery().get());
			controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getResultsFromARepairedQuery()));
			controllor.setListQueries();
			System.out.println("\n\n\nTEEEEEEST");
			// System.out.println("Row number");
			// This print the number of results, 10 in the example:)
			// System.out.println(this.main.getRun_CHAIn().getResultsFromARepairedQuery().getRowNumber());
			// System.out.println("Ressource model");
			// Beaucoup trop de choses..
			// System.out.println(this.main.getRun_CHAIn().getResultsFromARepairedQuery().getResourceModel());
			System.out.println("Result Vars");
			// Affiche les "titres" des colonnes: [id, dataSource, waterBodyId,
			// identifiedDate, affectsGroundwater]
			System.out.println(this.main.getRun_CHAIn().getResultsFromARepairedQuery().getResultVars());
			System.out.println("\nFin du testEEEEEEST\n");

			System.out.println("\n\n\nTEEEEEEST LIST results");
			for (int i = 0; i < listResults.size(); i++) {
				System.out.println("i: " + this.listResults.get(i));
			}

			System.out.println("\nFin du testEEEEEEST\n");
		} catch (

		IOException e) {
			e.printStackTrace();
		}

		// TEST
		System.out.println("REPAIRED QUERIES:");
		System.out.println(this.main.getRun_CHAIn().getRepairedQueries());

		// System.out.println(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());

	}

	@FXML
	public void back() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/SendQuery.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			SendQueryController controllor = loader.getController();
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

	/// TEST
	public void setListResults(List<QuerySolution> results) {
		this.listResults = results;
	}

	/// TEST
	public void setTopText(int nbrResults) {
		String test = "";
		if (nbrResults > 1) {
			test = "There are " + nbrResults + " responses corresponding to your request";
		} else if (nbrResults == 1) {
			test = "There is " + nbrResults + " response corresponding to your request";
		} else {
			test = "Sorry, there is no response for your request. Try to modify the parameters or take a look at the repaired queries ";
		}

		this.topText.setText(test);
	}

	public void setBottomTextAccordingToStatus(int result_status) {
		String text = "";
		switch (result_status) {

		case 0:
			text = "UNKNOWNSTATUS";
			break;
		case 5:
			text = "INITIALQUERYSUCCESS";
			break;
		case 6:
			text = "INVALIDQUERY";
			break;
		case 7:
			text = "SPSMFAILURE";
			break;
		case 8:
			text = "NOMATCHESFROMSPSM";
			break;
		case 9:
			text = "REPAIREDQUERYRUNERROR";
			break;
		case 10:
			text = "REPAIREDQUERYRESULTS";
			break;
		case 11:
			text = "REPAIREDQUERYNORESULTS";
			break;
		case 12:
			text = "DATAREPAIREDWITHRESULTS";
			break;
		default:
			text = "Invalid result_status returned by CHAIn";
			break;
		}

		this.bottomText.setText(text);
	}

}
