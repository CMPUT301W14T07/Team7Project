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
import ca.ualberta.team7project.R;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView;

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
	public boolean getAction()
	{
		// TODO would like to put this into a map.
		switch (this.item.getItemId())
		{
			case R.id.action_home:
				topicsHome();
				return true;
			case R.id.action_geo_location:
				geolocationPreferences();
				return true;
			case R.id.action_user_preferences:
				userPreferences();
				return true;
			case R.id.action_add_favorite:
				addTopicToFavorites();
				return true;
			case R.id.action_create_topic:
				createTopic();
				return true;
			case R.id.action_refresh:
				refreshView();
				return true;
			case R.id.action_sort:
				sortPreferences();
				return true;
		}
		return false;
	}

	/**
	 * Handles the user's request to return to main topic listings
	 */
	private void topicsHome()
	{

		// TODO
	}

	/**
	 * Handles the user's request to set geolocation preferences
	 */
	private void geolocationPreferences()
	{

		// TODO
	}

	/**
	 * Handles the user's request to set user preferences
	 */
	private void userPreferences()
	{
		// TODO. For now, this just updates username.
		CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
		userAlert.setCancelable(false);
		userAlert.show(fragment, "New User Name Alert");

	}

	/**
	 * Handles the user's request to add current topic to favorites
	 */
	private void addTopicToFavorites()
	{
		// TODO
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
/*		ThreadListView threadListView = ((ca.ualberta.team7project.MainActivity)mainContext)
				.getListController().getListView();
		
		ThreadAlertView threadAlert = new ThreadAlertView(threadListView);
		threadAlert.show(fragment, "New Thread Alert");*/
		
		// New method. Old stuff commented until fully tested.
		ThreadAlertView threadAlert = new ThreadAlertView();
		threadAlert.show(fragment, "New Thread Alert");

	}

	/**
	 * Handles the user's request to refresh the view
	 */
	private void refreshView()
	{
		// Just a temporary debug toast.	
		Toast.makeText(this.context, ca.ualberta.team7project.R.string.refresh_failed, Toast.LENGTH_SHORT).show();			
	}

	/**
	 * Handles the user's request to change sort preferences
	 */
	private void sortPreferences()
	{

		// http://stackoverflow.com/questions/2002288/static-way-to-get-context-on-android
		// SortPreferencesAlertView preferencesAlert = new
		// SortPreferencesAlertView();
		// preferencesAlert.show(getSupportFragmentManager(),
		// "Sort Preferences Alert");
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
