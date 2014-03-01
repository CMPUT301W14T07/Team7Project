/**
 * Responsible for serializing and deserializing user on the filesystem.
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	
	/**
	 * 
	 * @return newUser A deserialized  UserModel or a null object if failure
	 */
	public UserModel deserializeUser() 
	{
		String userName = lastOpenUser();
		UserModel newUser = null;
		
		// TODO perform file exists check
		
		if(userName != null) {
			try {
				FileInputStream fileStream = openFileInput(userName.concat(".dat"));
				ObjectInputStream objectStream = new ObjectInputStream(fileStream);

				newUser = (UserModel) objectStream.readObject();

				objectStream.close();
				fileStream.close();

				return newUser;
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ClassNotFoundException c) {
				c.printStackTrace();
			}
		}

		/* If return is null, then userName was null. Caller needs to create a new user */
		return newUser;
	}
		
	/**
	 * Gets the name of the last open user to aid deserialization.
	 * @return userName Representing the name of the user last created.
	 */
	public String lastOpenUser()
	{
		SharedPreferences persistence = getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
		String userName = persistence.getString("lastOpenUser", null); 
		return userName;	
	}
	
	/**
	 * An error checking function to ensure that the file exists on disk.
	 */
	public boolean fileExists(String file)
	{
		// TODO
		return true;
	}
}
