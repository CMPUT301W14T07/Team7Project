/**
 * TopicModel contains
 * <ul>
 * <li>All parent threads for a topic
 * <li>Topic name
 * <li>The topic author
 * <li>The date the topic was created and last updated
 * <li>Geolocation
 * </ul>
 * @author Michael Raypold
 */
package ca.ualberta.team7project;

import java.util.Date;
import java.util.LinkedList;

import android.location.Location;

public class TopicModel {

	private String topicName;
	private Date createdDate;
	private Date lastUpdated;
	private Location location;
	private CommentAuthorModel topicAuthor;
	private LinkedList<ThreadModel> threads;
	
	/**
	 * Initializes TopicModel with a title and author.
	 * 
	 * @param topicName 	The name (title) given to the topic by the author
	 * @param topicAuthor	The user who created the topic
	 */
	public TopicModel(String topicName, CommentAuthorModel topicAuthor) {
		super();
		this.topicName = topicName;
		this.topicAuthor = topicAuthor;
		
		//TODO Should set location, and dates here.
		//TODO waiting for clarification on dates and locations
		//TODO Every topic needs and parent thread. Set the thread.
		//TODO Maybe parent thread needs to be passed into topic as well?
	}
	
	/**
	 * Gets the most name (title) of the topic.
	 * 
	 * @return The name (title) of the topic
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * The topicAuthor sets a topic name.
	 * 
	 * @param The topic name (title) chosen by the topicAuthor.
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
	 * Gets the date which the topic was created.
	 * 
	 * @return the Date object which the topic was created
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * Uses the provided date to set the topic date.
	 * <p>
	 * setCreatedDate is only called in the constructor.
	 * 
	 * @param A Date object representing topic creation date.
	 */
	private void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Provides the date when the topic was last updated
	 * 
	 * @return The Date object when the topic was last updated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * Uses provided Date object to update the topic date
	 * <p>
	 * lastUpdated is set in the constructor, as well as whenever a user edits a topic.
	 * 
	 * @param lastUpdated, A Date object representing the update date.
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * Returns to the caller a Location object representing topic geolocation. 
	 * 
	 * @return A location object with longitude and latitude of topic creation position.
	 */
	public Location getLocation() {
		// TODO. Major clarification. see issue tracker..
		return location;
	}

	/**
	 * Set the topic geolocation.
	 * 
	 * @param location, a Location object with longitude and latitude. 
	 */
	private void setLocation(Location location) {
		// TODO. Clarification. Issue #24. May need to make this a public method
		this.location = location;
	}

	/**
	 * Returns the initial comment author.
	 * <p>
	 * If the user has updated their username, their old topics will keep the old username.
	 * 
	 * @return The author that created the topic.
	 */
	public CommentAuthorModel getTopicAuthor() {
		//TODO Perhaps we only need a string and hash representing topic author.
		return topicAuthor;
	}

	/**
	 * Takes a CommentAuthorModel object and sets the topic author.
	 * 
	 * @param topicAuthor, the user (userModel) who has created the topic.
	 */
	private void setTopicAuthor(CommentAuthorModel topicAuthor) {
		this.topicAuthor = topicAuthor;
	}
	
	/**
	 * Returns all parent threads and children corresponding to the topic.
	 * <p>
	 * Use care when calling this method, as depending on the amount of threads, the return object
	 * can be quite large.
	 * 
	 * @return Returns a LinkedList of all parent threads in the topic.
	 */
	public LinkedList<ThreadModel> getThreads() {
		return threads;
	}

	/**
	 * Adds a thread to the end of the LinkedList of threads.
	 * <p>
	 * Threads are ordered chronologically. Other methods exist to sort a Topic.
	 * 
	 * @param thread, the ThreadModel object to be added to the Topic.
	 */
	public void addThread(ThreadModel thread) {
		this.threads.add(thread);
	}

}
