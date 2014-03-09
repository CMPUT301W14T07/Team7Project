package ca.ualberta.team7project.views;

import java.util.UUID;

import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;
import ca.ualberta.team7project.models.ThreadModel;


public class ThreadView implements ThreadAlertListener
{
	final private UUID parentId;
	
	public ThreadView(UUID parentId)
	{
		super();
		this.parentId = parentId;
	}

	/**
	 * Called when the user has completed the ThreadAlertView dialog
	 * @see ThreadAlertView.java
	 * @param dialog associated with the thread prompt
	 * @param title associated with the new topic or comment
	 * @param comment associated with the new topic or comment
	 */
	@Override
	public void createThread(String title, String comment)
	{
		//we do not update our working model in place here
		
		if(parentId == null)
		{
			//create a new topic (ThreadModel)
			
			//ThreadModel topic = new ThreadModel();
		
			//send it to the TopicUpdater
		}
		else
		{
			//is replying
			
			//create a new comment (ThreadModel)
			
			//send it to a different method of the TopicUpdater
		}
	}

	/**
	 * Called when the user requests to add an image to their topic or topic reply
	 * @see ThreadAlertView.java
	 * @param dialog associated with the thread prompt
	 */
	@Override
	public void insertImage()
	{

		// TODO Auto-generated method stub
		
	}
}
