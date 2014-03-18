package ca.ualberta.team7project.network;

import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Updates the comments on the server
 * <p>
 * Passes comments on to ElasticSearchOperation
 */
public class ThreadUpdater
{
	ThreadListener refresh;
	
	/**
	 * Construct a TopicUpdater with no refresh callback
	 */
	public ThreadUpdater()
	{
		super();
		this.refresh = null;
	}
	
	/**
	 * Construct a TopicUpdater with an onRefresh() callback
	 * @param refresh an object implementing ThreadListener
	 */
	public ThreadUpdater(ThreadListener refresh)
	{
		super();
		this.refresh = refresh;
	}

	/**
	 * Pushes a single comment to the server
	 * <p>
	 * If editing, the old comment will be overwritten automatically
	 * <p>
	 * If a refresh callback was provided, it will be called after
	 * receiving a response from the server
	 * @param comment a comment/topic to be pushed
	 */
	public void sendComment(ThreadModel comment)
	{
		ElasticSearchOperation search = new ElasticSearchOperation();
		search.pushThreadModel(comment, refresh);
	}
	
	/*
	//from server
	private ThreadModel getCurrentTopic(UUID uniqueId)
	{
		return null;
	}
	
	//in memory
	private void updateOldTopic(ThreadModel oldTopic, ThreadModel newComment, UUID parent)
	{
		//add new comment to existing topic
		//the topic should be one that is temporarily being held in memory just for reply adding
	}
	
	//to server
	private void sendUpdatedTopic(ThreadModel topic)
	{
		persist.PushTopic(topic);
	}
	
	public void addTopic(ThreadModel thread)
	{
		//send new topic
		sendUpdatedTopic(thread);
	}
	
	public void addReply(ThreadModel newComment, UUID parent)
	{
		//update existing topic
		
		//if this function is called, then topmost should not be null
		ThreadModel oldTopic = getCurrentTopic(topmost);
		
		updateOldTopic(oldTopic, newComment, parent);
		
		sendUpdatedTopic(oldTopic);
	}
	*/
}
