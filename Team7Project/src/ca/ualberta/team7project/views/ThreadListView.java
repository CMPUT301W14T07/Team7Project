/**
 * Displays a list of threads to the screen.
 * <p>
 * Handles button clicks and calls the appropriate controllers.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import java.util.LinkedList;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;
import ca.ualberta.team7project.controllers.ThreadAdapter;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

public class ThreadListView extends Activity
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
		
		/* 
		 * Just testing basic stuff right now for application crashes.
		 * 
		 * However, instead of this block of code, we would call a method that populates the 
		 * list based off the model data.
		 * 
		 * So...Need to write use the custom adapter that has been written.
		 */
		
/*        activity.setContentView(ca.ualberta.team7project.R.layout.thread_list_view);
        
        list = (ListView) activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		        
        ArrayList<String> threads = new ArrayList<String>();

		String[] items = new String[] {"Hello", "World"};
		for(String s: items){
			threads.add(s);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, 
				ca.ualberta.team7project.R.layout.test_layout, items);
		
		list = (ListView)activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		list.setAdapter(adapter);*/
		
		/* End test */
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
        
    	LinkedList<ThreadModel> threads = new LinkedList<ThreadModel>();
		for(ThreadModel thread : listModel.getTopics()){
			threads.add(thread);
		}
		
        ThreadAdapter adapter = new ThreadAdapter(activity, 
        		ca.ualberta.team7project.R.layout.thread, threads);

		list = (ListView)activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		list.setAdapter(adapter);

	}
}
