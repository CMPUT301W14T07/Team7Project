package ca.ualberta.team7project.controllers;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

public class ThreadAdapter extends ArrayAdapter<ThreadModel>
{

	private LinkedList<ThreadModel> threads;
	private Context context;
		
	public ThreadAdapter(Context context, int resource,
			LinkedList<ThreadModel> threads)
	{
		super(context, resource);
    	//threads = new LinkedList<ThreadModel>();
    	this.threads = threads;
	}

	/*
	 * http://www.vogella.com/tutorials/AndroidListView/article.html
	 */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	Log.e("debug", "hello");
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        View rowView = inflater.inflate(ca.ualberta.team7project.R.layout.thread, parent, false);
                
        TextView textView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadTitle);
        
        /* Ensure the title is not null before setting it in the layout */
        if((threads.get(position).getTitle()) != null)
        {
            textView.setText(threads.get(position).getTitle());
        }
        else
        {
        	textView.setText("");
        }
        
		return rowView;
    }

}
