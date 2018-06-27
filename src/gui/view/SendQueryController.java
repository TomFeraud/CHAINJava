package gui.view;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.query.ResultSetFormatter;


import gui.main.Main_GUI;
import gui.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * The controller of the SendQuery view
 * 
 * @author Tom Feraud
 *
 */
public class SendQueryController {

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

	// TEST
	@FXML
	private Button sendButton;

	// If the interface has already been used
	private boolean initialized;

	// Reference to the main class
	private Main_GUI main;

	// Test
	private String example = "";

	/**
	 * Default constructor
	 */
	public SendQueryController() {
	}

	// EASIER TO TEST WITH THIS (fill all the parameters' fields)
	@FXML
	public void initialize() {
		// If initialized then we get the latest values associated to our Project object
		// (so the SendQuery view is already fill)
		if (this.isInitialized()) {
			Project p = this.main.getProjectModel();
			queryType.setText(p.getQueryType().get());
			minSim.setText(Double.toString(p.getMinSimilarity().get()));
			maxNbrResultsWanted.setText(Integer.toString(p.getMaxResultsWanted().get()));
			ontologyPath.setText(p.getOntologyPath().get());
			datasetPath.setText(p.getDatasetPath().get());
			maxNbrQueriesProduced.setText(Integer.toString(p.getMaxQueriesProduced().get()));
			query.setText(p.getInitialQuery().get());
			targets.setText(p.getTargets().get());

		}

		String queryInit = "";
		String targetInit = "";

		if (example.equalsIgnoreCase("Example1")) {
			System.out.println("\nRef test 8.0.1\n");
			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressurestest.n3>\n"
					+ "WHERE { ?id sepaw:dataSource ?dataSource;\n" + "sepaw:identifiedDate  ?identifiedDate  ;\n"
					+ "sepaw:affectsGroundwater ?affectsGroundwater ;\n" + "sepaw:waterBodyId ?waterBodyId .}" + "\n\n";

			targetInit = "waterBodyPressures(dataSource, identifiedDate, affectsGroundwater, waterBodyId)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("10");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example2")) {
			System.out.println("\nRef test 8.1.4\n");
			queryInit = "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
					+ "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
					+ "PREFIX  res: <http://dbpedia.org/resource/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
					+ "PREFIX yago: <hhtp://dbpedia.org/class/yago/> \n\n" + "SELECT DISTINCT *  \n"
					+ "WHERE { ?id rdf:type dbo:River ;\n"
					+ "dbo:lengthTest \"99300.0\"^^<http://www.w3.org/2001/XMLSchema#double> ;\n" + ".}\n"
					+ "LIMIT 10\n\n";

			targetInit = "River(length) ; River(size)";

			queryType.setText("dbpedia");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("20");
			ontologyPath.setText("queryData/dbpedia/dbpedia_ontology.json");
			// datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example3")) {
			System.out.println("\nRef test 8.0.3\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/surfaceWaterBodies.n3>\n"
					+ "WHERE { ?id sepaw:river ?river;\n" + "sepaw:associatedGroundwaterId ?associatedGroundwaterId .}"
					+ "\n\n";

			targetInit = "water(timePeriod,geo,measure,resource)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("20");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);

		} else if (example.equalsIgnoreCase("Example4")) {
			System.out.println("\nRef test 8.1.2\n");

			queryInit = "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
					+ "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
					+ "PREFIX  res: <http://dbpedia.org/resource/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
					+ "PREFIX yago: <hhtp://dbpedia.org/class/yago/> \n\n" + "SELECT DISTINCT *  \n"
					+ "WHERE { ?id rdf:type dbo:River ;\n" + "dbo:length ?length ;\n" + ".}\n" + "LIMIT 10\n\n";

			targetInit = "River(length)";

			queryType.setText("dbpedia");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("20");
			ontologyPath.setText("queryData/dbpedia/dbpedia_ontology.json");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example5")) {
			System.out.println("\nRef test 8.0.2\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/water.n3>\n" + "WHERE { ?id sepaw:timePeriod ?timePeriod;\n"
					+ "random:geo ?geo  ;\n" // The invalid bit - an undefined prefix
					+ "sepaw:measure ?measure ;\n" + "sepaw:resource ?resource .}" + "\n\n";

			targetInit = "water(timePeriod,geo,measure,resource)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("10");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example6")) {
			System.out.println("\nRef test 8.0.6\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressures.n3>\n"
					+ "WHERE { ?id sepaw:identifiedDate \"2008-04-01\";\n" + "sepaw:waterBodyId sepaidw:20308xxx  ;\n" // wont
																														// match
					+ "sepaw:assessmentCategory ?assessmentCategory ;\n" + "sepaw:source \"Lake\" .}";

			targetInit = "waterBodyPressures(identifiedDate,waterBodyId,assessmentCategory,source)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("10");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example7")) {
			System.out.println("\nRef test13 in Run_Query_Test_Cases\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/waterBodyMeasures.n3>\n"
					+ "WHERE { ?id sepaw:timePeriod ?timePeriod;\n" + "geo:geo ?geo  ;\n" + "sepaw:measure ?measure ;\n"
					+ "sepaw:resource ?resource .}";

			targetInit = "waterBodyMeasures(timePeriod, geo, measure, resource)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("10");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example8")) {
			System.out.println("\nRef test12 in Run_Query_Test_Cases\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "SELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/water.n3>\n" + "WHERE { ?id sepaw:timePeriod ?timePeriod;\n"
					+ "geo:geo ?geo  ;\n" + "sepaw:measure ?measure ;\n" + "sepaw:resource ?resource .}";

			targetInit = "water(timePeriod, geo, measure, resource)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("10");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		}

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
	public void launch() {

		// Information message to warn the user about the delay
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(
				"It will take some time to process. Please don't quit the interface." + "\nClick OK to continue");
		alert.showAndWait();

		// TEST
		//Button button = this.getSendButton();
		this.getSendButton().setText("CHARGING..");
		// button.setBackground(value);

		// NEED TO ADD EXCEPTIONS ACCORDING TO THE ENTRIES

		String qt = queryType.getText();
		double minSimilarity = Double.parseDouble(minSim.getText());
		int maxRes = Integer.parseInt(maxNbrResultsWanted.getText());
		String ontPath = ontologyPath.getText();
		String dataPath = datasetPath.getText();
		int maxQProduced = Integer.parseInt(maxNbrQueriesProduced.getText());
		String q = query.getText();
		String t = targets.getText();

		// Store in the ProjectModel object of the main class the initial parameters
		// which will be uses through the process
		this.main.getProjectModel().setParameters(qt, minSimilarity, maxRes, q, ontPath, dataPath, maxQProduced, t);
		this.main.getProjectModel().setInitialized(true);
		// Call to the runChain() method and store the results status in the
		// result_status variable of our main class
		this.main.setResult_status(this.main.getRun_CHAIn().runCHAIn(q, qt, t, dataPath, ontPath, maxRes, minSimilarity,
				maxQProduced, null));
		// Change scene
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayResults.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayResultsController controllor = loader.getController();
			controllor.setMainApp(this.main);

			int nbrResponse = 0;

			// Check if the initial query was successfuly run
			if (initialQuerySuccess(this.main.getResult_status())) {
				controllor.setResults(ResultSetFormatter.asText(this.main.getRun_CHAIn().getResultsFromInitialQuery()));
				nbrResponse = this.main.getRun_CHAIn().getResultsFromInitialQuery().getRowNumber();
			} else {
				if (hasRepairedQueryResult(this.main.getResult_status())) {
					// Need to create a copy because ResultSetFormatter is destructive
					ResultSet copy = ResultSetFactory
							.copyResults(this.main.getRun_CHAIn().getResultsFromARepairedQuery());

					// copy2 & copy3 are for the tests
					ResultSet copy2 = ResultSetFactory
							.copyResults(this.main.getRun_CHAIn().getResultsFromARepairedQuery());

					ResultSet copy3 = ResultSetFactory
							.copyResults(this.main.getRun_CHAIn().getResultsFromARepairedQuery());

					controllor.setResults(
							ResultSetFormatter.asText(this.main.getRun_CHAIn().getResultsFromARepairedQuery()));
					// System.out.println(ResultSetFormatter.asText(this.main.getRun_CHAIn().getTestResults()));

					nbrResponse = this.main.getRun_CHAIn().getResultsFromARepairedQuery().getRowNumber();


					/////////////////////////////////////////////////////
					// TEST TABLEAU

					System.out.println("Test display tab");

					// use to know the number of columns
					Iterator<String> columnIterator = ResultSetFormatter.toList(copy).get(0).varNames();
					// use to get the columns' name
					Iterator<String> columnItName = ResultSetFormatter.toList(copy3).get(0).varNames();

					int cptColumn = 0;

					while (columnIterator.hasNext()) {
						System.out.println(columnIterator.next());
						cptColumn++;
					}

					String[][] array = new String[nbrResponse + 1][cptColumn];

					// FILL THE FIRST ROW
					for (int cptC = 0; cptC < cptColumn; cptC++) {
						array[0][cptC] = columnItName.next();
					}
					System.out.println("\n\n");
					List<QuerySolution> listOfResults = ResultSetFormatter.toList(copy2);
					for (int i = 0; i < listOfResults.size(); i++) {
						for (int cptC = 0; cptC < cptColumn; cptC++) {

							array[i + 1][cptC] = testFillCell(array, i, cptC, listOfResults);
						}
					}

					for (int cptC = 0; cptC < cptColumn; cptC++) {
						array[0][cptC] += "      ";
					}

					System.out.println(Arrays.deepToString(array).replace("], ", "]\n"));

					System.out.println("FIN TEST display tab");

					/*
					for (int i = 0; i < listOfResults.size(); i++) {
						for (int cptC = 0; cptC < cptColumn; cptC++) {
							System.out.format("%15s   ", array[i][cptC]);
						}
					} */

				}

			}
			if (hasNoResult(this.main.getResult_status())) {
				controllor.setTopText(0);
			} else {
				controllor.setTopText(nbrResponse);
			}
			controllor.setBottomTextAccordingToStatus(this.main.getResult_status());

			this.setInitialized(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean initialQuerySuccess(int result_status) {
		if (result_status == 5) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasRepairedQueryResult(int result_status) {
		if (result_status == 10 || result_status == 12) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasNoResult(int result_status) {
		if (result_status == 6 || result_status == 7 || result_status == 8 || result_status == 9
				|| result_status == 11) {
			return true;
		} else {
			return false;
		}
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public TextField getQueryType() {
		return queryType;
	}

	public void setQueryType(TextField queryType) {
		this.queryType = queryType;
	}

	public TextField getMinSim() {
		return minSim;
	}

	public void setMinSim(TextField minSim) {
		this.minSim = minSim;
	}

	public TextField getMaxNbrResultsWanted() {
		return maxNbrResultsWanted;
	}

	public void setMaxNbrResultsWanted(TextField maxNbrResultsWanted) {
		this.maxNbrResultsWanted = maxNbrResultsWanted;
	}

	public TextField getOntologyPath() {
		return ontologyPath;
	}

	public void setOntologyPath(TextField ontologyPath) {
		this.ontologyPath = ontologyPath;
	}

	public TextField getDatasetPath() {
		return datasetPath;
	}

	public void setDatasetPath(TextField datasetPath) {
		this.datasetPath = datasetPath;
	}

	public TextField getMaxNbrQueriesProduced() {
		return maxNbrQueriesProduced;
	}

	public void setMaxNbrQueriesProduced(TextField maxNbrQueriesProduced) {
		this.maxNbrQueriesProduced = maxNbrQueriesProduced;
	}

	public TextArea getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query.setText(query);
	}

	public TextArea getTargets() {
		return targets;
	}

	public void setTargets(TextArea targets) {
		this.targets = targets;
	}

	public Main_GUI getMain() {
		return main;
	}

	public void setMain(Main_GUI main) {
		this.main = main;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public Button getSendButton() {
		return sendButton;
	}

	public void setSendButton(Button sendButton) {
		this.sendButton = sendButton;
	}

	// TEST WORKING
	private String testFillCell(String[][] array, int cptRow, int cptCol, List<QuerySolution> testJena) {
		String s = "";
		String columnName = array[0][cptCol];
		// System.out.println("Column name: " + columnName);
		// System.out.println(testJena.get(cptRow).get(columnName).toString());
		s = testJena.get(cptRow).get(columnName).toString();
		return s;
	}

}
