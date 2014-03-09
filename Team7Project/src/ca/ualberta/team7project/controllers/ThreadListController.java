package ca.ualberta.team7project.controllers;

import java.util.LinkedList;

import android.app.Activity;
import android.util.Log;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ThreadListView;


public class ThreadListController extends Activity
{

	private ThreadListModel listModel;
	private ThreadListView listView;
	private static Activity activity;
	
	public ThreadListController(Activity activity){
		
		ThreadListController.activity = activity;
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
		debugPopulate();
				
		/* Since UI runs on a separate thread, we have to wait for the list to populate */
		/* Therefore, we need to pull from elastic search or the cache before initializing the view */
		if(listModel.getSize() > 0){
			this.listView = new ThreadListView(this.listModel, activity);
		}
		else{
			// TODO pull from server/filesystem here.
		}

	}
	
	/*
	 * NOTE THIS IS JUST A DEBUG METHOD FOR TESTING LISTVIEW RIGHT NOW
	 * Once cache and network is up, this is not necessary
	 */
	public void debugPopulate()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null, "Pokedex one");
		ThreadModel threadOne = new ThreadModel("Caught Charmander", user, null, "Pokedex two");
		ThreadModel threadTwo = new ThreadModel("Caught Pidgeo", user, null, "Pokedex three");
		
		LinkedList<ThreadModel> threads = new LinkedList<ThreadModel>();
		threads.add(thread);
		threads.add(threadOne);
		threads.add(threadTwo);
				
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
	
		this.activity = activity;
	}
	
}
