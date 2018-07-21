package gui.view;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import gui.model.Matches;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * * The controller of the DisplayMatches view
 * 
 * @author Tom Feraud
 *
 */
public class DisplayMatchesController {

	@FXML
	private TextArea repairedQuery;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TreeTableView<Matches> treeTableView;

	@FXML
	private TreeTableColumn<Matches, String> initialColumn;

	@FXML
	private TreeTableColumn<Matches, String> relationColumn;

	@FXML
	private TreeTableColumn<Matches, String> repairedColumn;

	@FXML
	private Text simScore;

	// The index of the selected repaired query in the DisplayRepairedQuery view
	protected int selectedIndex;

	// Reference to the main class
	private Main_GUI main;

	/**
	 * Default constructor
	 */
	public DisplayMatchesController() {
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
	 * When the BACK button is clicked, this method is called. It loads the previous
	 * view and controller (DisplayRepairedQuery)
	 * 
	 */
	@FXML
	public void back() {
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

			// so when we go back the repaired query is selected
			controller.setTableResults(selectedIndex);
			controller.getListRepairedQueries().getSelectionModel().select(selectedIndex);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Format the tree table view (the container of the matches performs). It is
	 * made of 3 columns (initial, relation and repaired)
	 * 
	 * @param matchComponents
	 *            The number of components matched
	 * @param simScore
	 *            The value of the similarity score
	 */
	public void treeTableViewFormatting(ArrayList<String[]> matchComponents, String simScore) {
		String initialValues = "";
		String repairedValues = "";
		String semanticRelations = "";

		TreeItem<Matches> matchHead = new TreeItem<>(new Matches("Head", "Is", "Empty____FAILURE"));
		TreeItem<Matches> node;
		matchHead.setExpanded(true);

		int nbrMatchComponents = matchComponents.size();

		// so we can separate the different parameters and store them in an array
		for (String[] m : matchComponents) {
			initialValues += m[0] + ";";
			semanticRelations += m[1] + ";";
			repairedValues += m[2] + ";";
		}

		// Split then store in array
		String[] initialSchema = initialValues.split(";"); // after the narrowing down
		String[] relations = semanticRelations.split(";");
		String[] repairedSchema = repairedValues.split(";");

		// Store the values (without the head)
		String[] initialSchemaValues = new String[initialSchema.length];
		String[] repairedSchemaValues = new String[repairedSchema.length];

		String head = "";
		for (int i = 0; i < nbrMatchComponents; i++) {

			String[] initialSplit = initialSchema[i].split(",");
			String[] repairedSplit = repairedSchema[i].split(",");

			if (initialSplit.length > 1) {// length equal 0 when there is only the head
				initialSchemaValues[i] = initialSplit[1];
			}
			if (repairedSplit.length > 1) {// length equal 0 when there is only the head
				repairedSchemaValues[i] = repairedSplit[1];
			}

			// The above work only in the current case (one "child branch", need to optimize
			// in case of several)

			if (initialSchemaValues[i] != null && repairedSchemaValues[i] != null) {
				node = new TreeItem<>(
						new Matches(initialSchemaValues[i], relations[i], "       " + repairedSchemaValues[i]));
				matchHead.getChildren().add(node);
			} else {
				Matches test = new Matches(initialSplit[0], relations[i], repairedSplit[0]);
				matchHead.setValue(test);
			}
			head = initialSplit[0];
		}

		String[] fullInitialSchema = fullInitialSchema();
		if (fullInitialSchema.length > nbrMatchComponents) {
			ArrayList<String> missingMatch = new ArrayList<String>();
			missingMatch = findTermsWithoutMatch(fullInitialSchema, initialSchemaValues, head);
			for (String s : missingMatch) {
				node = new TreeItem<>(new Matches(s, "!", ""));
				matchHead.getChildren().add(node);
			}

		}

		initialColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getInitial());
		relationColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRelation());
		repairedColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRepaired());

		relationColumn.setCellFactory((TreeTableColumn<Matches, String> param) -> {
			TreeTableCell<Matches, String> cell = new TreeTableCell<Matches, String>() {

				@Override // by using Number we don't have to parse a String protected void
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					TreeTableRow<Matches> ttr = getTreeTableRow();
					if (item == null || empty) {
						setText(null);
						ttr.setStyle("");
						// setStyle("-fx-background-color:black");
					} else if (item.equalsIgnoreCase("<") || item.equalsIgnoreCase(">")) {
						setStyle("-fx-font-weight:bold;" + "-fx-text-fill:#FFB740;");

					} else if (item.equalsIgnoreCase("=")) {
						setStyle("-fx-font-weight:bold;" + "-fx-text-fill:#036319");

					} else {
						setStyle("-fx-font-weight:bold;" + "-fx-text-fill:red;");
					}
					setText(item);

				}
			};
			return cell;
		});

		this.simScore.setText("Similarity score: " + simScore);
		treeTableView.setRoot(matchHead);

	}

	/**
	 * Compute the full initial schema (head included) Used to compare the component
	 * matced with the initial so we know which ones did not matched
	 * 
	 * @return schemaSplit (the full initial schema)
	 */
	public String[] fullInitialSchema() {
		String schema = this.main.getInitialQuerySchema();
		int size = 0;
		String[] schemaSplit = schema.split("[,\\(\\)]"); // split ',' & '(' & ')'

		size = schemaSplit.length;
		System.out.println("Size of initial schema: " + size);

		return schemaSplit;
	}

	/**
	 * Compute the term in the full initial schema without match and store them in
	 * an array list
	 * 
	 * @param fullInitialSchema
	 * @param initialSchema
	 * @param head
	 * 
	 * @return terms (the list of terms without match)
	 */
	public ArrayList<String> findTermsWithoutMatch(String[] fullInitialSchema, String[] initialSchema, String head) {
		ArrayList<String> terms = new ArrayList<String>();

		ArrayList<String> listFullInitialSchema = new ArrayList<String>();
		ArrayList<String> listInitialSchema = new ArrayList<String>();

		for (int i = 0; i < initialSchema.length; i++) {
			listInitialSchema.add(initialSchema[i]);
		}
		listInitialSchema.add(head);
		for (int i = 0; i < fullInitialSchema.length; i++) {
			listFullInitialSchema.add(fullInitialSchema[i]);
		}
		System.out.println("LIST full: ");

		for (String s : listFullInitialSchema) {
			System.out.println(s);
		}
		System.out.println("LIST : ");
		for (String s : listInitialSchema) {
			System.out.println(s);
		}

		for (String s : listFullInitialSchema) {
			if (!listInitialSchema.contains(s)) {
				System.out.println(s + " has not been matched!");
				terms.add(s);
			}
		}

		return terms;

	}

	/**
	 * Pass the repaired query to the controller so the new scene can be initialized
	 * 
	 * @param results
	 */
	public void setRepairedQuery(String results) {
		this.repairedQuery.setText(results);
	}

	/**
	 * Pass the matches to the controller so the new scene can be initialized
	 * 
	 * @param q
	 */
	public void setInitialQuery(String q) {
		this.initialQuery.setText(q);
	}

	/**
	 * Gets the value of the selected index
	 * 
	 * @return selectedIndex
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

	/**
	 * Gets the repaired query
	 * 
	 * @return
	 */
	public TextArea getRepairedQuery() {
		return repairedQuery;
	}

	/**
	 * Sets the repaired query
	 * 
	 * @param repairedQuery
	 */
	public void setRepairedQuery(TextArea repairedQuery) {
		this.repairedQuery = repairedQuery;
	}

	/**
	 * Gets the tree table view
	 * 
	 * @return treeTableView
	 */
	public TreeTableView<Matches> getTreeTableView() {
		return treeTableView;
	}

	/**
	 * Sets the tree table view
	 * 
	 * @param treeTableView
	 */
	public void setTreeTableView(TreeTableView<Matches> treeTableView) {
		this.treeTableView = treeTableView;
	}

	/**
	 * Gets the initial column
	 * 
	 * @return initialColumn
	 */
	public TreeTableColumn<Matches, String> getInitialColumn() {
		return initialColumn;
	}

	/**
	 * Sets the initial column
	 * 
	 * @param initialColumn
	 */
	public void setInitialColumn(TreeTableColumn<Matches, String> initialColumn) {
		this.initialColumn = initialColumn;
	}

	/**
	 * Gets the relation column
	 * 
	 * @return relationColumn
	 */
	public TreeTableColumn<Matches, String> getRelationColumn() {
		return relationColumn;
	}

	/**
	 * Sets the relation column
	 * 
	 * @param relationColumn
	 */
	public void setRelationColumn(TreeTableColumn<Matches, String> relationColumn) {
		this.relationColumn = relationColumn;
	}

	/**
	 * Gets the repaired column
	 * 
	 * @return repairedColumn
	 */
	public TreeTableColumn<Matches, String> getRepairedColumn() {
		return repairedColumn;
	}

	/**
	 * Sets the repaired column
	 * 
	 * @param repairedColumn
	 */
	public void setRepairedColumn(TreeTableColumn<Matches, String> repairedColumn) {
		this.repairedColumn = repairedColumn;
	}

	/**
	 * Gets the similarity score
	 * 
	 * @return simScore
	 */
	public Text getSimScore() {
		return simScore;
	}

	/**
	 * Sets the similarity score
	 * 
	 * @param simScore
	 */
	public void setSimScore(Text simScore) {
		this.simScore = simScore;
	}

}
