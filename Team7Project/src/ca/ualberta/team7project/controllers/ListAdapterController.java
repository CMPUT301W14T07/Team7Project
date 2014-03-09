package ca.ualberta.team7project.controllers;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ca.ualberta.team7project.R;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;

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
	
	public ListAdapterController(Context c, int resource, int resourceId, LinkedList<ThreadModel> topics)
	{
		/*Constructor*/
		super(c, resource, resourceId, topics);
	}


	@Override
	public View getView(int position, View view, ViewGroup parent)
	{
		
		if (view == null) 
		{
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			view = inflater.inflate(R.layout.single_row, null);
		}
		
		ThreadModel tp = this.getItem(position);
		

		if (tp != null) 
		{
			
			TextView tv = (TextView) view.findViewById(R.id.comment_string);
			tv.setText(tp.getComment());
			
			ImageView im = (ImageView) view.findViewById(R.id.picture);
			if (im != null)
			{
				im.setImageBitmap(tp.getImage());
			}
			
			TextView ts = (TextView) view.findViewById(R.id.timestamp);
			ts.setText(tp.getTimestamp().toString());
			
			
			TextView an = (TextView) view.findViewById(R.id.author);
			an.setText(tp.getAuthorName());
						
			
		}
		return view;
	}
	
public void addThread(ThreadListModel topics, String comment, UserModel user, Location location, String title, Bitmap image)
{
	if (title == null && image == null) //not a top level comment and no image
	{
		ThreadModel tm = new ThreadModel(comment, user, location);
		topics.UpdateTopic(tm);		
	}
	
	else if (title == null && image != null) //not a top level comment but image exists
	{
		ThreadModel tm = new ThreadModel(comment, image, user, location);
		topics.UpdateTopic(tm);
	}
	
	else if (title != null && image == null) //top level comment, no image
	{
		ThreadModel tm = new ThreadModel(comment, user, location, title);
		topics.UpdateTopic(tm);
	}
	
	else if (title != null && image != null) //top level comment, image attached
	{
		ThreadModel tm = new ThreadModel(comment, image, user, location, title);
		topics.UpdateTopic(tm);
	}
	
}

//public ThreadModel(String comment, UserModel user, Location location, String title)
//public ThreadModel(String comment, Bitmap image, UserModel user,
//		Location location, String title)
//public ThreadModel(String comment, UserModel user, Location location)
//public ThreadModel(String comment, Bitmap image, UserModel user,
//		Location location)

/**
 * Because of issues in the ThreadListModel, this method is likely not
 * working as intended either
 * @author emar
 * @param tml
 * @return
 */

public LinkedList<ThreadModel> expandThread(ThreadListModel tml)
{
	return (LinkedList) tml.getList();
}

}
