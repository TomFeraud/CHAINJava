package gui.main;

import java.io.IOException;

import chain_source.Run_CHAIn;
import gui.model.Project;
import gui.view.SendQueryMapping;
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
		this.run_CHAIn.setTestResults(null);
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
	        mainStage.setFullScreen(true);

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
		loader.setLocation(Main_GUI.class.getResource("/gui/view/SendQuery.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			mainContainair.setCenter(content); // Then add it to our main container


			SendQueryMapping controllor = loader.getController();
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
	
	




}
