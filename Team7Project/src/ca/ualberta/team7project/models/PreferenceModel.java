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

public class PreferenceModel implements Serializable
{
	private UserModel user;
	private ArrayList<ThreadModel> favoriteTopics;
	private ArrayList<ThreadModel> authoredComments;
	
	public PreferenceModel(String username)
	{
		super();
		this.authoredComments = new ArrayList<ThreadModel>();
		this.favoriteTopics = new ArrayList<ThreadModel>();
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

	public ArrayList<ThreadModel> getFavoriteTopics()
	{

		return favoriteTopics;
	}

	public void addFavoriteTopic(ThreadModel topic)
	{

		this.favoriteTopics.add(topic);
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
