/**
 * The single class for which all children and and parent threads are derived
 * <P>
 * There contains four constructors for the users needs.
 * Every constructor requires a comment, user and location. However, the user 
 * has the option of entering an image or a title.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.models;

import java.util.Date;
import java.util.LinkedList;
import android.graphics.Bitmap;
import android.location.Location;

public class ThreadModel
{

	private String title;
	private String comment;
	private Bitmap image;
	private String authorName;
	private String authorUnique;
	private Date timestamp; //time of last update
	private Location location;
	private Integer uniqueID;
	private LinkedList<ThreadModel> comments;

	/**
	 * Constructs the ThreadModel with appropriate parameters.
	 * 
	 * @param comment associated with the thread
	 * @param image associated with the thread (may be null)
	 * @param user associated with the thread
	 * @param location the thread was written at
	 */
	public ThreadModel(String comment, Bitmap image, UserModel user,
			Location location)
	{
		super();
		this.comment = comment;
		this.image = image;
		this.authorName = user.getName();
		this.authorUnique = user.getUniqueName();
		this.location = location;
		this.title = null;
		this.timestamp = new Date();
		this.comments = new LinkedList<ThreadModel>();
	}

	/**
	 * Constructs the ThreadModel with appropriate parameters.
	 * 
	 * @param comment associated with the thread
	 * @param user associated with the thread
	 * @param location the thread was written at
	 */
	public ThreadModel(String comment, UserModel user, Location location)
	{
		super();
		this.comment = comment;
		this.authorName = user.getName();
		this.authorUnique = user.getUniqueName();
		this.location = location;
		this.title = null;
		this.image = null;
		this.timestamp = new Date();
		this.comments = new LinkedList<ThreadModel>();
	}
	
	/**
	 * Constructs the ThreadModel with appropriate parameters.
	 * 
	 * @param comment associated with the thread
	 * @param image associated with the thread (may be null)
	 * @param user associated with the thread
	 * @param location the thread was written at
	 * @param title of the thread
	 */
	public ThreadModel(String comment, Bitmap image, UserModel user,
			Location location, String title)
	{
		super();
		this.comment = comment;
		this.image = image;
		this.authorName = user.getName();
		this.authorUnique = user.getUniqueName();
		this.location = location;
		this.title = title;
		this.timestamp = new Date();
		this.comments = new LinkedList<ThreadModel>();
	}
	
	/**
	 * Constructs the ThreadModel with appropriate parameters.
	 * 
	 * @param comment associated with the thread
	 * @param user associated with the thread
	 * @param location the comment was written at
	 * @param title of the thread
	 */
	public ThreadModel(String comment, UserModel user, Location location, String title)
	{
		super();
		this.comment = comment;
		this.authorName = user.getName();
		this.authorUnique = user.getUniqueName();
		this.location = location;
		this.title = title;
		this.image = null;
		this.timestamp = new Date();
		this.comments = new LinkedList<ThreadModel>();
	}
	
	
	public String getComment()
	{

		return comment;
	}

	public void setComment(String comment)
	{

		this.comment = comment;
		this.timestamp = new Date();

	}

	public Bitmap getImage()
	{

		return image;
	}

	public void setImage(Bitmap image)
	{

		this.image = image;
		this.timestamp = new Date();

	}

	public String getAuthorName()
	{

		return authorName;
		
	}

	public void setAuthorName(String authorName)
	{

		this.authorName = authorName;
		this.timestamp = new Date();

	}

	public String getAuthorUnique()
	{

		return authorUnique;
	}

	public void setAuthorUnique(String authorUnique)
	{

		this.authorUnique = authorUnique;
		this.timestamp = new Date();

	}

	public Date getTimestamp()
	{

		return timestamp;
	}

	public void setTimestamp()
	{
		this.timestamp = new Date();
	}

	public Location getLocation()
	{

		return location;
	}

	public void setLocation(Location location)
	{

		this.location = location;
		this.timestamp = new Date();

	}

	public Integer getUniqueID()
	{

		return uniqueID;
	}

	public void setUniqueID(Integer uniqueID)
	{

		this.uniqueID = uniqueID;
		this.timestamp = new Date();

	}

	public LinkedList<ThreadModel> getComments()
	{

		return comments;
	}

	public void addComment(ThreadModel comment)
	{

		this.comments.add(comment);
		this.timestamp = new Date();

	}
	
	public String getTitle()
	{
	
		return title;
	}

	public void setTitle(String title)
	{
	
		this.title = title;
		this.timestamp = new Date();
	}

}
