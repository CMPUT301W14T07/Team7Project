/**
 * A container for all comments associated to the topic.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.models;

import android.graphics.Bitmap;
import android.location.Location;


public class TopicModel extends ThreadModel
{

	private String title;
	private Integer lastThreadUniqueID;
	
	/**
	 * Constructs a topic with the given parameters.
	 * 
     * @param comment associated with the topic
     * @param image associated with the topic
     * @param authorName chosen by the user
     * @param authorUnique unique hash generated for each user
     * @param location the topic was created at
	 */
    public TopicModel(String comment, Bitmap image, String authorName,
			String authorUnique, Location location, String title)
	{
    	
		super(comment, image, authorName, authorUnique, location);
		this.lastThreadUniqueID = 0;
		this.title = title;
		
	}

	
	public String getTitle()
	{
	
		return title;
	}

	
	public void setTitle(String title)
	{
	
		this.title = title;
	}

	/**
	 * Returns the unique ID of the thread last added to the topic.
	 * <p>
	 * The unique ID does not represent the post count since posts can be deleted
	 * @return The unique ID of the thread last added to the topic.
	 */
	public Integer getLastThreadUniqueID()
	{
		return lastThreadUniqueID;
	}

	/**
	 * Increment the current ID and then set the unique ID to the new count.
	 * <p>
	 * Must be called every time a new comment thread is added to the a topic.
	 */
	public void setLastThreadUniqueID()
	{
		Integer oldID = getLastThreadUniqueID();
		oldID++;
		this.lastThreadUniqueID = oldID;
	}

}
