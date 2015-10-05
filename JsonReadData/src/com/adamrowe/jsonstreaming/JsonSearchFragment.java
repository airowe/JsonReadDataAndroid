package com.adamrowe.jsonstreaming;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;

import com.adamrowe.adapters.PhysiciansAdapter;
import com.adamrowe.main.MainActivity;
import com.adamrowe.models.Physician;
import com.adamrowe.utilities.PhysicianComparator;
import com.adamrowe.utilities.ReadJsonStreamUtilities;
import com.adamrowe.utilities.SpecialtyComparator;
import com.example.jsonreaddata.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class JsonSearchFragment extends Fragment implements StreamAsyncTask.Callback
{
	private static final String urlString = "https://test-oakz.axialexchange.com/android_challenge/physicians";
	private static final int WebSearchItemIndex = 0;
	private static final int LocalSearchItemIndex = 1;
	
	private Button parseButton;
	
	//Switch to sort by Physician or Specialty
	private Switch sortBySwitch;
	
	private ListView physiciansListView;
	
	static List<Physician> physicians;
	
	private EventHandler eventHandler;
	private StreamAsyncTask streamAsyncTask;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.physicians_search_layout, container, false);
		
	    eventHandler = new EventHandler();
		
	    parseButton = (Button) rootView.findViewById(R.id.parseButton);		
	    parseButton.setOnClickListener(eventHandler);
	    
	    sortBySwitch = (Switch) rootView.findViewById(R.id.sortBySwitch);
	    sortBySwitch.setEnabled(false); //disabled by default as the physicians list is empty
	    sortBySwitch.setOnCheckedChangeListener(eventHandler);
	    
	    physiciansListView = (ListView) rootView.findViewById(R.id.physiciansListView);
		
		return rootView;
	}
	
	/*
	 * Method to populate the Physicians' List View and set adapter
	 * @param List<Physician> physicians - list of physician objects
	 */
	protected void populatePhysiciansListView(List<Physician> physicians)
	{
		PhysiciansAdapter physiciansAdapter = new PhysiciansAdapter(getActivity(),physicians);
		physiciansListView.setAdapter(physiciansAdapter);
	}
	
	/*
	 * Private Class EventHandler captures checked change events 
	 * CompoundButton.OnCheckedChange - SortBy Switch  
	 */
	protected class EventHandler implements View.OnClickListener, OnCheckedChangeListener
	{
		@Override
		public void onClick(View view) 
		{
			ViewPager viewPager = ((MainActivity) getActivity()).getPhysiciansSearchViewPager();
			
			if(viewPager.getCurrentItem() == WebSearchItemIndex) //Web Search
			{
				try 
				{
					/* parse Https URL Connection from URL String */
					HttpsURLConnection urlConnection = ReadJsonStreamUtilities.parseURLConnection(urlString);
					
					/* Execute AsyncTask with injected urlConnection */
					streamAsyncTask.execute(urlConnection);					
				}
				catch(IOException exc) 
				{
					Log.e("parseButtonOnClick.IOExc", exc.getMessage());
				}	
			}
			else if(viewPager.getCurrentItem() == LocalSearchItemIndex) //Local Search
			{
				/* Execute Async Task with null HttpsURLConnection */
				streamAsyncTask.execute((HttpsURLConnection) null);
			}
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
		{
			/* if checked (Physicians), sort by Physician's formatted name */
			if(isChecked)
			{
				Collections.sort(physicians, new PhysicianComparator());
				populatePhysiciansListView(physicians);
			}
			else /* if unchecked (Specialty), sort by Specialty name */
			{
				Collections.sort(physicians, new SpecialtyComparator());
				populatePhysiciansListView(physicians);
			}
			
		}
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		/* Instantiate AsyncTask */
		streamAsyncTask = new StreamAsyncTask(getActivity(), this);
	}

	@Override
	public void onDataLoaded(JSONArray jsonArray) 
	{
		if(jsonArray != null)
		{
			//populate physicians list using jsonArray
			JsonSearchFragment.physicians = ReadJsonStreamUtilities.populatePhysicians(jsonArray);
			
			if(!JsonSearchFragment.physicians.isEmpty())
	    	{
				/* after parsing, set switch enabled (was disabled by default) and set checked (default to Physicians) */
	    		sortBySwitch.setEnabled(true);
	    		sortBySwitch.setChecked(true);	
	    		
	    		/* Set Parse Button disabled so AsyncTask can't be executed */
	    		parseButton.setEnabled(false);
	    	}			
		}
	}
}
