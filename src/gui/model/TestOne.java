package gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TestOne {

	private StringProperty query = new SimpleStringProperty();
	private StringProperty target = new SimpleStringProperty();

	public TestOne() {
		this.query.set("");
		this.target.set("");
	}

	public TestOne(String query) {
		this.query.set(query);
		this.target.set("");
	}
	
	public TestOne(String query, String target) {
		this.query.set(query);
		this.target.set(target);
	}

	public StringProperty getQuery() {
		return query;
	}

	public void setQuery(StringProperty query) {
		this.query = query;
	}

	public StringProperty getTarget() {
		return target;
	}

	public void setTarget(StringProperty target) {
		this.target = target;
	}

	public String toString() {
		return "Query: " + this.query.get() + " ;Target(s) : " + this.target.get();
	}

}
