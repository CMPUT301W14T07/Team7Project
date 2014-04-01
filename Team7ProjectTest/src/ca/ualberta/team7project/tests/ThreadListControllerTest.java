package ca.ualberta.team7project.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.ThreadModel;


public class ThreadListControllerTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	Activity activity;
	
	public ThreadListControllerTest()
	{
		super(MainActivity.class);
	}
	
	public void testAddFavorite()
	{
		this.activity = getActivity();
		ThreadListController controller = new ThreadListController(activity);
		CacheOperation tool = new CacheOperation();
		PreferenceModel preference = new PreferenceModel("Blue");
		ThreadModel thread = new ThreadModel("Can't get Electabuzz", preference.getUser(), "Blue's problems");
		
		assertEquals("Should be empty", 0, ThreadModelPool.threadModelPool.size());
		tool.saveFile(activity);
		
		
	}
	
}