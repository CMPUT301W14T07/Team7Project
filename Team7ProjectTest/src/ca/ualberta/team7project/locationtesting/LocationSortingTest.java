package ca.ualberta.team7project.locationtesting;


import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.location.LocationComparator;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;


public class LocationSortingTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	private UserModel user;
	private int longitude = 50;
	private int latitude = 50;

	public LocationSortingTest()
	{
		super(MainActivity.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		this.user = new UserModel("Ash");
		this.user.setLocation(new LocationModel(longitude, latitude));
	}

	/**
	 * Compare distance between a set longitude and latitude
	 */
	public void testDistanceFromBase()
	{
		int longitude = 50;
		int latitude = 50;
		LocationComparator comparator;
		
		UserModel userModel = this.user;
		
		userModel.setLocation(new LocationModel(longitude, latitude));
		ThreadModel model = new ThreadModel("Hello", userModel, "World");
				
		comparator = new LocationComparator(latitude, latitude);
		
		int zeroDistance = (int) Math.round(comparator.distanceFromBase(model));
		assertTrue("Distance is zero", approximateDistance(zeroDistance, 0, 0));
		
		/* Location should be about 138000 meters between [50,50] and [51,51] */
		comparator = new LocationComparator(++latitude, ++longitude);
		int distance = (int) comparator.distanceFromBase(model);
		assertTrue("Distance is positive 138000", approximateDistance(distance,138000, 8000));
		
	}
	/*
	 * Helper function to check if distance is approximately correct. Not concerned with accuracy, just that a reasonable distance is returned.
	 * Reuse #1 https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#miscellaneous
	 */
	private boolean approximateDistance(int positionA, int positionB,
			int rounding)
	{
		return Math.abs(positionA - positionB) <= rounding;
	}	
	
}
