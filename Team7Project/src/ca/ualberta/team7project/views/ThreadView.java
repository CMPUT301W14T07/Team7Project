package ca.ualberta.team7project.views;

import java.util.UUID;

import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;


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
		//create a new ThreadModel
		
		//send it to the TopicUpdater
		
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
