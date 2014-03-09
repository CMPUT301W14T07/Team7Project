package ca.ualberta.team7project.controllers;

import java.util.LinkedList;

import android.app.FragmentManager;
import android.content.Context;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.views.ThreadListView;


public class ThreadListController
{

	private Context context;
	private FragmentManager fragment;
	private ThreadListModel listModel;
	private ThreadListView listView;
	
	public ThreadListController(Context context, FragmentManager fragment){
		
		this.context = context;
		this.fragment = fragment;
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
		debugPopulate(); //temporary debug populate

		this.listModel = new ThreadListModel();		
		this.listView = new ThreadListView(this.listModel);

	}
	
	/*
	 * NOTE THIS IS JUST A DEBUG METHOD FOR TESTING LISTVIEW RIGHT NOW
	 * Once cache and network is up, this is not necessary
	 */
	public void debugPopulate()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		ThreadModel threadOne = new ThreadModel("Caught Charmander", user, null);
		ThreadModel threadTwo = new ThreadModel("Caught Pidgeo", user, null);
		
		LinkedList<ThreadModel> threads = new LinkedList<ThreadModel>();
		threads.add(thread);
		threads.add(threadOne);
		threads.add(threadTwo);
		listModel.setTopics(threads);
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
	
}
