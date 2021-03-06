/**
 * Displays a list of threads to the screen.
 * <p>
 * Handles button clicks and calls the appropriate controllers.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.ImageAlertView;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;
import ca.ualberta.team7project.controllers.ThreadAdapter;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Prepares views and contains listeners for the ListView of comments
 */
public class ThreadListView extends Activity implements ThreadAlertListener, ThreadListener
{
	private ThreadListModel listModel;
	private Activity activity;
	private ListView list;
	private ThreadAdapter adapter;
	private ThreadListController controller;
	
	public ThreadListView(ThreadListModel listModel, Activity activity, ThreadListController controller)
	{
		super();
		this.listModel = listModel;
		this.activity = activity;
		this.controller = controller;
		
		MainActivity.threadListener = this;

		populateList();
		
	}
	
	public void onRefresh()
	{
		controller.refreshThreads();
	}

	/**
	 * Populate the listView with the contents of ThreadListModel
	 * <p>
	 * Called when we need to update a list with a new ThreadListModel or
	 * upon the initialization of the class.
	 * @see ThreadAdapter.java
	 */
	public void populateList()
	{
        activity.setContentView(ca.ualberta.team7project.R.layout.thread_list_view);
        list = (ListView) activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		
        adapter = new ThreadAdapter(activity, 
        		ca.ualberta.team7project.R.layout.thread, listModel.getTopics(), this);

		list = (ListView)activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		list.setAdapter(adapter);
		
		/* Individual threads are clickable within the list view without interfering with button onClick events */
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id)
			{
				onThreadClick(adapter.getItem(position));
			}
			
		});
	}

	
	public static enum FavoriteMode
	{
		FAVORITE, READ_LATER, PREV_READ
	}
	/**
	 * Add the ThreadModel to the user's favorites
	 *  
	 * Called when the Fav button for a ThreadModel in the ThreadListModel
	 * is clicked. See ThreadListController.java
	 */
	@Override
	public void onFavoriteClick(ThreadModel thread)
	{	
		controller.addFavorite(thread, FavoriteMode.FAVORITE);
	}

	@Override
	public void onReplyClick(ThreadModel thread)
	{
		controller.replyThread(thread);
	}

	@Override
	public void onCacheClick(ThreadModel thread)
	{
		controller.addFavorite(thread, FavoriteMode.READ_LATER);
	}

	@Override
	public void createThread(ThreadModel thread)
	{	
		controller.createThread(thread);
	}

	@Override
	public void onPhotoClick(ThreadModel thread)
	{
		ImageAlertView imageAlert = ImageAlertView.newInstance(thread);
		imageAlert.show(((ca.ualberta.team7project.MainActivity)MainActivity.getMainContext())
				.getFragmentManager(), "New Image Alert");

	}

	@Override
	public void onEditClick(ThreadModel thread)
	{
		controller.editThread(thread);
	}

	@Override
	public void onThreadClick(ThreadModel thread)
	{
		controller.addFavorite(thread, FavoriteMode.PREV_READ);
		controller.getNavigation().enterThread(thread);
	}

	@Override
	public void notifyListChange(ThreadListModel list)
	{
		this.listModel = list;
		adapter.notifyDataSetChanged();		
		this.populateList();
	}

	@Override
	public void notifyThreadInserted(ThreadModel thread)
	{
		this.listModel.addTopic(thread);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onTagSearch(String searchText)
	{
		controller.getNavigation().enterTags(searchText);
	}

	@Override
	public void onTagClick(ThreadModel thread)
	{
		controller.editTags(thread);
	}
}
