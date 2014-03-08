/**
 * Handles the state of ThreadModel and updates views
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import android.app.FragmentManager;
import android.content.Context;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.views.ThreadView;

public class ThreadController
{

	private Context context;
	private FragmentManager fragment;
	private ThreadModel thread;
	private ThreadView threadView;
	
	public ThreadController(Context context, FragmentManager fragment)
	{
		super();
		this.context = context;
		this.fragment = fragment;
	
		/* Create the view and set the models here */
		/*
		 * TODO:
		 * For now just setting thread = null. However, once threadpersistence works, need to set currently viewed
		 * thread in this constructor. Or if new application start, set the home screen for threads.
		 */
		threadView = new ThreadView();
		thread = null;
	}

	/**
	 * A helper method for context sensitive alert dialogs.
	 * @return True if the user is viewing 
	 */
	public Boolean inTopic(){
		//TODO
		return true;
	}
	
	
	public Context getContext()
	{
	
		return context;
	}

	
	public void setContext(Context context)
	{
	
		this.context = context;
	}

	
	public FragmentManager getFragment()
	{
	
		return fragment;
	}

	
	public void setFragment(FragmentManager fragment)
	{
	
		this.fragment = fragment;
	}

	
	public ThreadModel getThread()
	{
	
		return thread;
	}

	
	public void setThread(ThreadModel thread)
	{
	
		this.thread = thread;
	}

	
	public ThreadView getThreadView()
	{
	
		return threadView;
	}

	
	public void setThreadView(ThreadView threadView)
	{
	
		this.threadView = threadView;
	}
	
	
	
}
