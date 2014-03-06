/**
 * Takes input from from views and the updates the user model as appropriate
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import ca.ualberta.team7project.UserPersistence;
import ca.ualberta.team7project.userViewInterface;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.CreateIdentityAlertView;

/*
 * TODO Michael: I'm going to be working on this class on Thursday/Friday
 * - Implements the interface for listeners associated with UserModel (waiting for response from Alex about this)
 * - Updates userModel (need to add some getters/setters still)
 * - Writing some JUnit tests for this class
 */
public class UserController
{
	private Context context;
	private FragmentManager fragment;
	private UserModel user;
	private UserPersistence persistence;
	private userViewInterface updateViews;
	
	public UserController(Context context, FragmentManager fragment)
	{
		super();
		this.context = context;
		this.fragment = fragment;
		this.persistence = new UserPersistence(context);
		
		/*
		 * Set the user in the controller. 
		 * We must first check if a user exists in the filesystem.
		 */
		setUserInitialRun();
	}
	
	/**
	 * Creates a new UserModel
	 * <p>
	 * Typically called after an onDialogPositiveClick() from CreateIdentityAlertView()
	 */
	public void createNewUser(String userName)
	{
		UserModel newUser = new UserModel(userName);

		UserPersistence persistence = new UserPersistence(
				this.context);
		persistence.serializeUser(newUser);
		setUser(newUser);
		
	}
	
	/**
	 * A helper method for the constructor.
	 * <p>
	 * Determines if a user exists in the filesystem before setting the user.
	 */
	public void setUserInitialRun()
	{
		UserModel newUser = null;

		if (firstRun() == true)
		{
			/* Prompt alert to create new user */
			CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
			userAlert.setCancelable(false);
			userAlert.show(this.fragment, "New User Name Alert");
			
			// Replace with interface methods when working..
			
		} else
		{
			newUser = deserializeUser();
			
			/*
			 * However, if serialization fails, prompt new user dialog and then
			 * create new user
			 */
			if (newUser == null)
			{
				CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
				userAlert.setCancelable(false);
				userAlert.show(this.fragment, "New User Name Alert");
				
				// Replace with interface methods when working...
				
			} else
			{
				setUser(newUser);
			}
		}

		setFirstRun();	
	}
	
	private UserModel deserializeUser()
	{
		return persistence.deserializeUser();
	}

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
	
	public Context getContext()
	{
	
		return context;
	}

	/**
	 * Sets the context
	 * <p>
	 * This should always be set before using anything in the controller!
	 * @param context Current application context from an Activity.
	 */
	public void setContext(Context context)
	{
	
		this.context = context;
	}

	
	public UserModel getUser()
	{
	
		return user;
	}

	
	public void setUser(UserModel user)
	{
		this.user = user;
		//updateViews.UpdateUser(user); Commented out until I get this working...
	}
	
	public UserPersistence getPersistence()
	{
	
		return persistence;
	}

	
	public void setPersistence(UserPersistence persistence)
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

}
