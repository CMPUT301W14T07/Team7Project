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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ActionBarView;
import ca.ualberta.team7project.views.CreateIdentityAlertView;

public class MainActivity extends Activity
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
        
        user = getNewUser();
    }

    // TODO Need an onResume()

    /**
     * Places all items for the action bar in the application menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
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
	 * Returns a new user if this is the first time the application has run, or an existing 
	 * user from the filesystem if this is not the first run.
	 * 
	 * @return the user to be associated with the activity.
	 */
	public UserModel getNewUser()
	{
		UserModel newUser = null;
		
		if(firstRun() == true) {
			/* Display action dialog, prompting the user to create a new identity/user */
	    	CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
	    	userAlert.show(getFragmentManager(), "New User Name Alert");
	    	// TODO. Take data entered in userAlert and create the user.
	    	setFirstRun();
	    	
	    	return newUser;
		}
		else {
			/* This is not the first run, therefore a user must already exist */
			// TODO
			/* However, if serialization fails, prompt new user dialog and then create new user*/
			// TODO
			//return the result of the above process;
			// Returning null for now until entire method is finished
			return null;
		}
		
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
		SharedPreferences persistence = PreferenceManager.getDefaultSharedPreferences(this);
		return persistence.getBoolean("firstRun", true); 		
	}
    
	/**
	 * Create a setting to represent that the application has now run for the first time.
	 */
	public void setFirstRun()
	{
		SharedPreferences persistence = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = persistence.edit();
		
		editor.putBoolean("firstRun", false);
		editor.commit();
	}
}
