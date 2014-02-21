/**
 * ThreadModel contains
 * <ul>
 * <li>Comment
 * <li>Image uploaded by the user
 * <li>Number of votes (favorites)
 * <li>The thread author
 * <li>The date the thread was last updated
 * <li>Geolocation
 * <li>All associated child threads
 * <li>The Unique ID associated with a thread
 * </ul>
 * @author Michael Raypold
 */
package ca.ualberta.team7project;

import java.util.Date;
import java.util.LinkedList;
import android.graphics.Bitmap;
import android.location.Location;

public class ThreadModel {

	private String comment;
	private Bitmap image;
	private Integer upVotes;
	private CommentAuthorModel commentAuthor;
	private Date lastUpdatedDate;
	private Location location;
	private LinkedList<ThreadModel> threads;
	private Integer uniqueID;

	/**
	 * A public constructor that creates a thread that includes comments and all metadata.
	 * 
	 * @param comment	 	A comment written by the thread author
	 * @param image			Bitmap image choosen by the thread author
	 * @param commentAuthor Unique user representing the comment author
	 */
	public ThreadModel(String comment, Bitmap image,
			CommentAuthorModel commentAuthor) {
		super();
		this.comment = comment;
		this.image = image;
		this.commentAuthor = commentAuthor;
	}
	
	/**
	 * Returns the string based comment attached to the thread.
	 * 
	 * @return The comment associated the thread.
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Set the thread comment when creating or editing the thread.
	 * 
	 * @param comment, a String object representing the user's comment.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Returns the Bitmap associated with the thread.
	 * 
	 * @return An Android specific Bitmap object.
	 */
	public Bitmap getImage() {
		return image;
	}
	
	/**
	 * User sets an image upon creating or editing a thread.
	 * 
	 * @param image, and Android Bitmap object to associate with the thread
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	/**
	 * Returns an integer that represents the votes.
	 * <p>
	 * upVotes represents the number of times that a thread has been selected as a favorite.
	 *  
	 * @return The number of votes for the specified thread.
	 */
	public Integer getUpVotes() {
		return upVotes;
	}
	
	/**
	 * Increment the upVote.
	 */
	public void incrementUpVotes() {
		this.upVotes++;
	}
	
	/**
	 * Decrement the upVote.
	 */
	public void decrementUpVotes() {
		this.upVotes--;
	}
	
	/**
	 * Returns the original comment author.
	 * <p>
	 * Comment author is not updated when a user changes their username.
	 * 
	 * @return A CommentAuthorModel object representing the threads author.
	 */
	public CommentAuthorModel getCommentAuthor() {
		return commentAuthor;
	}
	
	/**
	 * Set the comment author for the thread.
	 * <p>
	 * Only called upon initial thread creation.
	 * 
	 * @param commentAuthor from the CommentAuthorModel representing the thread author.
	 */
	private void setCommentAuthor(CommentAuthorModel commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
	
	/**
	 * Returns the timestamp in a Date object when the thread was created or last updated.
	 * 
	 * @return A Date object when the thread was last edited.
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * Set the date that the thread has been created or updated.
	 * <p>
	 * Must be called everytime that a thread has been edited.
	 * 
	 * @param lastUpdatedDate Date object representing a timestamp.
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	/**
	 * Returns the set user set location of a thread.
	 * 
	 * @return A Location object representing the set location for the thread.
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the location based off user demands or the GPS.
	 * 
	 * @param location, a Location objectd including longitude and latitude.
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Returns all threads associated with the parent.
	 * <p>
	 * Use care when calling this method as all threads are return in a single linked list.
	 * The memory footprint can be quite large depending on the number of associated child threads.
	 * 
	 * @return A LinkedList object of all child threads in chronological order.
	 */
	public LinkedList<ThreadModel> getThreads() {
		return threads;
	}
	
	/**
	 * Adds a thread to the end of LinkedList of threads.
	 * <p>
	 * Threads are ordered chronologically. Other methods exist to sort a thread.
	 * 
	 * @param thread, a ThreadModel object representing a child thread to the current thread.
	 */
	public void addThread(ThreadModel thread) {
		this.threads.add(thread);
	}

	/**
	 * Get the unique ID associated to the thread.
	 * 
	 * @return Returns an integer representing the unique ID of the thread
	 */
	public Integer getUniqueID() {
		return uniqueID;
	}

	/**
	 * Takes a unique ID and attaches it to the thread.
	 * <p>
	 * The uniqueID can only be generated when a thread is added to a topic.
	 * 
	 * @param uniqueID A generated unique ID from the the Topic the thread will be added to.
	 */
	public void setUniqueID(Integer uniqueID) {
		this.uniqueID = uniqueID;
	}
}
