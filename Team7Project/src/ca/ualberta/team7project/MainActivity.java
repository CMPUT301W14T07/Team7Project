/**
 * The main view associated with the application.
 * <p>
 * Contains a list of all topics or threads depending on user selections.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView.IdentityListener;
import ca.ualberta.team7project.controllers.LocationController;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.interfaces.UserListener;
import ca.ualberta.team7project.views.ActionBarView;

public class MainActivity extends Activity implements IdentityListener
{
	public static final String DEBUG = "debug"; // Log statements 
	
	private static UserController userController;
	private static ThreadListController listController;
	private static LocationController locationController;
	
	/* mainContext is necessary for casting to all listeners and is used in dialog fragments */
	private static Context mainContext;

	// See issue https://github.com/CMPUT301W14T07/Team7Project/issues/28
	public static ThreadListener threadListener;
	public static UserListener userListener;

	/**
	 * Creates the state of the application when the activity is initialized
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(ca.ualberta.team7project.R.layout.thread_list_view);
				
		Context context = getApplicationContext();
		FragmentManager fragment = getFragmentManager();
		mainContext = this;
		
		ActionBar actionBar = getActionBar();
		actionBar.show();
		
		//MainActivity.locationController = new LocationController(context, locationManager);
		MainActivity.userController = new UserController(context, fragment);
		MainActivity.listController = new ThreadListController(this);
		MainActivity.locationController = new LocationController(context);
		
	}
		
	
	@Override
	public void onBackPressed()
	{
		if(!listController.exitThread())
		{
			//exit the application
			finish();
		}
	}

	// TODO onResume https://github.com/CMPUT301W14T07/Team7Project/issues/26

	/**
	 * Places all items for the action bar in the application menu.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getMenuInflater().inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Handles button clicks in the action bar
	 * <p>
	 * When a user clicks a button, actionBarController is called with the item, 
	 * where the request is handled by calling controllers for other models/views.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		ActionBarView actionBarView = new ActionBarView(item, getFragmentManager(), 
				getApplicationContext(), this);
		
		return actionBarView.getAction();
	}
	
	/*
	 * TODO Move onIdentityCreate to UserView - There exist some null pointer errors when moved right now.
	 * https://github.com/CMPUT301W14T07/Team7Project/issues/27
	 */
	
	/**
	 * Takes the user name and updates the user model.
	 * <p>
	 * An onIdentityCreate is received only when creating a new UserModel.
	 */
	@Override
	public void onIdentityCreate(String userName)
	{				
		UserController.createNewUser(userName);
	}
	
	public static UserController getUserController()
	{
	
		return userController;
	}

	public void setController(UserController userController)
	{
	
		MainActivity.userController = userController;
	}
	
	public ThreadListController getListController()
	{
	
		return listController;
	}

	public void setListController(ThreadListController listController)
	{
	
		MainActivity.listController = listController;
	}

	public static Context getMainContext()
	{
	
		return mainContext;
	}

	public void setMainContext(Context mainContext)
	{
	
		MainActivity.mainContext = mainContext;
	}
	
	public void setUserController(UserController userController)
	{
	
		MainActivity.userController = userController;
	}

	public static ThreadListener getThreadListener()
	{

		return threadListener;
	}

	public void setThreadListener(ThreadListener threadListener)
	{

		MainActivity.threadListener = threadListener;
	}

	public UserListener getUserListener()
	{

		return userListener;
	}

	public void setUserListener(UserListener userListener)
	{

		MainActivity.userListener = userListener;
	}

	public static LocationController getLocationController()
	{

		return locationController;
	}

	public static void setLocationController(LocationController locationController)
	{

		MainActivity.locationController = locationController;
	}
	
}
