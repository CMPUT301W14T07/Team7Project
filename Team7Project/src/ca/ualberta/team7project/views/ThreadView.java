package ca.ualberta.team7project.views;

import java.util.UUID;

import android.app.Activity;
import android.util.Log;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.network.TopicUpdater;


public class ThreadView extends Activity implements ThreadAlertListener, ThreadListener
{
	final private UUID parentId;
	private TopicUpdater updater;

	/*
	 * Notes on the difference in listeners (~michael)
	 * ThreadAlertsListener listens for the creation of new ThreadModels from ThreadAlertView
	 * ThreadListener listens for button clicks on ThreadModel in ThreadAdapter
	 */
	
	public ThreadView(UUID parentId, TopicUpdater updater)
	{
		super();
		this.parentId = parentId;
		this.updater = updater;
	}

	/**
	 * Called when the user has completed the ThreadAlertView dialog
	 * @see ThreadAlertView.java
	 * @param title associated with the new topic or comment
	 * @param comment associated with the new topic or comment
	 */
	@Override
	public void createThread(String title, String comment)
	{
		//we do not update our working model in place here
		// All this should go in the controller. Only listening for button click here.
/*		if(parentId == null)
		{
			//create a new topic (ThreadModel)
			 Belongs in the controller 
			//ThreadModel topic = new ThreadModel(comment, user, title);
			
			//send it to the TopicUpdater
			//updater.addTopic(topic);
		}
		else
		{
			//is replying
			
			//create a new comment (ThreadModel)
			
			//send it to a different method of the TopicUpdater
		}*/
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

	@Override
	public void onFavoriteClick(ThreadModel thread)
	{
		Log.e("debug", "listeners worked!");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReplyClick(ThreadModel thread)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCacheClick(ThreadModel thread)
	{

		// TODO Auto-generated method stub
		
	}
}
