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
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.views.ActionBarView;

public class MainActivity extends Activity implements IdentityListener
{
	private UserController userController;
	private ThreadListController listController;
	
	/* mainContext is necessary for casting to all listeners */
	private static Context mainContext;
	
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
		
		this.userController = new UserController(context, fragment);
		this.listController = new ThreadListController(this);
		
	}

	// TODO Need an onResume()

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

	
	public UserController getUserController()
	{
	
		return userController;
	}

	
	public void setController(UserController userController)
	{
	
		this.userController = userController;
	}

	
	public ThreadListController getListController()
	{
	
		return listController;
	}

	
	public void setListController(ThreadListController listController)
	{
	
		this.listController = listController;
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
	
		this.userController = userController;
	}
}
