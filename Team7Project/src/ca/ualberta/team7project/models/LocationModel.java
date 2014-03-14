package ca.ualberta.team7project.models;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Provides location data in the form of longitude/latitude coordinates.
 * <p>
 * Longitude and latitude can be set using the LocationManager android class or
 * through use of Geocoder to provide an address lookup.
 * <p>
 * Location data is saved in a [lon, lat] array to conform with GeoJSON (http://geojson.org/).
 */

// Using http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/ as a reference
// http://www.elasticsearch.org/guide/en/elasticsearch/reference/current/mapping-geo-point-type.html
public class LocationModel extends Service implements LocationListener
{
	private Context context;
	private Location location;
	private double latitude;
	private double longitude;

	
	public LocationModel(Context context)
	{
		super();
		this.setContext(context);
		this.location = getLocation();
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

	public Location getLocation()
	{
		try{
			LocationManager locationManager = (LocationManager)this.context.getSystemService(LOCATION_SERVICE);

			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 10, this);
			Log.e("debug", "started location manager");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return location;
	}

	
	public void setLocation(Location location)
	{
	
		this.location = location;
	}

	
	public double getLatitude()
	{
	
		return latitude;
	}

	
	public void setLatitude(double latitude)
	{
	
		this.latitude = latitude;
	}

	
	public double getLongitude()
	{
	
		return longitude;
	}

	
	public void setLongitude(double longitude)
	{
	
		this.longitude = longitude;
	}
	

	public Context getContext()
	{

		return context;
	}


	public void setContext(Context context)
	{

		this.context = context;
	}

}
