package gui.view;

import java.time.LocalDate;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class TESTsite
{
	@SuppressWarnings("unchecked")
	public static TreeItem<Person> getModel()
	{
		// Create all persons
		// First level
		Person person1 = new Person("FirstName1", "LastName1", LocalDate.of(1930, 1, 1));

		// Second level
		Person person2 = new Person("FirstName2", "LastName2", LocalDate.of(1956, 12, 17));
		Person person3 = new Person("FirstName3", "LastName3", LocalDate.of(1961, 3, 1));
		Person person4 = new Person("FirstName4", "LastName4", LocalDate.of(1968, 1, 12));
		Person person5 = new Person("FirstName5", "LastName5", LocalDate.of(1978, 4, 14));

		// Third level
		Person person6 = new Person("FirstName6", "LastName6", LocalDate.of(1980, 5, 10));
		Person person7 = new Person("FirstName7", "LastName7", LocalDate.of(1981, 3, 20));
		Person person8 = new Person("FirstName8", "LastName8", LocalDate.of(1982, 6, 3));
		Person person9 = new Person("FirstName9", "LastName9", LocalDate.of(1990, 8, 27));
		Person person10 = new Person("FirstName10", "LastName10", LocalDate.of(1994, 5, 15));

		// Fourth level
		Person person11 = new Person("FirstName11", "LastName11", LocalDate.of(2010, 6, 3));
		Person person12 = new Person("FirstName12", "LastName12", LocalDate.of(2012, 10, 11));
		Person person13 = new Person("FirstName13", "LastName13", LocalDate.of(2012, 10, 11));

		// Build nodes
		TreeItem<Person> person6Node = new TreeItem<>(person6);
		person6Node.getChildren().addAll(new TreeItem<>(person11), new TreeItem<>(person12));

		TreeItem<Person> person7Node = new TreeItem<>(person7);
		person7Node.getChildren().addAll(new TreeItem<>(person13));

		TreeItem<Person> person2Node = new TreeItem<>(person2);
		person2Node.getChildren().addAll(person6Node, new TreeItem<>(person8),person7Node);

		TreeItem<Person> person3Node = new TreeItem<>(person3);
		person3Node.getChildren().addAll(new TreeItem<>(person9), new TreeItem<>(person10));

		TreeItem<Person> person4Node = new TreeItem<>(person4);
		TreeItem<Person> person5Node = new TreeItem<>(person5);

		// Create the root node and add children
		TreeItem<Person> rootNode = new TreeItem<>(person1);
		rootNode.getChildren().addAll(person2Node, person3Node, person4Node, person5Node);

		return rootNode;
	}

	// Returns Person Id TreeTableColumn
	public static TreeTableColumn<Person, Integer> getIdColumn()
	{
		TreeTableColumn<Person, Integer> idColumn = new TreeTableColumn<>("Id");
		idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("personId"));
		return idColumn;
	}

	// Returns First Name TreeTableColumn
	public static TreeTableColumn<Person, String> getFirstNameColumn()
	{
		TreeTableColumn<Person, String> firstNameCol = new TreeTableColumn<>("First Name");
		firstNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstName"));
		return firstNameCol;
	}

	// Returns Last Name TreeTableColumn
	public static TreeTableColumn<Person, String> getLastNameColumn()
	{
		TreeTableColumn<Person, String> lastNameCol = new TreeTableColumn<>("Last Name");
		lastNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("lastName"));
		return lastNameCol;
	}

	// Returns Birth Date TreeTableColumn 
	public static TreeTableColumn<Person, LocalDate> getBirthDateColumn()
	{
		TreeTableColumn<Person, LocalDate> birthDateCol = new TreeTableColumn<>("Birth Date");
		birthDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("birthDate"));
		return birthDateCol;
	}

	// Returns Age Category TreeTableColumn
	public static TreeTableColumn<Person, Person.AgeCategory> getAgeCategoryColumn()
	{
		TreeTableColumn<Person, Person.AgeCategory> birthDateCol = new TreeTableColumn<>("Age Category");
		birthDateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("ageCategory"));
		return birthDateCol;
	}
}