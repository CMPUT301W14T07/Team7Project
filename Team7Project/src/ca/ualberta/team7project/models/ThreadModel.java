/**
 * The class for which comments and topics are derived.
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

    protected String comment;
    protected Bitmap image;
    protected String authorName;
    protected String authorUnique;
    protected Date lastUpdatedDate;
    protected Location location;
    protected Integer uniqueID;
    
    //TODO
    // There must be a better way than putting a class of subclass in the superclass.......
    // Doesn't seem 'correct'
    protected LinkedList<CommentThreadModel> comments;
    
	/**
     * Constructs the ThreadModel with appropriate parameters.
     *  
     * @param comment associated with the thread
     * @param image associated with the thread
     * @param authorName chosen by the user
     * @param authorUnique unique hash generated for each user
     * @param location the comment was written at
     */
	public ThreadModel(String comment, Bitmap image, String authorName,
			String authorUnique, Location location)
	{
		super();
		this.comment = comment;
		this.image = image;
		this.authorName = authorName;
		this.authorUnique = authorUnique;
		this.location = location;
		this.comments = new LinkedList<CommentThreadModel>();
		
	}

	
	public String getComment()
	{
	
		return comment;
	}

	
	public void setComment(String comment)
	{
	
		this.comment = comment;
	}

	
	public Bitmap getImage()
	{
	
		return image;
	}

	
	public void setImage(Bitmap image)
	{
	
		this.image = image;
	}

	
	public String getAuthorName()
	{
	
		return authorName;
	}

	
	public void setAuthorName(String authorName)
	{
	
		this.authorName = authorName;
	}

	
	public String getAuthorUnique()
	{
	
		return authorUnique;
	}

	
	public void setAuthorUnique(String authorUnique)
	{
	
		this.authorUnique = authorUnique;
	}

	
	public Date getLastUpdatedDate()
	{
	
		return lastUpdatedDate;
	}

	
	public void setLastUpdatedDate(Date lastUpdatedDate)
	{
	
		this.lastUpdatedDate = lastUpdatedDate;
	}

	
	public Location getLocation()
	{
	
		return location;
	}

	
	public void setLocation(Location location)
	{
	
		this.location = location;
	}

	
	public Integer getUniqueID()
	{
	
		return uniqueID;
	}

	public void setUniqueID(Integer uniqueID)
	{
		this.uniqueID = uniqueID;
	}
	
	
	public LinkedList<CommentThreadModel> getComments()
	{
	
		return comments;
	}

	
	public void addComment(CommentThreadModel comment)
	{
	
		this.comments.add(comment);
	}

}
