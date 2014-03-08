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
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.team7project.controllers.ListAdapterController;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.models.TopicListModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ActionBarView;
import ca.ualberta.team7project.views.CreateIdentityAlertView;
import ca.ualberta.team7project.views.CreateIdentityAlertView.IdentityListener;

public class MainActivity extends Activity implements IdentityListener, userViewInterface
{

	
	private UserModel user;
	private UserController userController;
	private ListView lv;
	private TopicListModel topics;
	
	/**
	 * Creates the state of the application when the activity is initialized
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lv = (ListView) findViewById(R.id.listView1);
		ListAdapterController adapter = new ListAdapterController(this, R.layout.activity_main, R.id.listView1, topics.getList());
		
		ActionBar actionBar = getActionBar();
		actionBar.show();

		this.userController = new UserController(getApplicationContext(), getFragmentManager());
		
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
				getFragmentManager());
		return actionBarController.getAction();
	}

	public UserModel getUser()
	{

		return user;
	}

	public void setUser(UserModel user)
	{

		this.user = user;
	}

	/**
	 * Takes the user name and updates the user in the view.
	 * <p>
	 * An onDialigPositiveClick is received only when creating a new UserModel.
	 */
	@Override
	public void onDialogPositiveCLick(DialogFragment dialog, String userName)
	{
		// TODO: I would like to rename this dialog to be more descriptive.
				
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
	
	@Override
	public void UpdateUser(UserModel user)
	{
		this.user = user;
		ToastUser();
	}

	@Override
	public void ToastUser()
	{
		Toast.makeText(getApplicationContext(),
				"Logged in as " + user.getName(), Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void PromptIdentityAlertView()
	{
		CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
		userAlert.setCancelable(false);
		userAlert.show(getFragmentManager(), "New User Name Alert");		
	}

}
