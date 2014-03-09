package ca.ualberta.team7project.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

public class ThreadListAdapter extends ArrayAdapter
{

	private ThreadListModel listModel;
	private Context context;
		
	public ThreadListAdapter(Context context, int resource,
			ThreadListModel listModel)
	{
		super(context, resource);
		this.listModel = listModel;
	}

	/*
	 * http://www.vogella.com/tutorials/AndroidListView/article.html
	 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View rowView = inflater.inflate(ca.ualberta.team7project.R.layout.thread, parent, false);
        
        /* Get the ThreadModel from the list */
        ThreadModel thread = listModel.getThread(position);
        
        TextView textView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadTitle);
        textView.setText("stuff");
        
		return rowView;
    }

}
