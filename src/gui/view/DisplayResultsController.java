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
				// If the result status is different from REPAIREDQUERYRUNERROR
				// Otherwise error when displaying repaired queries
				if (this.main.getResult_status() != 9) {
					// controller.setResults(
					// ResultSetFormatter.asText(this.main.getRun_CHAIn().getResultsFromARepairedQuery()));
				}
				
				controller.setResultsList(this.main.getResultsList());
				System.out.println("Main 0.... : " +this.main.getResultsList().get(0));


				// TEST
				// controller.TESTSetResults();
				controller.setListRepairedQueries();

			} catch (IOException e) {
				e.printStackTrace();
			}

			// TEST
			System.out.println("REPAIRED QUERIES:");
			System.out.println(this.main.getRun_CHAIn().getRepairedQueriesList());

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
		// System.out.println("BACK !! :)");
	}

	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = createTableView(dataArray, columnsArray);
	}

	public void setTopText(int nbrResults) {
		String test = "";
		if (nbrResults > 1) {
			test = nbrResults + " responses filtered according to your request";
		} else if (nbrResults == 1) {
			test = nbrResults + " response filtered according to your request";
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

	public int resultsFormatting() {

		ResultSet copy = null;
		ResultSet copy2 = null;
		ResultSet copy3 = null;
		int nbrResponse = 0;

		if (initialQuerySuccess(this.main.getResult_status())) {
			copy = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromInitialQuery());
			copy2 = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromInitialQuery());
			copy3 = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromInitialQuery());
		}

		else if (hasRepairedQueryResult(this.main.getResult_status())) {
			/*
			copy = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromARepairedQuery());
			copy2 = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromARepairedQuery());
			copy3 = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getResultsFromARepairedQuery());
		*/
		/////////////////////////////
		
			ArrayList<ResultSet> resultsList = this.main.getRun_CHAIn().getListResultsFromRepairedQuery();
			
			this.main.setResultsList(resultsList);
			
			//this.main.getRun_CHAIn().setListResultsFromRepairedQuery(resultsList);
			
			
			
			System.out.println("WTFFFFFFFFFFFf : " +this.main.getRun_CHAIn().getListResultsFromRepairedQuery().get(0));
			System.out.println("TEEEEEEEEST RESULTS LIST:");
			int nbrRepairedQueries = resultsList.size();
			double[] similarityTab = new double[nbrRepairedQueries];
			System.out.println("nbrRepaired queries: " +nbrRepairedQueries );
			for(int cpt = 0 ; cpt<nbrRepairedQueries; cpt++) {
				//System.out.println(ResultSetFormatter.toList(resultsList.get(cpt)));
				
				System.out.println("Sim value: " +this.main.getRun_CHAIn().getRepairedQueriesList().get(cpt).getSimValue());
				similarityTab[cpt] = this.main.getRun_CHAIn().getRepairedQueriesList().get(cpt).getSimValue();
			}
			double simMax = 0;
			int index = 666;
			for(int cpt = 0; cpt < similarityTab.length ; cpt ++) {
				//NEED TO WORK OUT HOW TO DO IF EQUALS LIKE IN EXAMPLE2...
				if(simMax<similarityTab[cpt]) {
					simMax = similarityTab[cpt];
					index = cpt;
				}
			}
			
			//COPY THE RESULTS FOR THE REPAIRED QUERY WITH THE BEST MATCHING SCORE
			//if equal, take the first one (< and no <=)
			//To optimize!
			copy = ResultSetFactory.copyResults(resultsList.get(index));
			copy2 = ResultSetFactory.copyResults(resultsList.get(index));
			copy3 = ResultSetFactory.copyResults(resultsList.get(index));
			
			//WORKING! implement it!
			System.out.println("INDEX: " + index);
			System.out.println("Result for query at index " + index +": " +ResultSetFormatter.toList(resultsList.get(index)));
			
			///////////////////////
		
		
		
		
		
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
				resultsArray[i][cptC] = fillCell(resultsArray, i, cptC, listOfResults, columnsArray);
			}
		}
		this.setResultsView(resultsArray, columnsArray);

		
		return nbrResponse;
	}

	private String fillCell(String[][] array, int cptRow, int cptCol, List<QuerySolution> testJena,
			String[] columnsArray) {
		String s = "";
		String columnName = columnsArray[cptCol];
		// System.out.println("Column name: " + columnName);
		// System.out.println(testJena.get(cptRow).get(columnName).toString());
		s = testJena.get(cptRow).get(columnName).toString();
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

}