/**
 * UserModel contains
 * <ul>
 * <li>Name
 * <li>The current location of the user
 * <li>A chronologically ordered list of authored topics
 * <li>A list of favorite topics by UUID
 * <li>A chronologically ordered list of posted threads by UUID
 * <li>Unique ID tied to a username
 * <li>Last viewed topic
 * <li>Topic position (Unique thread identifier)
 * </ul>
 * @author Michael Raypold
 */

package ca.ualberta.team7project.models;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.team7project.MainActivity;

/**
 * Stores the user's settings, including their UserModel
 * <p>
 * In addition to attributes of UserModel, a list of favorited comments is stored
 */
public class PreferenceModel
{
	private UserModel user;
	private ArrayList<UUID> favoriteComments;
	private ArrayList<UUID> authoredComments;

	
	/**
	 * Constructs the PreferenceModel with a given username
	 * @param username a newly selected username 
	 */
	public PreferenceModel(String username)
	{
		super();
		this.authoredComments = new ArrayList<UUID>();
		this.favoriteComments = new ArrayList<UUID>();
		this.user = new UserModel(username);

	}

	public UserModel getUser()
	{
		return user;
	}

	public void setUser(UserModel user)
	{
		this.user = user;
	}

	/**
	 * Without knowing anything for how the cache works, will just 
	 * return ArrayList of UUIDs for now (for instance, which method
	 * and where will populate a view with ThreadModels using these 
	 * UUIDs need to be discussed)
	 * @return
	 */
	public ArrayList<UUID> getFavoriteComments()
	{
		return favoriteComments;
	}
	/**
	 * Only add the UUID to the list if it is not already in the ArrayList
	 * @param comment
	 */
	public void addFavoriteComment(ThreadModel comment)
	{

		if (!this.favoriteComments.contains(comment.getUniqueID()))
		{
			this.favoriteComments.add(comment.getUniqueID());
			MainActivity.getUserController().saveOpenUser();
		}
		
	}

	public ArrayList<UUID> getAuthoredComments()
	{

		return authoredComments;
	}

	public void addAuthoredComment(ThreadModel authoredComment)
	{
		if (!this.favoriteComments.contains(authoredComment))
		{
			this.authoredComments.add(authoredComment.getUniqueID());
			MainActivity.getUserController().saveOpenUser();
		}
	}
}
