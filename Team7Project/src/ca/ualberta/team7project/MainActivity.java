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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ActionBarView;
import ca.ualberta.team7project.views.CreateIdentityAlertView;
import ca.ualberta.team7project.views.CreateIdentityAlertView.IdentityListener;

public class MainActivity extends Activity implements IdentityListener
{
	private UserModel user;
	
    /**
     * Creates the state of the application when the activity is initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.show();
                
        user = null;
        setNewUser();
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

        ActionBarView actionBarController = new ActionBarView(item, getFragmentManager());
        return actionBarController.getAction();
    }

	/**
	 * Determine if creating a new user or using an existing user, then sets the open user.
	 */
	public void setNewUser()
	{		
		UserModel newUser = null;
		
		if(firstRun() == true) {
			/* Prompt alert to create new user */
			promtIdentityAlertView();

		}
		else {
			/* This is not the first run, therefore a user must already exist */
			UserPersistence persistence = new UserPersistence(getApplicationContext());
			newUser = persistence.deserializeUser();

			/* However, if serialization fails, prompt new user dialog and then create new user */
			if(newUser == null) {
				promtIdentityAlertView();
			}
			else {
				setUser(newUser);
		        Toast.makeText(getApplicationContext(), "Logged in as " + user.getName(), Toast.LENGTH_SHORT).show();
			}
		}
		
		setFirstRun();
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
	 * A simple check to determine if the application has ever been run on this phone.
	 * @return Boolean True if application has run before.
	 */
	public boolean firstRun() 
	{
		SharedPreferences persistence = getApplicationContext().getSharedPreferences(
				"appPreferences", Context.MODE_PRIVATE);
		
		return persistence.getBoolean("firstRun", true); 		
	}
    
	/**
	 * Create a setting to represent that the application has now run for the first time.
	 */
	public void setFirstRun()
	{
		SharedPreferences persistence = getApplicationContext().getSharedPreferences(
				"appPreferences", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = persistence.edit();
		
		editor.putBoolean("firstRun", false);
		editor.commit();

	}
	
	/**
	 * Prompts the CreatIdentityAlertView.
	 * <p>
	 * After the view has been prompted, the new user will be serialized to the filesystem.
	 */
	public void promtIdentityAlertView() 
	{
    	CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
    	userAlert.setCancelable(false);
    	userAlert.show(getFragmentManager(), "New User Name Alert");
	}

	/**
	 * Takes the user name and updates the user in the view
	 */
	@Override
	public void onDialogPositiveCLick(DialogFragment dialog, String userName)
	{
		UserModel newUser = new UserModel(userName);

		UserPersistence persistence = new UserPersistence(getApplicationContext());
		persistence.serializeUser(newUser);
		setUser(newUser);

        Toast.makeText(getApplicationContext(), "Logged in as " + user.getName(), Toast.LENGTH_SHORT).show();

	}


}
