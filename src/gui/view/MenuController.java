package gui.view;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MenuController {
	// Reference to the main class
	private Main_GUI main;

	/**
	 * Use to link the controllor with the main class
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Main_GUI mainApp) {
		this.main = mainApp;
	}

	@FXML
	public void home() {
		this.main.contentInit();
	}

	// Fermer l'application
	@FXML
	public void exit() {
		// On affiche un message car on est poli.
		Alert bye = new Alert(AlertType.INFORMATION);
		bye.setHeaderText("Good bye!");
		bye.showAndWait();

		// Et on clos le stage principal, donc l'application
		this.main.getMainStage().close();
	}

	@FXML
	//SEPA query, CHAIn generates 1 returned query with results
	public void example1() {
		String example1 = "Example1";
		this.main.getSendQueryController().setExample(example1);
		this.main.getSendQueryController().initialize();
	}

	@FXML
	//DBPEDIA query, CHAIn generates 2 returned query with results
	public void example2() {
		String example2 = "Example2";
		this.main.getSendQueryController().setExample(example2);
		this.main.getSendQueryController().initialize();
	}
	
	@FXML
	//Ref test8_0_3 in Run_CHAIn_Test_Cases
	//SEPA query that returns no results, 0 query created
	public void example3() {
		String example3 = "Example3";
		this.main.getSendQueryController().setExample(example3);
		this.main.getSendQueryController().initialize();
	}

}
