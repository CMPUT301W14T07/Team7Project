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

package ca.ualberta.team7project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.location.Location;

public class UserModel
{

    private String name;
    private String uniqueID;
    private Location currentLocation;
    private ArrayList<TopicModel> authoredTopics; // Are we saving authored
                                                  // topics or threads?
    private ArrayList<TopicModel> favoriteTopics;
    private ArrayList<ThreadModel> postedThreads;
    private TopicModel lastViewedTopic;
    private Integer topicPosition;

    /**
     * Construct the user
     * <p>
     * Construct the user with the given name and generate a unique ID.
     * 
     * @param userName The name given to the user.
     */
    public UserModel(String userName)
    {

        setName(userName);
        this.authoredTopics = new ArrayList<TopicModel>();
        this.favoriteTopics = new ArrayList<TopicModel>();
        this.postedThreads = new ArrayList<ThreadModel>();

        // Set location?
    }

    /**
     * Return the username to an actor outside the class.
     * 
     * @return The user name
     */
    public String getName()
    {

        return name;
    }

    /**
     * Update or set the username and sets the new uniqueID.
     * 
     * @param name The new name of the user.
     */
    public void setName(String name)
    {

        this.name = name;
        setUniqueID();
    }

    /**
     * Return the unique ID of the user, which uniquely identifies all posts.
     * 
     * @return The unique ID of the user.
     */
    public String getUniqueID()
    {

        return uniqueID;
    }

    /**
     * Generates a unique ID with the given user name.
     * <p>
     * A random number and the current date with microsecond precision is
     * appended to the current user name to ensure that the UniqueID is unique
     * when using the .hashCode() method.
     * <p>
     * Random numbers are appended in case multiple users are created at
     * precisely the same millisecond. The current date is appended in case
     * multiple users have the same user name.
     * <p>
     * Despite these measures, UniqueID is not guaranteed to be unique.
     * Nevertheless, it prevents UserModel from storing a list of usernames on
     * the server.
     * <p>
     * This private method is only called when names are set.
     */
    private void setUniqueID()
    {

        // Just a temporary hash function until we come up with something.
        // TODO should use built in android locale function to detect location
        // for dates
        // Is it ok to have an android specific function in a model class?
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss",
                Locale.CANADA);
        Date currentDate = Calendar.getInstance().getTime();
        String dateString = dateFormat.format(currentDate);

        Random random = new Random();
        Integer randomInt = random.nextInt();

        String userName = getName() + dateString + String.valueOf(randomInt);

        this.uniqueID = String.valueOf(userName.hashCode());
    }

    /**
     * Returns a Location object with longitude, latitude and a timestamp.
     * 
     * @return The Location object associated with the user.
     */
    public Location getCurrentLocation()
    {

        return currentLocation;
    }

    /**
     * Uses provide Location object to update the position of the user.
     * 
     * @param currentLocation A Location object with longitude and latitude set.
     */
    public void setCurrentLocation(Location currentLocation)
    {

        this.currentLocation = currentLocation;
    }

    /**
     * Returns all authored topics by the user in chronological order.
     * 
     * @return All authored topics by the user.
     */
    public ArrayList<TopicModel> getAuthoredTopics()
    {

        return authoredTopics;
    }

    /**
     * Adds to the list of all authored topic the most recently created topic by
     * the user.
     * <p>
     * Insertion is in chronological order.
     * 
     * @param authoredTopics A new topic created by the user.
     */
    public void addAuthoredTopics(ArrayList<TopicModel> authoredTopics)
    {

        this.authoredTopics = authoredTopics;
    }

    /**
     * Returns all topics the user has selected as favorites.
     * <p>
     * Favorite topics are returned in the order that the user favorited them
     * in.
     * 
     * @return All topics the user has selected as favorited.
     */
    public ArrayList<TopicModel> getFavoriteTopics()
    {

        return favoriteTopics;
    }

    /**
     * Add to the list of favorite topics, a topic the user has selected as a
     * favorite.
     * 
     * @param favoriteTopics A topic the user has selected as a favorite.
     */
    public void addFavoriteTopics(ArrayList<TopicModel> favoriteTopics)
    {

        this.favoriteTopics = favoriteTopics;
    }

    /**
     * Return in chronological order all threads the user has created.
     * 
     * @return All threads the user has created (posted).
     */
    public ArrayList<ThreadModel> getPostedThreads()
    {

        return postedThreads;
    }

    /**
     * Add to the chronological list of posted threads by the user.
     * 
     * @param postedThreads A new thread created by the user.
     */
    public void addPostedThreads(ArrayList<ThreadModel> postedThreads)
    {

        this.postedThreads = postedThreads;
    }

    /**
     * Retrieve the last topic viewed by the user.
     * 
     * @return The topic last viewed by the user.
     */
    public TopicModel getLastViewedTopic()
    {

        return lastViewedTopic;
    }

    /**
     * Update the topic being viewed by the user.
     * 
     * @param lastViewedTopic The topic last viewed by the user.
     */
    public void setLastViewedTopic(TopicModel lastViewedTopic)
    {

        this.lastViewedTopic = lastViewedTopic;
    }

    /**
     * Keep track of the current thread viewed within a topic.
     * 
     * @return An Integer representing the unique number associated with a
     *         thread.
     */
    public Integer getTopicPosition()
    {

        return topicPosition;
    }

    /**
     * Set the thread number associated with a topic that the user is viewing.
     * 
     * @param topicPosition The unique number associated with a thread.
     */
    public void setTopicPosition(Integer topicPosition)
    {

        this.topicPosition = topicPosition;
    }

}
