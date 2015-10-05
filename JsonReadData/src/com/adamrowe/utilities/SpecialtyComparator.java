package com.adamrowe.utilities;

import java.util.Comparator;

import com.adamrowe.models.Physician;

//Specialty Comparator to sort on specialty name
public class SpecialtyComparator implements Comparator<Physician> 
{
    @Override
    public int compare(Physician physician1, Physician physician2) 
    {
    	if(physician1 != null && physician2 != null)
    	{
    		//First compare on specialty
	    	int priorityComparison = physician1.getSpecialty().compareTo(physician2.getSpecialty());		 
    		if(priorityComparison != 0) //if names aren't equal, return that comparison
    		{
	    		return priorityComparison;
    		} 
    		else
    		{
    			//if they are the same name, compare on name
    			return physician1.getFormattedName().compareTo(physician2.getFormattedName());
    		}	    		
    	}
    	else
    		return -1;
    }
}
