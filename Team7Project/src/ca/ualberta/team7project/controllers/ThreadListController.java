package ca.ualberta.team7project.controllers;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.TagInsertAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.network.ConnectionDetector;
import ca.ualberta.team7project.network.ServerPolling;
import ca.ualberta.team7project.network.ThreadFetcher;
import ca.ualberta.team7project.network.ThreadUpdater;
import ca.ualberta.team7project.views.ThreadListView;
import ca.ualberta.team7project.views.ThreadListView.FavoriteMode;

/**
 * Manages the active list of comments
 * <p>
 * Contains methods for refreshing, adding, and editing comments
 * <p>
 * Manages the internal comment-nesting stack and handles navigation between different nesting levels
 */
public class ThreadListController extends Activity
{
	private ThreadListModel listModel;
	private static ThreadListView listView;
	private static Activity activity;
	
	/* Attributes necessary for passing between ThreadAlertView and ThreadListView */
	private Boolean inTopic;
	private Boolean editingThread;
	private ThreadModel openThread;
	
	private NavigationController navigation;
	private ConnectionDetector connection;
	
	public ThreadListController(Activity activity)
	{
		ThreadListController.activity = activity;
		
		inTopic = false;
		editingThread = false;
		openThread = null;
		
		/* ThreadListModel needs to be populated. Either pull from elastic search or cache */
		listModel = new ThreadListModel();
		ThreadListController.listView = new ThreadListView(this.listModel, activity, this);
		
		navigation = new NavigationController(listView, activity);
		
		this.refreshThreads();
		
		connection = new ConnectionDetector(MainActivity.getMainContext());
		initiateServerPolling();
	}
	
	/**
	 * Polls elastic search for updates every 20 seconds
	 */
	public void initiateServerPolling()
	{
		ServerPolling updater = new ServerPolling(new Runnable() {
			
			@Override
			public void run()
			{	
				checkForUpdate();
			}
		});

		updater.startUpdates();
	}
	
	/**
	 * pull the threads from server and check if there are updates.
	 * but actually checking is based on number variation and first thread difference
	 * It only works for small scale of threads
	 */
	public void checkForUpdate(){
		
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				ArrayList<ThreadModel> threads = navigation.getRefreshedThreads();
				
				//little bug that threads is null when app starts
				//don't know how to fix it
				if(threads == null)
					return;
				
				if(listModel.getSize() != threads.size()){
					MainActivity.userListener.pullNewToast();
					
					//save in cache
					CacheOperation saveCache = new CacheOperation();
					saveCache.saveCollection(threads);
					saveCache.saveFile(activity);
				}
				else if(threads.size()!=0){
					//check if the first thread is the same
					UUID uuid1 = listModel.getThread(0).getUniqueID();
					UUID uuid2 = threads.get(0).getUniqueID();
					if(!uuid1.equals(uuid2)){
						MainActivity.userListener.pullNewToast();
						
						//save in cache
						CacheOperation saveCache = new CacheOperation();
						saveCache.saveCollection(threads);
						saveCache.saveFile(activity);
					}
						
				}
			}
		});
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
				listModel = new ThreadListModel();
				
				ArrayList<ThreadModel> threads = navigation.getRefreshedThreads();
				
				if(threads != null)
					listModel.setTopics(threads);
					
				listView.notifyListChange(listModel);
				
				CacheOperation saveCache = new CacheOperation();
				saveCache.saveCollection(threads);
				saveCache.saveFile(activity);
			}
		});
	}
	
	
	/**
	 * When a user clicks on the Favorite button, the ThreadModel UUID is added
	 * <p>
	 * And All the comments of the thread is cached 
	 * @param thread which the user clicked on
	 */
	public void addFavorite(ThreadModel thread, FavoriteMode fm)
	{
		CacheOperation operation = MainActivity.getCacheOperation();
		Context context = MainActivity.getMainContext();
		ThreadFetcher fetcher = new ThreadFetcher();
		
		switch(fm)
		{
			case FAVORITE:
				MainActivity.userListener.favoriteToast();
				MainActivity.getUserController().getUser().addFavoriteComment(thread);
				
				//pull the list of thread from server and save into cache
				operation.saveCollection(fetcher.fetchAllComments(thread.getTopicUUID()));
				
				operation.saveThread(thread);
				operation.saveFile(context);
				break;
				
			case READ_LATER:
				MainActivity.userListener.cacheToast();

				MainActivity.getUserController().getUser().addFavoriteComment(thread);

				//pull the list of thread from server and save into cache
				operation.saveCollection(fetcher.fetchAllComments(thread.getTopicUUID()));
				

				operation.saveThread(thread);
				operation.saveFile(context);
				break;
				
			case PREV_READ:
				MainActivity.getUserController().getUser().addFavoriteComment(thread);
				
				operation.saveCollection(fetcher.fetchAllComments(thread.getTopicUUID()));
				operation.saveThread(thread);
				operation.saveFile(context);
				break;
		}

	}
	

	/**
	 * When called, PreferenceModel will return the UUIDs of favoriteComments.
	 * @return ArrayList of UUIDs marked as favorites
	 */
	public ArrayList<UUID> getFavorites()
	{		
		return MainActivity.getUserController().getUser().getFavoriteComments();
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
		//check if editor's uniqueName matches the one associated with the comment
		UserModel currentUser = MainActivity.getUserController().getUser().getUser();
		String myUnique = currentUser.getUniqueName();
		String commUnique = thread.getAuthorUnique();
		
		if( !(myUnique.equals(commUnique)))
		{
			MainActivity.userListener.invalidEditPermissions();
			return;
		}
		
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
	 * Responds to the user's desire to append or delete tags from a ThreadModel.
	 * <p>
	 * The thread to be edited passed to the TagAlertView and then returned to the controller
	 * after the modifications have been made.
	 * @param thread The thread for the tags the user wished to edit.
	 */
	public void editTags(ThreadModel thread)
	{
		setOpenThread(thread);

		TagInsertAlertView tagAlert = TagInsertAlertView.newInstance(thread);
		tagAlert.setCancelable(false);
		tagAlert.show(((ca.ualberta.team7project.MainActivity)MainActivity.getMainContext())
				.getFragmentManager(), "New Tag Alert");
	}
		
	/**
	 * Persist a new or updated thread into the cache and elastic search
	 * @param thread to be inserted
	 */
	public void InsertThread(ThreadModel thread)
	{
		ThreadUpdater updater = new ThreadUpdater(listView);
		updater.sendComment(thread);
		this.refreshThreads();
	}
	
	/**
	 * Creates a new thread and inserts it into the model in response to a click listener
	 * @param Thread to be uploaded to the cache and elastic search 
	 */
	//public void createThread(String title, String comment, LocationModel location, Bitmap cameraPhoto, String tags)
	public void createThread(ThreadModel thread)
	{	
		ConnectionDetector detector = new ConnectionDetector(MainActivity.getMainContext());
		
		if(detector.isConnectingToInternet())
		{			
			/* Determine if the user was editing, replying or creating a new thread */
			if(getEditingTopic() == true)
			{
				/* Insert newThread in place of the open thread in the models */						
				thread.setUniqueID(getOpenThread().getUniqueID());
				thread.setParentUUID(getOpenThread().getParentUUID());
				thread.setTopicUUID(getOpenThread().getTopicUUID());
			
				InsertThread(thread);
				MainActivity.userListener.editToast();
			}
			else
			{
				/* User was not editing thread. Could be replying or creating a topic */
				if(getInTopic() == true)
				{
					/* Append the reply to the openThread and then replace in the model */				
					thread.setParentUUID(getOpenThread().getUniqueID());
					thread.setTopicUUID(getOpenThread().getTopicUUID());
				
					InsertThread(thread);
					MainActivity.userListener.replyingToast();
				}
				/* User created new topic. Upload to Elastic Search */
				else
				{				
					InsertThread(thread);
					MainActivity.userListener.newTopicToast();
				}
			}
		
			setEditingTopic(false);
	
			this.refreshThreads();
		}
		else
		{
			MainActivity.userListener.postFailToast();
		}
	}
	
	public ThreadListView getListView()
	{
		return listView;
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

	public NavigationController getNavigation()
	{
		return navigation;
	}
}
