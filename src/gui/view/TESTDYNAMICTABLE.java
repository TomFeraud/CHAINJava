package gui.view;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
 
public class TESTDYNAMICTABLE extends Application{
 
    private final String[][] dataArray = {
            { "jeep", "camaro", "corvette", "fefe"},
            { "accord", "camry", "mustang", "dzdazd"}
    };
 
    @Override
    public void start(Stage stage) throws Exception {
        TableView<ObservableList<String>> tableView = createTableView(dataArray);
        stage.setScene(new Scene(tableView));
        stage.show();
    }
 
    private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
 
        for (String[] row : dataArray) {
            data.add(FXCollections.observableArrayList(row));
        }
 
        return data;
    }
 
    private TableView<ObservableList<String>> createTableView(String[][] dataArray) {
        TableView<ObservableList<String>> tableView = new TableView<>();
        tableView.setItems(buildData(dataArray));
 
        for (int i = 0; i < dataArray[0].length; i++) {
            final int curCol = i;
            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    "Col " + (curCol + 1)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }
 
        return tableView;
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}