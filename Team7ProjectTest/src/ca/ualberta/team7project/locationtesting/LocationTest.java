package ca.ualberta.team7project.locationtesting;

import java.util.Locale;

import android.location.Address;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.controllers.LocationController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserModel;


public class LocationTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	/*
	 * Must be using a Google AVD to run these tests properly
	 */
	
	MainActivity mainActivity;

	double latitude = 53.0;
	double longitude = 100.0;

	
	public LocationTest()
	{
		super(MainActivity.class);

	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		mainActivity = getActivity();
	}

	/**
	 * Ensure that address lookup is returning a non null result and that the location is properly set
	 * 
	 * Sometimes this test fails from geolocation api query response time.
	 */
	@SuppressWarnings("static-access")
	public void testAlternateLocation()
	{
		LocationController locationController = mainActivity.getLocationController();
		locationController.addressLookup("University of Alberta");
		
		sleep();
		
		/* Once the thread completes the alternate location should not be null */
		LocationModel locationModel = locationController.getAlternateLocation();
		assertNotNull("location not null", locationModel);
		
		/* Alternate location should be somewhere close to the longitude/latitude for University of Alberta */
		assertTrue("Longitude is approximate", approximatePosition(locationModel.getLongitude(), (double) -113.5, 5));
		assertTrue("Latitude is approximate", approximatePosition(locationModel.getLatitude(), (double) 53.5, 5));
	}

	/**
	 * Inject coordinates into a Address and see if location controller update the models correctly
	 * <p>
	 * updateSetLocation() is called in response to user input of an address from the alert view.
	 * updateCoordinates() is called when the LocationManager notifies the controller of a new position.
	 * <p>
	 * Note that locationController caches LocationModels as attributes before forwarding them to the UserModel elsewhere.
	 */
	@SuppressWarnings("static-access")
	public void testLocationUpdates()
	{
		LocationController locationController = mainActivity.getLocationController();
				
		/* Check that a call from the AlertView will update the setLocation correctly */
		Address address = new Address(Locale.getDefault());
		address.setLongitude(longitude);
		address.setLatitude(latitude);
		
		locationController.updateSetLocation(address);		
		LocationModel setLocation = locationController.getAlternateLocation();
		
		assertEquals("setLocation was updated in the controller", setLocation.getLatitude(), latitude);
		assertEquals("setLocation was updated in the controller", setLocation.getLongitude(), longitude);

		/* Check that a call from the LocationManager will update the userLocation correctly */
		Location loc = new Location("Some Provider");
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		
		boolean updated = locationController.updateCoordinates(loc);
		assertTrue("Location Model was not null, and user location set", updated);
		
		LocationModel userLocation = locationController.getUserLocation();
		assertEquals("userLocation was updated in the controller", userLocation.getLatitude(), latitude);
		assertEquals("userLocation was updated in the controller", userLocation.getLongitude(), longitude);
		
	}
	
	/**
	 * Forcing a location update should result in a new LocationModel in the controller and UserModel should be updated
	 */
	@SuppressWarnings("static-access")
	public void testForceLocationUpdate()
	{
		/* Create a new user model since the application may not have one to start with */
		UserController userController = mainActivity.getUserController();
		userController.createNewUser("Mark Watney");
		PreferenceModel preference = userController.getUser();
		UserModel user = preference.getUser();
		
		assertEquals("User model was created", user.getName(), "Mark Watney");
		
		LocationController locationController = mainActivity.getLocationController();
		locationController.forceLocationUpdate();
	
		sleep();

		LocationModel newModel = locationController.getUserLocation();
		
		assertTrue("User location was updated with the force update", newModel.equals(user.getLocation()));
	}

	/*
	 * Helper function to check if longitude/latitude is approximately correct. (Not used in actual application)
	 * Reuse #1 https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#miscellaneous
	 */
	private boolean approximatePosition(double positionA, double positionB,
			int rounding)
	{
		return Math.abs(positionA - positionB) <= rounding;
	}

	/* Puts the thread to sleep since it is not possible to perform injection on GeolocationLookup */
	public void sleep()
	{
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/* Make sure approximatePosition is working for tests that will follow */
	public void testApproximatePosition()
	{
		assertTrue(approximatePosition(0, 0, 0));
		assertTrue(approximatePosition(5, 5, 0));
		assertTrue(approximatePosition(5, 4, 1));
		assertTrue(approximatePosition(-5, -8, 3));
		assertFalse(approximatePosition(5, 3, 1));
	}

}
