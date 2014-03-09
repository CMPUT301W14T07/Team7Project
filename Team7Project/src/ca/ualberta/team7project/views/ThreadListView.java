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
import android.widget.TextView;
import ca.ualberta.team7project.models.ThreadListModel;

public class ThreadListView
{
	private ThreadListModel listModel;
	private static Activity activity;
	
	public ThreadListView(ThreadListModel listModel, Activity activity)
	{
		super();
		this.listModel = listModel;
		ThreadListView.activity = activity;
		
		TextView textView = (TextView)activity.findViewById(ca.ualberta.team7project.R.id.textView1);
		textView.setText("hello");

		//View v = activity.findViewById (ca.ualberta.team7project.R.layout.activity_main);
		//v.invalidate();
	}

	
}
