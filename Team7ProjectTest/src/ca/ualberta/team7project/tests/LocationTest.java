package ca.ualberta.team7project.tests;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.controllers.LocationController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserModel;


public class LocationTest extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	private MainActivity mainActivity;
	
	public LocationTest()
	{
		super(MainActivity.class);
		mainActivity = getActivity();
	}
    
	/**
	 * Ensure that address lookup is returning a non null result and that the location is properly set
	 */
	public void testAlternateLocation()
	{
		LocationController locationController = mainActivity.getLocationController();
		
		/* Set alternate location to null before beginning test so that we can check against it */
		locationController.setAlternateLocation(null);
		
		locationController.addressLookup("University of Alberta");		
				
		/* Wait a reasonable time for the thread to finish. Perhaps there is a better way to do this? */
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		/* Once the thread completes the alternate location should not be null */
		LocationModel locationModel = locationController.getAlternateLocation();
		assertNotNull("location not null", locationModel);
		assertNotNull("Alerternate location is not null", locationController.getAlternateLocation());
	}
	
	/**
	 * Forcing a location update should result in a new LocationModel in the controller and UserModel should be updated
	 */
	public void testForceLocationUpdate()
	{
		LocationController locationController = mainActivity.getLocationController();
		
		/* Save the old locationModel to test against the one set in the forced update */
		LocationModel oldModel = locationController.getUserLocation();
		
		locationController.forceLocationUpdate();
		/* Wait a reasonable time for the thread to finish. Perhaps there is a better way to do this? */
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		LocationModel newModel = locationController.getUserLocation();
		assertFalse("Location models should be different", newModel.equals(oldModel));
		
		UserController userController = mainActivity.getUserController();
		PreferenceModel preference = userController.getUser();
		UserModel user = preference.getUser();
		
		assertTrue("User location was updated with the force update", newModel.equals(user.getLocation()));
	}

}
