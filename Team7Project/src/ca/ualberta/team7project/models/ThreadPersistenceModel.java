package ca.ualberta.team7project.models;

import java.util.LinkedList;

/**
 * Class that provides methods for storing and loading from the ElasticSearch
 * server and the disk
 * 
 */
public class ThreadPersistenceModel
{

	// TODO: method that gets list of most relevant X topicID

	/**
	 * Fetch a single topic from the Elastic Search server
	 * 
	 * @param topicID
	 *            the Elastic Search index genereted when the topic was created
	 * @return The up-to-date topic from the server
	 */
	public ThreadModel PullTopic(Integer topicID)
	{

		// TODO:

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
	 * @param topic
	 *            the existing topic that needs to be updated
	 * @return The most up-to-date version of the topic
	 */
	public ThreadModel GetUpdatedTopic(ThreadModel topic)
	{

		ThreadModel temp = PullTopic(topic.getUniqueID());

		if (topic == temp)
			return topic; // if already up-to-date, just return the topic
							// argument

		return temp;
	}

	// TODO:
	// ***********************************************************************
	// How will we add new comments?
	// approach: push entire updated topic to server
	// problem: serious race condition results
	// approach: insert a single comment into the elastic search object thing
	// problem: not sure if/how we can do this
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
	 * @param topics
	 *            the entire list of topics to save
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
