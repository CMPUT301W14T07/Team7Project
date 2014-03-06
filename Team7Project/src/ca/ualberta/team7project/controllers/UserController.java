/**
 * Takes input from from views and the updates the user model as appropriate
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import android.content.Context;
import ca.ualberta.team7project.UserPersistence;
import ca.ualberta.team7project.models.UserModel;

/*
 * TODO Michael: I'm going to be working on this class on Thursday/Friday
 * - Implements the interface for listeners associated with UserModel (waiting for response from Alex about this)
 * - Updates userModel (need to add some getters/setters still)
 * - Writing some JUnit tests for this class
 */
public class UserController
{
	private Context context;
	private UserModel user;
	private UserPersistence persistence;
	
	public UserController(Context context)
	{
		super();
		this.context = context;
		this.persistence = new UserPersistence(context);
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
	}
	
	public UserPersistence getPersistence()
	{
	
		return persistence;
	}

	
	public void setPersistence(UserPersistence persistence)
	{
	
		this.persistence = persistence;
	}

}
