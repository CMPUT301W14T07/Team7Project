package ca.ualberta.team7project.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class holds the list of topics
 * 
 */
public class ThreadListModel
{

	private LinkedList<ThreadModel> topics;

	/**
	 * Construct new, empty list
	 */
	public ThreadListModel()
	{

		super();
		topics = new LinkedList<ThreadModel>();
	}

	/**
	 * Construct from existing list of topics
	 * 
	 * @param topics
	 *            list of existing topics
	 */
	public ThreadListModel(LinkedList<ThreadModel> topics)
	{

		super();
		this.topics = topics;
	}

	/**
	 * Adds to or updates a topic in the list
	 * <p>
	 * Replaces the old topic object if it exists Otherwise simply adds the new
	 * topic to the list
	 * <p>
	 * This method should only be called to sync from disk or the server. This
	 * method should not be called <i>directly</i> to update the topic after the
	 * user writes a new comment
	 * 
	 * @param topic
	 *            the topic to add or update from
	 */
	public void UpdateTopic(ThreadModel topic)
	{

		Integer id = topic.getUniqueID();

		boolean exists = false;

		for (ThreadModel t : topics)
		{
			if (t.getUniqueID() == topic.getUniqueID())
			{
				exists = true; // update the existing topic
				topics.remove(t);
				topics.add(topic);
				break;
			}
		}

		if (exists == false)
		{
			topics.add(topic); // add a new topic
		}
	}
	
	public List<ThreadModel> getList() 
	{
		return null;
	}

}
