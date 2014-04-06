package ca.ualberta.team7project.controllers;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.SortPreferencesAlertView.SortPreference;
import ca.ualberta.team7project.alertviews.SortPreferencesAlertView.SortPreferencesAlertListener;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.PreferenceModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.ThreadTagModel;
import ca.ualberta.team7project.models.UserModel;
import ca.ualberta.team7project.network.ThreadFetcher;
import ca.ualberta.team7project.network.ThreadFetcher.SortMethod;

/**
 * Manages the navigation between different pages of comments
 * <p>
 * Handles navigating by parent, by favorites, by tags, and view all comments globally.
 * Also allows the user to filter by picture
 * <p>
 * Keeps a "stack" of Navigator objects to facilitate navigation between pages.
 * Uses ThreadFetcher to grab comments from the server or the local cache.
 * @author Logan
 */
public class NavigationController implements SortPreferencesAlertListener
{
	private ThreadListener listener;
	private Activity activity;
	
	protected enum NavigatorMode
	{
		//PARENT: view a page of child (or top-level comments)
		//GLOBAL: view a page of comments, not constrained by parent
		//FAVORITE: view a page of all favorited comments
		//TAG: view a page of comments tagged with some tag
		PARENT, GLOBAL, FAVORITE, TAG
	}
	
	private ArrayList<Navigator> stack;
	
	private static enum PictureFilterMode
	{
		NO_FILTER_PICTURE, FILTER_PICTURE
	}
	private PictureFilterMode picFilter = PictureFilterMode.NO_FILTER_PICTURE;
	
	//NO_SORT, DATE, LOCATION
	private SortMethod sortMethod = SortMethod.DATE;
	
	public NavigationController(ThreadListener listener, Activity activity)
	{
		this.listener = listener;
		this.activity = activity;
		stack = new ArrayList<Navigator>();
		stack.add(new Navigator(UUID.fromString(ThreadModel.ROOT)));
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
		listener.onRefresh();
		return true;
	}
	
	public void enterThread(ThreadModel thread)
	{
		UUID id = thread.getUniqueID();
		
		stack.add(new Navigator(id));
		
		listener.onRefresh();
	}
	
	public void enterFavorites()
	{
		stack.add(new Navigator(NavigatorMode.FAVORITE));
		
		listener.onRefresh();
	}
	
	public void enterTags(String tags)
	{
		String tempTags = tags.replace(" ", "");
		ThreadTagModel tagModel = new ThreadTagModel();
		tagModel.parseAndSet(tempTags, ",");
		
		stack.add(new Navigator(tagModel));
		
		listener.onRefresh();
	}
	
	public void topicsHome()
	{
		if(stack.size() > 1)
		{
			stack.subList(1, stack.size()).clear();
			listener.onRefresh();
		}
	}
	
	/**
	 * If a network connection exists, pull the latest and greatest from ElasticSearch
	 * <p>
	 * ThreadListModel is populated with the results from ElasticSearch
	 */
	public ArrayList<ThreadModel> getRefreshedThreads()
	{
		ThreadFetcher fetcher = new ThreadFetcher();
		
		//get current location and feed it to the ThreadFetcher
		PreferenceModel prefs = MainActivity.getUserController().getUser();
		if(prefs == null)
			return null;
		UserModel currentUser = prefs.getUser();
		fetcher.SetLocation(currentUser.getLocation().getLatitude(), currentUser.getLocation().getLongitude());
		
		//get current sorting method
		MainActivity mainActivity = (ca.ualberta.team7project.MainActivity)MainActivity.getMainContext();
		ThreadListController controller = mainActivity.getListController();
		if(controller == null)
			return null;
		SortMethod currSort = getSortMethod();
		
		Navigator currentPage = stack.get(stack.size()-1);
		
		ArrayList<ThreadModel> threads = null;
		
		//add picture filter in if enabled
		if(picFilter == PictureFilterMode.FILTER_PICTURE)
			fetcher.EnablePictureSort();
		
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
			ThreadTagModel tagModel = currentPage.getTags();
			
			ArrayList<String> tags = tagModel.getTags();
			
			threads = fetcher.fetchTaggedComments(tags);
		}
		else if(NavigatorMode.GLOBAL == currentPage.getMode())
		{				
			threads = fetcher.fetchComments(currSort);
		}
		else return null;
		
		return threads;
	}
	
	public void setSortPreferences(SortPreference newPreference)
	{
		switch(newPreference)
		{
			case BY_DATE:
				sortMethod = SortMethod.DATE;
				listener.onRefresh();
				break;
				
			case BY_LOCATION:
				sortMethod = SortMethod.LOCATION;
				listener.onRefresh();
				break;
				
			case FILTER_PICTURE:
				picFilter = PictureFilterMode.FILTER_PICTURE;
				listener.onRefresh();
				break;
				
			case UNFILTER_PICTURE:
				picFilter = PictureFilterMode.NO_FILTER_PICTURE;
				listener.onRefresh();
				break;
				
			case BY_PARENTS:
				//change navigation mode to PARENT
				//takes the user home
				stack = new ArrayList<Navigator>();
				stack.add(new Navigator(UUID.fromString(ThreadModel.ROOT)));
				listener.onRefresh();
				break;
				
			case GLOBALLY:
				//change the navigation mode to GLOBAL
				//takes the user home
				stack = new ArrayList<Navigator>();
				stack.add(new Navigator(NavigatorMode.GLOBAL));
				listener.onRefresh();
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
		private ThreadTagModel tags;
		
		public Navigator(NavigatorMode mode)
		{
			this.mode = mode;
		}
		
		public Navigator(UUID uuid)
		{
			this.mode = NavigatorMode.PARENT;
			this.uuid = uuid;
		}
		
		public Navigator(ThreadTagModel tags)
		{
			this.mode = NavigatorMode.TAG;
			this.tags = tags;
		}
		
		public NavigatorMode getMode()
		{
			return mode;
		}
		
		public UUID getUuid()
		{
			return uuid;
		}
		
		public ThreadTagModel getTags()
		{
			return tags;
		}
	}
}
