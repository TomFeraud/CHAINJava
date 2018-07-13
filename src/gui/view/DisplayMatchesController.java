package gui.view;

import java.io.IOException;
import java.util.ArrayList;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import gui.model.Matches;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

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
		System.out.println("BACK !! :)");
	}

	////////
	// These methods below may need to change/disappear ('cause at this step we
	// suppose to have every information required /!\ expect which query from the
	// list is selected)
	///////
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
	 * @param results
	 */
	public void setInitialQuery(String q) {
		this.initialQuery.setText(q);
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	public TextArea getRepairedQuery() {
		return repairedQuery;
	}

	public void setRepairedQuery(TextArea repairedQuery) {
		this.repairedQuery = repairedQuery;
	}

	//////////////////////

	public TreeTableColumn<Matches, String> getRelationColumn() {
		return relationColumn;
	}

	public TreeTableView<Matches> getTreeTableView() {
		return treeTableView;
	}

	public void setTreeTableView(TreeTableView<Matches> treeTableView) {
		this.treeTableView = treeTableView;
	}

	public void setRelationColumn(TreeTableColumn<Matches, String> relationColumn) {
		this.relationColumn = relationColumn;
	}

	public TreeTableColumn<Matches, String> getRepairedColumn() {
		return repairedColumn;
	}

	public void setRepairedColumn(TreeTableColumn<Matches, String> repairedColumn) {
		this.repairedColumn = repairedColumn;
	}

	public Text getSimScore() {
		return simScore;
	}

	public void setSimScore(Text simScore) {
		this.simScore = simScore;
	}

	public void treeTableViewFormatting(ArrayList<String[]> matchComponents, String simScore) {
		String initialValues = "";
		String repairedValues = "";
		String semanticRelations = "";

		TreeItem<Matches> matchHead = new TreeItem<>(new Matches("zdzad", "dazd", "qfgb"));
		TreeItem<Matches> node;
		matchHead.setExpanded(true);

		int nbrMatchComponents = matchComponents.size();

		// so we can separate the different parameters and store them in an array
		for (String[] m : matchComponents) {
			initialValues += m[0] + ";";
			semanticRelations += m[1] + ";";
			repairedValues += m[2] + ";";
		}

		System.out.println("TEST INITIAL VALUES:\n" + initialValues);
		System.out.println("TEST REPAIRED VALUES:\n" + repairedValues);

		// Split then store in array
		String[] initialSchema = initialValues.split(";");
		String[] relations = semanticRelations.split(";");
		String[] repairedSchema = repairedValues.split(";");

		System.out.println("INITIAL SCHEMA:");
		for (int i = 0; i < initialSchema.length; i++) {
			System.out.println(initialSchema[i]);
		}

		for (int cpt = 0; cpt < relations.length; cpt++) {
			System.out.println("RELATIONS: " + relations[cpt]);
		}

		System.out.println("REPAIRED SCHEMA:");
		for (int i = 0; i < repairedSchema.length; i++) {
			System.out.println(repairedSchema[i]);
		}

		// Store the values (without the head)

		String[] initialSchemaValues = new String[initialSchema.length];
		String[] repairedSchemaValues = new String[repairedSchema.length];

		for (int i = 0; i < nbrMatchComponents; i++) {

			String[] initialSplit = initialSchema[i].split(",");
			String[] repairedSplit = repairedSchema[i].split(",");

			if (initialSplit.length > 1) {// length equal 0 when there is only the head
				initialSchemaValues[i] = initialSplit[1];
			}
			if (repairedSplit.length > 1) {// length equal 0 when there is only the head
				repairedSchemaValues[i] = repairedSplit[1];
			}
			if (initialSchemaValues[i] != null && repairedSchemaValues[i] != null) {
				node = new TreeItem<>(new Matches(initialSchemaValues[i], relations[i], repairedSchemaValues[i]));
				matchHead.getChildren().add(node);
			} else {
				Matches test = new Matches(initialSplit[0], relations[i], repairedSplit[0]);
				matchHead.setValue(test);
			}

		}

		initialColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getInitial());
		relationColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRelation());
		repairedColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRepaired());

		TreeItem<Matches> test = new TreeItem<>(
				new Matches("Similarity score: " + simScore + "\n" + "Click here to display matches", "", ""));

		test.getChildren().add(matchHead);

		this.simScore.setText("Similarity score: " +simScore);
		treeTableView.setRoot(matchHead);

	}

	public void setTreeTableView(int nbrMatchComponents, String initialSchemaHead, String repairedSchemaHead,
			String[] initialSchemaValues, String[] relations, String[] repairedSchemaValues, String simScore) {

		// The match between the initial and repaired schemas' heads
		TreeItem<Matches> matchHead = new TreeItem<>(new Matches(initialSchemaHead, relations[0], repairedSchemaHead));
		TreeItem<Matches> node;
		matchHead.setExpanded(true);

		for (int i = 0; i < nbrMatchComponents; i++) {
			if (initialSchemaValues[i] != null && repairedSchemaValues[i] != null) {
				node = new TreeItem<>(new Matches(initialSchemaValues[i], relations[i], repairedSchemaValues[i]));
				matchHead.getChildren().add(node);
			}
		}

		initialColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getInitial());
		relationColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRelation());
		repairedColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRepaired());

		initialColumn.setCellFactory(col -> {
			TreeTableCell<Matches, String> cell = new TreeTableCell<Matches, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(item.toString());
					}
				}
			};

			// cell.setAlignment(Pos.CENTER);

			return cell;
		});

		relationColumn.setCellFactory(col -> {
			TreeTableCell<Matches, String> cell = new TreeTableCell<Matches, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(item.toString());
					}
				}
			};

			cell.setAlignment(Pos.CENTER);

			return cell;
		});

		repairedColumn.setCellFactory(col -> {
			TreeTableCell<Matches, String> cell = new TreeTableCell<Matches, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(item.toString());
					}
				}
			};

			// cell.setAlignment(Pos.CENTER);

			return cell;
		});

		TreeItem<Matches> test = new TreeItem<>(
				new Matches("Similarity score: " + simScore + "\n" + "Click here to display matches", "", ""));

		test.getChildren().add(matchHead);

		treeTableView.setRoot(test);

	}

}
