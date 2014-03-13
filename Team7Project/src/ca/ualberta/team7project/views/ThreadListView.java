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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;
import ca.ualberta.team7project.controllers.ThreadAdapter;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

public class ThreadListView extends Activity implements ThreadAlertListener, ThreadListener
{
	private ThreadListModel listModel;
	private static Activity activity;
	private ListView list;
	
	public ThreadListView(ThreadListModel listModel, Activity activity)
	{
		super();
		this.listModel = listModel;
		ThreadListView.activity = activity;
		
		populateList();
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
		
        final ThreadAdapter adapter = new ThreadAdapter(activity, 
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

	@Override
	public void onFavoriteClick(ThreadModel thread)
	{
		Log.e("debug", "Favorite pressed");
		Log.e("debug", "Thread title:" + thread.getTitle());
	}

	@Override
	public void onReplyClick(ThreadModel thread)
	{
		Log.e("debug", "Reply pressed");
		Log.e("debug", "Thread title:" + thread.getTitle());		
	}

	@Override
	public void onCacheClick(ThreadModel thread)
	{
		Log.e("debug", "Cache pressed");
		Log.e("debug", "Thread title:" + thread.getTitle());
	}

	@Override
	public void createThread(String title, String comment)
	{
		Log.e("debug", "Create thread pressed");
		Log.e("debug", "Thread title:" + title + " comment: " + comment);		
	}

	@Override
	public void insertImage()
	{
		Log.e("debug", "Insert image pressed");
	}

	@Override
	public void onPhotoClick(ThreadModel thread)
	{
		// Haven't implemented the button for this yet.
	}

	@Override
	public void onEditClick(ThreadModel thread)
	{
		Log.e("debug", "Edit pressed");
		Log.e("debug", "Thread title:" + thread.getTitle());
	}

	@Override
	public void onThreadClick(ThreadModel thread)
	{
		// This method should allow the user to move forward through the replies
		Log.e("debug", "Thread pressed");
		Log.e("debug", "Thread title: " + thread.getTitle());
		
	}

}
