package ca.ualberta.team7project.location;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.team7project.MainActivity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

/**
 * A helper class to handle multithreading and asynchronous server responses with locations
 * <p>
 * This class can accept multiple addresses, however, method calls typically only send one.
 * 
 * @author michael
 *
 */
public class GeolocationLookup extends AsyncTask<String, Void, ArrayList<Address>>
{
	/* Reuse statements
	 * https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#geolocationlookup
	 */
	
	/**
	 * For each string address, create an Address object and append to an array.
	 * <p>
	 * Exceptions are caught, but not handled.
	 * <p>
	 * Only the most relevant (first) Address is returned for each string query.
	 */
	@Override
	protected ArrayList<Address> doInBackground(String... params)
	{
		ArrayList<Address> locations = new ArrayList<Address>();
		
		for(String address : params)
		{
			Geocoder coder = new Geocoder(MainActivity.getMainContext());
			List<Address> addresses;
			
			try 
			{
				addresses = coder.getFromLocationName(address, 1);	
				locations.add(addresses.get(0));
	    		Log.e("debug", "nope1");

			}
			catch (Exception e)
			{
				// Do nothing..But don't crash the application either. Location will not be used.
	    		Log.e("debug", "exception1");

			}
		}

		return locations;
	}

    protected void onPostExecute(ArrayList<Address> locations) 
    {
    	/*
    	 * For now just use the first address.
    	 * 
    	 * If time, implement an option for the user to save multiple addresses.
    	 */
    	try
    	{
        	MainActivity.getLocationController().updateSetLocation(locations.get(0));
    		Log.e("debug", "nope");

    	}
    	catch (Exception e)
    	{
    		Log.e("debug", "exception");
    	}
    }

}
