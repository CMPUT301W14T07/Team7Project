/**
 * TopicModel contains
 * <ul>
 * <li>All parent threads for a topic
 * <li>Topic name
 * <li>The topic author and unique ID.
 * <li>The date the topic was created and last updated
 * <li>Geolocation
 * <li>The unique ID of the last thread added to the topic
 * </ul>
 * @author Michael Raypold
 */
package ca.ualberta.team7project;

import java.util.Date;
import java.util.LinkedList;

import android.location.Location;

public class TopicModel
{

    private String topicName;
    private Date createdDate;
    private Date lastUpdated;
    private Location location;
    private LinkedList<ThreadModel> threads;
    private Integer lastThreadUniqueID;
    private String authorName;
    private String authorUnique;

    /**
     * Initializes TopicModel with a title and author.
     * 
     * @param topicName The name (title) given to the topic by the author
     * @param topicAuthor The user who created the topic
     */
    public TopicModel(String topicName, String authorName, String authorUnique)
    {

        super();
        this.topicName = topicName;
        this.authorName = authorName;
        this.authorUnique = authorUnique;
        this.lastThreadUniqueID = 0;
        this.threads = new LinkedList<ThreadModel>();

        setCreatedDate(new Date());
        setLastUpdated(getCreatedDate());

        // TODO Should set location, and dates here.
        // TODO waiting for clarification on dates and locations
        // TODO Every topic needs and parent thread. Set the thread.
        // TODO Maybe parent thread needs to be passed into topic as well?
    }

    /**
     * Gets the most name (title) of the topic.
     * 
     * @return The name (title) of the topic
     */
    public String getTopicName()
    {

        return topicName;
    }

    /**
     * The topicAuthor sets a topic name.
     * 
     * @param The topic name (title) chosen by the topicAuthor.
     */
    public void setTopicName(String topicName)
    {

        this.topicName = topicName;
    }

    /**
     * Gets the date which the topic was created.
     * 
     * @return the Date object which the topic was created
     */
    public Date getCreatedDate()
    {

        return createdDate;
    }

    /**
     * Uses the provided date to set the topic date.
     * <p>
     * setCreatedDate is only called in the constructor.
     * 
     * @param A Date object representing topic creation date.
     */
    private void setCreatedDate(Date createdDate)
    {

        this.createdDate = createdDate;
    }

    /**
     * Provides the date when the topic was last updated
     * 
     * @return The Date object when the topic was last updated
     */
    public Date getLastUpdated()
    {

        return lastUpdated;
    }

    /**
     * Uses provided Date object to update the topic date
     * <p>
     * lastUpdated is set in the constructor, as well as whenever a user edits a
     * topic.
     * 
     * @param lastUpdated A Date object representing the update date.
     */
    public void setLastUpdated(Date lastUpdated)
    {

        this.lastUpdated = lastUpdated;
    }

    /**
     * Returns to the caller a Location object representing topic geolocation.
     * 
     * @return A location object with longitude and latitude of topic creation
     *         position.
     */
    public Location getLocation()
    {

        // TODO. Major clarification. see issue tracker..
        return location;
    }

    /**
     * Set the topic geolocation.
     * 
     * @param location a Location object with longitude and latitude.
     */
    private void setLocation(Location location)
    {

        // TODO. Clarification. Issue #24. May need to make this a public method
        this.location = location;
    }

    /**
     * Returns all parent threads and children corresponding to the topic.
     * <p>
     * Use care when calling this method, as depending on the amount of threads,
     * the return object can be quite large.
     * 
     * @return Returns a LinkedList of all parent threads in the topic.
     */
    public LinkedList<ThreadModel> getThreads()
    {

        return threads;
    }

    /**
     * Adds a thread to the end of the LinkedList of threads.
     * <p>
     * Threads are ordered chronologically. Other methods exist to sort a Topic.
     * 
     * @param thread the ThreadModel object to be added to the Topic.
     */
    public void addThread(ThreadModel thread)
    {

        this.threads.add(thread);
    }

    /**
     * Returns the unique ID of the thread last added to the topic.
     * <p>
     * The unique ID does not represent post count since posts can be deleted.
     * 
     * @return The unique ID of the thread last added to the topic.
     */
    public Integer getLastThreadUniqueID()
    {

        return lastThreadUniqueID;
    }

    /**
     * Increment the current ID and then set the unique ID to the new count.
     * <p>
     * Must be called everytime a thread is added to a topic, or a thread to a
     * thread.
     */
    public void setLastThreadUniqueID()
    {

        Integer oldID = getLastThreadUniqueID();
        oldID++;
        this.lastThreadUniqueID = oldID;
    }

    /**
     * Gets the non-unique topic author name.
     * 
     * @return The name of the topic author.
     */
    public String getAuthorName()
    {

        return authorName;
    }

    /**
     * Set the non-unique topic author name.
     * 
     * @param authorName The given name of the topic author.
     */
    public void setAuthorName(String authorName)
    {

        this.authorName = authorName;
    }

    /**
     * Returns a unique ID of the topic author created in the UserModel.
     * 
     * @return Returns the unique ID associated with the topic author.
     */
    public String getAuthorUnique()
    {

        return authorUnique;
    }

    /**
     * A unique ID used to identify topics by an author.
     * 
     * @param authorUnique The given unique ID created in the USerModel.
     */
    public void setAuthorUnique(String authorUnique)
    {

        this.authorUnique = authorUnique;
    }

}
