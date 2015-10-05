package com.adamrowe.models;

//POJO Physician Object
public class Physician 
{
	private final String name;
	private final String specialty;
	
	public Physician(String name, String specialty)
	{
		this.name = name;
		this.specialty = specialty;
	}
	
	public final String getName()
	{
		return name;
	}
	
	public final String getSpecialty()
	{
		return specialty;
	}
	
	//get formatted name for displaying last name, first name
	public String getFormattedName()
	{
		String[] nameArray = name.split(" ");
		if(nameArray.length > 1)
		{
			return nameArray[1] + ", " + nameArray[0];
		}
		else return name;
	}

}
