package ca.ualberta.team7project.network;

import java.util.UUID;

import ca.ualberta.team7project.interfaces.RefreshListener;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.ThreadPersistenceModel;

public class TopicUpdater
{
	RefreshListener refresh;
	
	public TopicUpdater(RefreshListener refresh)
	{
		super();
		this.refresh = refresh;
	}

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
