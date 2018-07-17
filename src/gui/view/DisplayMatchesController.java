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
		
		System.out.println("TEST IN display matches : \n" +this.main.getInitialQuerySchema() + "\n");

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

		System.out.println("TEST INITIAL VALUES:\n" + initialValues);
		System.out.println("TEST REPAIRED VALUES:\n" + repairedValues);

		// Split then store in array
		String[] initialSchema = initialValues.split(";"); //after the narrowing down
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

			// The above work only in the current case (one "child branch", need to optimize
			// in case of several)

			if (initialSchemaValues[i] != null && repairedSchemaValues[i] != null) {
				node = new TreeItem<>(new Matches(initialSchemaValues[i], relations[i], "       "+repairedSchemaValues[i]));
				matchHead.getChildren().add(node);
			} else {
				Matches test = new Matches(initialSplit[0], relations[i], repairedSplit[0]);
				matchHead.setValue(test);
			}

		}
		
		System.out.println("NBR MATCH COMPONENTS: " +nbrMatchComponents);	
		String[] fullInitialSchema = fullInitialSchema(); 
		if(fullInitialSchema.length > nbrMatchComponents) {
			String[] missingMatch;
			missingMatch = findTermsWithoutMatch(fullInitialSchema, initialSchema);
			node = new TreeItem<>(new Matches("MISSING", "!",""));
			matchHead.getChildren().add(node);
		}
		
		
		

		initialColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getInitial());
		relationColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRelation());
		repairedColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRepaired());

		
		
		
		

		
		/* relationColumn.setCellFactory((TreeTableColumn<Matches, String> param) -> {
            TreeTableCell<Matches,String> cell = new TreeTableCell<Matches, String>(){
                @Override
                //by using Number we don't have to parse a String
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<Matches> ttr = getTreeTableRow();
                    System.out.println("ITEM:" +item);
                    if (item == null || empty){
                        setText(null);
                        ttr.setStyle("");
                        //setStyle("-fx-background-color:black");
                    } 
                    else if(item.equalsIgnoreCase("<") || item.equalsIgnoreCase(">")) {
                    	setText(item);
                    	setStyle("-fx-text-fill:#FFB740");
                    	}
                    else if(item.equalsIgnoreCase("=")) {
                    	setText(item);
                    	setStyle("-fx-text-fill:#40C16D");
                    }
                    
                else {
                	setStyle("-fx-text-fill:red");}
                }
                
            };
            return cell;
        });		*/
		
		
		this.simScore.setText("Similarity score: " + simScore);
		treeTableView.setRoot(matchHead);

	}
	
	public String[] fullInitialSchema() {
		String schema = this.main.getInitialQuerySchema();
		int size = 0;
		String[] schemaSplit = schema.split("[,\\(\\)]"); //(,|\(|\))

		size = schemaSplit.length;
		System.out.println("Size of initial schema: " + size);

		return schemaSplit;
	}
	
	public String[] findTermsWithoutMatch(String[] fullInitialSchema, String[] initialSchema) {
		String[] test = null;
		
		for(int i = 0; i < initialSchema.length; i++) {
			System.out.println("Initial schema value: " +initialSchema[i]);
		}
		for(int i = 0; i < fullInitialSchema.length; i++) {
			System.out.println("FULL Initial schema value: " +fullInitialSchema[i]);
		}
		
		
		
		return test;
		
	}

}
