package ca.ualberta.team7project.views;

import android.app.DialogFragment;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;


public class ThreadView implements ThreadAlertListener
{

	
	
	
	/**
	 * Called when the user has completed the ThreadAlertView dialog
	 * @see ThreadAlertView.java
	 * @param dialog associated with the thread prompt
	 * @param title associated with the new topic or comment
	 * @param comment associated with the new topic or comment
	 */
	@Override
	public void createThread(DialogFragment dialog, String title, String comment)
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
