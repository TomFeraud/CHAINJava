package gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Matches {
	private StringProperty initial = new SimpleStringProperty();
	private StringProperty relation = new SimpleStringProperty();
	private StringProperty repaired = new SimpleStringProperty();
	
	public Matches() {
		this.initial.set("");
		this.relation.set("");
		this.repaired.set("");
	}
	
	public Matches(String initial, String relation, String repaired) {
		this.initial.set(initial);
		this.relation.set(relation);
		this.repaired.set(repaired);
	}

	public StringProperty getInitial() {
		return initial;
	}

	public void setInitial(StringProperty initial) {
		this.initial = initial;
	}

	public StringProperty getRelation() {
		return relation;
	}

	public void setRelation(StringProperty relation) {
		this.relation = relation;
	}

	public StringProperty getRepaired() {
		return repaired;
	}

	public void setRepaired(StringProperty repaired) {
		this.repaired = repaired;
	}

}
