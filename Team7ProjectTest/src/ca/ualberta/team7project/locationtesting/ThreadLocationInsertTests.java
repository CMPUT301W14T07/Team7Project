package ca.ualberta.team7project.locationtesting;

import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserModel;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Covers functionality of location within threads and topics.
 * @author michael
 *
 */
public class ThreadLocationInsertTests extends ActivityInstrumentationTestCase2<MainActivity>
{
	MainActivity mainActivity;
	UserController userController;
	PreferenceModel preference;

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
		preference = userController.getUser();
	}

	
	/**
	 * A newly created thread should have data on the most recent user location
	 */
	public void testThreadLocation() 
	{
		/* Inject a new user location into the user */				
		UserController.updateLocationModel(new LocationModel(50, -20));
		UserModel user = preference.getUser();
		
		assertEquals("User location was set", user.getLocation().getLongitude(), (double)50);
	}
	
}
