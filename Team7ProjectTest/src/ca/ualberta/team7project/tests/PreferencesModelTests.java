package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
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
		CacheOperation tool = new CacheOperation();
		//nice thing about initializing CacheOperation here is we can directly load the save file
		ThreadModelPool.threadModelPool.clear();
		assertEquals("ThreadModelPool should be empty", 0, ThreadModelPool.threadModelPool.size());
		tool.saveFile(activity);
		
		ThreadModel thread = new ThreadModel("Pokemon: The Movie", preference.getUser(), "Bob's favorite movies");
		preference.addFavoriteComment(thread);
		
		ArrayList<UUID> favorites = new ArrayList<UUID>();
		favorites.add(thread.getUniqueID());
		
		assertEquals("Favorites were inserted correctly", preference.getFavoriteComments(), favorites);
		
		favorites.add(thread.getUniqueID());
		favorites.add(thread.getUniqueID());
		assertEquals("No duplicates were entered into ArrayList", preference.getFavoriteComments().size(), 1);

		assertEquals("Should now have one item", 1, ThreadModelPool.threadModelPool.size());
		ThreadModel thread2 = new ThreadModel("Pokemon2: Pikachu's Revenge", preference.getUser(), "More favs");
		//change the threadModelPool directly
		ThreadModelPool.threadModelPool.add(thread2);
		
		tool.loadFile(activity);
		assertEquals("Should be 1 again", 1, ThreadModelPool.threadModelPool.size());
		
	}
	
	public void testAddCache()
	{
		PreferenceModel preference = new PreferenceModel("Bob");

		ThreadModel thread = new ThreadModel("Pokemon: The Movie", preference.getUser(), "Bob's favorite movies");
		preference.addCache(thread);
		
		ArrayList<UUID> cached = new ArrayList<UUID>();
		cached.add(thread.getUniqueID());
		
		assertEquals("Cached thread inserted correctly", preference.getCacheComments(), cached);
		
		
		cached.add(thread.getUniqueID());
		cached.add(thread.getUniqueID());
		assertEquals("No duplicates were entered into ArrayList", preference.getCacheComments().size(), 1);
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
