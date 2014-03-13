package ca.ualberta.team7project.controllers;

import java.util.LinkedList;

import android.app.Activity;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ThreadListView;


public class ThreadListController extends Activity
{

	private ThreadListModel listModel;
	private ThreadListView listView;
	private static Activity activity;
	
	/* Attributes necessary for passing between ThreadAlertView and ThreadListView */
	private static Boolean inTopic;
	private static Boolean editingThread;
	private static ThreadModel openThread;
	
	public ThreadListController(Activity activity){
		
		ThreadListController.activity = activity;
		
		ThreadListController.inTopic = false;
		ThreadListController.editingThread = false;
		ThreadListController.openThread = null;
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
		debugPopulate();
		
		this.listView = new ThreadListView(this.listModel, activity);
		
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

		LinkedList<ThreadModel> threads = new LinkedList<ThreadModel>();
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
		// TODO call the persistence and network classes here
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
	
		this.listView = listView;
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
