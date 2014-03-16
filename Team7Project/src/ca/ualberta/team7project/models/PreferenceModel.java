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
import java.util.ArrayList;

/**
 * Stores the user's settings, including their UserModel
 * <p>
 * In addition to attributes of UserModel, a list of favorited comments is stored
 */
public class PreferenceModel implements Serializable
{
	private UserModel user;
	private ArrayList<ThreadModel> favoriteComments;
	private ArrayList<ThreadModel> authoredComments;
	
	/**
	 * Constructs the PreferenceModel with a given username
	 * @param username a newly selected username 
	 */
	public PreferenceModel(String username)
	{
		super();
		this.authoredComments = new ArrayList<ThreadModel>();
		this.favoriteComments = new ArrayList<ThreadModel>();
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

	public ArrayList<ThreadModel> getFavoriteComments()
	{

		return favoriteComments;
	}

	public void addFavoriteComment(ThreadModel comment)
	{

		this.favoriteComments.add(comment);
	}

	public ArrayList<ThreadModel> getAuthoredComments()
	{

		return authoredComments;
	}

	public void addAuthoredComment(ThreadModel authoredComment)
	{

		this.authoredComments.add(authoredComment);
	}

}
