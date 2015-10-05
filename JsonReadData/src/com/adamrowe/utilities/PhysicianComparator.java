package com.adamrowe.utilities;

import java.util.Comparator;

import com.adamrowe.models.Physician;

//Physician Comparator to sort on physician's name
public class PhysicianComparator implements Comparator<Physician> 
{
    @Override
    public int compare(Physician physician1, Physician physician2) 
    {
    	if(physician1 != null && physician2 != null)
    	{
    		//First compare on names
	    	int priorityComparison = physician1.getFormattedName().compareTo(physician2.getFormattedName()); 
    		if(priorityComparison != 0) //if names aren't equal, return that comparison
    		{
	    		return priorityComparison;
    		} 
    		else
    		{
    			//if they are the same name, compare on specialty
    			return physician1.getSpecialty().compareTo(physician2.getSpecialty());
    		}	    		
    	}
    	else
    		return -1;
    }
}
