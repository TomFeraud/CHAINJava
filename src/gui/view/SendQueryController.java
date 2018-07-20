package gui.view;

import java.io.IOException;
import java.util.ArrayList;
import com.hp.hpl.jena.query.ResultSet;
import gui.main.Main_GUI;
import gui.model.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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

	@FXML
	private Button sendButton;

	// If the interface has already been used
	private boolean initialized;

	// To handle the different example predefined in the interface
	private String example = "";

	// To handle the size of split pane divider (otherwise not fluid)
	@FXML
	private SplitPane splitPane;

	// Reference to the main class
	private Main_GUI main;

	/**
	 * Default constructor
	 */
	public SendQueryController() {
	}

	// EASIER TO TEST WITH THIS (fill all the parameters' fields)
	@FXML
	public void initialize() {

		// Set tooltips for the input fields
		Tooltip tooltipType = new Tooltip("\"sepa\" or \"dbpedia\"");
		queryType.setTooltip(tooltipType);

		Tooltip tooltipSim = new Tooltip("Between 0 and 1, e.g. 0.5");
		minSim.setTooltip(tooltipSim);

		Tooltip tooltipMaxRes = new Tooltip("Enter 0 to get all the responses");
		maxNbrResultsWanted.setTooltip(tooltipMaxRes);

		Tooltip tooltipMaxQProd = new Tooltip("Enter 0 for no limitations");
		maxNbrQueriesProduced.setTooltip(tooltipMaxQProd);

		Tooltip tooltipQuery = new Tooltip("Enter your query here");
		query.setTooltip(tooltipQuery);

		Tooltip tooltipTarget = new Tooltip("Enter your target(s) here. If more than one, separate them by a \";\"");
		targets.setTooltip(tooltipTarget);

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
			System.out.println("\nRef test 8.1.2\n");

			queryInit = "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
					+ "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
					+ "PREFIX  res: <http://dbpedia.org/resource/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
					+ "PREFIX yago: <hhtp://dbpedia.org/class/yago/> \n\n" + "SELECT DISTINCT *  \n"
					+ "WHERE { ?id rdf:type dbo:River ;\n" + "dbo:length ?length ;\n" + ".}\n \n";

			targetInit = "River(length)";

			queryType.setText("dbpedia");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("20");
			ontologyPath.setText("queryData/dbpedia/dbpedia_ontology.json");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		}

		else if (example.equalsIgnoreCase("Example2")) {
			System.out.println("\nRef test 8.0.1\n");
			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "\nSELECT *  \n"
					+ "FROM <queryData/sepa/sepa_datafiles/waterBodyPressurestest.n3>\n"
					+ "WHERE { ?id sepaw:dataSource ?dataSource;\n" + "sepaw:time  ?time  ;\n"
					+ "sepaw:waterAffected ?waterAffected ;\n" + "sepaw:waterCourse ?waterCourse .}" + "\n\n";

			targetInit = "waterBodyPressures(dataSource, identifiedDate, affectsGroundwater, waterBodyId)";

			queryType.setText("sepa");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("10");
			ontologyPath.setText("queryData/sepa/sepa_ontology.json");
			datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example3")) {
			System.out.println("\nRef test 8.1.4\n");
			queryInit = "PREFIX  dbo:  <http://dbpedia.org/ontology/> \n"
					+ "PREFIX  dbp: <http://dbpedia.org/property/>   \n"
					+ "PREFIX  res: <http://dbpedia.org/resource/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  foaf: <http://xlmns.com/foaf/0.1/> \n"
					+ "PREFIX yago: <hhtp://dbpedia.org/class/yago/> \n\n" + "SELECT DISTINCT *  \n"
					+ "WHERE { ?id rdf:type dbo:River ;\n"
					+ "dbo:duration \"99300.0\"^^<http://www.w3.org/2001/XMLSchema#double> ;\n" + ".}\n";

			targetInit = "River(length) ; River(size)";

			queryType.setText("dbpedia");
			minSim.setText("0.5");
			maxNbrResultsWanted.setText("20");
			ontologyPath.setText("queryData/dbpedia/dbpedia_ontology.json");
			// datasetPath.setText("queryData/sepa/sepa_datafiles/");
			maxNbrQueriesProduced.setText("5");
			query.setText(queryInit);
			targets.setText(targetInit);
		} else if (example.equalsIgnoreCase("Example4")) {
			System.out.println("\nRef test 8.0.3\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "\nSELECT *  \n"
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

		} else if (example.equalsIgnoreCase("Example5")) {
			System.out.println("\nRef test 8.0.2\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "\nSELECT *  \n"
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
			System.out.println("\nRef test13 in Run_Query_Test_Cases\n");

			queryInit = "PREFIX  geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
					+ "PREFIX  sepaidw: <http://data.sepa.org.uk/id/Water/>   \n"
					+ "PREFIX  sepaidloc: <http://data.sepa.org.uk/id/Location/> \n"
					+ "PREFIX  rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
					+ "PREFIX  sepaw: <http://data.sepa.org.uk/ont/Water#> \n" + "\nSELECT *  \n"
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
		} else if (example.equalsIgnoreCase("Example7")) {
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
				"It will take some time to process. Please don't leave the interface." + "\nClick OK to continue");
		alert.showAndWait();

		// TEST
		// Button button = this.getSendButton();
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
		this.main.setInitialQuerySchema(this.main.getRun_CHAIn().getInitialQuerySchema());

		// Change scene
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main_GUI.class.getResource("/gui/view/DisplayResults.fxml"));
		try {
			AnchorPane content = (AnchorPane) loader.load(); // Gets the container wich contains the data
			this.main.getMainContainair().setCenter(content); // Then add it to our main container

			DisplayResultsController controller = loader.getController();
			controller.setMainApp(this.main);
			
			double [] pos = this.getSplitPane().getDividerPositions();
			this.main.getSendQueryController().getSplitPane().setDividerPositions(pos[0]);

			int nbrResponse = 666;
			int result_status = this.main.getResult_status();

			// Check if the initial query has run with results
			if (initialQuerySuccess(result_status) || hasRepairedQueryResult(result_status)) {
				nbrResponse = controller.resultsFormatting();
			}

			// Store the list of results from the repaired queries
			if (!hasNoRepairedQueries(result_status)) {
				// Store the list of results from the repaired queries
				ArrayList<ResultSet> resultsList = this.main.getRun_CHAIn().getListResultsFromRepairedQuery();
				// Assign it to our Main class to use it elsewhere
				this.main.setResultsList(resultsList);

				int nbrRepairedQueries = resultsList.size();

				this.main.setNbrRepairedQueries(nbrRepairedQueries);
			} else {// no repaired query in the other case so we set the number to 0

				this.main.setNbrRepairedQueries(0);

			}

			if (hasNoResult(result_status)) {
				controller.setTopText(0);
				controller.getResultsTable().setPlaceholder(new Label("No results were found for your request"));

			} else {
				controller.setTopText(nbrResponse);
			}

			controller.setBottomTextAccordingToStatus(result_status);
			controller.setTextButtonRepairedQueries();

			this.setInitialized(true);
		} catch (

		IOException e) {
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

	public boolean hasNoRepairedQueries(int result_status) {
		if (result_status == 5 || result_status == 6 || result_status == 7 || result_status == 8) {
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

	public SplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(SplitPane splitPane) {
		this.splitPane = splitPane;
	}

}
