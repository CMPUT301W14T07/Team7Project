package ca.ualberta.team7project.controllers;

import android.app.FragmentManager;
import android.content.Context;
import ca.ualberta.team7project.models.ThreadListModel;
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
		this.listModel = new ThreadListModel();		
		this.listView = new ThreadListView();
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
	}
	
}
