/**
 * A ThreadModel object with the ability to recursively add more comments
 * and a user voting system.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.models;

import android.graphics.Bitmap;
import android.location.Location;

public class CommentThreadModel extends ThreadModel
{

	private Integer votes;
	
	/**
	 * Constructs a comment thread using the ThreadModel superclass
	 * 
     * @param comment associated with the thread
     * @param image associated with the thread
     * @param authorName chosen by the user
     * @param authorUnique unique hash generated for each user
     * @param location the comment was written at
	 */
	public CommentThreadModel(String comment, Bitmap image, String authorName,
			String authorUnique, Location location)
	{
		super(comment, image, authorName, authorUnique, location);
		this.votes = 0;
	}

	
	public Integer getVotes()
	{
	
		return votes;
	}

	
	public void setVotes(Integer votes)
	{
	
		this.votes = votes;
	}

	
}
