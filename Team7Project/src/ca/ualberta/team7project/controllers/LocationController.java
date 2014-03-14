package ca.ualberta.team7project.controllers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.LocationModel;

/* Sources of reference 
 * http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/ as a reference
 * http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
 */

/**
 * Handles location functionality for the entire application.
 * <p>
 * Uses the android LocationManager, Location and Geocoder to update LocationModels
 * for both the user and the sorting preferences.
 * <p>
 * For simplicity (since we aren't concerned with battery life), this LocationController is 
 * always willing to accept connections. 
 */
public class LocationController
{
	private Context context;
	private Location location;
	private static LocationManager locationManager;
	
	private LocationModel userLocation;
	private LocationModel sortingLocation;
		
	private double longitude;
	private double latitude;
	
	public LocationController(Context context, LocationManager locationManager)
	{
		super();
		this.context = context;
		this.setLocationManager(locationManager);
		
		this.setUserLocation(new LocationModel());
		this.setSortingLocation(new LocationModel());
				
	}

	public Boolean updateCoordinates(Location location)
	{
		Boolean updated = false;
		
		if(location != null)
		{
			this.setLatitude(location.getLatitude());
			this.setLongitude(location.getLongitude());
			updated = true;
		}

		/* Notify all active models that coordinates have been updated */
		MainActivity.userListener.locationUpdated(this.longitude, this.latitude);
		
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
	
	public LocationManager getLocationManager()
	{

		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager)
	{
		LocationController.locationManager = locationManager;
	}
	
}
