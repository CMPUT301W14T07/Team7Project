package ca.ualberta.team7project.controllers;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
	private Location location;
	private LocationManager locationManager;
	private Criteria criteria = null;
	private String provider = null;
	
	private LocationModel userLocation;
	private LocationModel alternateLocation;
				
	public LocationController()
	{
		super();
		
		this.userLocation = new LocationModel();
		this.alternateLocation = new LocationModel();
		
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
		alternateLocation = new LocationModel(address.getLongitude(), address.getLatitude());
		
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
		// See issue https://github.com/CMPUT301W14T07/Team7Project/issues/29
		/* Set the location manager */
		try{
			locationManager = (LocationManager) MainActivity.getMainContext().getSystemService(LOCATION_SERVICE);
		}catch (Exception e) {
			/* Could not initiate location manager. Perhaps not enabled on the phone */
		}

		/* Find the best provider */
		try{
			criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, false);
		}catch (Exception e) {
			/* Did not find an appropriate provider */
		}
		
		/* Use the provider to find a location */
		try{
			location = locationManager.getLastKnownLocation(provider);
			locationManager.requestLocationUpdates(provider, 0, 0, this);
		}catch (Exception e) {
			/* Could not request location */
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
			this.userLocation = new LocationModel(location.getLongitude(), location.getLatitude());			
			updated = true;
		}		

		MainActivity.userListener.locationModelUpdate(userLocation);
		return updated;
	}
	
	public void setLocationSettings(Criteria criteria, String provider)
	{
		this.criteria = criteria;
		this.provider = provider;
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
		try {
			location = locationManager.getLastKnownLocation(provider);
			updateCoordinates(location);
		}
		catch (IllegalArgumentException e) {
			/* If illegal argument, then location was null */
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

	
	public LocationModel getUserLocation()
	{
		return userLocation;
	}

	public LocationModel getAlternateLocation()
	{
		return alternateLocation;
	}

}
