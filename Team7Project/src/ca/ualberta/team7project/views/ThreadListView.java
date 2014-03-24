/**
 * Displays a list of threads to the screen.
 * <p>
 * Handles button clicks and calls the appropriate controllers.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.ualberta.team7project.MainActivity;
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
	private static Activity activity;
	private ListView list;
	private ThreadAdapter adapter;
	private ThreadListController controller;
	
	public ThreadListView(ThreadListModel listModel, Activity activity, ThreadListController controller)
	{
		super();
		this.listModel = listModel;
		ThreadListView.activity = activity;
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

	/**
	 * Add the ThreadModel to the user's favorites
	 *  
	 * Called when the Fav button for a ThreadModel in the ThreadListModel
	 * is clicked. See ThreadListController.java
	 */
	@Override
	public void onFavoriteClick(ThreadModel thread)
	{
		Log.e(MainActivity.DEBUG, "Favorite pressed");
		Log.e(MainActivity.DEBUG, "Thread title:" + thread.getTitle());
		
		controller.addFavorite(thread);
	
	}

	@Override
	public void onReplyClick(ThreadModel thread)
	{
					
		Log.e(MainActivity.DEBUG, "Reply pressed");
		Log.e(MainActivity.DEBUG, "Thread title:" + thread.getTitle());			

		controller.replyThread(thread);
	}

	@Override
	public void onCacheClick(ThreadModel thread)
	{
		Log.e(MainActivity.DEBUG, "Cache pressed");
		Log.e(MainActivity.DEBUG, "Thread title:" + thread.getTitle());
	}

	@Override
	public void createThread(String title, String comment, long spinnerId)
	{	
		Log.e(MainActivity.DEBUG, "Create thread pressed");
		Log.e(MainActivity.DEBUG, "Thread title:" + title);
		
		controller.createThread(title, comment, spinnerId);
	}

	@Override
	public void insertImage()
	{
		// Next milestone
		Log.e(MainActivity.DEBUG, "Insert image pressed");
	}

	@Override
	public void onPhotoClick(ThreadModel thread)
	{
		// Next milestone
		Log.e(MainActivity.DEBUG, "Image pressed");

	}

	@Override
	public void onEditClick(ThreadModel thread)
	{
		Log.e(MainActivity.DEBUG, "Edit pressed");
		Log.e(MainActivity.DEBUG, "Thread title:" + thread.getTitle());
		
		controller.editThread(thread);
	}

	@Override
	public void onThreadClick(ThreadModel thread)
	{
		// This method should allow the user to move forward through the replies
		Log.e(MainActivity.DEBUG, "Thread pressed");
		Log.e(MainActivity.DEBUG, "Thread title: " + thread.getTitle());
		
		controller.enterThread(thread);
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
	public void editToast()
	{
		Toast.makeText(activity, ca.ualberta.team7project.R.string.toast_edit, 
				Toast.LENGTH_SHORT).show();

	}

	@Override
	public void replyingToast()
	{
		Toast.makeText(activity, ca.ualberta.team7project.R.string.toast_reply, 
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void newTopicToast()
	{
		Toast.makeText(activity, ca.ualberta.team7project.R.string.toast_create, 
				Toast.LENGTH_SHORT).show();
	}

}
