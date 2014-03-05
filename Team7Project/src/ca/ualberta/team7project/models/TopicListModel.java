package ca.ualberta.team7project.models;

import java.util.LinkedList;

/**
 * This class holds the list of topics
 * 
 */
public class TopicListModel
{

	private LinkedList<TopicModel> topics;

	/**
	 * Construct new, empty list
	 */
	public TopicListModel()
	{

		super();
		topics = new LinkedList<TopicModel>();
	}

	/**
	 * Construct from existing list of topics
	 * 
	 * @param topics
	 *            list of existing topics
	 */
	public TopicListModel(LinkedList<TopicModel> topics)
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
	public void UpdateTopic(TopicModel topic)
	{

		Integer id = topic.getTopicID();

		boolean exists = false;

		for (TopicModel t : topics)
		{
			if (t.getTopicID() == topic.getTopicID())
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

}
