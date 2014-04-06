package ca.ualberta.team7project.network;

import ca.ualberta.team7project.cache.CacheOperation;
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
		CacheOperation cache = new CacheOperation();
		
		search.pushThreadModel(comment, refresh);
		cache.saveThread(comment);
	}
}
