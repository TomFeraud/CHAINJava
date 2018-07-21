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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The controller of the DisplayResults view
 * 
 * @author Tom Feraud
 *
 */

public class DisplayResultsController {

	@FXML
	private TableView<ObservableList<String>> resultsTable;

	@FXML
	private Text topText;

	@FXML
	private Text bottomText;

	@FXML
	private Button repairedQueriesButton;

	@FXML
	private CheckBox checkBox;

	boolean checkBoxSelected;

	// These 2 variables are created here so we can access them from the checkbox
	// listener
	int nbrResponse;
	int cptColumn;

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

	/**
	 * Builds the data from the array of results in parameter
	 * 
	 * @param dataArray
	 * 
	 * @return data
	 */
	private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

		for (String[] row : dataArray) {
			data.add(FXCollections.observableArrayList(row));
		}

		return data;
	}

	/**
	 * Creates the table view by building the data and then creating the columns and
	 * filling them
	 * 
	 * @param dataArray
	 * @param columnsArray
	 * 
	 * @return resultsTable
	 */
	private TableView<ObservableList<String>> createTableView(String[][] dataArray, String[] columnsArray) {
		resultsTable.setItems(buildData(dataArray));
		resultsTable.getColumns().clear(); // clear the columns otherwise they add when check/uncheck url box
		for (int i = 0; i < dataArray[0].length; i++) {
			final int numColumn = i;
			final TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnsArray[i]);
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(numColumn)));
			resultsTable.getColumns().add(column);
		}

		return resultsTable;
	}

	/**
	 * Sets the TableView resultsTable by calling createTableView()
	 * 
	 * @param dataArray
	 * @param columnsArray
	 */
	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = createTableView(dataArray, columnsArray);
	}

	/**
	 * This is called when the next (repaired query) button is clicked It creates
	 * and initialize the DisplayRepairedQueriesController
	 * 
	 */
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
				BorderPane content = (BorderPane) loader.load(); // Gets the container wich contains the data
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

	/**
	 * This is called when the back button is clicked
	 * 
	 * It creates a SendQueryController and sets the Main object of the application
	 * to it
	 * 
	 */
	@FXML
	public void back() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/SendQuery.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			SendQueryController controller = loader.getController();
			controller.setMainApp(this.main);
			double[] pos = this.main.getSendQueryController().getSplitPane().getDividerPositions();
			controller.setQuery(this.main.getProjectModel().getInitialQuery().get());
			controller.setInitialized(true);
			controller.initialize();
			controller.getSplitPane().setDividerPositions(pos[0]);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the text at the top of the display It displays information about the
	 * number of results and the number of repaired queries
	 * 
	 * @param nbrResults
	 */
	public void setTopText(int nbrResults) {
		String text = "";
		int nbrRepairedQueries = this.main.getNbrRepairedQueries();
		System.out.println("SETTOPTEXT NBRREPQUERIES: " + nbrRepairedQueries);
		if (nbrRepairedQueries > 1) {
			if (nbrResults > 1) {
				text = nbrResults + " responses filtered according to your request";
				text += "\nThere are " + nbrRepairedQueries + " repaired queries ";
				text += "\nThe results according to the one with the highest similarity is displayed";
				System.out.println(text);
			} else if (nbrResults == 1) {
				text = nbrResults + " response filtered according to your request";
				text += "\nThere are " + nbrRepairedQueries + " repaired queries ";
				text += "\nThe results according to the one with the highest similarity is displayed";
			} else {
				text = "Sorry, there is no response for your request";
				text += "\nTry to look at the " + nbrRepairedQueries + " repaired queries generated ";
			}
		} else if (nbrRepairedQueries == 1) {
			if (nbrResults > 1) {
				text = nbrResults + " responses filtered according to your request";
				text += "\nThere is " + nbrRepairedQueries + " repaired query ";
				System.out.println(text);
			} else if (nbrResults == 1) {
				text = nbrResults + " response filtered according to your request";
				text += "\nThere is " + nbrRepairedQueries + " repaired query ";
			} else {
				text = "Sorry, there is no response for your request. Try to modify the parameters or take a look at the repaired queries ";
				text += "\nTry to look at the repaired query generated ";
			}
		} else {
			if (nbrResults > 1) {
				text = nbrResults + " responses filtered according to your request";
			} else if (nbrResults == 1) {
				text = nbrResults + " response filtered according to your request";
			} else {
				text = "Sorry, there is no response for your request. Try to modify the parameters or type again your query ";
			}
		}
		this.topText.setText(text);
	}

	/**
	 * 
	 * Sets the text at the bottom of the display It displays information about the
	 * process outcome (i.e. results status) By changing the boolean (at the
	 * beginning of the method) to true, the results status (e.g. "INVALIDQUERY"
	 * will be displayed)
	 * 
	 * @param result_status
	 */
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

	/**
	 * Sets the text inside the "repaired query" button
	 * 
	 */
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

	/**
	 * Formats the results for all cell of the table view and returns the number of
	 * results (row)
	 * 
	 * @return nbrResponse
	 */
	public int resultsFormatting() {

		ResultSet copy = null;
		ResultSet copy2 = null;
		ResultSet copy3 = null;

		nbrResponse = 0;

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
			for (int cpt = 0; cpt < nbrRepairedQueries; cpt++) {
				similarityTab[cpt] = this.main.getRun_CHAIn().getRepairedQueriesList().get(cpt).getSimValue();
			}
			double simMax = 0;
			int index = 666;
			// The variable index is equal to the index of the greater similarity score so
			// we can display the result according to its query
			for (int cpt = 0; cpt < similarityTab.length; cpt++) {
				// NEED TO WORK OUT HOW TO DO IF EQUALS
				if (simMax < similarityTab[cpt]) {
					simMax = similarityTab[cpt];
					index = cpt;
				}
			}

			// COPY THE RESULTS FOR THE REPAIRED QUERY WITH THE BEST MATCHING SCORE
			// if equal, take the first one (< and no <=)
			copy = ResultSetFactory.copyResults(resultsList.get(index));
			copy2 = ResultSetFactory.copyResults(resultsList.get(index));
			copy3 = ResultSetFactory.copyResults(resultsList.get(index));
		}

		// use to know the number of columns
		Iterator<String> columnIterator = ResultSetFormatter.toList(copy).get(0).varNames();
		// use to get the columns' name
		Iterator<String> columnItName = ResultSetFormatter.toList(copy2).get(0).varNames();

		cptColumn = 0;

		while (columnIterator.hasNext()) {
			System.out.println(columnIterator.next());
			cptColumn++;
		}

		List<QuerySolution> listOfResults = ResultSetFormatter.toList(copy3);
		nbrResponse = listOfResults.size();

		// resultsArray contains all the row (except the first one which is the column
		// name contained in columnsArray)
		String[][] resultsArray = new String[nbrResponse][cptColumn];
		String[] columnsArray = new String[cptColumn];

		// Columns name array
		for (int cptC = 0; cptC < cptColumn; cptC++) {
			columnsArray[cptC] = columnItName.next();
		}

		// results array
		for (int i = 0; i < nbrResponse; i++) {
			for (int cptC = 0; cptC < cptColumn; cptC++) {
				resultsArray[i][cptC] = cellValue(i, cptC, listOfResults, columnsArray);
			}
		}
		this.setResultsView(resultsArray, columnsArray);

		// Hide/show URL when checked
		checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				checkBoxSelected = newValue;
				for (int i = 0; i < nbrResponse; i++) {
					for (int cptC = 0; cptC < cptColumn; cptC++) {
						resultsArray[i][cptC] = cellValue(i, cptC, listOfResults, columnsArray);
					}
				}
				TableView<ObservableList<String>> resultsTable = createTableView(resultsArray, columnsArray);

			}

		});

		return nbrResponse;
	}

	/**
	 * Compute the value of the according cell and display or not the URL according
	 * to the checkbox
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
		s = listOfResults.get(cptRow).get(columnName).toString();
		if (checkBoxSelected) {
			s = formatStringResult(s);
		}

		return s;
	}

	/**
	 * Has the initial query run successfully
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean initialQuerySuccess(int result_status) {
		if (result_status == 5) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Has the repaired query ran with results
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean hasRepairedQueryResult(int result_status) {
		if (result_status == 10 || result_status == 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the resultsTable
	 * 
	 * @return resultsTable
	 */
	public TableView<ObservableList<String>> getResultsTable() {
		return resultsTable;
	}

	/**
	 * Format the string by cutting the URL to only show the "result" part
	 * 
	 * @param s
	 * 
	 * @return s
	 */
	private String formatStringResult(String s) {

		if (s.contains("^^")) { // for example 17702.8"^^<http://www.w3.org/2001/XMLSchema#double
			String[] test = s.split("[\\^^]"); // "\\" are escape characters
			s = test[0];
		} else if (s.contains("//")) { // For example http://dbpedia.org/resource/Cochrane_River
			String[] test = s.split("[\\/]"); // "\\" are escape characters
			int sizeSplit = test.length - 1;
			s = test[sizeSplit];
			// System.out.println("s:" + s);
		}
		return s;
	}

	/**
	 * Gets the check box
	 * 
	 * @return checkBox
	 */
	public CheckBox getCheckBox() {
		return checkBox;
	}

}