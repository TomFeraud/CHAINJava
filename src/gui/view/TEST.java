package gui.view;

import gui.model.Matches;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TEST extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		TreeItem<Matches> match = new TreeItem<>(new Matches("waterBodyPressurestest", "=", "waterBodyPressures"));
		
		TreeItem<Matches> match1 = new TreeItem<>(new Matches("waterBodyId", "=", "waterBodyId"));
		
		TreeItem<Matches> match2 = new TreeItem<>(new Matches("identifiedDate", "=", "identifiedDate"));
		
		TreeItem<Matches> match3 = new TreeItem<>(new Matches("dataSource", "=", "dataSource"));
		
		match.getChildren().addAll(match1,match2,match3);
		// Create the RootNode
		match.setExpanded(true);



		TreeTableView<Matches> treeTable = new TreeTableView<>(match);
		TreeTableColumn<Matches, String> initial = new TreeTableColumn<>("Initial Query");
		TreeTableColumn<Matches, String> relation = new TreeTableColumn<>("Semantic Relation");
		TreeTableColumn<Matches, String> repaired = new TreeTableColumn<>("Repaired Query");

		initial.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getInitial());

		relation.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRelation());
		repaired.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Matches, String> param) -> param.getValue().getValue().getRepaired());
		
		treeTable.getColumns().add(initial);
		treeTable.getColumns().add(relation);
		treeTable.getColumns().add(repaired);
		
		
		TreeItem<Matches> test = new TreeItem<>(new Matches("Click here to display matches", "", ""));

		test.getChildren().add(match);
		
		treeTable.setRoot(test);
		


		// Create the VBox
		VBox root = new VBox(treeTable);

		// Create the Scene
		Scene scene = new Scene(root);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title
		stage.setTitle("TEST TreeTableView");
		// Display the Stage
		stage.show();

	}

}
