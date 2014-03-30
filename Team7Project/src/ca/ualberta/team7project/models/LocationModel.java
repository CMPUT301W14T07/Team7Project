package ca.ualberta.team7project.models;

/**
 * Provides location data in the form of longitude/latitude coordinates.
 * <p>
 * Longitude and latitude can be set using the LocationManager android class or
 * through use of Geocoder to provide an address lookup.
 * <p>
 * Location data is saved in a [lon, lat] array to conform with GeoJSON (http://geojson.org/) for 
 * Elasticsearch.
 */
public class LocationModel
{
	private double[] locationInner;

	public LocationModel()
	{
		super();
		this.locationInner = new double[2];
	}
	
	public LocationModel(double longitude, double latitude)
	{
		super();
		this.locationInner = new double[2];
		setLocation(longitude, latitude);
	}
		
	public void setLongitude(double longitude)
	{
		this.locationInner[0] = longitude;
	}
	
	public void setLatitude(double latitude)
	{
		this.locationInner[1] = latitude;
	}
	
	public double getLongitude()
	{
		return this.locationInner[0];
	}
	
	public double getLatitude()
	{
		return this.locationInner[1];
	}
	
	public double[] getLocation()
	{
	
		return locationInner;
	}

	public void setLocation(double longitude, double latitude)
	{
		setLongitude(longitude);
		setLatitude(latitude);
	}
	
	/**
	 * Overrides the java equals method
	 */
	public boolean equals(Object obj)
	{
		/* http://stackoverflow.com/questions/27581/overriding-equals-and-hashcode-in-java*/
		if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof LocationModel))
            return false;
        
        LocationModel model = (LocationModel) obj;
        
        if(model.getLongitude() != this.getLongitude())
        	return false;
        			
        if(model.getLatitude() != this.getLatitude())
        	return false;
        
        return true;
	}
	
}
