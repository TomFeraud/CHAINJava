package gui.main;

import java.io.IOException;

import chain_source.Run_CHAIn;
import gui.model.TestOne;
import gui.view.TestQueryMapping;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The main class of the CHAIn GUI. Responsible for launching the graphical
 * interface and calling CHAIn methods
 * 
 * A JavaFX application contains one or more stages corresponding to windows
 * which have a scene attached to it.
 *
 * @author Tom Feraud
 * 
 * @version 0.1
 */
public class TESTOneMain_GUI extends Application {

	// Class variable to access them from everywhere
	private Stage mainStage;
	private BorderPane mainContainair;
	private int result;

	private Run_CHAIn run_CHAIn;

	private ObservableList<TestOne> listQueries = FXCollections.observableArrayList();

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Constructor of our main class. STILL IN TESTING PHASE
	 *
	 */
	public TESTOneMain_GUI() {

		// Run_CHAIn run_CHAIn = new Run_CHAIn();
		run_CHAIn = new Run_CHAIn();

		String query = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
				+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
				+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
				+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
				+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressurestest.n3>\n"
				+ "WHERE { ?id sepaw:dataSource ?dataSource;\n" + "sepaw:identifiedDate  ?identifiedDate  ;\n"
				+ "sepaw:affectsGroundwater ?affectsGroundwater ;\n" + "sepaw:waterBodyId ?waterBodyId .}" + "\n\n";

		String target = "places(dataSource, identifiedDate, affectsGroundwater, waterBodyId) ; waterBodyPressures(dataSource, identifiedDate, affectsGroundwater, waterBodyId)";

		listQueries.add(new TestOne(query, target));

		// this.result = run_CHAIn.runCHAIn(query, queryType, target, dataDir,
		// ontologyPath, 10, simThresholdVal, 5, null);
		// run_CHAIn.runCHAIn(query, queryType, target, dataDir, ontologyPath, 10,
		// simThresholdVal, 5, null);
		// this.result = run_CHAIn.getReturnStatus();
	}

	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		mainStage.setTitle("Test CHAIn GUI");

		// FXML files are used in these methodes
		mainContainerInit();
		contentInit();
	}

	/**
	 * Initializes the main container (containing the scene) of our JavaFX
	 * application's main stage by loading the file MainContainer.fxml
	 * 
	 */
	private void mainContainerInit() {
		FXMLLoader loader = new FXMLLoader(); // creates a loader of FXML
		// sets the FXML file to load location
		loader.setLocation(TESTOneMain_GUI.class.getResource("/gui/view/MainContainer.fxml"));
		try {
			mainContainair = (BorderPane) loader.load(); // The loading returns the container
			Scene scene = new Scene(mainContainair); // Defines a main scene for the container
			mainStage.setScene(scene); // That is affected to the main stage
			mainStage.show(); // To display it
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the contents of our main container
	 * 
	 */
	private void contentInit() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TESTOneMain_GUI.class.getResource("/gui/view/test.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			mainContainair.setCenter(content); // Then add it to our main container

			TestQueryMapping controllor = loader.getController();
			controllor.setMainApp(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the main stage of our application
	 * 
	 * @return mainStage
	 */
	public Stage getMainStage() {
		return mainStage;
	}

	/**
	 * Sets the main stage of our application
	 * 
	 * @param mainStage
	 */
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	/**
	 * Gets the main container of our application
	 * 
	 * @return mainContainair
	 */
	public BorderPane getMainContainair() {
		return mainContainair;
	}

	/**
	 * Sets the main container of our application
	 * 
	 * @param mainContainair
	 */
	public void setMainContainair(BorderPane mainContainair) {
		this.mainContainair = mainContainair;
	}

	/**
	 * IN TEST
	 * 
	 */
	public ObservableList<TestOne> getListQueries() {
		return listQueries;
	}

	/**
	 * IN TEST
	 * 
	 */
	public void setListQueries(ObservableList<TestOne> listQueries) {
		this.listQueries = listQueries;
	}

	/**
	 * Returns the first query of our list of queries IN TEST
	 * 
	 * @return the first query as a String
	 */
	public String getFirstQuery() {
		return this.getListQueries().get(0).getQuery().get();
	}

	/**
	 * 
	 * IN TEST
	 * 
	 * @return first target as a String
	 */
	public String getTargets() {
		return this.getListQueries().get(0).getTarget().get();
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * IN TEST
	 * 
	 */
	public void testRun() {

		String queryType = "sepa";
		double simThresholdVal = 0.3;
		String dataDir = "queryData/sepa/sepa_datafiles/";
		String ontologyPath = "queryData/sepa/sepa_ontology.json";

		String query = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
				+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
				+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
				+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
				+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressurestest.n3>\n"
				+ "WHERE { ?id sepaw:dataSource ?dataSource;\n" + "sepaw:identifiedDate  ?identifiedDate  ;\n"
				+ "sepaw:affectsGroundwater ?affectsGroundwater ;\n" + "sepaw:waterBodyId ?waterBodyId .}" + "\n\n";

		String target = "places(dataSource, identifiedDate, affectsGroundwater, waterBodyId) ; waterBodyPressures(dataSource, identifiedDate, affectsGroundwater, waterBodyId)";

		run_CHAIn.runCHAIn(query, queryType, target, dataDir, ontologyPath, 10, simThresholdVal, 5, null);
		this.result = run_CHAIn.getReturnStatus();
	}

}
