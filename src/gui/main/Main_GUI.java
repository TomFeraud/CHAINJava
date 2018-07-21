package gui.main;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSet;

import chain_source.Run_CHAIn;
import gui.model.Project;
import gui.view.MenuController;
import gui.view.SendQueryController;
import javafx.application.Application;
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
 * @version 1.0
 */
public class Main_GUI extends Application {

	// Class variable to access them from everywhere
	private Stage mainStage;
	private BorderPane mainContainair;
	private Run_CHAIn run_CHAIn;
	private Project projectModel;
	private int result_status;
	private int nbrRepairedQueries;
	private ArrayList<ResultSet> resultsList;
	// contains the predicate + all values
	private String initialQuerySchema;
	// To access it from other controllers
	private SendQueryController sendQueryController;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Constructor of our main class. STILL IN TESTING PHASE
	 *
	 */
	public Main_GUI() {

		run_CHAIn = new Run_CHAIn();
		projectModel = new Project();
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
		loader.setLocation(Main_GUI.class.getResource("/gui/view/Menu.fxml"));
		try {
			mainContainair = (BorderPane) loader.load(); // The loading returns the container
			Scene scene = new Scene(mainContainair); // Defines a main scene for the container
			mainStage.setScene(scene); // That is affected to the main stage

			MenuController controller = loader.getController();
			controller.setMainApp(this);

			mainStage.show(); // To display it
			mainStage.setFullScreen(true); // In full screen by default

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the contents of our main container
	 */
	public void contentInit() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/SendQuery.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			mainContainair.setCenter(content); // Then add it to our main container
			sendQueryController = loader.getController();
			sendQueryController.setMainApp(this);

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
	 * Gets the Run_CHAIn object of our application
	 * 
	 * @return run_CHAIn
	 */
	public Run_CHAIn getRun_CHAIn() {
		return run_CHAIn;
	}

	/**
	 * Sets the Run_CHAIn object of our application
	 * 
	 * @param run_CHAIn
	 */
	public void setRun_CHAIn(Run_CHAIn run_CHAIn) {
		this.run_CHAIn = run_CHAIn;
	}

	/**
	 * Gets the Project object of our application
	 * 
	 * @return projectModel
	 */
	public Project getProjectModel() {
		return projectModel;
	}

	/**
	 * Sets the Project object of our application
	 * 
	 * @param projectModel
	 */
	public void setProjectModel(Project projectModel) {
		this.projectModel = projectModel;
	}

	/**
	 * Gets the SendQueryController container of our application
	 * 
	 * @return sendQueryController
	 */
	public SendQueryController getSendQueryController() {
		return sendQueryController;
	}

	/**
	 * Sets the SendQueryController container of our application
	 * 
	 * @param sendQueryController
	 */
	public void setSendQueryController(SendQueryController sendQueryController) {
		this.sendQueryController = sendQueryController;
	}

	/**
	 * Gets the result status linked to our project returned by CHAIn
	 * 
	 * @return result_status
	 */
	public int getResult_status() {
		return result_status;
	}

	/**
	 * Sets the result status linked to our project returned by CHAIn
	 * 
	 * @param result_status
	 */
	public void setResult_status(int result_status) {
		this.result_status = result_status;
	}

	/**
	 * Gets the list of results
	 * 
	 * @return resultsList
	 */
	public ArrayList<ResultSet> getResultsList() {
		return resultsList;
	}

	/**
	 * Sets the list of results
	 * 
	 * @param resultsList
	 */
	public void setResultsList(ArrayList<ResultSet> resultsList) {
		this.resultsList = resultsList;
	}

	/**
	 * Gets the number of repaired queries
	 * 
	 * @return nbrRepairedQueries
	 */
	public int getNbrRepairedQueries() {
		return nbrRepairedQueries;
	}

	/**
	 * Sets the number of repaired queries
	 * 
	 * @param nbrRepairedQueries
	 */
	public void setNbrRepairedQueries(int nbrRepairedQueries) {
		this.nbrRepairedQueries = nbrRepairedQueries;
	}

	/**
	 * Gets the initial schema of the initial query
	 * 
	 * @return initialQuerySchema
	 */
	public String getInitialQuerySchema() {
		return initialQuerySchema;
	}

	/**
	 * Sets the initial schema of the initial query
	 * 
	 * @param initialQuerySchema
	 */
	public void setInitialQuerySchema(String initialQuerySchema) {
		this.initialQuerySchema = initialQuerySchema;
	}

}
