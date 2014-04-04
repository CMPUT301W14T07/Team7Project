/**
 * Responsible for serializing and deserializing user on the filesystem.
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Facilitates storing and loading the PreferenceModel to/from disk
 * <p>
 * Utilizes Java's built-in serialization for objects implementing the Serializable interface
 */
public class UserPersistenceModel extends Activity
{

	private Context context;

	public UserPersistenceModel(Context context)
	{

		super();
		this.context = context;
	}

	/**
	 * Serialize the user to the filesystem with the filename set as the user name
	 * 
	 * @param user A non-null UserModel object
	 */
	public void serializeUser(PreferenceModel user)
	{   
	    try
	    {
	    	String userName = user.getUser().getName();
	    	
			SharedPreferences persistence = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = persistence.edit();

			Gson gson = new Gson();
			String json = gson.toJson(user);
			
			editor.putString(userName, json);
			editor.commit();	
			
			setLastOpenUser(userName);
			
	    } catch (Exception e)
	    {
	    	e.printStackTrace();
	    }	    
	}

	/**
	 * Deserializes a UserModel or returns null if none exist.
	 * 
	 * @return newUser A deserialized UserModel or a null object if failure
	 */
	public PreferenceModel deserializeUser()
	{

		String userName = lastOpenUser();
		PreferenceModel newUser = null;

		if (userName != null)
		{
			try
			{
				SharedPreferences persistence = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
				
				Gson gson = new Gson();
				String json = persistence.getString(userName, null);
				
				newUser = gson.fromJson(json, PreferenceModel.class);
				
				return newUser;
			} catch (Exception e)
			{

				e.printStackTrace();
			}
		}

		return newUser;
	}

	/**
	 * Gets the name of the last open user to aid deserialization.
	 * 
	 * @return userName Representing the name of the user last created.
	 */
	public String lastOpenUser()
	{

		SharedPreferences persistence = context.getSharedPreferences(
				"appPreferences", Context.MODE_PRIVATE);
		String userName = persistence.getString("lastOpenUser", null);

		return userName;
	}

	/**
	 * Sets the name of the last open user in the preferences
	 */
	public void setLastOpenUser(String userName)
	{

		SharedPreferences persistence = context.getSharedPreferences(
				"appPreferences", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = persistence.edit();
		editor.putString("lastOpenUser", userName);
		editor.commit();

	}


	/**
	 * Returns the name of the last logged in user to the application.
	 * @return String of the last open user. Null if none exist.
	 */
	public String getLastOpenUser()
	{
		SharedPreferences persistence = context.getSharedPreferences(
			"appPreferences", Context.MODE_PRIVATE);
		
		return persistence.getString("lastOpenUser", null);
	}
	
	/**
	 * Delete a user from the SharedPreferences system
	 * @return
	 */
	public void deleteUser(String userName)
	{
	    try
	    {
			SharedPreferences persistence = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = persistence.edit();

			editor.remove(userName);
			editor.commit();	
	    } catch (Exception e)
	    {
	    	e.printStackTrace();
	    }	    
	}

	/**
	 * Determine if a user exists on the filesystem
	 * @param userName of the user we are searching for
	 * @return boolean representing whether the user exists
	 */
	public boolean userExists(String userName)
	{
		boolean exists = false;
		
		SharedPreferences persistence = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);

		if(persistence.getString(userName, null) != null)
			exists = true;
		
		return exists;
	}
}
