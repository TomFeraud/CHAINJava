package gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Contains the major components of a match: initial word, semantic relation and
 * repaired word This is used to display the matches in a TreeTableView in
 * DisplayMatchesController
 * 
 * @author Tom Feraud
 *
 */

public class Matches {
	private StringProperty initial = new SimpleStringProperty();
	private StringProperty relation = new SimpleStringProperty();
	private StringProperty repaired = new SimpleStringProperty();

	/**
	 * Default constuctor
	 * 
	 */
	public Matches() {
		this.initial.set("");
		this.relation.set("");
		this.repaired.set("");
	}

	/**
	 * Constructor
	 * 
	 * @param initial
	 * @param relation
	 * @param repaired
	 */
	public Matches(String initial, String relation, String repaired) {
		this.initial.set(initial);
		this.relation.set(relation);
		this.repaired.set(repaired);
	}

	/**
	 * Gets the initial word
	 * 
	 * @return initial
	 */
	public StringProperty getInitial() {
		return initial;
	}

	/**
	 * Sets the initial word
	 * 
	 * @param initial
	 */
	public void setInitial(StringProperty initial) {
		this.initial = initial;
	}

	/**
	 * Gets the semantic relation
	 * 
	 * @return relation
	 */
	public StringProperty getRelation() {
		return relation;
	}

	/**
	 * Sets the semantic relation
	 * 
	 * @param relation
	 */
	public void setRelation(StringProperty relation) {
		this.relation = relation;
	}

	/**
	 * Gets the repaired word
	 * 
	 * @return repaired
	 */
	public StringProperty getRepaired() {
		return repaired;
	}

	/**
	 * Sets the repaired word
	 * 
	 * @param repaired
	 */
	public void setRepaired(StringProperty repaired) {
		this.repaired = repaired;
	}

}
