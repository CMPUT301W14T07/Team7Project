package ca.ualberta.team7project.controllers;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.interfaces.PositionListener;
import ca.ualberta.team7project.location.GeolocationLookup;
import ca.ualberta.team7project.models.LocationModel;

/* Reuse statements https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements 
 * This class borrows heavily from the linked code 
 */

/**
 * Handles location functionality for the entire application.
 * <p>
 * Uses the android LocationManager, Location and Geocoder to update LocationModels
 * for both the user and the sorting preferences.
 * <p>
 * For simplicity (since we aren't concerned with battery life), this LocationController is 
 * always willing to accept connections. 
 * <p>
 * This controller makes heavy use of error checking since any errors have multiplicative
 * effects for the rest of the application. 
 */
public class LocationController extends Activity implements PositionListener
{
	private Context context;
	private Location location;
	private LocationManager locationManager;
	private Criteria criteria = null;
	private String provider = null;
	
	private LocationModel userLocation;
	private LocationModel alternateLocation;
		
	private double longitude;
	private double latitude;
		
	public LocationController(Context context)
	{
		super();
		this.context = context;
		
		this.setUserLocation(new LocationModel());
		this.setAlternateLocation(new LocationModel());
		
		inititiateLocationTracking();
		
		MainActivity.positionListener = this;
	}
		
	/* Extends activity so that this class can act as a listener for LocationManager */
	@Override
	public void onCreate(Bundle savedState)
	{
		super.onCreate(savedState);
	}
	
	/**
	 * Updates the alternate location LocationModel and displays a toast to user with new address
	 */
	@Override
	public void updateSetLocation(Address address)
	{	
		alternateLocation.setLongitude(address.getLongitude());
		alternateLocation.setLatitude(address.getLatitude());
		
		/* Toast the user with the new location */
		String local = address.getThoroughfare();
		
		if(local != null)
			MainActivity.userListener.toastAddress(local);
		else
		{
			// Try a broader address, since thoroughfare could not be found.
			local = address.getLocality();
			
			if(local != null)
				MainActivity.userListener.toastAddress(local);
		}
	}

	/**
	 * Performs an address lookup in response to the LocationLookupAlertView
	 */
	@Override
	public void addressLookup(String address)
	{
		new GeolocationLookup().execute(address);
	}
	
	public void inititiateLocationTracking()
	{
		Log.e(MainActivity.DEBUG, "Attempting to initiate tracking - LocationController");

		// See issue https://github.com/CMPUT301W14T07/Team7Project/issues/29
		/* Set the location manager */
		try{
			locationManager = (LocationManager) MainActivity.getMainContext().getSystemService(LOCATION_SERVICE);
		}catch (Exception e) {
			/* Could not initiate location manager. Perhaps not enabled on the phone */
			// See issue #29 for work to be done in try catch blocks still.
			Log.e(MainActivity.DEBUG, "Could not initiate location manager - LocationController");
		}

		/* Find the best provider */
		try{
			criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, false);
		}catch (Exception e) {
			/* Did not find an appropriate provider */
			Log.e(MainActivity.DEBUG, "Could not find provider - LocationController");
		}
		
		/* Use the provider to find a location */
		try{
			location = locationManager.getLastKnownLocation(provider);
			locationManager.requestLocationUpdates(provider, 0, 0, this);
		}catch (Exception e) {
			/* Could not request location */
			Log.e(MainActivity.DEBUG, "Could not find location - LocationController");
		}
		
		forceLocationUpdate();
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

		MainActivity.userListener.locationModelUpdate(userLocation);
		
		Log.e(MainActivity.DEBUG, "Location has changed");
		return updated;
	}
	
	public void setLocationSettings(Criteria criteria, String provider)
	{
		this.setCriteria(criteria);
		this.setProvider(provider);
	}
		
	/* Reuse statement #4 
	 * https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#locationcontroller */
	/**
	 * Force a location update from locationManager
	 * <p>
	 * If the user never moves from the original location, then onLocationChanged() is never called.
	 * Therefore, this method is used to ensure that we have a location for the user.
	 */
	@Override
	public void forceLocationUpdate()
	{
		Log.e(MainActivity.DEBUG, "Forcing location update - LocationController");

		try {
			location = locationManager.getLastKnownLocation(provider);
			updateCoordinates(location);
		}
		catch (IllegalArgumentException e) {
			/* If illegal argument, then location was null */
			// issue # 29, haven't decided how to handle errors yet.
			Log.e(MainActivity.DEBUG, "Could not force update - LocationController");
		}
	}

	@Override
	public void onLocationChanged(Location location)
	{
		updateCoordinates(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras){}

	@Override
	public void onProviderEnabled(String provider){}

	@Override
	public void onProviderDisabled(String provider){}

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

	public LocationModel getAlternateLocation()
	{
		return alternateLocation;
	}

	public void setAlternateLocation(LocationModel alternateLocation)
	{
		this.alternateLocation = alternateLocation;
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

		this.locationManager = locationManager;
	}


	public void setCriteria(Criteria criteria)
	{

		this.criteria = criteria;
	}

	public String getProvider()
	{

		return provider;
	}

	public void setProvider(String provider)
	{

		this.provider = provider;
	}

}
