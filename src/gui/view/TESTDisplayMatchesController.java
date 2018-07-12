package gui.view;

import java.io.IOException;

import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
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
public class TESTDisplayMatchesController {

	@FXML
	private TextArea repairedQuery;

	@FXML
	private TextArea initialQuery;

	@FXML
	private TreeView<String> treeView;

	@FXML
	private void initicontrolleralize() {

	}
	
	protected int selectedIndex;

	// Reference to the main class
	private Main_GUI main;

	/**
	 * Default constructor
	 */
	public TESTDisplayMatchesController() {
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
			
			//so when we go back the repaired query is selected
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


	
	//////////////////////
	public TreeView<String> getTreeView() {
		return treeView;
	}

	public void setTreeView(TreeView<String> treeView) {
		this.treeView = treeView;
	}
	

}
