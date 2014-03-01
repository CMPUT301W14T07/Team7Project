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
import ca.ualberta.team7project.models.UserModel;

public class UserPersistence extends Activity
{

	public UserPersistence()
	{
		super();
	}

	/**
	 * Serialize the user with the filename set as the user name
	 * @param user A non-null UserModel object
	 */
	public void serializeUser(UserModel user)
	{
		try {
			FileOutputStream fileStream = openFileOutput(user.getName().concat(".dat"), 0);
			ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

			objectStream.writeObject(user);

			fileStream.flush();
			objectStream.flush();
			objectStream.close();
			fileStream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deserializes a UserModel or returns null if none exist.
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
	
	// TODO delete user from disk method
}
