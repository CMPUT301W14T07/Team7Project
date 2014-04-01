package ca.ualberta.team7project.controllers;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.SortPreferencesAlertView.SortPreference;
import ca.ualberta.team7project.alertviews.SortPreferencesAlertView.SortPreferencesAlertListener;
import ca.ualberta.team7project.alertviews.ThreadAlertView;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.network.ThreadFetcher;
import ca.ualberta.team7project.network.ThreadFetcher.SortMethod;
import ca.ualberta.team7project.network.ThreadUpdater;
import ca.ualberta.team7project.views.ThreadListView;

/**
 * Manages the active list of comments
 * <p>
 * Contains methods for refreshing, adding, and editing comments
 * <p>
 * Manages the internal comment-nesting stack and handles navigation between different nesting levels
 */
public class ThreadListController extends Activity implements SortPreferencesAlertListener
{
	protected enum NavigatorMode
	{
		//PARENT: view a page of child (or top-level comments)
		//GLOBAL: view a page of comments, not constrained by parent
		//FAVORITE: view a page of all favorited comments
		//TAG: view a page of comments tagged with some tag
		PARENT, GLOBAL, FAVORITE, TAG
	}
	
	private ArrayList<Navigator> stack;
	
	private ThreadListModel listModel;
	private static ThreadListView listView;
	private static Activity activity;
	
	/* Attributes necessary for passing between ThreadAlertView and ThreadListView */
	private Boolean inTopic;
	private Boolean editingThread;
	private ThreadModel openThread;
	
	private static enum PictureFilterMode
	{
		NO_FILTER, FILTER_PICTURE, FILTER_NO_PICTURE
	}
	private PictureFilterMode picFilter = PictureFilterMode.NO_FILTER;
	
	private SortMethod sortMethod = SortMethod.DATE;
	
	public ThreadListController(Activity activity)
	{
		stack = new ArrayList<Navigator>();
		stack.add(new Navigator(UUID.fromString(ThreadModel.ROOT)));
		
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
		
		stack.add(new Navigator(id));
		
		refreshThreads();
	}
	
	public void enterFavorites()
	{
		stack.add(new Navigator(NavigatorMode.FAVORITE));
		
		refreshThreads();
	}
	
	public void topicsHome()
	{
		if(stack.size() > 1)
		{
			stack.subList(1, stack.size()).clear();
			refreshThreads();
		}
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
		
				ThreadFetcher fetcher = new ThreadFetcher();
				
				//get current location and feed it to the ThreadFetcher
				PreferenceModel prefs = MainActivity.getUserController().getUser();
				if(prefs == null)
					return;
				UserModel currentUser = prefs.getUser();
				fetcher.SetLocation(currentUser.getLocation().getLatitude(), currentUser.getLocation().getLongitude());
				
				//get current sorting method
				MainActivity mainActivity = (ca.ualberta.team7project.MainActivity)MainActivity.getMainContext();
				ThreadListController controller = mainActivity.getListController();
				if(controller == null)
					return;
				SortMethod currSort = controller.getSortMethod();
				
				Navigator currentPage = stack.get(stack.size()-1);
				
				ArrayList<ThreadModel> threads = null;
				
				if(NavigatorMode.PARENT == currentPage.getMode())
				{
					UUID parent = (stack.get(stack.size()-1)).getUuid();
					
					threads = fetcher.fetchChildComments(parent, currSort);
				}
				else if(NavigatorMode.FAVORITE == currentPage.getMode())
				{
					ArrayList<UUID> favs = prefs.getFavoriteComments();
					
					threads = fetcher.fetchFavorites(favs, currSort);
				}
				else if(NavigatorMode.TAG == currentPage.getMode())
				{
					String tag = currentPage.getTag();
					
					//TODO: fetch by tag
				}
				else if(NavigatorMode.GLOBAL == currentPage.getMode()) //this works, just need the GUI for it
				{
					threads = fetcher.fetchComments(currSort);
				}
				else return;
				
				listModel = new ThreadListModel();
				listModel.setTopics(threads);
					
				listView.notifyListChange(listModel);
			}
		});
	}
	
	/**
	 * This code is identical as above, but co-opted for now to run a separate listView with
	 * our favorite threads
	 * @author Eden
	 */
	/*
	public void viewFavorites()
	{
		activity.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
		
				ThreadFetcher fetcher = new ThreadFetcher();
				PreferenceModel prefs = MainActivity.getUserController().getUser();
				ArrayList<UUID> favs =prefs.getFavoriteComments();
				ArrayList<ThreadModel> results = new ArrayList<ThreadModel>();
				
				if(prefs == null)
					return;
				UserModel currentUser = prefs.getUser();
				fetcher.SetLocation(currentUser.getLocation().getLatitude(), currentUser.getLocation().getLongitude());
				
				//unsure how to return to a previous view using the stack
				UUID parent = (stack.get(stack.size()-1)).getUuid();
				
				ThreadListController controller = MainActivity.getListController();
				
				if(controller == null)
					return;
				
				//ThreadFetcher returns an array of ThreadModels, but to remain consistent, 
				//the view the user sees should be similar to start up, that is a list of top
				//level comments. Because of this, can only feed ThreadFetcher the uniqueID of
				//a favorited thread and it will only return one item in the arraylist. As such,
				//have to add each result to a general results variable and then pass it to the 
				//ThreadListModel
				for (UUID id : favs)
				{
					ArrayList<ThreadModel> threads = fetcher.fetchChildComments(id, controller.getSortMethod());
					results.add(threads.get(0));					
				}
				
				
				
				listModel = new ThreadListModel();
				listModel.setTopics(results);
					
				listView.notifyListChange(listModel);
		
			}
		});
	}
	*/
	
	/**
	 * When a user clicks on the Favorite button, the ThreadModel UUID is added
	 * @param thread which the user clicked on
	 */
	public void addFavorite(ThreadModel thread)
	{
		ThreadListController.listView.favoriteToast();
		MainActivity.getUserController().getUser().addFavoriteComment(thread);
	}
	

	/**
	 * When called, PreferenceModel will return the UUIDs of favoriteComments.
	 * @return
	 */
	public ArrayList<UUID> getFavorites()
	{		
		return MainActivity.getUserController().getUser().getFavoriteComments();
	}
	
	/**
	 * When user clicks on the Cache button, UUID is added
	 * A toast is also displayed. This may be redundant, especially
	 * since functionally speaking Caching and Favoriting are 
	 * identical actions, but how we handle the categories may be
	 * different
	 * @return
	 */
	public void addCache(ThreadModel thread)
	{
		ThreadListController.listView.cacheToast();
		MainActivity.getUserController().getUser().addCache(thread);
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
	 * Creates a new thread and inserts it into the model in response to a click listener.
	 * 
	 * @param title of the thread
	 * @param body of the thread
	 */
	public void createThread(String title, String comment, LocationModel location, Bitmap cameraPhoto)
	{
		/* First we need to get the UserModel to associate with a ThreadModel */
		UserModel currentUser = MainActivity.getUserController().getUser().getUser();
		currentUser.setLocation(location);
		ThreadModel newThread = new ThreadModel(comment, currentUser, title);
		
		ThreadUpdater updater = new ThreadUpdater(listView);
		
		if(cameraPhoto != null)
			newThread.setImage(cameraPhoto);
		
		/* Determine if the user was editing, replying or creating a new thread */
		if(getEditingTopic() == true)
		{
			/* User edited a thread. Update models appropriately */
			/* Insert newThread in place of the open thread in the models */						
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
				UUID parent = this.getOpenThread().getUniqueID();
				UUID topic = this.getOpenThread().getTopicUUID();
				
				newThread.setParentUUID(parent);
				newThread.setTopicUUID(topic);
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
	
	public void setSortPreferences(SortPreference newPreference)
	{
		switch(newPreference)
		{
			case BY_DATE:
				sortMethod = SortMethod.DATE;
				Toast.makeText(activity, "Set sort by date", Toast.LENGTH_SHORT).show();
				this.refreshThreads();
				break;
				
			case BY_LOCATION:
				sortMethod = SortMethod.LOCATION;
				Toast.makeText(activity, "Set sort by location", Toast.LENGTH_SHORT).show();
				this.refreshThreads();
				break;
				
			case FILTER_PICTURE:
				if(picFilter != PictureFilterMode.FILTER_PICTURE)
					picFilter = PictureFilterMode.FILTER_PICTURE;
				else
					picFilter = PictureFilterMode.NO_FILTER;
				
				this.refreshThreads();
				break;
		}
	}

	public PictureFilterMode getPicFilter()
	{
		
		return picFilter;
	}

	public SortMethod getSortMethod()
	{
		
		return sortMethod;
	}
	
	/**
	 * Used to represent a single page in our navigation,
	 * such as a page of child comments or a page of favorites
	 */
	private class Navigator
	{
		private NavigatorMode mode;
		private UUID uuid;
		private String tag;
		
		public Navigator(NavigatorMode mode)
		{
			this.mode = mode;
		}
		
		public Navigator(UUID uuid)
		{
			this.mode = NavigatorMode.PARENT;
			this.uuid = uuid;
		}
		
		public Navigator(String tag)
		{
			this.mode = NavigatorMode.TAG;
			this.tag = tag;
		}
		
		public NavigatorMode getMode()
		{
			return mode;
		}
		
		public UUID getUuid()
		{
			return uuid;
		}
		
		public String getTag()
		{
			return tag;
		}
	}
}
