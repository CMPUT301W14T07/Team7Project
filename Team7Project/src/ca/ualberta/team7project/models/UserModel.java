/**
 * UserModel contains
 * <ul>
 * <li>Name
 * <li>The current location of the user
 * <li>A chronologically ordered list of authored topics
 * <li>A list of favorite topics
 * <li>A chronologically ordered list of posted threads
 * <li>Unique ID tied to a username
 * <li>Last viewed topic
 * <li>Topic position (Unique thread identifier)
 * </ul>
 * @author Michael Raypold
 */

package ca.ualberta.team7project.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class UserModel implements Serializable
{
	private String name;
	private String uniqueName;
	private LocationModel location;

	/**
	 * Construct the user with the given parameters
	 * 
	 * @param userName The name given to the user.
	 */
	public UserModel(String userName)
	{
		setName(userName);
		setUniqueName();
		
		this.setLocation(null);
	}

	public String getName()
	{

		return name;
	}

	/**
	 * Sets the new username and updates the uniqueName
	 * 
	 * @param name of the new user
	 */
	public void setName(String name)
	{

		this.name = name;
		setUniqueName();
	}

	public String getUniqueName()
	{

		return uniqueName;
	}
	
	/**
	 * Generates a unique name with the given user name.
	 * <p>
	 * A random number and the current date with microsecond precision is
	 * appended to the current user name to ensure that the UniqueID is unique
	 * when using the .hashCode() method.
	 * <p>
	 * Random numbers are appended in case multiple users are created at
	 * precisely the same millisecond. The current date is appended in case
	 * multiple users have the same user name.
	 * <p>
	 * Despite these measures, UniqueID is not guaranteed to be unique.
	 * Nevertheless, it prevents UserModel from storing a list of usernames on
	 * the server.
	 * <p>
	 * This private method is only called when names are set.
	 */
	private void setUniqueName()
	{

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss",
				Locale.CANADA);
		Date currentDate = Calendar.getInstance().getTime();
		String dateString = dateFormat.format(currentDate);

		Random random = new Random();
		Integer randomInt = random.nextInt();

		String userName = getName() + dateString + String.valueOf(randomInt);

		this.uniqueName = String.valueOf(userName.hashCode());
	}

	public LocationModel getLocation()
	{

		return location;
	}

	public void setLocation(LocationModel location)
	{

		this.location = location;
	}

}
