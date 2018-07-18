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
 * @version 0.1
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
	
	//Test
	//contains the predicate + all values
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

			// SendQueryController controllor = loader.getController();
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

	public Run_CHAIn getRun_CHAIn() {
		return run_CHAIn;
	}

	public void setRun_CHAIn(Run_CHAIn run_CHAIn) {
		this.run_CHAIn = run_CHAIn;
	}

	public Project getProjectModel() {
		return projectModel;
	}

	public void setProjectModel(Project projectModel) {
		this.projectModel = projectModel;
	}

	public SendQueryController getSendQueryController() {
		return sendQueryController;
	}

	public void setSendQueryController(SendQueryController sendQueryController) {
		this.sendQueryController = sendQueryController;
	}

	public int getResult_status() {
		return result_status;
	}

	public void setResult_status(int result_status) {
		this.result_status = result_status;
	}

	public ArrayList<ResultSet> getResultsList() {
		return resultsList;
	}

	public void setResultsList(ArrayList<ResultSet> resultsList) {
		this.resultsList = resultsList;
	}

	public int getNbrRepairedQueries() {
		return nbrRepairedQueries;
	}

	public void setNbrRepairedQueries(int nbrRepairedQueries) {
		this.nbrRepairedQueries = nbrRepairedQueries;
	}

	//TEST
	public String getInitialQuerySchema() {
		return initialQuerySchema;
	}

	public void setInitialQuerySchema(String initialQuerySchema) {
		this.initialQuerySchema = initialQuerySchema;
	}
	
	
	
	
}
