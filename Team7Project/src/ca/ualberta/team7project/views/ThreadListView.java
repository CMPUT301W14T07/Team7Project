/**
 * Displays a list of threads to the screen.
 * <p>
 * Handles button clicks and calls the appropriate controllers.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import android.app.ListActivity;
import android.os.Bundle;
import ca.ualberta.team7project.models.ThreadListModel;

public class ThreadListView extends ListActivity
{

	private ThreadListModel listModel;

	public ThreadListView(ThreadListModel listModel)
	{
		this.listModel = listModel;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*
		 * String[] values = new String[] { "a", "b", "c", "d", "e", "f", "g",
		 * "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
		 * "w", "x", "y", "z" };
		 * 
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_single_choice, values);
		 * setListAdapter(adapter);
		 * getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 */
		
	}
	
	
	
}
