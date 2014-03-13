/**
 * This class holds the list of ThreadModel objects
 * <p>
 * The list is used to populate the list view of threads in the application.
 * 
 */
package ca.ualberta.team7project.models;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import ca.ualberta.team7project.network.ElasticSearchOperation;

public class ThreadListModel
{

	private LinkedList<ThreadModel> topics;

	/**
	 * Construct new, empty list
	 */
	public ThreadListModel()
	{
		super();
		this.topics = new LinkedList<ThreadModel>();
	}
	

	/**
	 * Construct from existing list of topics
	 * 
	 * @param topics list of existing topics
	 */
	public ThreadListModel(LinkedList<ThreadModel> topics)
	{
		super();
		this.topics = topics;
	}
	
	public void addThreadCollection(Collection<ThreadModel> threads){
		this.topics.addAll(threads);
	}
	
	public LinkedList<ThreadModel> getTopics()
	{
	
		return topics;
	}
	
	public void setTopics(LinkedList<ThreadModel> topics)
	{
	
		this.topics = topics;
	}	
	
	public void addTopic(ThreadModel thread)
	{
		this.topics.add(thread);
		
		//just to test if it's working wel
		ElasticSearchOperation.pushThreadModel(thread);
	}

	/**
	 * Retrieve a ThreadModel by index position
	 * @param index of the ThreadModel
	 * @return the Thread associated with that index
	 */
	public ThreadModel getThread(int index)
	{
		return this.topics.get(index);
	}
	
	public int getSize()
	{
		return this.topics.size();
	}
	
	public ThreadModel getLastTopic()
	{
		return topics.getLast();
	}
	
	public ThreadModel getFirstTopic()
	{
		return topics.getFirst();
	}
	
	public void insertFirstTopic(ThreadModel thread)
	{
		this.topics.addFirst(thread);
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
	 * @param topic the topic to add or update from
	 */
	public void UpdateTopic(ThreadModel topic)
	{

		UUID id = topic.getUniqueID();

		boolean exists = false;

		for (ThreadModel t : topics)
		{
			if (t.getUniqueID() == topic.getUniqueID())
			{
				exists = true;
				topics.remove(t);
				topics.add(topic);
				break;
			}
		}

		if (exists == false)
		{
			topics.add(topic);
		}
	}


	public void clear() {
		// TODO Auto-generated method stub
		this.topics.clear();
	}
}

