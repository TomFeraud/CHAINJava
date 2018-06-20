package gui.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {
	private StringProperty queryType = new SimpleStringProperty();
	private DoubleProperty minSimilarity = new SimpleDoubleProperty();;
	private IntegerProperty maxResultsWanted = new SimpleIntegerProperty();;
	private StringProperty query = new SimpleStringProperty();

	private StringProperty ontologyPath = new SimpleStringProperty();
	private StringProperty datasetPath = new SimpleStringProperty();
	private IntegerProperty maxQueriesProduced = new SimpleIntegerProperty();
	private StringProperty targets = new SimpleStringProperty();

	public Project() {
		this.queryType.set("");
		this.minSimilarity.set(0);
		this.maxResultsWanted.set(0);
		this.query.set("");
		this.ontologyPath.set("");
		this.datasetPath.set("");
		this.maxQueriesProduced.set(0);
		this.targets.set("");
	}

	public Project(String queryType, double minSimilarity, int maxResultsWanted, String query, String ontologyPath,
			String datasetPath, int maxQueriesProduced, String targets) {
		this.queryType.set(queryType);
		this.minSimilarity.set(minSimilarity);
		this.maxResultsWanted.set(maxResultsWanted);
		this.query.set(query);
		this.ontologyPath.set(ontologyPath);
		this.datasetPath.set(datasetPath);
		this.maxQueriesProduced.set(maxQueriesProduced);
		this.targets.set(targets);
	}

	public StringProperty getQueryType() {
		return queryType;
	}

	public void setQueryType(StringProperty queryType) {
		this.queryType = queryType;
	}

	public DoubleProperty getMinSimilarity() {
		return minSimilarity;
	}

	public void setMinSimilarity(DoubleProperty minSimilarity) {
		this.minSimilarity = minSimilarity;
	}

	public IntegerProperty getMaxResultsWanted() {
		return maxResultsWanted;
	}

	public void setMaxResultsWanted(IntegerProperty maxResultsWanted) {
		this.maxResultsWanted = maxResultsWanted;
	}

	public StringProperty getQuery() {
		return query;
	}

	public void setQuery(StringProperty query) {
		this.query = query;
	}

	public StringProperty getOntologyPath() {
		return ontologyPath;
	}

	public void setOntologyPath(StringProperty ontologyPath) {
		this.ontologyPath = ontologyPath;
	}

	public StringProperty getDatasetPath() {
		return datasetPath;
	}

	public void setDatasetPath(StringProperty datasetPath) {
		this.datasetPath = datasetPath;
	}

	public IntegerProperty getMaxQueriesProduced() {
		return maxQueriesProduced;
	}

	public void setMaxQueriesProduced(IntegerProperty maxQueriesProduced) {
		this.maxQueriesProduced = maxQueriesProduced;
	}

	public StringProperty getTargets() {
		return targets;
	}

	public void setTargets(StringProperty targets) {
		this.targets = targets;
	}

	public void setParameters(String queryType, double minSimilarity, int maxResultsWanted, String query,
			String ontologyPath, String datasetPath, int maxQueriesProduced, String targets) {
		this.queryType.set(queryType);
		this.minSimilarity.set(minSimilarity);
		this.maxResultsWanted.set(maxResultsWanted);
		this.query.set(query);
		this.ontologyPath.set(ontologyPath);
		this.datasetPath.set(datasetPath);
		this.maxQueriesProduced.set(maxQueriesProduced);
		this.targets.set(targets);
	}

	public String toString() {
		return "Query Type: " + this.queryType.get() + "\nMin. similarity value: " + this.minSimilarity.get()
				+ "\nMax. results wanted: " + this.maxResultsWanted.get() + "\nOntology path: "
				+ this.ontologyPath.get() + "\nDataset path: " + this.datasetPath.get() + "\nMax. queries produced: "
				+ this.maxQueriesProduced.get() + "\nTarget(s): " + this.targets.get() + "\nQuery:\n " + this.query.get()
				+ "\n";
	}
}
