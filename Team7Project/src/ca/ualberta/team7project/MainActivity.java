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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView.IdentityListener;
import ca.ualberta.team7project.controllers.LocationController;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.interfaces.UserListener;
import ca.ualberta.team7project.views.ActionBarView;

public class MainActivity extends Activity implements IdentityListener, LocationListener
{
	private static UserController userController;
	private static ThreadListController listController;
	private static LocationController locationController;
	
	/* mainContext is necessary for casting to all listeners and is used in dialog fragments */
	private static Context mainContext;

	private static ThreadListener threadListener;
	private static UserListener userListener;	
	
	private static LocationManager locationManager;
	private Criteria criteria;
	private String provider = null;

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
		
		/* Set the location manager and choose the best provider (GPS or Network) */
		MainActivity.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		this.criteria = new Criteria();
		this.provider = MainActivity.locationManager.getBestProvider(criteria, false);
		MainActivity.locationManager.getLastKnownLocation(provider);
		MainActivity.locationManager.requestLocationUpdates(this.provider, 1, 1, this);

		
		MainActivity.userController = new UserController(context, fragment);
		MainActivity.listController = new ThreadListController(this);
		MainActivity.setLocationController(new LocationController(context, MainActivity.locationManager));
		
		/* Cast the listeners to the MainActivity for passing button clicks between asynchronous classes */
		this.setThreadListener(((ca.ualberta.team7project.MainActivity)MainActivity.mainContext). // TODO (~michael Reminder to look at NPE latter)
				getListController().getListView());
		this.setUserListener(MainActivity.getUserController().getUserView()); // TODO (~michael Reminder to look at NPE latter)

	}

	// TODO onResume ~ was crashing application when I tried to insert it here.

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
	
	// TODO onIdentityPositiveClick should be moved to UserView. However, getting null pointer.
	
	/**
	 * Takes the user name and updates the user model.
	 * <p>
	 * An onDialigPositiveClick is received only when creating a new UserModel.
	 */
	@Override
	public void onIdentityCreate(String userName)
	{				
		userController.setContext(getApplicationContext());
		userController.setFragment(getFragmentManager());
		userController.createNewUser(userName);
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

	@Override
	public void onLocationChanged(Location location)
	{
		Log.e("debug", "location changed");
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		
	}
}
