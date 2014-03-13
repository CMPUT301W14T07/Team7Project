/**
 * A custom Adapter to populate a list view with the contents of ThreadListModel
 * <p>
 * Referenced http://www.vogella.com/tutorials/AndroidListView/article.html
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import ca.ualberta.team7project.interfaces.ThreadListener;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.views.ThreadListView;

public class ThreadAdapter extends ArrayAdapter<ThreadModel>
{

	private LinkedList<ThreadModel> threads = new LinkedList<ThreadModel>();
	private Context context;
	private ThreadListView view;
		
	public ThreadAdapter(Context context, int resource,
			LinkedList<ThreadModel> threads, ThreadListView view)
	{
		super(context, resource);
    	this.threads = threads;
    	this.context = context;
    	this.view = view;
	}
	
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(ca.ualberta.team7project.R.layout.thread, parent, false);

        /* Ensure the title is not null before setting it in the layout */
        TextView titleView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadTitle);
        String title = getItem(position).getTitle();
        
        if(title != null)
        	titleView.setText(title);
        else 
        	titleView.setText("");

        /* Set comment body. Body should never be null, so this is just a precaution */
        TextView bodyView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadBody);
        String body = getItem(position).getComment();
        
        if(body != null)
        	bodyView.setText(body);
        else 
        	bodyView.setText("");        
        
        /* Set picture */
        // TODO
        
        /* Favorite button on click listener */
        ImageButton editButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadEdit);
        
        editButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onEditClick(getItem(position));
			}
        	
        });
        
        /* Favorite button on click listener */
        ImageButton favoriteButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadFavorite);
        
        favoriteButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onFavoriteClick(getItem(position));
			}
        	
        });
        
        /* Reply button on click listener */
        ImageButton replyButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadReply);
        
        replyButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onReplyClick(getItem(position));
			}	
			
        });
        
        
        /* Cache button on click listener */
        ImageButton cacheButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadCache);
        
        cacheButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onCacheClick(getItem(position));
			}	
			
        });
        
        
		return rowView;
    }

    @Override
    public int getCount()
    {
        return threads.size();
    }
    
    @Override
    public ThreadModel getItem(int position) {
        return threads.get(position);
    }
}
