/**
 * Responsible for serializing and deserializing user on the filesystem.
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import ca.ualberta.team7project.models.UserModel;

public class UserPersistence extends Activity
{

	public UserPersistence()
	{
		super();
	}

	public void serializeUser(UserModel user)
	{
		// TODO
	}
	
	public UserModel deserializeUser() 
	{
		
		return null;
	}
	
	/**
	 * A helper function to determine if a user exists on the filesystem.
	 */
	public boolean userAlreadyExists() 
	{
		return false;
	}
	
	/**
	 * Gets the name of the last open user to aid deserialization.
	 * @return userName Representing the name of the user last created.
	 */
	public String lastOpenUser()
	{
		String userName = null;
    	SharedPreferences preferences = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = 
    	
    	
		return userName;
		
	}
	
	/**
	 * An error checking function to ensure that the file exists on disk.
	 */
	public boolean fileExists(String file)
	{
		return true;
	}
}
