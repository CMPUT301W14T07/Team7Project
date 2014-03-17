package ca.ualberta.team7project.tests;

import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.LocationModel;
import android.test.ActivityInstrumentationTestCase2;


public class LocationModelTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	public LocationModelTest()
	{
		super(MainActivity.class);
	}

	public void testGetLongitude()
	{
		LocationModel lm = new LocationModel(25.0, 25.0);
		
		
		assertEquals("Should be 25.0", 25.0, lm.getLongitude());
	}
	
	public void testGetLatitude()
	{
		LocationModel lm = new LocationModel(65, 55);
		
		assertEquals("Should be 55.0", 55, lm.getLatitude());
		
	}
}
