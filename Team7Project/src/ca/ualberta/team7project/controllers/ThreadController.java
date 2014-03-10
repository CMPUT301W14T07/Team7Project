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
import ca.ualberta.team7project.network.TopicUpdater;
import ca.ualberta.team7project.views.ThreadView;

public class ThreadController
{
	final private UUID parentId;
	private Context context;
	private FragmentManager fragment;
	private ThreadModel thread;
	private ThreadView threadView;
	private UUID topmost; //null only if currently browsing topics (comments with no parents)
	private TopicUpdater updater;
	
	/*
	 * Notes (@michael):
	 * 
	 * I don't think UUID topmost is necessary. ThreadModel contains all necessary information within itself to determine if 
	 * a comment is topmost or not. ThreadListView onclick listeners can set the ThreadModel in this class.
	 * 
	 * The only ThreadModel we care about is the one the user is interacting with. IE: the one they are replying to, caching etc.
	 * 
	 * I also don't see why we need to pass in a UserModel. Get it from the controller if it is needed at all.
	 */
	
	public ThreadController(Context context, FragmentManager fragment, UUID parentId, UserModel user, UUID topmost)
	{
		super();
		this.context = context;
		this.parentId = parentId;
	
		/* Create the view and set the models here */
		/*
		 * TODO:
		 * For now just setting thread = null.
		 */
		thread = null;
		
		this.topmost = topmost;
		
		this.updater = new TopicUpdater(topmost);  
		
		threadView = new ThreadView(parentId, user, this.updater);
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
