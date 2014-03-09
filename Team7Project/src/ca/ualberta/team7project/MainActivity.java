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
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView.IdentityListener;
import ca.ualberta.team7project.controllers.ThreadController;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.views.ActionBarView;

public class MainActivity extends Activity implements IdentityListener
{
	private UserController userController;
	private ThreadListController listController;
	private ThreadController threadController;
	
	//private ListView lv;
	//private ThreadListModel topics;
	
	/**
	 * Creates the state of the application when the activity is initialized
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* Below code does not compile and run in the emulator */
		//lv = (ListView) findViewById(R.id.listView1);
		//ListAdapterController adapter = new ListAdapterController(this, R.layout.activity_main, R.id.listView1, topics.getList());
		
		ActionBar actionBar = getActionBar();
		actionBar.show();
	
		Context context = getApplicationContext();
		FragmentManager fragment = getFragmentManager();
		
		this.userController = new UserController(context, fragment);
		this.listController = new ThreadListController(context, fragment);
		this.threadController = new ThreadController(context, fragment);
		
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
	 * where the request is handled.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		ActionBarView actionBarController = new ActionBarView(item,
				getFragmentManager(), getApplicationContext());
		return actionBarController.getAction();
	}

	/**
	 * Takes the user name and updates the user model.
	 * <p>
	 * An onDialigPositiveClick is received only when creating a new UserModel.
	 */
	@Override
	public void onIdentityPositiveCLick(DialogFragment dialog, String userName)
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


}
