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
		LocationModel lm = new LocationModel(0.0, 0.0);
		
		
		assertEquals("Should be 0.0", 0.0, lm.getLongitude());
	}
	
	public void testGetLatitude()
	{
		LocationModel lm = new LocationModel(650000000.0, 1000000000.5);
		
		assertEquals("Should be 1000000000.5", 1000000000.5, lm.getLatitude());
		
	}
	
	public void testSetLongitude()
	{
		LocationModel lm = new LocationModel(55, 60);
		lm.setLongitude(99.0);
		
		assertEquals("This should be 99.0", 99.0, lm.getLongitude());
		
	}
	
	public void testSetLatitude()
	{
		LocationModel lm = new LocationModel(1000000, 500000);
		lm.setLatitude(9);
		
		assertEquals("This should be 9.0 now", 9.0, lm.getLatitude(), 0.0);
	}
	
	/* This test fails for some reason */
	public void testGetLocation()
	{
		LocationModel lm = new LocationModel(200.0,150.0);
		double[] testarray = new double[2];
		testarray[0] = (double) 200.0;
		testarray[1] = (double) 150.0;
		
		assertTrue("This is supposed to return same location", lm.getLocation().equals(testarray));
	}
	
	public void testSetLocation()
	{
		LocationModel lm = new LocationModel(9000, 5000);
		lm.setLocation(1, 2);
		
		assertEquals("New long is 1.0", 1.0, lm.getLongitude());
		assertEquals("New lat is 2.0,", 2.0, lm.getLatitude());
		
	}
}
