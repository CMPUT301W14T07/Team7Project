/**
 * Handles all functions related to the buttons the user clicks on the action bar
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.MenuItem;
import android.widget.Toast;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView;
import ca.ualberta.team7project.alertviews.LocationLookupAlertView;
import ca.ualberta.team7project.alertviews.SortPreferencesAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.interfaces.ThreadListener;

/**
 * View for the Android action bar
 */
public class ActionBarView extends Activity
{

	private MenuItem item;
	private FragmentManager fragment;
	private Context context;
	private Context mainContext;

	/**
	 * Construction ActionBarView which handles button clicks and calls the appropriate controllers.
	 * @param item The ID associated with the action bar item.
	 * @param fragment from the MainActivity
	 * @param context from the the MainActivity
	 * @param contextMain from the MainActivity is the actual context; not from getApplicationContext()
	 */
	public ActionBarView(MenuItem item, FragmentManager fragment, Context context, Context mainContext)
	{
		this.fragment = fragment;
		this.context = context;
		this.item = item;
		this.mainContext = mainContext;
		
	}

	/**
	 * Takes an item id and calls the associated method to provide button
	 * functionality. to the action bar.
	 * 
	 * @return Boolean representing whether a method has been called associated
	 *         with button.
	 */
	public boolean getAction(MenuItem item)
	{
		switch (item.getItemId())
		{
			case ca.ualberta.team7project.R.id.action_home:
				topicsHome();
				return true;
			case ca.ualberta.team7project.R.id.action_show_favorites:
				showFavorites();
				return true;
			case ca.ualberta.team7project.R.id.action_create_topic:
				createTopic();
				return true;
			case ca.ualberta.team7project.R.id.action_sort:
				sortPreferences();
				return true;
			case ca.ualberta.team7project.R.id.action_geo_location:
				geolocationPreferences();
				return true;
			case ca.ualberta.team7project.R.id.action_refresh:
				refreshView();
				return true;
			case ca.ualberta.team7project.R.id.action_user_preferences:
				userPreferences();
				return true;
		}
		return false;
	}

	
	
	/**
	 * Handles the users request to show favorite threads
	 */
	private void showFavorites()
	{
		MainActivity.getListController().getNavigation().enterFavorites();
	}
	
	/**
	 * Handles the user's request to return to main topic listings
	 */
	private void topicsHome()
	{
		MainActivity.getListController().getNavigation().topicsHome();
	}

	/**
	 * Handles the user's request to set geolocation preferences
	 */
	private void geolocationPreferences()
	{
		LocationLookupAlertView location = new LocationLookupAlertView();
		location.setCancelable(false);
		location.show(fragment, "Set Location Alert");
	}

	/**
	 * Handles the user's request to set user preferences
	 */
	private void userPreferences()
	{
		// at the moment this just creates a new user. Next milestone has proper functionality
		CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
		userAlert.setCancelable(false);
		userAlert.show(fragment, "New User Name Alert");

	}

	/**
	 * Handles the user's request to create a new topic
	 * <p>
	 * threadListView cast to MainActivity's activity is passed in to provide functionality to
	 * ThreadListener.
	 * @see ThreadAlertView.java
	 * @see ThreadListener.java
	 */
	private void createTopic()
	{		
		MainActivity.getListController().setInTopic(false);
		MainActivity.getListController().setEditingTopic(false);
		
		ThreadAlertView threadAlert = new ThreadAlertView();
		threadAlert.show(fragment, "New Thread Alert");

	}

	/**
	 * Handles the user's request to refresh the view
	 */
	private void refreshView()
	{
		MainActivity.getListController().refreshThreads();
		Toast.makeText(this.context, ca.ualberta.team7project.R.string.refresh, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Handles the user's request to change sort preferences
	 */
	private void sortPreferences()
	{
		SortPreferencesAlertView preferencesAlert = new SortPreferencesAlertView();
		preferencesAlert.show(fragment, "Sort Preference Alert");
	}

	/**
	 * Provides the caller with which button was clicked on the action bar.
	 * 
	 * @return item The id button that was clicked on the action bar.
	 */
	public MenuItem getItem()
	{

		return item;
	}

	public Context getMainContext()
	{

		return mainContext;
	}

	public void setMainContext(Context mainContext)
	{

		this.mainContext = mainContext;
	}

}
