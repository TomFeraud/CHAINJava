package gui.view;

import java.io.IOException;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisplayResultsController {

	@FXML
	private TableView<ObservableList<String>> resultsTable;

	@FXML
	private Text topText;

	@FXML
	private Text bottomText;

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

	private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		for (String[] row : dataArray) {
			data.add(FXCollections.observableArrayList(row));
		}

		return data;
	}

	private TableView<ObservableList<String>> createTableView(String[][] dataArray, String[] columnsArray) {
		resultsTable.setItems(buildData(dataArray));

		for (int i = 0; i < dataArray[0].length; i++) {
			final int numColumn = i;
			final TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnsArray[i]);
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(numColumn)));
			resultsTable.getColumns().add(column);
		}

		// return resultsTable;
		return resultsTable;
	}

	@FXML
	public void nextScene() {
		if (this.main.getResult_status() == 0 || this.main.getResult_status() == 5 || this.main.getResult_status() == 6
				|| this.main.getResult_status() == 7 || this.main.getResult_status() == 8) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("There is no repaired query");
			alert.showAndWait();
		} else {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayRepairedQueries.fxml"));
			try {
				AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
				this.main.getMainContainair().setCenter(content); // Then add it to our main container

				DisplayRepairedQueriesController controllor = loader.getController();
				controllor.setMainApp(this.main);

				controllor.setInitialQuery(this.main.getProjectModel().getInitialQuery().get());
				// If the result status is different from REPAIREDQUERYRUNERROR
				// Otherwise error when displaying repaired queries
				if (this.main.getResult_status() != 9) {
					controllor.setResults(
							ResultSetFormatter.asText(this.main.getRun_CHAIn().getResultsFromARepairedQuery()));
				}
				controllor.setListRepairedQueries();
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
				/*
				 * System.out.println("\n\n\nTEEEEEEST LIST results"); for (int i = 0; i <
				 * listResults.size(); i++) { System.out.println("i: " +
				 * this.listResults.get(i)); }
				 */
				// System.out.println("\nFin du testEEEEEEST\n");

			} catch (IOException e) {
				e.printStackTrace();
			}

			// TEST
			System.out.println("REPAIRED QUERIES:");
			System.out.println(this.main.getRun_CHAIn().getRepairedQueries());

			// System.out.println(this.main.getRun_CHAIn().getRepairedQueries().get(0).getQuery());

		}
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

			controllor.setQuery(this.main.getProjectModel().getInitialQuery().get());
			controllor.setInitialized(true);
			controllor.initialize();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BACK !! :)");
	}

	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = createTableView(dataArray, columnsArray);
	}

	public void setTopText(int nbrResults) {
		String test = "";
		if (nbrResults > 1) {
			test = nbrResults + " responses filtered according to your request";
		} else if (nbrResults == 1) {
			test = nbrResults + " response filtered corresponding to your request";
		} else {
			test = "Sorry, there is no response for your request. Try to modify the parameters or take a look at the repaired queries ";
		}

		this.topText.setText(test);
	}

	// Need to add more detail explications (keep the return status displayed for
	// testing)
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