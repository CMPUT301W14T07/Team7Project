/**
 * Takes input from from views and the updates the user model as appropriate
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserPersistenceModel;
import ca.ualberta.team7project.views.UserView;

public class UserController
{
	private Context context;
	private FragmentManager fragment;
	
	private static PreferenceModel user;
	private UserView userView;
	
	private UserPersistenceModel persistence;
	
	public UserController(Context context, FragmentManager fragment)
	{
		super();
		this.context = context;
		this.fragment = fragment;
		
		this.setUserView(new UserView(this.context, this.fragment));
		this.persistence = new UserPersistenceModel(context);
		
		/* Set the user by retrieving from file system, or creating a new user */
		setUserInitialRun();
	}
	
	/**
	 * Creates a new PreferenceModel, saves the model to the file system and updates the appropriate views.
	 */
	public void createNewUser(String userName)
	{
		PreferenceModel newUser = new PreferenceModel(userName);

		UserPersistenceModel persistence = new UserPersistenceModel(this.context);
		persistence.serializeUser(newUser);
		setUser(newUser);
		
	}
	
	/**
	 * Sets the open user by checking if one exists on the file system, or creating a new user.
	 */
	public void setUserInitialRun()
	{
		PreferenceModel newUser = null;

		if (firstRun() == true)
		{
			userView.promptIdentityAlertView();			
		} else
		{
			newUser = deserializeUser();
			
			/* If serialization fails, create a new user. */
			if (newUser == null)
			{
				userView.promptIdentityAlertView();				
			} else
			{
				setUser(newUser);
			}
		}

		setFirstRun();	
	}
	
	private PreferenceModel deserializeUser()
	{
		return persistence.deserializeUser();
	}

	
	/* The following two methods should be moved to a persistence class or the main activity 
	 * Low on the TODO list right now.
	 * */
	
	/**
	 * A simple check to determine if the application has ever been run on this
	 * phone.
	 * <p>
	 * This is necessary to set the user in this is the first time the application 
	 * has launched.
	 * @return Boolean True if application has run before.
	 */
	public boolean firstRun()
	{

		SharedPreferences persistence = this.context
				.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);

		return persistence.getBoolean("firstRun", true);
	}

	/**
	 * Create a setting to represent that the application has now run for the
	 * first time.
	 */
	public void setFirstRun()
	{
		
		SharedPreferences persistence = this.context
				.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = persistence.edit();

		editor.putBoolean("firstRun", false);
		editor.commit();

	}
	
	/**
	 * The new longitude/latitude coordinates for the UserModel are set
	 */
	public static void updateLocationModel(double longitude, double latitude)
	{
		LocationModel location = new LocationModel(longitude, latitude);
		UserController.user.getUser().setLocation(location);
		Log.e("debug", "updating user coordinates");
	}
	
	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public PreferenceModel getUser()
	{
		return user;
	}

	public void setUser(PreferenceModel user)
	{
		this.user = user;
		userView.updateViews(user);
	}
	
	public UserPersistenceModel getPersistence()
	{
		return persistence;
	}

	public void setPersistence(UserPersistenceModel persistence)
	{
		this.persistence = persistence;
	}
	
	public FragmentManager getFragment()
	{
		return fragment;
	}

	public void setFragment(FragmentManager fragment)
	{
		this.fragment = fragment;
	}

	public UserView getUserView()
	{
		return userView;
	}

	public void setUserView(UserView userView)
	{
		this.userView = userView;
	}

}
