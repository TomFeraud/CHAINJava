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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * The controller of the DisplayRepairedQueries view
 * 
 * @author Tom Feraud
 *
 */
public class DisplayRepairedQueriesController {

	@FXML
	private Text topText;

	@FXML
	private ListView<String> listRepairedQueries;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TableView<ObservableList<String>> resultsTable;

	private ArrayList<ResultSet> resultsList = new ArrayList<ResultSet>();

	protected int selectedIndex;

	// Reference to the main class
	private Main_GUI main;

	/**
	 * Default constructor
	 */
	public DisplayRepairedQueriesController() {
	}

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

		resultsTable.getColumns().clear();// need to clear each time otherwise the new columns are added to the previous

		for (int i = 0; i < dataArray[0].length; i++) {
			final int numColumn = i;
			final TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnsArray[i]);
			column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(numColumn)));
			resultsTable.getColumns().add(column);
		}

		// return resultsTable;
		return resultsTable;
	}

	/**
	 * Sets the TableView resultsTable by calling createTableView()
	 * 
	 * @param dataArray
	 * @param columnsArray
	 */
	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = new TableView<ObservableList<String>>();
		resultsTable = createTableView(dataArray, columnsArray);
	}

	/**
	 * This is called when the next (display matches) button is clicked It creates
	 * and initialize the DisplayMatchesController
	 * 
	 */
	@FXML
	public void nextScene() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayMatches.fxml"));
		try {
			BorderPane content = (BorderPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayMatchesController controller = loader.getController();
			controller.setMainApp(this.main);

			controller.setInitialQuery(this.main.getProjectModel().getInitialQuery().get());
			controller
					.setRepairedQuery(this.main.getRun_CHAIn().getRepairedQueriesList().get(selectedIndex).getQuery());

			ArrayList<String[]> matchComponents = this.main.getRun_CHAIn().getRepairedQueriesList().get(selectedIndex)
					.getMatchComponents();
			String simScore = Double
					.toString(this.main.getRun_CHAIn().getRepairedQueriesList().get(selectedIndex).getSimValue());
			controller.setSelectedIndex(selectedIndex);
			controller.treeTableViewFormatting(matchComponents, simScore);

		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This is called when the back button is clicked
	 * 
	 * It creates a DisplayResultsController and sets the Main object of the
	 * application to it
	 * 
	 */
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

			if (!hasNoResult(this.main.getResult_status())) {
				nbrResponse = controller.resultsFormatting();
			}
			controller.setTopText(nbrResponse);
			controller.setBottomTextAccordingToStatus(this.main.getResult_status());

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("BACK !! :)");
		// System.out.println(this.main.getResultsList().get(0));

	}

	/**
	 * Pass the query to the controller so the new scene can be initialized
	 * 
	 * @param q
	 */
	public void setInitialQuery(String q) {
		this.initialQuery.setText(q);
	}

	/**
	 * Gets the list of repaired queries
	 * 
	 * @return listRepairedQueries
	 */
	public ListView<String> getListRepairedQueries() {
		return listRepairedQueries;
	}

	/**
	 * Sets the the list of repaired queries and add a listener to it so the index
	 * of the repaired query selected is computed in real time
	 * 
	 */
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
				selectedIndex = listRepairedQueries.getSelectionModel().getSelectedIndex();
				setTableResults(selectedIndex);
			}
		});

	}

	/**
	 * Sets the table of results according to the selected repaired query
	 * 
	 * @param selectedIndex
	 */
	public void setTableResults(int selectedIndex) {

		if (selectedIndex == -1) { // to initialize the display
			resultsTable.setPlaceholder(new Label("Please select a repaired query to display the results"));
		} else {

			if (!hasNoResult(this.main.getResult_status())) {

				ResultSet copy = null;
				ResultSet copy2 = null;
				ResultSet copy3 = null;

				// System.out.println(this.resultsList.get(0));
				copy = ResultSetFactory.copyResults(resultsList.get(selectedIndex));
				copy2 = ResultSetFactory.copyResults(resultsList.get(selectedIndex));
				copy3 = ResultSetFactory.copyResults(resultsList.get(selectedIndex));

				int nbrResponse = 0;
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
						resultsArray[i][cptC] = cellValue(i, cptC, listOfResults, columnsArray);
					}
				}
				this.setResultsView(resultsArray, columnsArray);
			} else {// No results
				resultsTable.setPlaceholder(new Label("There is no result according to this repaired query"));

			}
		}
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
	 * If the request has no results
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean hasNoResult(int result_status) {
		if (result_status == 6 || result_status == 7 || result_status == 8 || result_status == 9
				|| result_status == 11) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets the text at the top of the interface It contains information about the
	 * number of repaired queries in the list
	 * 
	 */
	public void setTopText() {
		int nbrRepairedQueries = this.main.getNbrRepairedQueries();

		String text = "";
		if (nbrRepairedQueries == 1) {
			text = "The list of repaired queries contains " + nbrRepairedQueries + " item";
			text += "\nSelect it to display its results and matches";

		} else {
			text = "The list of repaired queries contains " + nbrRepairedQueries + " items";
			text += "\nSelect one to display its results and matches";
		}

		this.topText.setText(text);
	}

	/**
	 * Gets the value of the selected index
	 * 
	 * @return
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * Sets the value of the selected index
	 * 
	 * @param selectedIndex
	 */
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

}
