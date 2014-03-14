package ca.ualberta.team7project.controllers;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Activity;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.ThreadPersistenceModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.network.TopicFetcher;
import ca.ualberta.team7project.network.TopicUpdater;
import ca.ualberta.team7project.views.ThreadListView;


public class ThreadListController extends Activity
{

	private ThreadListModel listModel;
	private static ThreadListView listView;
	private static Activity activity;
	
	/* Attributes necessary for passing between ThreadAlertView and ThreadListView */
	private static Boolean inTopic;
	private static Boolean editingThread;
	private static ThreadModel openThread;
	
	public ThreadListController(Activity activity)
	{
		
		ThreadListController.activity = activity;
		
		ThreadListController.inTopic = false;
		ThreadListController.editingThread = false;
		ThreadListController.openThread = null;
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
		listModel = new ThreadListModel();
		ThreadListController.listView = new ThreadListView(this.listModel, activity, this);
		
		this.refreshThreads();
	}
	
	/*
	 * NOTE THIS IS JUST A DEBUG METHOD FOR TESTING LISTVIEW RIGHT NOW
	 * Once cache and network is up, this is not necessary
	 */
	public void debugPopulate()
	{	
		
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax. Anyone else tired of pokemon examples?" +
				" I sure am. Guess ill start using movie references. Is elastic search pull working so I don't have " +
				"to do this anymore?", user, "Pokedex one");
		ThreadModel threadOne = new ThreadModel("Caught Charmander. Lots and lots of text. WOOOOOOT. " +
				"MORE TEXT. Stop writing already.", user, "Pokedex two");
		ThreadModel threadTwo = new ThreadModel("Caught Pidgeo. Keep adding text to see how the layout is " +
				"working. Is it working well? The buttons look ugly. Oh well, ill change them later. " +
				"They are just here so that ppl can start working on click listeners. Internal monologues are great.", user, 
				"Pokedex three");
		ThreadModel threadThree = new ThreadModel("Caught Pidgeo. Just checking if the list will scroll. "
				+ "Lots and lots of text needs to be written. Soon we can pull from ES and I won't have to write boring "
				+ "lines of text list this. YEAHHHHHH!", user, "Pokedex four");

		ArrayList<ThreadModel> threads = new ArrayList<ThreadModel>();
		threads.add(thread);
		threads.add(threadOne);
		threads.add(threadTwo);
		threads.add(threadThree);
				
		ThreadListModel newListModel = new ThreadListModel();
		newListModel.setTopics(threads);
		setListModel(newListModel);
	}

	/**
	 * If a network connection exists, pull the latest and greatest from ElasticSearch
	 * <p>
	 * ThreadListModel is populated with the results from ElasticSearch
	 */
	public void refreshThreads()
	{
		TopicFetcher fetcher = new TopicFetcher();
		
		ArrayList<ThreadModel> threads = fetcher.fetchTopics(TopicFetcher.SortMethod.NO_SORT);
		
		listModel = new ThreadListModel();
		listModel.setTopics(threads);
			
		//listView = new ThreadListView(this.listModel, activity);
		listView.notifyListChange(this.listModel);
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
	public static void editThread(ThreadModel thread)
	{
		
		// TODO confirm that the user has permission to edit this thread.
		
		ThreadListController.setEditingTopic(true);
		ThreadListController.setOpenThread(thread);
		
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
	public static void replyThread(ThreadModel thread)
	{
		ThreadListController.setEditingTopic(false);
		ThreadListController.setInTopic(true);
		ThreadListController.setOpenThread(thread);

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
		/* First we need to get the UserModel to associate with a ThreadModel */
		UserModel currentUser = MainActivity.getUserController().getUser().getUser();
		ThreadModel newThread = new ThreadModel(comment, currentUser, title);
		
		TopicUpdater updater = new TopicUpdater();
		
		/* Determine if the user was editing, replying or creating a new thread */
		if(getEditingTopic() == true)
		{
			/* User edited a thread. Update models appropriately */
			/* Insert newThread in place of the open thread in the models */
			// TODO thread persistence model needs a method for this.
		}
		else
		{
			/* User was not editing thread. Could be replying or creating a topic */
			if(getInTopic() == true)
			{
				/* User replied. Updated models appropriately */
				/* Append the reply to the openThread and then replace in the model */
				// TODO thread persistence model needs a method for this
			}
			/* User created new topic. Upload to Elastic Search */
			else
			{				
				
			}
		}
		
		setEditingTopic(false);
		
		updater.sendComment(newThread);
		
		this.refreshThreads();
	}
	
	/* Your standard getters/setters */
	
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
	
	public static Boolean getInTopic()
	{
		
		return ThreadListController.inTopic;
	}
	
	public static void setInTopic(Boolean type)
	{
		
		ThreadListController.inTopic = type;
	}
		
	public static Boolean getEditingTopic()
	{
		
		return ThreadListController.editingThread;
	}
	
	public static void setEditingTopic(Boolean type)
	{
		
		ThreadListController.editingThread = type;
	}

	public static ThreadModel getOpenThread()
	{
		
		return openThread;
	}

	public static void setOpenThread(ThreadModel openThread)
	{
		
		ThreadListController.openThread = openThread;
	}
}
