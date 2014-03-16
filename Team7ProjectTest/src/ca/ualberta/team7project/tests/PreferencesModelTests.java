package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.ThreadModel;
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
		PreferenceModel preference = new PreferenceModel("BoB");

		ThreadModel thread = new ThreadModel("Bob's favorite movies", preference.getUser(), UUID.randomUUID(), UUID.randomUUID());
		preference.addFavoriteTopic(thread);
		
		ArrayList<ThreadModel> favorites = new ArrayList<ThreadModel>();
		favorites.add(thread);
		
		assertEquals("Favorites were inserted correctly", favorites, preference.getFavoriteTopics());
	}
	
	public void testAuthoredComments()
	{
		PreferenceModel preference = new PreferenceModel("BoB");

		ThreadModel thread = new ThreadModel("Bob's favorite places to eat", preference.getUser(), UUID.randomUUID(), UUID.randomUUID());
		preference.addAuthoredComment(thread);
		
		ArrayList<ThreadModel> restaurants = new ArrayList<ThreadModel>();
		restaurants.add(thread);
		
		assertEquals("Authored comments were inserted correctly", restaurants, preference.getAuthoredComments());

	}
}
