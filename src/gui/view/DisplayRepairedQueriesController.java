package gui.view;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSet;
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

	// Reference to the main class
	private Main_GUI main;

	protected int selectedIndex;

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
	
	public void setResultsView(String[][] dataArray, String[] columnsArray) {
		TableView<ObservableList<String>> resultsTable = createTableView(dataArray, columnsArray);
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
				
				
				
				DisplayRepairedQueriesController test = new DisplayRepairedQueriesController();
				test.TESTSetResults(selectedIndex);
			}
		});
		

	}

	public void TESTSetResults(int selectedIndex ) {
		System.out.println("TEST SELECTED INDEX: " +selectedIndex);
	}

}
