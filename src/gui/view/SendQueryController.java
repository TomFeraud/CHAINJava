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
	/**
	 * Initialize the inputs using predefined examples and sets tooltips
	 * 
	 */
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

	/**
	 * This is called when the send button is clicked
	 * It creates and initialize the DisplayResultsController
	 * 
	 */
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

			// So the splitpane divider is the same size
			double[] pos = this.getSplitPane().getDividerPositions();
			this.main.getSendQueryController().getSplitPane().setDividerPositions(pos[0]);

			int nbrResponse = 666; // if 666 is displayed then there is an error
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

	/**
	 * If the initial query has run successfully
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean initialQuerySuccess(int result_status) {
		if (result_status == 5) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If the repaired query has ran with results
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean hasRepairedQueryResult(int result_status) {
		if (result_status == 10 || result_status == 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If the request has no results
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean hasNoResult(int result_status) {
		if (result_status == 6 || result_status == 7 || result_status == 8 || result_status == 9
				|| result_status == 11) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If there is no repaired query
	 * 
	 * @param result_status
	 * 
	 * @return boolean
	 */
	public boolean hasNoRepairedQueries(int result_status) {
		if (result_status == 5 || result_status == 6 || result_status == 7 || result_status == 8) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the example
	 * 
	 * @return example
	 */
	public String getExample() {
		return example;
	}

	/**
	 * Sets the example
	 * 
	 * @param  example
	 */
	public void setExample(String example) {
		this.example = example;
	}

	/**
	 * Gets the query type
	 * 
	 * @return queryType
	 */
	public TextField getQueryType() {
		return queryType;
	}

	/**
	 * Sets the query type
	 * 
	 * @param queryType
	 */
	public void setQueryType(TextField queryType) {
		this.queryType = queryType;
	}

	/**
	 * Gets the minimum similarity value
	 * 
	 * @return minSim
	 */
	public TextField getMinSim() {
		return minSim;
	}

	/**
	 * Sets the minimum similarity value
	 * 
	 * @param minSim
	 */
	public void setMinSim(TextField minSim) {
		this.minSim = minSim;
	}

	/**
	 * Gets the maximum number of results wanted
	 * 
	 * @return maxNbrResultsWanted
	 */
	public TextField getMaxNbrResultsWanted() {
		return maxNbrResultsWanted;
	}

	/**
	 * Sets the maximum number of results wanted
	 * 
	 * @param maxNbrResultsWanted
	 */
	public void setMaxNbrResultsWanted(TextField maxNbrResultsWanted) {
		this.maxNbrResultsWanted = maxNbrResultsWanted;
	}

	/**
	 * Gets the ontology path
	 * 
	 * @return ontologyPath
	 */
	public TextField getOntologyPath() {
		return ontologyPath;
	}

	/**
	 * Sets the ontology path
	 * 
	 * @param ontologyPath
	 */
	public void setOntologyPath(TextField ontologyPath) {
		this.ontologyPath = ontologyPath;
	}

	/**
	 * Gets the dataset path
	 * 
	 * @return datasetPath
	 */
	public TextField getDatasetPath() {
		return datasetPath;
	}

	/**
	 * Sets the dataset path
	 * 
	 * @param datasetPath
	 */
	public void setDatasetPath(TextField datasetPath) {
		this.datasetPath = datasetPath;
	}

	/**
	 * Gets the maximumn number of queries produced
	 * 
	 * @return maxNbrQueriesProduced
	 */
	public TextField getMaxNbrQueriesProduced() {
		return maxNbrQueriesProduced;
	}

	/**
	 * Sets the maximumn number of queries produced
	 * 
	 * @param maxNbrQueriesProduced
	 */
	public void setMaxNbrQueriesProduced(TextField maxNbrQueriesProduced) {
		this.maxNbrQueriesProduced = maxNbrQueriesProduced;
	}

	/**
	 * Gets the query
	 * 
	 * @return query
	 */
	public TextArea getQuery() {
		return query;
	}

	/**
	 * Sets the query
	 * 
	 * @param query
	 */
	public void setQuery(String query) {
		this.query.setText(query);
	}

	/**
	 * Gets the targets
	 * 
	 * @return targets
	 */
	public TextArea getTargets() {
		return targets;
	}

	/**
	 * Sets the targets
	 * 
	 * @param targets
	 */
	public void setTargets(TextArea targets) {
		this.targets = targets;
	}

	/**
	 * Gets the Main object
	 * 
	 * @return main
	 */
	public Main_GUI getMain() {
		return main;
	}

	/**
	 * Has the interface already been used since launched
	 * 
	 * @return initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets if the interface is initialized or not
	 * 
	 * @param initialized
	 */
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	/**
	 * Gets the button object
	 * 
	 * @return sendButton
	 */
	public Button getSendButton() {
		return sendButton;
	}

	/**
	 * Sets the button objecdt
	 * 
	 * @param sendButton
	 */
	public void setSendButton(Button sendButton) {
		this.sendButton = sendButton;
	}

	/**
	 * Gets the split pane
	 * 
	 * @return splitPane
	 */
	public SplitPane getSplitPane() {
		return splitPane;
	}

	/**
	 * Sets the split pane
	 * 
	 * @param splitPane
	 */
	public void setSplitPane(SplitPane splitPane) {
		this.splitPane = splitPane;
	}

}
