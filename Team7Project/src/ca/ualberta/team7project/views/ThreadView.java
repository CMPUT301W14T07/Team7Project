package ca.ualberta.team7project.views;

import android.app.DialogFragment;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;


public class ThreadView implements ThreadAlertListener
{

	/*
	 * Listeners for clicks we be here. To be implemented.
	 */
	
	/**
	 * Called when the user has completed the ThreadAlertView dialog
	 * @see ThreadAlertView.java
	 * @param title associated with the new topic or comment
	 * @param comment associated with the new topic or comment
	 */
	@Override
	public void createThread(String title, String comment)
	{

		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the user requests to add an image to their topic or topic reply
	 * @see ThreadAlertView.java
	 * @param dialog associated with the thread prompt
	 */
	@Override
	public void insertImage(DialogFragment dialog)
	{

		// TODO Auto-generated method stub
		
	}

	
	

}
