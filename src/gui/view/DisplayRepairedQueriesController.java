package gui.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * * The controller of the DisplayRepairedQueries view
 * 
 * @author Tom Feraud
 *
 */
public class DisplayRepairedQueriesController {

	@FXML
	private ListView<String> listRepairedQueries;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TextArea results;

	@FXML
	private TableView<ObservableList<String>> resultsTable;
	
	protected int selectedIndex;

	// Reference to the main class
	private Main_GUI main;

	
	
	//TEST
	private ArrayList<ResultSet> resultsList = new ArrayList<ResultSet>();

	/**
	 * Default constructor
	 */
	public DisplayRepairedQueriesController() {
	}
	
	@FXML
	public void initialize() {
		System.out.println("jdapofjpofk");

	}
	

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
			resultsTable.getColumns().clear();// need to clear each time otherwise the new columns are added to the previous
			resultsTable.getColumns().add(column);
		}

		// return resultsTable;
		return resultsTable;
	}

	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = new TableView<ObservableList<String>>();
				resultsTable =createTableView(dataArray, columnsArray);
	}

	@FXML
	public void nextScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayMatches.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayMatchesController controllor = loader.getController();
			controllor.setMainApp(this.main);

			controllor.setInitialQuery(this.main.getProjectModel().getInitialQuery().get());
			// THIS WILL CHANGE
			controllor.setRepairedQuery(this.main.getRun_CHAIn().getRepairedQueriesList().get(0).getQuery());
			//////
			// THIS WILL CHANGE TOO
			ArrayList<String[]> matchComponents = this.main.getRun_CHAIn().getRepairedQueriesList().get(0)
					.getMatchComponents();
			/////////////////////////////
			String matchesStr = new String();
			String simScore = Double.toString(this.main.getRun_CHAIn().getRepairedQueriesList().get(0).getSimValue());
			//////////////////////////////////////
			matchesStr = "";
			matchesStr += "Similarity score: " + simScore + "\n\n";
			for (String[] m : matchComponents) {
				matchesStr += "(";
				matchesStr += m[0];
				matchesStr += ")";
				matchesStr += m[1];
				matchesStr += "(";
				matchesStr += m[2];
				matchesStr += ")";
				matchesStr += "\n";
			}
			controllor.setMatches(matchesStr);
			////////

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void back() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayResults.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayResultsController controller = loader.getController();
			controller.setMainApp(this.main);

			int nbrResponse = 0;
			nbrResponse = controller.resultsFormatting();
			controller.setTopText(nbrResponse);
			controller.setBottomTextAccordingToStatus(this.main.getResult_status());

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BACK !! :)");
		//System.out.println(this.main.getResultsList().get(0));

	}

	/**
	 * Pass the results to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setResults(String results) {
		this.results.setText(results);
	}

	/**
	 * Pass the query to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setInitialQuery(String q) {
		this.initialQuery.setText(q);
	}

	public ListView<String> getListRepairedQueries() {
		return listRepairedQueries;
	}

	public void setListRepairedQueries() {
		// From this example we can work out how a datasource org. member can select one
		// or
		// several queries to retun (by putting ".MULTIPLE")
		this.listRepairedQueries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		for (int i = 0; i < this.main.getRun_CHAIn().getRepairedQueriesList().size(); i++) {
			listRepairedQueries.getItems().add(this.main.getRun_CHAIn().getRepairedQueriesList().get(i).getQuery());
		}

		this.listRepairedQueries.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// change the label text value to the newly selected item
				// results.setText("You Selected " + newValue);
				selectedIndex = listRepairedQueries.getSelectionModel().getSelectedIndex();
				results.setText(Integer.toString(selectedIndex));
				//WORKING!
				
				System.out.println("INDEX: " + selectedIndex);
				System.out.println("Result for query at index " + selectedIndex + ": "
						+ ResultSetFormatter.toList(resultsList.get(selectedIndex)));
				
				setTableResults(selectedIndex);
				

			}
		});

	}

	public void setTableResults(int selectedIndex) {
		System.out.println("TEST SELECTED INDEX: " + selectedIndex);

		ResultSet copy = null;
		ResultSet copy2 = null;
		ResultSet copy3 = null;

		System.out.println(this.resultsList.get(0));
		copy = ResultSetFactory.copyResults(resultsList.get(selectedIndex));
		copy2 = ResultSetFactory.copyResults(resultsList.get(selectedIndex));
		copy3 = ResultSetFactory.copyResults(resultsList.get(selectedIndex));

		int nbrResponse = 0;


		System.out.println("INDEX: " + selectedIndex);
		System.out.println("Result for query at index " + selectedIndex + ": "
				+ ResultSetFormatter.toList(resultsList.get(selectedIndex)));

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

		String[][] resultsArray = new String[nbrResponse][cptColumn];
		String[] columnsArray = new String[cptColumn];

		// Columns name array
		for (int cptC = 0; cptC < cptColumn; cptC++) {
			columnsArray[cptC] = columnItName.next();
		}

		// results array
		for (int i = 0; i < nbrResponse; i++) {
			for (int cptC = 0; cptC < cptColumn; cptC++) {
				resultsArray[i][cptC] = fillCell(resultsArray, i, cptC, listOfResults, columnsArray);
			}
		}
		this.setResultsView(resultsArray, columnsArray);

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

	
	//TEST
	public ArrayList<ResultSet> getResultsList() {
		return resultsList;
	}

	public void setResultsList(ArrayList<ResultSet> resultsList) {
		this.resultsList = resultsList;
	}
	
	

}
