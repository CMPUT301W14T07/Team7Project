package ca.ualberta.team7project.controllers;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.interfaces.PositionListener;
import ca.ualberta.team7project.models.LocationModel;

/* Reuse statements https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements */

/**
 * Handles location functionality for the entire application.
 * <p>
 * Uses the android LocationManager, Location and Geocoder to update LocationModels
 * for both the user and the sorting preferences.
 * <p>
 * For simplicity (since we aren't concerned with battery life), this LocationController is 
 * always willing to accept connections. 
 */
public class LocationController implements PositionListener
{
	private Context context;
	private Location location;
	
	private LocationModel userLocation;
	private LocationModel sortingLocation;
		
	private double longitude;
	private double latitude;
		
	public LocationController(Context context)
	{
		super();
		this.context = context;
	
		this.setUserLocation(new LocationModel());
		this.setSortingLocation(new LocationModel());
						
	}
	
	/**
	 * Is called when the GPS/Network updates the position.
	 * <p>
	 * Longitude/Latitude are saved and UserModel is updated.
	 * 
	 * @param location of the GPS or network position
	 * @return update True if the location was updated properly.
	 */
	public Boolean updateCoordinates(Location location)
	{
		Boolean updated = false;
		
		if(location != null)
		{
			setLatitude(location.getLatitude());
			setLongitude(location.getLongitude());
			
			/* Notify UserModel of the changes */
			LocationModel locationModel = new LocationModel(longitude, latitude);
			setUserLocation(locationModel);
			
			updated = true;
		}

		/* Notify all active models that coordinates have been updated */
		MainActivity.userListener.locationUpdated(this.longitude, this.latitude);
		
		Log.e(MainActivity.DEBUG, "Location has changed");
		
		return updated;
	}
	
	public Location getLocation()
	{		
		return location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
	}
	
	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public LocationModel getUserLocation()
	{
		return userLocation;
	}

	public void setUserLocation(LocationModel userLocation)
	{
		this.userLocation = userLocation;
	}

	public LocationModel getSortingLocation()
	{
		return sortingLocation;
	}

	public void setSortingLocation(LocationModel sortingLocation)
	{
		this.sortingLocation = sortingLocation;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	@Override
	public void requestLocationUpdate()
	{
		// See issue https://github.com/CMPUT301W14T07/Team7Project/issues/29
		MainActivity.requestLocation();
	}	
}
