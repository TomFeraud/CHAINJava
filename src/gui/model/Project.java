package gui.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The model of the GUI project. Contains all the parameters of the run_Chain()
 * method that are handle using JavaFX through the process
 * 
 * @author Tom Feraud
 *
 */
public class Project {
	private StringProperty queryType = new SimpleStringProperty();
	private DoubleProperty minSimilarity = new SimpleDoubleProperty();;
	private IntegerProperty maxResultsWanted = new SimpleIntegerProperty();;
	private StringProperty initialQuery = new SimpleStringProperty();

	private StringProperty ontologyPath = new SimpleStringProperty();
	private StringProperty datasetPath = new SimpleStringProperty();
	private IntegerProperty maxQueriesProduced = new SimpleIntegerProperty();
	private StringProperty targets = new SimpleStringProperty();

	private boolean initialized = false;

	/**
	 * Default constructor
	 */
	public Project() {
		this.queryType.set("");
		this.minSimilarity.set(0);
		this.maxResultsWanted.set(0);
		this.initialQuery.set("");
		this.ontologyPath.set("");
		this.datasetPath.set("");
		this.maxQueriesProduced.set(0);
		this.targets.set("");
	}

	/**
	 * Project constructor
	 * 
	 * @param queryType
	 * @param minSimilarity
	 * @param maxResultsWanted
	 * @param initialQuery
	 * @param ontologyPath
	 * @param datasetPath
	 * @param maxQueriesProduced
	 * @param targets
	 */
	public Project(String queryType, double minSimilarity, int maxResultsWanted, String initialQuery,
			String ontologyPath, String datasetPath, int maxQueriesProduced, String targets) {
		this.queryType.set(queryType);
		this.minSimilarity.set(minSimilarity);
		this.maxResultsWanted.set(maxResultsWanted);
		this.initialQuery.set(initialQuery);
		this.ontologyPath.set(ontologyPath);
		this.datasetPath.set(datasetPath);
		this.maxQueriesProduced.set(maxQueriesProduced);
		this.targets.set(targets);
	}

	/**
	 * Gets the query's type
	 * 
	 * @return queryType
	 */
	public StringProperty getQueryType() {
		return queryType;
	}

	/**
	 * 
	 * Sets the query's type
	 * 
	 * @param queryType
	 */
	public void setQueryType(StringProperty queryType) {
		this.queryType = queryType;
	}

	/**
	 * Gets the minimum similarity value
	 * 
	 * @return minSimilarity
	 */
	public DoubleProperty getMinSimilarity() {
		return minSimilarity;
	}

	/**
	 * 
	 * Sets the query's type
	 * 
	 * @param minSimilarity
	 */
	public void setMinSimilarity(DoubleProperty minSimilarity) {
		this.minSimilarity = minSimilarity;
	}

	/**
	 * Gets the maximum number of results wanted
	 * 
	 * @return maxResultsWanted
	 */
	public IntegerProperty getMaxResultsWanted() {
		return maxResultsWanted;
	}

	/**
	 * 
	 * Sets the maximum number of results wanted
	 * 
	 * @param maxResultsWanted
	 */
	public void setMaxResultsWanted(IntegerProperty maxResultsWanted) {
		this.maxResultsWanted = maxResultsWanted;
	}

	/**
	 * Gets the initial query
	 * 
	 * @return initialQuery
	 */
	public StringProperty getInitialQuery() {
		return initialQuery;
	}

	/**
	 * 
	 * Sets the initial query
	 * 
	 * @param initialQuery
	 */
	public void setInitialQuery(StringProperty initialQuery) {
		this.initialQuery = initialQuery;
	}

	/**
	 * Gets the query's ontology path
	 * 
	 * @return ontologyPath
	 */
	public StringProperty getOntologyPath() {
		return ontologyPath;
	}

	/**
	 * 
	 * Sets the ontology path
	 * 
	 * @param ontologyPath
	 */
	public void setOntologyPath(StringProperty ontologyPath) {
		this.ontologyPath = ontologyPath;
	}

	/**
	 * Gets the dataset path
	 * 
	 * @return datasetPath
	 */
	public StringProperty getDatasetPath() {
		return datasetPath;
	}

	/**
	 * 
	 * Sets the dataset path
	 * 
	 * @param datasetPath
	 */
	public void setDatasetPath(StringProperty datasetPath) {
		this.datasetPath = datasetPath;
	}

	/**
	 * Gets the maximum number of queries produced
	 * 
	 * @return maxQueriesProduced
	 */
	public IntegerProperty getMaxQueriesProduced() {
		return maxQueriesProduced;
	}

	/**
	 * 
	 * Sets the maximum number of queries produced
	 * 
	 * @param maxQueriesProduced
	 */
	public void setMaxQueriesProduced(IntegerProperty maxQueriesProduced) {
		this.maxQueriesProduced = maxQueriesProduced;
	}

	/**
	 * Gets the targets
	 * 
	 * @return targets
	 */
	public StringProperty getTargets() {
		return targets;
	}

	/**
	 * 
	 * Sets the targets
	 * 
	 * @param targets
	 */
	public void setTargets(StringProperty targets) {
		this.targets = targets;
	}

	/**
	 * Sets all the parameters
	 * 
	 * @param queryType
	 * @param minSimilarity
	 * @param maxResultsWanted
	 * @param query
	 * @param ontologyPath
	 * @param datasetPath
	 * @param maxQueriesProduced
	 * @param targets
	 */
	public void setParameters(String queryType, double minSimilarity, int maxResultsWanted, String query,
			String ontologyPath, String datasetPath, int maxQueriesProduced, String targets) {
		this.queryType.set(queryType);
		this.minSimilarity.set(minSimilarity);
		this.maxResultsWanted.set(maxResultsWanted);
		this.initialQuery.set(query);
		this.ontologyPath.set(ontologyPath);
		this.datasetPath.set(datasetPath);
		this.maxQueriesProduced.set(maxQueriesProduced);
		this.targets.set(targets);
	}

	/**
	 * Is the object initialized
	 * 
	 * @return initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets the object initialization status
	 * 
	 * @param initialized
	 */
	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	/**
	 * To display all parameters in console
	 * 
	 */
	public String toString() {
		return "Query Type: " + this.queryType.get() + "\nMin. similarity value: " + this.minSimilarity.get()
				+ "\nMax. results wanted: " + this.maxResultsWanted.get() + "\nOntology path: "
				+ this.ontologyPath.get() + "\nDataset path: " + this.datasetPath.get() + "\nMax. queries produced: "
				+ this.maxQueriesProduced.get() + "\nTarget(s): " + this.targets.get() + "\nInitial query:\n "
				+ this.initialQuery.get() + "\n";
	}
}
