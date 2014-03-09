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
import android.widget.ListView;
import ca.ualberta.team7project.controllers.ThreadListAdapter;
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
	 * @see ThreadListAdapter.java
	 */
	public void populateList()
	{
        activity.setContentView(ca.ualberta.team7project.R.layout.thread_list_view);
        list = (ListView) activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
       
        /* Debug */
        Log.e("debug", "printing");
        if(listModel.getSize() < 1)
        {
        	Log.e("debug", "something is wrong, who knows what?");
        }
        ThreadModel newThread = this.listModel.getThread(0);
        if(newThread != null)
        {
        	Log.e("debug", "this shouldn't be null ffs");
        }
        if(newThread.getTitle() == null)
        {
        	Log.e("debug", "wtf is going on.....");
        }        
        /* Debug */
        
        ThreadListAdapter adapter = new ThreadListAdapter(activity, 
        		ca.ualberta.team7project.R.layout.thread, listModel);

		list = (ListView)activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		list.setAdapter(adapter);

	}
}
