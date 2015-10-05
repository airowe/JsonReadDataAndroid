package com.adamrowe.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adamrowe.models.Physician;

import android.util.Log;

public class ReadJsonStreamUtilities 
{
	private static HttpsURLConnection urlConnection;
	
	//Parse HttpsURLConnection from URL. Connection is closed where it's referenced
	public static HttpsURLConnection parseURLConnection(String urlString) throws IOException
	{
		URL url = new URL(urlString);
		urlConnection = (HttpsURLConnection) url.openConnection();
		
		return urlConnection;
	}

	//Performance-focused Utility method to read Input Stream and convert to String
	public static String readStream(InputStream inputStream, String encoding) throws IOException 
	{
		StringBuilder total = new StringBuilder();
		 final int BLOCK_BUFFER_SIZE = 4096; //One block
		 byte[] buf = new byte[BLOCK_BUFFER_SIZE];
		 int len;
         while((len = inputStream.read(buf)) > 0) //while input stream's length is greater than 0
        	total.append(new String(buf, encoding)); //append new string to string builder
         
		return total.toString();
		
	}
	
	//Utility method to populate physicians list from JSONArray
	public static List<Physician> populatePhysicians(JSONArray jsonArray)
	{
		List<Physician> physicians = new ArrayList<Physician>();
		
		if(jsonArray != null)
		{
			for(int i = 0; i < jsonArray.length(); i++) //iterate through JSONArray
			{
				try
				{
					//Get JSONObject representation of Physician
					JSONObject jsonPhysician = jsonArray.getJSONObject(i);
					
					//Instantiate POJO Physician from properties of JSONObject
					Physician physician = new Physician(jsonPhysician.getString("name"),jsonPhysician.getString("specialty"));
					physicians.add(physician);
				}
				catch(JSONException exc)
				{
					Log.e("ReadJsonStreamUtilities.populatePhysicians.JSONExc", exc.getMessage());
				}
			}
		}
		else
			Log.i("populatePhysicians", "jsonArray Null");
		
		return physicians;
	}
}
