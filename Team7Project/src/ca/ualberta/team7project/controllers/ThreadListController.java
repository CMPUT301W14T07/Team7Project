package ca.ualberta.team7project.controllers;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
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
	private ArrayList<UUID> stack;
	
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
				PreferenceModel prefs = MainActivity.getUserController().getUser();
				if(prefs == null)
					return;
				UserModel currentUser = prefs.getUser();
				fetcher.SetLocation(currentUser.getLocation().getLatitude(), currentUser.getLocation().getLongitude());
				
				UUID parent = stack.get(stack.size()-1);
				
				ThreadListController controller = MainActivity.getListController();
				
				if(controller == null)
					return;
				
				ArrayList<ThreadModel> threads = fetcher.fetchChildComments(parent, controller.getSortMethod());
				
				listModel = new ThreadListModel();
				listModel.setTopics(threads);
					
				listView.notifyListChange(listModel);
		
			}
		});
	}
	
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
	public void createThread(String title, String comment, LocationModel location)
	{
		/* First we need to get the UserModel to associate with a ThreadModel */
		UserModel currentUser = MainActivity.getUserController().getUser().getUser();
		currentUser.setLocation(location);
		ThreadModel newThread = new ThreadModel(comment, currentUser, title);
		
		ThreadUpdater updater = new ThreadUpdater(listView);
		
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
}
