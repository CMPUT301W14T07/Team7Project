package ca.ualberta.team7project.controllers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import ca.ualberta.team7project.models.LocationModel;

//Using http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/ as a reference
//http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html

/**
 * Handles location functionality for the entire application.
 * <p>
 * Uses the android LocationManager, Location and Geocoder to update LocationModels
 * for both the user and the sorting preferences.
 * <p>
 * For simplicity (since we aren't concerned with battery life), this LocationController is 
 * always willing to accept connections. 
 */
public class LocationController extends Service implements LocationListener
{

	private Context context;
	private LocationModel userLocation;
	private LocationModel sortingLocation;
	
	public LocationController(Context context)
	{
		super();
		this.setContext(context);
		this.setUserLocation(new LocationModel());
		this.setSortingLocation(new LocationModel());
	}

	@Override
	public void onLocationChanged(Location location)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent)
	{

		// TODO Auto-generated method stub
		return null;
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

}
