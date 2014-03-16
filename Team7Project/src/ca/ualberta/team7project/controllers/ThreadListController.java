package ca.ualberta.team7project.controllers;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.util.Log;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.network.TopicFetcher;
import ca.ualberta.team7project.network.TopicUpdater;
import ca.ualberta.team7project.views.ThreadListView;


public class ThreadListController extends Activity
{
	private ArrayList<UUID> stack;
	
	private ThreadListModel listModel;
	private static ThreadListView listView;
	private static Activity activity;
	
	/* Attributes necessary for passing between ThreadAlertView and ThreadListView */
	private Boolean inTopic;
	private Boolean editingThread;
	private ThreadModel openThread;
	
	public ThreadListController(Activity activity)
	{
		stack = new ArrayList<UUID>();
		stack.add(UUID.fromString(ThreadModel.ROOT));
		
		ThreadListController.activity = activity;
		
		inTopic = false;
		editingThread = false;
		openThread = null;
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
		listModel = new ThreadListModel();
		ThreadListController.listView = new ThreadListView(this.listModel, activity, this);
		
		this.refreshThreads();
	}
	
	/**
	 * Backs up to parent thread
	 * 
	 * @return true if there was a thread to exit to, false if there is no parent thread
	 */
	public boolean exitThread()
	{
		if(stack.size() == 1)
			return false;
		
		stack.remove(stack.size()-1);
		refreshThreads();
		return true;
	}
	
	public void enterThread(ThreadModel thread)
	{
		UUID id = thread.getUniqueID();
		
		stack.add(id);
		
		refreshThreads();
	}
	
	/**
	 * If a network connection exists, pull the latest and greatest from ElasticSearch
	 * <p>
	 * ThreadListModel is populated with the results from ElasticSearch
	 */
	public void refreshThreads()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
		
				TopicFetcher fetcher = new TopicFetcher();
				
				UUID parent = stack.get(stack.size()-1);
				
				ArrayList<ThreadModel> threads = fetcher.fetchChildComments(parent, TopicFetcher.SortMethod.NO_SORT);
				
				listModel = new ThreadListModel();
				listModel.setTopics(threads);
					
				listView.notifyListChange(listModel);
		
			}
		});
	}

	/**
	 * Responds to the users desire to edit a thread.
	 * <p>
	 * editingTopic is set to true to represent that the user is editing a topic. The thread is saved
	 * in expectation that the user will finish the prompt and that the new model can be updated and inserted
	 * into the appropriate persistence models in other methods.
	 * 
	 * @param thread that the user is editing
	 */
	public void editThread(ThreadModel thread)
	{
		// TODO confirm that the user has permission to edit this thread
		// See issue https://github.com/CMPUT301W14T07/Team7Project/issues/31
		
		setEditingTopic(true);
		setOpenThread(thread);
		
		ThreadAlertView threadAlert = new ThreadAlertView();
		threadAlert.show(((ca.ualberta.team7project.MainActivity)MainActivity.getMainContext())
				.getFragmentManager(), "New Thread Alert");
	}
	
	/**
	 * Responds to the users desire to reply to a thread.
	 * <p>
	 * editingTopic is set to false to represent that we are not editing a topic, and setInTopic is true
	 * to represent that we are not creating a new topic and are in an existing topic.
	 * <p> 
	 * The thread we are replying to is saved in expectation that the user will finish the prompt and 
	 * add the reply to the thread, whereupon it is inserted into the appropriate model in other methods 
	 * 
	 * @param thread that the user is replying to
	 */
	public void replyThread(ThreadModel thread)
	{
		setEditingTopic(false);
		setInTopic(true);
		setOpenThread(thread);

		ThreadAlertView threadAlert = new ThreadAlertView();
		threadAlert.show(((ca.ualberta.team7project.MainActivity)MainActivity.getMainContext())
				.getFragmentManager(), "New Thread Alert");
	}
	
	/**
	 * Creates a new thread and inserts it into the model in response to a click listener.
	 * 
	 * @param title of the thread
	 * @param body of the thread
	 */
	public void createThread(String title, String comment)
	{
		// See issue https://github.com/CMPUT301W14T07/Team7Project/issues/32
		// Much of this code should actually be in the updater class or persistence.....
		
		/* First we need to get the UserModel to associate with a ThreadModel */
		UserModel currentUser = MainActivity.getUserController().getUser().getUser();
		ThreadModel newThread = new ThreadModel(comment, currentUser, title);
		
		TopicUpdater updater = new TopicUpdater(listView);
		
		/* Determine if the user was editing, replying or creating a new thread */
		if(getEditingTopic() == true)
		{
			/* User edited a thread. Update models appropriately */
			/* Insert newThread in place of the open thread in the models */			
			if(this.getOpenThread() == null)
				Log.e(MainActivity.DEBUG, "Editing null thread");
			
			newThread.setUniqueID(this.getOpenThread().getUniqueID());
			newThread.setParentUUID(this.getOpenThread().getParentUUID());
			newThread.setTopicUUID(this.getOpenThread().getTopicUUID());
			
			updater.sendComment(newThread);
			ThreadListController.listView.editToast();
		}
		else
		{
			/* User was not editing thread. Could be replying or creating a topic */
			if(getInTopic() == true)
			{
				/* User replied. Updated models appropriately */
				/* Append the reply to the openThread and then replace in the model */
				// TODO thread persistence model needs a method for this (or TopicUpdate)
				// see https://github.com/CMPUT301W14T07/Team7Project/issues/32
				
				if(this.getOpenThread() == null)
					Log.e(MainActivity.DEBUG, "reply to null thread");
				UUID parent = this.getOpenThread().getUniqueID();
				
				newThread.setParentUUID(parent);
				updater.sendComment(newThread);
				
				ThreadListController.listView.replyingToast();
			}
			/* User created new topic. Upload to Elastic Search */
			else
			{				
				updater.sendComment(newThread);
				ThreadListController.listView.newTopicToast();
			}
		}
		
		setEditingTopic(false);
	
		this.refreshThreads();
	}
	
	public ThreadListModel getListModel()
	{
	
		return listModel;
	}
	
	public void setListModel(ThreadListModel threads)
	{
	
		this.listModel = threads;
	}
	
	public ThreadListView getListView()
	{
	
		return listView;
	}

	public void setListView(ThreadListView listView)
	{
	
		ThreadListController.listView = listView;
	}
	
	public Activity getActivity()
	{
	
		return activity;
	}
	
	public void setActivity(Activity activity)
	{
		
		ThreadListController.activity = activity;
	}
	
	public Boolean getInTopic()
	{
		
		return inTopic;
	}
	
	public void setInTopic(Boolean type)
	{
		
		inTopic = type;
	}
		
	public Boolean getEditingTopic()
	{
		
		return editingThread;
	}
	
	public void setEditingTopic(Boolean type)
	{
		
		editingThread = type;
	}

	public ThreadModel getOpenThread()
	{
		
		return openThread;
	}

	public void setOpenThread(ThreadModel openThread)
	{
		this.openThread = openThread;
	}
}
