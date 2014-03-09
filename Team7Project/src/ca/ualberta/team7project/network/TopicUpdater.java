package ca.ualberta.team7project.network;

import java.util.UUID;
import ca.ualberta.team7project.models.ThreadModel;

public class TopicUpdater
{
	private ThreadModel getCurrentTopic(UUID uniqueId)
	{
		return null;
	}
	
	private void updateOldTopic(ThreadModel oldTopic, ThreadModel newComment, UUID parent)
	{
		
	}
	
	private void sendUpdatedTopic(ThreadModel topic)
	{
		
	}
	
	public void updateTopic(ThreadModel thread)
	{
		ThreadModel oldTopic = getCurrentTopic(thread.getUniqueID());
		
		if(oldTopic == null)
		{
			//brand new topic so just send it
			sendUpdatedTopic(thread);
			
		}
		else
		{
			//update existing topic
		}
		
		//either way, re-fetch this one topic
	}
}
