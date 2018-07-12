package gui.view;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Person
{
	// Declaring the attributes page 424
	private String firstName;
	private String lastName;
	private LocalDate birthDate;

	// An enum for age categories
	public enum AgeCategory
	{
		BABY,
		CHILD,
		TEEN,
		ADULT,
		SENIOR,
		UNKNOWN
	};

	public Person(String firstName, String lastName, LocalDate birthDate)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public LocalDate getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate)
	{
		this.birthDate = birthDate;
	}

	@Override
	public String toString()
	{
		return firstName + " " + lastName + ", " + birthDate.toString();
	}

	/* Domain specific business rules */
	public AgeCategory getAgeCategory()
	{
		if (birthDate == null)
		{
			return AgeCategory.UNKNOWN;
		}

		long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());

		if (years >= 0 && years < 2)
		{
			return AgeCategory.BABY;
		}
		else if (years >= 2 && years < 13)
		{
			return AgeCategory.CHILD;
		}
		else if (years >= 13 && years <= 19)
		{
			return AgeCategory.TEEN;
		}
		else if (years > 19 && years <= 50)
		{
			return AgeCategory.ADULT;
		}
		else if (years > 50)
		{
			return AgeCategory.SENIOR;
		}
		else
		{
			return AgeCategory.UNKNOWN;
		}
	}
}