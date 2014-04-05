/**
 * A custom Adapter to populate a list view with the contents of ThreadListModel
 * <p>
 * Referenced http://www.vogella.com/tutorials/AndroidListView/article.html
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.controllers;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.ThreadTagModel;
import ca.ualberta.team7project.views.ThreadListView;

/**
 * Custom adapter for ThreadModel
 */
public class ThreadAdapter extends ArrayAdapter<ThreadModel>
{

	private ArrayList<ThreadModel> threads = new ArrayList<ThreadModel>();
	private Context context;
	private ThreadListView view;
		
	public ThreadAdapter(Context context, int resource, ArrayList<ThreadModel> threads, ThreadListView view)
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
        final ThreadModel thread = getItem(position);
        View rowView;

        /* Use a different layout depending on whether the thread has an image */
        Bitmap bitmap = thread.getImage();

        ImageView image;

        if(bitmap != null)
        {
        	rowView = inflater.inflate(ca.ualberta.team7project.R.layout.thread, parent, false);
            image = (ImageView) rowView.findViewById(ca.ualberta.team7project.R.id.threadImage);
        	image.setImageBitmap(bitmap);
        }
        else
        {
        	rowView = inflater.inflate(ca.ualberta.team7project.R.layout.thread_no_images, parent, false);
        }
        
        /* Set the author name */
        TextView authorView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.authorName);
        authorView.setText(thread.getAuthorName());
        
        /* Ensure the title is not null before setting it in the layout */
        TextView titleView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadTitle);
        String title = thread.getTitle();
        
        if(title != null)
        	titleView.setText(title);
        else 
        	titleView.setText("");

        /* Set comment body. Body should never be null, so this is just a precaution */
        TextView bodyView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadBody);
        String body = thread.getComment();
        
        if(body != null)
        	bodyView.setText(body);
        else 
        	bodyView.setText("");        
        
        /* Set tags */
        TextView tagView = (TextView) rowView.findViewById(ca.ualberta.team7project.R.id.threadTags);
        tagView.setTextColor(Color.GRAY);

        ThreadTagModel tags = thread.getTags();        
        String tag = context.getResources().getString(ca.ualberta.team7project.R.string.tag);

        if(tags != null && tags.tagCount() >= 2)
            tagView.setText(tag + tags.customFormatTag(", "));
                
        /* Edit button on click listener */
        ImageButton editButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadEdit);
        
        editButton.setFocusable(false);
        editButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onEditClick(thread);
			}
        	
        });
        
        /* Favorite button on click listener */
        ImageButton favoriteButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadFavorite);
        
        favoriteButton.setFocusable(false);
        favoriteButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onFavoriteClick(thread);
			}
        	
        });
        
        /* Tag button on click listener */
        ImageButton tagButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadTagsEdit);
        
        tagButton.setFocusable(false);
        tagButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onTagClick(thread);
			}
        	
        });
        
        /* Reply button on click listener */
        ImageButton replyButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadReply);
        
        replyButton.setFocusable(false);
        replyButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onReplyClick(thread);
			}	
			
        });
        
        /* Cache button on click listener */
        ImageButton cacheButton = (ImageButton) rowView.findViewById(ca.ualberta.team7project.R.id.threadCache);
        
        cacheButton.setFocusable(false);
        cacheButton.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				view.onCacheClick(thread);
			}	
			
        });
        
		return rowView;
    }

    @Override
    public int getCount()
    {
        return threads.size();
    }
    
    /**
     * Retrieve a ThreadModel for a particular position in the listview.
     * @param The position within the listview to retrieve.
     * @return The thread at a particular position in the listview.
     */
    @Override
    public ThreadModel getItem(int position) {
        return threads.get(position);
    }
}
