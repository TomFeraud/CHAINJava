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

	// Exit application
	@FXML
	public void exit() {
		// Display a polite message
		Alert bye = new Alert(AlertType.INFORMATION);
		bye.setHeaderText("Good bye!");
		bye.showAndWait();

		// Close the main stage (and so the application)
		this.main.getMainStage().close();
	}

	@FXML
	// Ref test8_0_1 in Run_CHAIn_Test_Cases
	// SEPA query, CHAIn generates 1 returned query with results
	//Return code 10: REPAIREDQUERYRESULTS
	public void example1() {
		this.main.contentInit();
		String example1 = "Example1";
		this.main.getSendQueryController().setExample(example1);
		this.main.getSendQueryController().initialize();
	}

	@FXML
	// Ref test8_1_4 in Run_CHAIn_Test_Cases
	// DBPEDIA query, CHAIn generates 2 returned query with results
	//Return code 10: REPAIREDQUERYRESULTS
	public void example2() {
		this.main.contentInit();
		String example2 = "Example2";
		this.main.getSendQueryController().setExample(example2);
		this.main.getSendQueryController().initialize();
	}

	@FXML
	// Ref test8_0_3 in Run_CHAIn_Test_Cases
	// SEPA query, no results from SPSM, 0 query created
	//Return code 8: NOMATCHESFROMSPSM
	public void example3() {
		this.main.contentInit();
		String example3 = "Example3";
		this.main.getSendQueryController().setExample(example3);
		this.main.getSendQueryController().initialize();
	}

	@FXML
	// Ref test8_1_2 in Run_CHAIn_Test_Cases
	// dbpedia query that  already runs successfully and does not require repair.
	//Return code 5: INITIALQUERYSUCCESS
	public void example4() {
		this.main.contentInit();
		String example4 = "Example4";
		this.main.getSendQueryController().setExample(example4);
		this.main.getSendQueryController().initialize();
	}
	
	@FXML
	// Ref test8_0_2 in Run_CHAIn_Test_Cases
	// invalid sepa query
	//Return code 6: INVALIDQUERY
	public void example5() {
		this.main.contentInit();
		String example5 = "Example5";
		this.main.getSendQueryController().setExample(example5);
		this.main.getSendQueryController().initialize();
	}
	
	@FXML
	// Ref test8_0_6 in Run_CHAIn_Test_Cases
	// Sepa query with non-matching data in it - requires data repair
	//Return code 12: DATAREPAIREDWITHRESULTS
	public void example6() {
		this.main.contentInit();
		String example6 = "Example6";
		this.main.getSendQueryController().setExample(example6);
		this.main.getSendQueryController().initialize();
	}
	
	
	@FXML
	// Ref test13 in Run_Query_Test_Cases
	// Query HAS run successfully but NO data has been returned.
	//Return code 11: REPAIREDQUERYNORESULTS
	public void example7() {
		this.main.contentInit();
		String example7 = "Example7";
		this.main.getSendQueryController().setExample(example7);
		this.main.getSendQueryController().initialize();
	}
	
	@FXML
	// Ref test12 in Run_Query_Test_Cases
	// Query has NOT run successfully
	//Return code 9: REPAIREDQUERYRUNERROR
	public void example8() {
		this.main.contentInit();
		String example8 = "Example8";
		this.main.getSendQueryController().setExample(example8);
		this.main.getSendQueryController().initialize();
	}

}
