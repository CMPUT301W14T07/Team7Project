/**
 * Takes input from from views and the updates the user model as appropriate
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.UserPersistence;
import ca.ualberta.team7project.UserViewInterface;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.CreateIdentityAlertView;

public class UserController implements UserViewInterface
{
	private Context context;
	private FragmentManager fragment;
	private UserModel user;
	private UserPersistence persistence;
	
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
			promptIdentityAlertView();			
		} else
		{
			newUser = deserializeUser();
			
			/*
			 * However, if serialization fails, prompt new user dialog and then
			 * create new user
			 */
			if (newUser == null)
			{
				promptIdentityAlertView();				
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
		updateViews(user);
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

	@Override
	public void updateViews(UserModel user)
	{
		MainActivity.user = this.user;
		toastUser();
	}

	@Override
	public void toastUser()
	{
		Toast.makeText(this.context,
				"Logged in as " + user.getName(), Toast.LENGTH_SHORT).show();			
	}

	@Override
	public void promptIdentityAlertView()
	{
		CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
		userAlert.setCancelable(false);
		userAlert.show(this.fragment, "New User Name Alert");			
	}

}
