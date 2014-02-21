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

import java.util.ArrayList;
import android.location.Location;

public class UserModel {

		private String name;
		private String uniqueID;
		private Location currentLocation;
		private ArrayList<TopicModel> authoredTopics;	// Are we saving authored topics or threads?
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
		public UserModel(String userName) {
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
		public String getName() {
			return name;
		}

		/**
		 * Update or set the username and sets the new uniqueID.
		 * 
		 * @param name The new name of the user.
		 */
		public void setName(String name) {
			this.name = name;
			setUniqueID();
		}

		/**
		 * Return the unique ID of the user, which uniquely identifies all posts.
		 * 
		 * @return The unique ID of the user.
		 */
		public String getUniqueID() {
			return uniqueID;
		}

		/**
		 * Generates a unique ID with the given the user name using hashCode().
		 * <p>
		 * This private method is only called when names are set.
		 */
		private void setUniqueID() {
			// Just a temporary hash function until we come up with something.			
			this.uniqueID = String.valueOf(getName().hashCode());
		}
		
		/**
		 * Returns a Location object with longitude, latitude and a timestamp.
		 * 
		 * @return The Location object associated with the user.
		 */
		public Location getCurrentLocation() {
			return currentLocation;
		}

		/**
		 * Uses provide Location object to update the position of the user.
		 * 
		 * @param currentLocation A Location object with longitude and latitude set.
		 */
		public void setCurrentLocation(Location currentLocation) {
			this.currentLocation = currentLocation;
		}

		/**
		 * Returns all authored topics by the user in chronological order.
		 * 
		 * @return All authored topics by the user.
		 */
		public ArrayList<TopicModel> getAuthoredTopics() {
			return authoredTopics;
		}

		/**
		 * Adds to the list of all authored topic the most recently created topic by the user.
		 * <p>
		 * Insertion is in chronological order.
		 * 
		 * @param authoredTopics A new topic created by the user.
		 */
		public void addAuthoredTopics(ArrayList<TopicModel> authoredTopics) {
			this.authoredTopics = authoredTopics;
		}

		/**
		 * Returns all topics the user has selected as favorites.
		 * <p>
		 * Favorite topics are returned in the order that the user favorited them in.
		 * 
		 * @return All topics the user has selected as favorited.
		 */
		public ArrayList<TopicModel> getFavoriteTopics() {
			return favoriteTopics;
		}

		/**
		 * Add to the list of favorite topics, a topic the user has selected as a favorite.
		 * 
		 * @param favoriteTopics A topic the user has selected as a favorite.
		 */
		public void addFavoriteTopics(ArrayList<TopicModel> favoriteTopics) {
			this.favoriteTopics = favoriteTopics;
		}

		/**
		 * Return in chronological order all threads the user has created.
		 * 
		 * @return All threads the user has created (posted).
		 */
		public ArrayList<ThreadModel> getPostedThreads() {
			return postedThreads;
		}

		/**
		 * Add to the chronological list of posted threads by the user.
		 * 
		 * @param postedThreads A new thread created by the user.
		 */
		public void addPostedThreads(ArrayList<ThreadModel> postedThreads) {
			this.postedThreads = postedThreads;
		}

		/**
		 * Retrieve the last topic viewed by the user.
		 * 
		 * @return The topic last viewed by the user.
		 */
		public TopicModel getLastViewedTopic() {
			return lastViewedTopic;
		}

		/**
		 * Update the topic being viewed by the user.
		 * 
		 * @param lastViewedTopic The topic last viewed by the user.
		 */
		public void setLastViewedTopic(TopicModel lastViewedTopic) {
			this.lastViewedTopic = lastViewedTopic;
		}

		/**
		 * Keep track of the current thread viewed within a topic.
		 * 
		 * @return An Integer representing the unique number associated with a thread.
		 */
		public Integer getTopicPosition() {
			return topicPosition;
		}

		/**
		 * Set the thread number associated with a topic that the user is viewing.
		 * 
		 * @param topicPosition The unique number associated with a thread.
		 */
		public void setTopicPosition(Integer topicPosition) {
			this.topicPosition = topicPosition;
		}
		
}
