/**
 * Displays a list of threads to the screen.
 * <p>
 * Handles button clicks and calls the appropriate controllers.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import java.util.ArrayList;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ca.ualberta.team7project.models.ThreadListModel;

public class ThreadListView extends Activity
{
	private ThreadListModel listModel;
	private static Activity activity;
	
	public ThreadListView(ThreadListModel listModel, Activity activity)
	{
		super();
		this.listModel = listModel;
		ThreadListView.activity = activity;
		
		/* 
		 * Just testing basic stuff right now for application crashes.
		 * 
		 * However, instead of this block of code, we would call a method that populates the 
		 * list based off the model data.
		 * 
		 * So...Need to write use the custom adapter that has been written.
		 */
        activity.setContentView(ca.ualberta.team7project.R.layout.thread_list_view);
        
        ListView list = (ListView) activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		        
        ArrayList<String> threads = new ArrayList<String>();

		String[] items = new String[] {"Hello", "World"};
		for(String s: items){
			threads.add(s);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, 
				ca.ualberta.team7project.R.layout.test_layout, items);
		
		list = (ListView)activity.findViewById(ca.ualberta.team7project.R.id.threads_list);
		list.setAdapter(adapter);
		/* End test */
	}

}
