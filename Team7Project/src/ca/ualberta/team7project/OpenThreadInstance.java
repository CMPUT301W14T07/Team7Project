package ca.ualberta.team7project;

import ca.ualberta.team7project.models.ThreadModel;

/**
 * Singleton used to pass a user-selected thread to an alert view
 */
public class OpenThreadInstance
{
	private static OpenThreadInstance instance;
	private ThreadModel thread;
	
	private OpenThreadInstance() {}
	
	public static OpenThreadInstance getInstance()
	{
		if(instance == null)
			instance = new OpenThreadInstance();
		
		return instance;
		
	}
	
	public ThreadModel getThread()
	{
		return this.thread;
	}
	
	public void setThread(ThreadModel thread)
	{
		this.thread = thread;
	}
}
