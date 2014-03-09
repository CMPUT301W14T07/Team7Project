/**
 * Handles the state of ThreadModel and updates views
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import java.util.UUID;

import android.app.FragmentManager;
import android.content.Context;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ThreadView;

public class ThreadController
{
	final private UUID parentId;
	private Context context;
	private FragmentManager fragment;
	private ThreadModel thread;
	private ThreadView threadView;
	
	public ThreadController(Context context, FragmentManager fragment, UUID parentId, UserModel user)
	{
		super();
		this.context = context;
		this.fragment = fragment;
		this.parentId = parentId;
	
		/* Create the view and set the models here */
		/*
		 * TODO:
		 * For now just setting thread = null. However, once threadpersistence works, need to set currently viewed
		 * thread in this constructor. Or if new application start, set the home screen for threads.
		 */
		thread = null;
		
		threadView = new ThreadView(parentId, user);
	}

	/**
	 * A helper method for context sensitive alert dialogs.
	 * <P>
	 * When user clicks a reply or post button, the alert dialogs need to know whether
	 * the user is posting a new topic or replying to an existing topic.
	 * <P>
	 * A user is either viewing the home thread of all topics, or is inside a topic.
	 * 
	 * @return True if the user is in the child thread of a topic.
	 */
	public static Boolean inTopic(){
		// TODO
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
	
	public UUID getParentId()
	{
	
		return parentId;
	}
}
