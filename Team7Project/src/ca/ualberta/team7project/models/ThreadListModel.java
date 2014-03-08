package ca.ualberta.team7project.models;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import android.graphics.Bitmap;
import android.location.Location;
import android.widget.ArrayAdapter;

/**
 * This class holds the list of topics
 * 
 */
public class ThreadListModel
{

	private LinkedList<ThreadModel> topics;
	private ArrayAdapter<ThreadModel> adapter;

	/**
	 * Construct new, empty list
	 */
	public ThreadListModel()
	{

		super();
		topics = new LinkedList<ThreadModel>();
	}

	/**
	 * Construct from existing list of topics
	 * 
	 * @param topics
	 *            list of existing topics
	 */
	public ThreadListModel(LinkedList<ThreadModel> topics)
	{

		super();
		this.topics = topics;
	}
	
	/**
	 * Sets the adapter for the list
	 * @author emar
	 * @param adapter
	 */
	public void setAdapter(ArrayAdapter<ThreadModel> adapter)
	{
		this.adapter = adapter;
	}
	
	/**
	 * Adds to or updates a topic in the list
	 * <p>
	 * Replaces the old topic object if it exists Otherwise simply adds the new
	 * topic to the list
	 * <p>
	 * This method should only be called to sync from disk or the server. This
	 * method should not be called <i>directly</i> to update the topic after the
	 * user writes a new comment
	 * 
	 * @param topic
	 *            the topic to add or update from
	 */
	public void UpdateTopic(ThreadModel topic)
	{

		UUID id = topic.getUniqueID();

		boolean exists = false;

		for (ThreadModel t : topics)
		{
			if (t.getUniqueID() == topic.getUniqueID())
			{
				exists = true; // update the existing topic
				topics.remove(t);
				topics.add(topic);
				break;
			}
		}

		if (exists == false)
		{
			topics.add(topic); // add a new topic
		}
	}
	
	/**
	 * Returns the list of ThreadModels in the ThreadListModel; because of the 
	 * method .unmodifiableList, have to return as a list, so will need to cast 
	 * to a linked list down the road
	 * @author emar
	 * @return
	 */	
	public List<ThreadModel> getList() 
	{
		return Collections.unmodifiableList(this.topics);
	}
	/**
	 * Adds a collection of ThreadModels into the list wholesale; this might come
	 * in handy for wholesale addition of ThreadModels
	 * @author emar
	 * @param topics
	 */
	public void addThreadCollection(Collection<ThreadModel> collection)
	{
		this.topics.addAll(collection);
		this.adapter.notifyDataSetChanged();
		
		//oh god am I doing this right?
		for (ThreadModel t : collection)
		{
			this.UpdateTopic(t);
		}
		
	}
	/**
	 * Adds a brand new topic to the ThreadListModel. For now, all of these
	 * must have a title and a picture.
	 * @author emar
	 * @param comment
	 * @param image
	 * @param user
	 * @param location
	 * @param title
	 */
	public void addOriginalPost(String comment, Bitmap image, UserModel user,
			Location location, String title)
	{
		ThreadModel tm = new ThreadModel(comment, image, user, location, title);
		this.topics.add(tm);
		this.adapter.notifyDataSetChanged();
		
		//is this being used right?
		this.UpdateTopic(tm);
	}
	/**
	 * Adds a reply to any ThreadModel. Must have a picture for now.
	 * @author emar
	 * @param comment
	 * @param image
	 * @param user
	 * @param location
	 */
	public void addReply(String comment, Bitmap image, UserModel user,
			Location location)
	{
		ThreadModel tm = new ThreadModel(comment, image, user, location);
		this.topics.add(tm);
		this.adapter.notifyDataSetChanged();
		
		//hopefully I'm using this right
		this.UpdateTopic(tm);
	}
}
