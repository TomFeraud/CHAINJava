package gui.view;

import gui.main.TESTOneMain_GUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;

public class TestQueryMapping {

	@FXML
	private TextArea textQuery;

	@FXML
	private TextArea textTarget;

	@FXML
	private TextField result;

	// Objet servant de référence à notre classe principale
	// afin de pouvoir récupérer la liste de nos objets.
	private TESTOneMain_GUI main;

	// Un constructeur par défaut
	public TestQueryMapping() {
	}

	// Méthode qui initialise notre interface graphique
	// avec nos données métier
	@FXML
	private void initialize() {
		// Initialize the Personne table with the two columns.
		textQuery.setText("EMPTY");
		textTarget.setText("EMPTY");
	}

	// Méthode qui sera utilisée dans l'initialisation de l'IHM
	// dans notre classe principale
	public void setMainApp(TESTOneMain_GUI mainApp) {
		this.main = mainApp;
		// On lie notre liste observable au composant TableView
		textQuery.setText(main.getFirstQuery());
		textTarget.setText(main.getTargets());
		String resultInfo = String.valueOf(main.getResult());
		result.setText(resultInfo);
	}

	@FXML
	public void launch() {
		String query = textQuery.getText();
		if (query != "") {
			this.main.testRun();
			
			String resultInfo = String.valueOf(main.getResult());
			System.out.println(resultInfo);
			if (resultInfo.equals("10")) {
				result.setText("REPAIREDQUERYRESULTS");
			} else {
				result.setText(resultInfo);
			} 

		}
	}
}
