package ca.ualberta.team7project.controllers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.team7project.models.ThreadModel;

public class ListAdapterController extends ArrayAdapter<ThreadModel>
{
/**
 * This is a custom adapter that handles the conversion from ThreadListModel to the MainActivity. 
 * Much of this was taken from https://github.com/zjullion/PicPosterComplete/blob/master/src/ca/ualberta/cs/picposter/view/PicPostModelAdapter.java
 * @author emar
 * @param c
 * @param resource
 * @param resourceId
 * @param topics
 */
	
	public ListAdapterController(Context c, int resource, int resourceId, List<ThreadModel> topics)
	{
		/*Constructor*/
		super(c, resource, resourceId, topics);
	}

	@Override
	public int getCount()
	{
		return this.getCount();
	}

	@Override
	public ThreadModel getItem(int arg0)
	{
		return this.getItem(arg0);
	}


	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		

		if (view == null) 
		{
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			//view = inflater.inflate(R.layout.single_row, null);
		}
		
		ThreadModel tp = this.getItem(position);
		
		//if (tp != null) 
		//{
			//TextView tv = (TextView) view.findViewById(R.id.textView1);
		//}
		
		
		return view;
	}
	
	
	
}
