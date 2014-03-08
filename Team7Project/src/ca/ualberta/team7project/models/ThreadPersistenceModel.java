package ca.ualberta.team7project.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Class that provides methods for storing and loading from the ElasticSearch
 * server and the disk
 * 
 */
public class ThreadPersistenceModel
{

	public static enum SortMethod //methods for getting the "best/most relevant" topics
	{
		DATE, LOCATION, DATE_LOCATION
	}

	/**
	 * Fetch a single topic from the Elastic Search server
	 * 
	 * @param topicID	the topic's Elastic Search index, generated by java UUID
	 * 
	 * @return The up-to-date topic from the server
	 */
	public ThreadModel PullTopic(UUID topicID)
	{

		// TODO:
		//pass in the elastic search query?
		// search by "query" method on ID, or just pull by index
		// receive a ThreadModel
		// (the timestamp needs to be filled in?)

		return null;
	}
	
	/**
	 * Fetch the most relevant topics by some sorting criteria, up to (maxTopics) number of topics
	 * <p>
	 * Topics may be fetched by the following criteria: 
	 * <ul>
	 * <li>most recently updated</li>
	 * <li>top-level comment closest to the user's set location</li>
	 * <li>both of the above</li>
	 * </ul>
	 * 
	 * @param maxTopics	the max amount of topics to pull
	 * 				
	 * @return an ArrayList of topics, containing from 0 to (maxTopics) elements
	 */
	public ArrayList<ThreadModel> PullBestTopics(SortMethod sortMethod, Integer maxTopics)
	{

		// TODO:
		// pass in the elastic search query?
		// receive a list of ThreadModel
		// (the timestamp needs to be filled in?)

		return null;
	}


	/**
	 * Fetch the most up-to-date version of a topic from the server
	 * <p>
	 * Checks if the topic's data has changed. If the topic has changed, returns
	 * the updated topic (the caller should save the updated topic to disk)
	 * <p>
	 * For now, this simply pulls the entire topic from server and compare. We
	 * may later add in a timestamp comparison before pulling to increase
	 * efficiency
	 * 
	 * @param topic	the existing topic that needs to be updated
	 * @return The most up-to-date version of the topic
	 */
	public ThreadModel GetUpdatedTopic(ThreadModel topic)
	{

		ThreadModel temp = PullTopic(topic.getUniqueID());

		if (topic.getTimestamp() == temp.getTimestamp())
			return topic; // if already up-to-date, just return the topic argument

		return temp;
	}

	// TODO:
	// ***********************************************************************
	// How will we add new comments?
	// approach: push entire updated topic to server
	// problem: serious race condition results
	// solution: check after pushing to see if it went through
	// ***********************************************************************

	/**
	 * Saves an entire topic list to disk
	 * <p>
	 * Should be called every time the data changes ie.
	 * <ul>
	 * <li>after pulling topics from the server</li>
	 * <li>after submitting/editing comments (overlaps with above)</li>
	 * <li>after deleting topics for caching purposes</li>
	 * </ul>
	 * <p>
	 * Because the TopicPersistenceClass has no knowledge of the TopicListModel,
	 * it will probably be neccasary to call this method from outside the class
	 * 
	 * @param topics	the entire list of topics to save
	 */
	public void SaveTopicListToDisk(LinkedList<ThreadModel> topics)
	{

		// TODO:

	}

	/**
	 * Loads the entire topic list from disk
	 * <p>
	 * Should be called on app startup only
	 * 
	 * @return A LinkedList of TopicModel for initializing a TopicListModel
	 */
	public LinkedList<ThreadModel> LoadTopicListFromDisk()
	{

		// TODO:

		return null;
	}
}
