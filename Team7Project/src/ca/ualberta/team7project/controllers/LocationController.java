package ca.ualberta.team7project.controllers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
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
public class LocationController extends Service implements LocationListener
{
	/* Settings for locationManager */
	private final long PINGTIME = 6000;
	private final long DISTANCE = 10;
	
	private Context context;
	private Location location;
	private LocationManager locationManager;
	private LocationModel userLocation;
	private LocationModel sortingLocation;
		
	private double longitude;
	private double latitude;
	
	public LocationController(Context context)
	{
		super();
		this.setContext(context);
		this.setUserLocation(new LocationModel());
		this.setSortingLocation(new LocationModel());
		this.setLocationManager();
		updateLocation();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		updateCoordinates();
		// Call to update userModel
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
	
	private Boolean isNetworkAvailable()
	{
		return this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	private Boolean isGPSAvailable()
	{
		return this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
		
	private void updateLocation()
	{
		/* Set location manager to update every PINGTIME and only when DISTANCE has eclipsed */
		this.locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 
				DISTANCE, 
				PINGTIME, 
				this);
		
		/* Try to get GPS connection first. If it fails then use network connection */
		if(this.locationManager != null)
		{
			if(!updateLocationFromGPS())
			{
				updatetLocationFromNetwork();
			}
		}
	}
	
	private boolean updatetLocationFromNetwork()
	{
		/* If network is available, update the locations */
		if(isNetworkAvailable())
		{
			this.location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		return updateCoordinates();
	}
	
	private Boolean updateLocationFromGPS()
	{		
		/* If GPS is available, update the locations */
		if(isGPSAvailable())
		{
			this.location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		return updateCoordinates();
	}
	
	public Boolean updateCoordinates()
	{
		Boolean updated = false;
		
		if(location != null)
		{
			this.setLatitude(location.getLatitude());
			this.setLongitude(location.getLongitude());
			updated = true;
		}
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

	public LocationManager getLocationManager()
	{

		return locationManager;
	}

	public void setLocationManager()
	{
		try{
			this.locationManager = (LocationManager)this.context.getSystemService(LOCATION_SERVICE);
		}
		catch (Exception e){
			e.printStackTrace();
			Log.e("debug", "failed to initiate location manager in LocationController");
		}
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

}
