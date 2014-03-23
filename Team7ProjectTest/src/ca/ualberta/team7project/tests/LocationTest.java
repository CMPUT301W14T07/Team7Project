package ca.ualberta.team7project.tests;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.LocationModel;


public class LocationTest extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	public LocationTest()
	{
		super(MainActivity.class);
	}
    
	/*
	 * When the user inputs and alternate location, the value should be set in the controller
	 */
	public void testAlternateLocation()
	{
		MainActivity.getLocationController().addressLookup("University of Alberta");
				
		LocationModel location = MainActivity.getLocationController().getAlternateLocation();
		
		assertNotNull("location not null", location);
		
		/* geocoder coordinates for uofa are ~53 for the latitude and ~-113 fore longitude */
		double latitude = location.getLatitude();
		
		boolean result = false;
		
		if(latitude > 50 & latitude < 55)
			result = true;
	
		assertTrue("locations were set", result);
	}
}
