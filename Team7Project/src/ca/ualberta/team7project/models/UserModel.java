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

import java.util.UUID;

/**
 * Stores information for identifying a user
 * <p>
 * Since this class is aggregated into ThreadModel, only attributes that need to be attached to
 * the ThreadModel are stored here
 * <p>
 * This class is also aggregated into PreferenceModel 
 *
 */
public class UserModel
{
	private String name;
	private String uniqueName;
	private LocationModel locationModel;

	/**
	 * Construct a new UserModel with a given username
	 * 
	 * @param userName name the user selected
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
	 * Sets the new username and generates a new uniqueName
	 * 
	 * @param name a new name which the user selected
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
	 */
	private void setUniqueName()
	{

		this.uniqueName = String.valueOf(UUID.randomUUID());
	}

	public LocationModel getLocation()
	{

		return locationModel;
	}

	public void setLocation(LocationModel location)
	{

		this.locationModel = location;
	}

}
