package com.adamrowe.jsonstreaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;

import com.adamrowe.utilities.ReadJsonStreamUtilities;
import com.example.jsonreaddata.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/*
 * Async Task to create an InputStream either from Http Endpoint or Local File
 * @param HttpsURLConnection - HttpsURLConnection for parsing JSON data
 * @param Integer - for progress updates if implementation is required
 * @param JSONArray - Return this JSONArray back in Callback.onDataLoaded()
 */
public class StreamAsyncTask extends AsyncTask<HttpsURLConnection, Integer, JSONArray> 
{
	private Activity activity;
	private Callback callback;
	
	public StreamAsyncTask(Activity activity, Callback callback)
	{
		this.activity = activity;
		this.callback = callback;
	}
	
	public interface Callback 
	{
        public void onDataLoaded(JSONArray jsonArray);
    }

	@Override
	protected JSONArray doInBackground(HttpsURLConnection... urlConnections) 
	{
		/* get HttpsURLConnection passed in to AsyncTask */
		HttpsURLConnection urlConnection = urlConnections[0];
		JSONArray jsonArray = null;
		InputStream inputStream = null;
		try 
		{
			if(urlConnection != null) //Web Search
			{
				/* create Buffered Input Stream from urlConnection */
				inputStream = new BufferedInputStream(urlConnection.getInputStream());
			}
			else //Local Search
			{
				/* read input stream from raw resource */
				inputStream = activity.getResources().openRawResource(R.raw.physicians);				
			}
			
			/* convert input stream to String */
			String stringStream = ReadJsonStreamUtilities.readStream(inputStream, "UTF-8");
			
			/* populate JSONArray using String */
			jsonArray = new JSONArray(stringStream);
		}
		catch(final IOException exc)
		{
			Log.e("StreamAsyncTask.IOExc", exc.getMessage());
			activity.runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					Toast.makeText(activity, "IO Exception in StreamAsyncTask: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
		} 
		catch (final JSONException exc) 
		{
			Log.e("StreamAsyncTask.JSONExc", exc.getMessage());
			activity.runOnUiThread(new Runnable() 
			{
				public void run() 
				{
					Toast.makeText(activity, "JSON Exception in StreamAsyncTask: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
		} 
	    finally 
	    {
	    	if(urlConnection != null)
	    	{
		    	urlConnection.disconnect();
	    	}
	    	if(inputStream != null)
	    	{
	    		try 
	    		{
					inputStream.close();
				} 
	    		catch (final IOException exc) 
	    		{
					Log.e("StreamAsyncTaskCloseStream.IOExc", exc.getMessage());
					activity.runOnUiThread(new Runnable() 
					{
						public void run() 
						{
							Toast.makeText(activity, "IO Exception in StreamAsyncTask while closing Input Stream: " + exc.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
	    	}
	    } 
		return jsonArray;
    }

	@Override
    protected void onPostExecute(JSONArray jsonArray)
    {	
    	callback.onDataLoaded(jsonArray);
    }

}
