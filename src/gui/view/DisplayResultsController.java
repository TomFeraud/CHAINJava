package gui.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

import chain_source.Match_Struc;
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

	// TEST
	@FXML
	private Button repairedQueriesButton;

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

				DisplayRepairedQueriesController controller = loader.getController();
				controller.setMainApp(this.main);
				controller.setInitialQuery(this.main.getProjectModel().getInitialQuery().get());
				controller.setResultsList(this.main.getResultsList());
				controller.setListRepairedQueries();
				controller.setTopText();
				controller.setTableResults(-1); // -1 to initialize the display

			} catch (IOException e) {
				e.printStackTrace();
			}

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
		// System.out.println("BACK !! :)");
	}

	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = createTableView(dataArray, columnsArray);
	}

	public void setTopText(int nbrResults) {
		String test = "";
		int nbrRepairedQueries = this.main.getNbrRepairedQueries();
		System.out.println("SETTOPTEXT NBRREPQUERIES: " + nbrRepairedQueries);
		if (nbrRepairedQueries > 1) {
			if (nbrResults > 1) {
				test = nbrResults + " responses filtered according to your request";
				test += "\nThere are " + nbrRepairedQueries + " repaired queries ";
				test += "\nThe results according to the one with the highest similarity is displayed";
				System.out.println(test);
			} else if (nbrResults == 1) {
				test = nbrResults + " response filtered according to your request";
				test += "\nThere are " + nbrRepairedQueries + " repaired queries ";
				test += "\nThe results according to the one with the highest similarity is displayed";
			} else {
				test = "Sorry, there is no response for your request";
				test += "\nTry to look at the " + nbrRepairedQueries + " repaired queries generated ";
			}
		} else if (nbrRepairedQueries == 1) {
			if (nbrResults > 1) {
				test = nbrResults + " responses filtered according to your request";
				test += "\nThere is " + nbrRepairedQueries + " repaired query ";
				System.out.println(test);
			} else if (nbrResults == 1) {
				test = nbrResults + " response filtered according to your request";
				test += "\nThere is " + nbrRepairedQueries + " repaired query ";
			} else {
				test = "Sorry, there is no response for your request. Try to modify the parameters or take a look at the repaired queries ";
				test += "\nTry to look at the repaired query generated ";
			}
		} else {
			if (nbrResults > 1) {
				test = nbrResults + " responses filtered according to your request";
			} else if (nbrResults == 1) {
				test = nbrResults + " response filtered according to your request";
			} else {
				test = "Sorry, there is no response for your request. Try to modify the parameters or type again your query ";
			}
		}
		this.topText.setText(test);
	}

	// Need to add more detail explications (keep the return status displayed for
	// testing)
	public void setBottomTextAccordingToStatus(int result_status) {
		boolean TEST = false; // if true, display the result status
		String text = "";
		int nbrRepairedQueries = this.main.getNbrRepairedQueries();

		switch (result_status) {

		case 0:
			text = "UNKNOWNSTATUS";
			break;
		case 5:
			text = "The query has run successfully";
			if (TEST) {
				text += "\nResult status: INITIALQUERYSUCCESS";
			}
			break;
		case 6:
			text = "You entered an invalid SPARQL query";
			if (TEST) {
				text += "\nResult status: INVALIDQUERY";
			}
			break;
		case 7:
			text = "SPSM failure (this should not happen)";
			if (TEST) {
				text += "\nResult status: SPSMFAILURE";
			}
			break;
		case 8:// 0 matches returned from SPSM
			text = "The query has not run successfully and no matches were found ";
			if (TEST) {
				text += "\nResult status: NOMATCHESFROMSPSM";
			}
			break;
		case 9:
			nbrRepairedQueries = 1; // otherwise don't work..
			if (nbrRepairedQueries == 1) {
				text = "The repaired query has not run successfully Click on Repaired Query for more information";
			} else if (nbrRepairedQueries > 1) {
				text = "The repaired queries has not run successfully Click on Repaired Queries for more information";

			}
			if (TEST) {
				text += "\nResult status: REPAIREDQUERYRUNERROR";
			}
			break;
		case 10:
			if (nbrRepairedQueries == 1) {
				text = "The query needed to be repaired in order to provide results Click on Repaired Query for more information";
			} else if (nbrRepairedQueries > 1) {
				text = "The query needed to be repaired in order to provide results Click on Repaired Queries for more information";

			}
			if (TEST) {
				text += "\nResult status: REPAIREDQUERYRESULTS";
			}
			break;
		case 11:
			text = "There was an attempt to run the repaired query but no results were found";
			if (TEST) {
				text += "\nResult status: REPAIREDQUERYNORESULTS";
			}
			break;
		case 12:
			text = "";
			if (nbrRepairedQueries == 1) {
				text = "The query needed to be repaired in order to provide results Click on Repaired Query for more information";
			} else if (nbrRepairedQueries > 1) {
				text = "The query needed to be repaired in order to provide results Click on Repaired Queries for more information";

			}
			if (TEST) {
				text += "\nResult status: DATAREPAIREDWITHRESULTS";
			}
			break;
		default:
			text = "Invalid result_status returned by CHAIn";
			break;
		}

		this.bottomText.setText(text);
	}

	public void setTextButtonRepairedQueries() {
		int nbrRepairedQueries = this.main.getNbrRepairedQueries();
		// Otherwise don't work...
		if (this.main.getResult_status() == 9) {
			nbrRepairedQueries = 1;
		}

		if (nbrRepairedQueries == 0) {
			this.repairedQueriesButton.setText("No Repaired Query");
			this.repairedQueriesButton.setVisible(false);
		} else if (nbrRepairedQueries == 1) {
			this.repairedQueriesButton.setText("Repaired Query");
		} else if (nbrRepairedQueries > 1) {
			this.repairedQueriesButton.setText("Repaired Queries");
		} else {// FAILURE
			this.repairedQueriesButton.setText("XXXX");
		}

	}

	public int resultsFormatting() {

		ResultSet copy = null;
		ResultSet copy2 = null;
		ResultSet copy3 = null;
		int nbrResponse = 0;

		int result_status = this.main.getResult_status();

		// If the query when successfully the first time (no need to handle several
		// queries)
		if (initialQuerySuccess(result_status)) {
			copy = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromInitialQuery());
			copy2 = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromInitialQuery());
			copy3 = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromInitialQuery());
		}

		// If the repaired query(ies) return results
		else if (hasRepairedQueryResult(result_status)) {

			// Store the list of results from the repaired queries
			ArrayList<ResultSet> resultsList = this.main.getRun_CHAIn().getListResultsFromRepairedQuery();
			// Assign it to our Main class to use it elsewhere
			this.main.setResultsList(resultsList);

			int nbrRepairedQueries = resultsList.size();
			// this.main.setNbrRepairedQueries(nbrRepairedQueries);
			// Array of double which will contain the similarity score of the repaired
			// queries
			double[] similarityTab = new double[nbrRepairedQueries];
			System.out.println("nbrRepaired queries: " + nbrRepairedQueries);
			for (int cpt = 0; cpt < nbrRepairedQueries; cpt++) {
				// System.out.println(ResultSetFormatter.toList(resultsList.get(cpt)));

				System.out.println(
						"Sim value: " + this.main.getRun_CHAIn().getRepairedQueriesList().get(cpt).getSimValue());
				similarityTab[cpt] = this.main.getRun_CHAIn().getRepairedQueriesList().get(cpt).getSimValue();
			}
			double simMax = 0;
			int index = 666;
			// The variable index is equal to the index of the greater similarity score so
			// we can display the result according to its query
			for (int cpt = 0; cpt < similarityTab.length; cpt++) {
				// NEED TO WORK OUT HOW TO DO IF EQUALS LIKE IN EXAMPLE2...
				if (simMax < similarityTab[cpt]) {
					simMax = similarityTab[cpt];
					index = cpt;
				}
			}

			// COPY THE RESULTS FOR THE REPAIRED QUERY WITH THE BEST MATCHING SCORE
			// if equal, take the first one (< and no <=)
			// To optimize!
			copy = ResultSetFactory.copyResults(resultsList.get(index));
			copy2 = ResultSetFactory.copyResults(resultsList.get(index));
			copy3 = ResultSetFactory.copyResults(resultsList.get(index));

			System.out.println("INDEX: " + index);
			System.out.println(
					"Result for query at index " + index + ": " + ResultSetFormatter.toList(resultsList.get(index)));

		}

		// use to know the number of columns
		Iterator<String> columnIterator = ResultSetFormatter.toList(copy).get(0).varNames();
		// use to get the columns' name
		Iterator<String> columnItName = ResultSetFormatter.toList(copy2).get(0).varNames();

		int cptColumn = 0;

		while (columnIterator.hasNext()) {
			System.out.println(columnIterator.next());
			cptColumn++;
		}

		List<QuerySolution> listOfResults = ResultSetFormatter.toList(copy3);
		nbrResponse = listOfResults.size();
		/*
		 * System.out.println("TOM:\n" + listOfResults);
		 * System.out.println("TOM cptColumn:\n" + cptColumn);
		 * System.out.println("TOM lis size:\n" + listOfResults.size());
		 * System.out.println("TOM nb respnse:\n" + nbrResponse);
		 */

		// resultsArray contains all the row (except the first one which is the column
		// name contained in columnsArray)
		String[][] resultsArray = new String[nbrResponse][cptColumn];
		String[] columnsArray = new String[cptColumn];

		// Columns name array
		for (int cptC = 0; cptC < cptColumn; cptC++) {
			columnsArray[cptC] = columnItName.next();
		}

		/*
		 * System.out.println("Column array"); for (int j = 0; j < columnsArray.length;
		 * j++) { System.out.println(columnsArray[j]); }
		 */

		// results array
		for (int i = 0; i < nbrResponse; i++) {
			for (int cptC = 0; cptC < cptColumn; cptC++) {
				resultsArray[i][cptC] = cellValue(i, cptC, listOfResults, columnsArray);
			}
		}
		this.setResultsView(resultsArray, columnsArray);

		return nbrResponse;
	}

	/**
	 * Compute the value of the according cell
	 * 
	 * @param cptRow
	 *            The number of rows in the array
	 * @param cptCol
	 *            The number of columns in the array
	 * @param testJena
	 *            The list of results
	 * 
	 * @param columnsArray
	 *            The array containing the colums name
	 * 
	 * @return s
	 */
	private String cellValue(int cptRow, int cptCol, List<QuerySolution> listOfResults, String[] columnsArray) {
		String s = "";
		String columnName = columnsArray[cptCol];
		// System.out.println("Column name: " + columnName);
		// System.out.println(testJena.get(cptRow).get(columnName).toString());
		s = listOfResults.get(cptRow).get(columnName).toString();
		return s;
	}

	public boolean initialQuerySuccess(int result_status) {
		if (result_status == 5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasRepairedQueryResult(int result_status) {
		if (result_status == 10 || result_status == 12) {
			return true;
		} else {
			return false;
		}
	}

	public TableView<ObservableList<String>> getResultsTable() {
		return resultsTable;
	}

	public void setResultsTable(TableView<ObservableList<String>> resultsTable) {
		this.resultsTable = resultsTable;
	}

}