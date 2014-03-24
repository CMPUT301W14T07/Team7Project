package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.models.UserPersistenceModel;


public class PreferencesModelTests extends
		ActivityInstrumentationTestCase2<MainActivity>
{
	
	Activity activity;

	public PreferencesModelTests()
	{
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
	    this.activity = getActivity();
	}
	
	/*
	 * This test fails right now.
	 */
	/* Successfully serialize the PreferenceModel to the disk */
	public void testPreferenceSerialize()
	{

		PreferenceModel preference = new PreferenceModel("BoB");
		UserPersistenceModel persistence = new UserPersistenceModel(activity.getApplicationContext());
		
		persistence.serializeUser(preference);
		
		PreferenceModel newPrefernce = persistence.deserializeUser();
		
		assertNotNull("Deserialize was not null", newPrefernce);
		assertEquals("Serialize/Deserialize of PreferenceModel works", newPrefernce, preference);
	}
	
	/* Add favorites */
	public void testAddFavorite()
	{
		PreferenceModel preference = new PreferenceModel("Bob");

		ThreadModel thread = new ThreadModel("Pokemon: The Movie", preference.getUser(), "Bob's favorite movies");
		preference.addFavoriteComment(thread);
		
		ArrayList<UUID> favorites = new ArrayList<UUID>();
		favorites.add(thread.getUniqueID());
		
		assertEquals("Favorites were inserted correctly", preference.getFavoriteComments(), favorites);
	}
	
	
	/* Add to authored comments */
	public void testAuthoredComments()
	{
		PreferenceModel preference = new PreferenceModel("BoB");

		ThreadModel thread = new ThreadModel("Tim Horton's", preference.getUser(), "Bob's favorite places to eat");
		preference.addAuthoredComment(thread);
		
		ArrayList<UUID> restaurants = new ArrayList<UUID>();
		restaurants.add(thread.getUniqueID());
		
		assertEquals("Authored comments were inserted correctly", restaurants, preference.getAuthoredComments());

	}
	
	public void testSetUser()
	{
		PreferenceModel preference = new PreferenceModel("BoB");
		UserModel user = new UserModel("Bill");
		preference.setUser(user);
		
		assertEquals("The user should now be Bill",preference.getUser(),user);
	}
	
	public void testGetUser()
	{
		PreferenceModel preference = new PreferenceModel("BoB");
		
		assertEquals("The user should be BoB", preference.getUser().getName(),"BoB");
	}	
	
}
