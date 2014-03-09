/**
 * Responsible for serializing and deserializing user on the filesystem.
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.UserModel;

public class UserPersistence extends Activity
{

	private Context context;

	public UserPersistence(Context context)
	{

		super();
		this.context = context;
	}

	/**
	 * Serialize the user with the filename set as the user name
	 * 
	 * @param user
	 *            A non-null UserModel object
	 */
	public void serializeUser(PreferenceModel user)
	{

		try
		{
			FileOutputStream fileStream = context.openFileOutput(user.getUser().getName()
					.concat(".dat"), 0);
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
			objectStream.writeObject(user);

			fileStream.flush();
			objectStream.flush();
			objectStream.close();
			fileStream.close();

			setLastOpenUser(user.getUser().getName());

		} catch (IOException e)
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

		// TODO perform file exists check

		if (userName != null)
		{
			try
			{
				FileInputStream fileStream = context.openFileInput(userName
						.concat(".dat"));
				ObjectInputStream objectStream = new ObjectInputStream(
						fileStream);
				newUser = (PreferenceModel) objectStream.readObject();

				objectStream.close();
				fileStream.close();

				return newUser;
			} catch (IOException e)
			{

				e.printStackTrace();
			} catch (ClassNotFoundException c)
			{

				c.printStackTrace();
			}
		}

		/*
		 * If return is null, then userName was null. Caller needs to create a
		 * new user
		 */
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
	 * An error checking function to ensure that the file exists on disk.
	 */
	public boolean fileExists(String file)
	{

		// TODO
		return true;
	}

	// TODO delete user from disk method
}
