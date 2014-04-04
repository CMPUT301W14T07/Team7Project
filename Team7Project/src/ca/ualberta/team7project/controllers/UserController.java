/**
 * Takes input from from views and the updates the user model as appropriate
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import android.app.FragmentManager;
import android.content.Context;
import ca.ualberta.team7project.models.ApplicationStateModel;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserPersistenceModel;
import ca.ualberta.team7project.views.UserView;

/**
 * Manages the user and their preferences
 */
public class UserController
{
	private static Context context;
	
	private static PreferenceModel user = null;
	private static UserView userView;
	
	private static LocationModel cachedLocation;
	private UserPersistenceModel persistence;
	
	public UserController(Context context, FragmentManager fragment)
	{
		UserController.context = context;
		UserController.cachedLocation = new LocationModel();
		
		UserController.userView = new UserView(UserController.context, fragment);
		this.persistence = new UserPersistenceModel(context);
		
		setUserInitialRun();	
	}
	
	/**
	 * Creates a new PreferenceModel, saves the model to the file system and updates the appropriate views.
	 */
	public static void createNewUser(String userName)
	{
		PreferenceModel newUser = new PreferenceModel(userName);

		UserPersistenceModel persistence = new UserPersistenceModel(context);
		persistence.serializeUser(newUser);
		setUser(newUser);
		
	}
	
	/**
	 * Sets the open user by checking if one exists on the file system, or creating a new user.
	 */
	public void setUserInitialRun()
	{
		PreferenceModel newUser = null;

		ApplicationStateModel appState = new ApplicationStateModel(context);
		
		if (appState.firstRun() == true)
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

		appState.setFirstRun();	
	}
	
	private PreferenceModel deserializeUser()
	{
		return persistence.deserializeUser();
	}

		
	/**
	 * Update the location of the user or cache the coordinates if no user exists.
	 * <p>
	 * Since the AlertView to create UserModel runs on the UI thread, it is possible
	 * that no UserModel exists. Therefore, setting the location is wrapped in a try/catch
	 * block and the location is cached.
	 * 
	 * @param location of the phone
	 */
	public static void updateLocationModel(LocationModel location)
	{
		try{
			UserController.user.getUser().setLocation(location);		
		} 
		catch (Exception e) {
			UserController.cachedLocation = location;
		}
	}
	
	public Context getContext()
	{
		return context;
	}

	public void setContext(Context context)
	{
		UserController.context = context;
	}

	public PreferenceModel getUser()
	{
		return user;
	}

	/**
	 * Create a new user.
	 * <p>
	 * Since GPS updates are infrequent, the users location is set to the current cached location 
	 * until a new signal is received.
	 * 
	 * @param user New user associated with the application.
	 */
	public static void setUser(PreferenceModel user)
	{
		user.getUser().setLocation(cachedLocation);
		UserController.user = user;	
		userView.updateViews(user);
	}
	
}
