package ca.ualberta.team7project.locationtesting;

import java.util.Locale;

import android.location.Address;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.controllers.LocationController;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserModel;

/**
 * Covers functionality of location within threads and topics.
 * @author michael
 *
 */
public class ThreadLocationInsertTests extends ActivityInstrumentationTestCase2<MainActivity>
{
	MainActivity mainActivity;
	UserController userController;
	ThreadListController threadController;
	LocationController locationController;
	PreferenceModel preference;
	
	double longitude = 50;
	double latitude = -20;

	public ThreadLocationInsertTests()
	{

		super(MainActivity.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		mainActivity = getActivity();
		
		/* Create a new user we need for all the tests */
		UserController.createNewUser("Mark Watney");
		
		userController = MainActivity.getUserController();
		threadController = MainActivity.getListController();
		locationController = MainActivity.getLocationController();
		
		preference = userController.getUser();
	}

	
	/**
	 * A newly created thread should have data on the most recent user location
	 */
	public void testThreadLocation() 
	{	
		
		LocationModel model = new LocationModel(longitude, latitude);
		
		/* Inject a new user location into the user */				
		UserController.updateLocationModel(model);
		UserModel user = preference.getUser();
		
		System.out.println(user.getLocation().getLongitude());
		
		assertEquals("User location was set", user.getLocation().getLongitude(),longitude);
		
		/* Inject an alternate location with a different location*/
		Address address = new Address(Locale.getDefault());
		address.setLongitude(longitude);
		address.setLatitude(latitude);
		
		locationController.updateSetLocation(address);
		
		assertEquals("Alternate location was set", locationController.getAlternateLocation(), model);
		
		/* Test that the alert view is able to correctly attach the locations when the spinner asks for them */
		ThreadAlertView alert = new ThreadAlertView();
		
		LocationModel threadLocation = new LocationModel();
		
		// Spinner handles the mainActivity.getResources().getString(ca.ualberta.team7project.R.string.current_gps) in the actual class implementation.
		// Not really this long of a statement.
		threadLocation = alert.getLocationModel(mainActivity.getResources().getString(ca.ualberta.team7project.R.string.current_gps));
		assertEquals("Alert got GPS location", user.getLocation(), threadLocation);

		threadLocation = alert.getLocationModel(mainActivity.getResources().getString(ca.ualberta.team7project.R.string.alternate_location));
		assertEquals("Alert got alternate location", locationController.getAlternateLocation(), threadLocation);		
	}
	
}
