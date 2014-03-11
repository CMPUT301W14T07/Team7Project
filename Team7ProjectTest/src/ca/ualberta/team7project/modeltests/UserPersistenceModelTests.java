package ca.ualberta.team7project.modeltests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.models.UserPersistenceModel;


public class UserPersistenceModelTests extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	Activity activity;

	public UserPersistenceModelTests(Class<MainActivity> activityClass)
	{
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
	    this.activity = getActivity();
	}
	
	public void testSerializeUser()
	{
		/* Not sure how we are going to test this right now */
	}
	
	public void testDeserializeUser()
	{
		PreferenceModel user = new PreferenceModel("Bruce Wayne");
		UserPersistenceModel persistence = new UserPersistenceModel(activity.getApplicationContext());

		persistence.serializeUser(user);
		
		PreferenceModel oldUser = persistence.deserializeUser();
		
		assertEquals("Deserialized user is the same", oldUser, user);
		
	}
	
	/* Last open user needs to be successfully set and retrieved in filesystem */
	public void testLastOpenUser()
	{
		UserModel user = new UserModel("Bruce Wayne");
		UserPersistenceModel persistence = new UserPersistenceModel(activity.getApplicationContext());
		
		persistence.setLastOpenUser(user.getName());
		String name = persistence.getLastOpenUser();
		
		assertEquals("Last open user was saved and retrieved from filesystem", name, user.getName());	
	}
}
