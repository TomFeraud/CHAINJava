package gui.view;

import java.io.IOException;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;

import gui.main.Main_GUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SendQueryMapping {

	@FXML
	private TextField queryType;

	@FXML
	private TextField minSim;

	@FXML
	private TextField maxNbrResultsWanted;

	@FXML
	private TextField ontologyPath;

	@FXML
	private TextField datasetPath;

	@FXML
	private TextField maxNbrQueriesProduced;

	@FXML
	private TextArea query;

	@FXML
	private TextArea targets;

	// Objet servant de référence à notre classe principale
	// afin de pouvoir récupérer la liste de nos objets.
	private Main_GUI main;

	// Un constructeur par défaut
	public SendQueryMapping() {
	}

	// EASIER TO TEST WITH THIS
	@FXML
	private void initialize() {

		String queryTEST = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
				+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
				+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
				+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
				+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressurestest.n3>\n"
				+ "WHERE { ?id sepaw:dataSource ?dataSource;\n" + "sepaw:identifiedDate  ?identifiedDate  ;\n"
				+ "sepaw:affectsGroundwater ?affectsGroundwater ;\n" + "sepaw:waterBodyId ?waterBodyId .}" + "\n\n";

		String targetTEST = "places(dataSource, identifiedDate, affectsGroundwater, waterBodyId) ; waterBodyPressures(dataSource, identifiedDate, affectsGroundwater, waterBodyId)";

		queryType.setText("sepa");
		minSim.setText("0.3");
		maxNbrResultsWanted.setText("10");
		ontologyPath.setText("queryData/sepa/sepa_ontology.json");
		datasetPath.setText("queryData/sepa/sepa_datafiles/");
		maxNbrQueriesProduced.setText("5");
		query.setText(queryTEST);
		targets.setText(targetTEST);
	}

	// Méthode qui sera utilisée dans l'initialisation de l'IHM
	// dans notre classe principale

	public void setMainApp(Main_GUI mainApp) {
		this.main = mainApp;
		// On lie notre liste observable au composant TableView
		// textQuery.setText(main.getFirstQuery());
		// textTarget.setText(main.getTargets());
		// String resultInfo = String.valueOf(main.getResult());
		// result.setText(resultInfo);
	}

	@FXML
	public void launch() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(
				"It will take some time to process. Please don't quit the interface." + "\nClick OK to continue");
		alert.showAndWait();

		// NEED TO ADD EXCEPTIONS ACCORDING TO THE ENTRIES

		String qt = queryType.getText();
		double minSimilarity = Double.parseDouble(minSim.getText());
		int maxRes = Integer.parseInt(maxNbrResultsWanted.getText());
		String ontPath = ontologyPath.getText();
		String dataPath = datasetPath.getText();
		int maxQProduced = Integer.parseInt(maxNbrQueriesProduced.getText());
		String q = query.getText();
		String t = targets.getText();

		this.main.getProjectModel().setParameters(qt, minSimilarity, maxRes, q, ontPath, dataPath, maxQProduced, t);

		this.main.getRun_CHAIn().runCHAIn(q, qt, t, dataPath, ontPath, maxRes, minSimilarity, maxQProduced, null);

		// Change scene
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayResults.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayResultsMapping controllor = loader.getController();
			controllor.setMainApp(this.main);
			
			//Need to create a copy because ResultSetFormatter is destructive
			ResultSet copy = ResultSetFactory.copyResults(this.main.getRun_CHAIn().getTestResults());
			
			controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getTestResults()));
			//System.out.println(ResultSetFormatter.asText(this.main.getRun_CHAIn().getTestResults()));

			//THIS MAY BE USEFUL TO HAVE A BETTER DISPLAY!
			System.out.println(ResultSetFormatter.toList(copy).get(0));
			//System.out.println(ResultSetFormatter.toModel(this.main.getRun_CHAIn().getTestResults()));
			
			
			
			
		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

}
