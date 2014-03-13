/**
 * Displays a list of threads to the screen.
 * <p>
 * Handles button clicks and calls the appropriate controllers.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.ThreadAlertView.ThreadAlertListener;
import ca.ualberta.team7project.controllers.ThreadAdapter;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.network.ElasticSearchOperation;

public class ThreadListView extends Activity implements ThreadAlertListener, ThreadListener
{
	private ThreadListModel listModel;
	private static Activity activity;
	private ListView list;
	//for test
	private boolean flag = false;
	
	//Just to add adpater here.for test.
	//to be honest, I really don't understand this MVC!
	private ThreadAdapter adapter;
	
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
		
        adapter = new ThreadAdapter(activity, 
        		ca.ualberta.team7project.R.layout.thread, listModel.getTopics());

		list = (ListView)activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		list.setAdapter(adapter);
	}

	@Override
	public void onFavoriteClick(ThreadModel thread)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReplyClick(ThreadModel thread)
	{

		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCacheClick(ThreadModel thread)
	{
		Log.e("debug", "listeners worked!");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createThread(String title, String comment)
	{	//just test
		if (flag == false){
		try {
			ElasticSearchOperation.searchRecipes(null);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adapter.notifyDataSetChanged();
		Log.e("debug", "listeners worked!");
		flag= true;
		}
		else{
			listModel.addThreadCollection(ElasticSearchOperation.buffer);
			adapter.notifyDataSetChanged();
			Log.e("debug", "listeners worked!");
			flag =false;
		}
	}

	@Override
	public void insertImage()
	{
		
		// TODO Auto-generated method stub
		Log.e("debug", "listeners worked!");
		//ElasticSearchOperation.searchForThreadModels(null, listModel, activity);
		adapter.notifyDataSetChanged();
		
	}
}
