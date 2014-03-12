/**
 * The single class for which all children and and parent threads are derived
 * <P>
 * There contains four constructors for the users needs.
 * Every constructor requires a comment, user and location. However, the user 
 * has the option of entering an image or a title.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.models;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;

public class ThreadModel
{

	private String title = null;
	private String comment = null;
	private BitmapData bitmapData = null; // bitmap stored as base64-encoded
											// byte array
	private Date timestamp = null; // time of last change of any kind
	private UUID uniqueID = null; // this is also the Elastic Search index
	
	private UUID parentUUID = null;
	private UUID topicUUID = null;
	private UserModel user;

	/**
	 * Constructs the ThreadModel with appropriate parameters. Generic reply
	 * with a picture
	 * 
	 * @param comment
	 *            associated with the thread
	 * @param image
	 *            associated with the thread (may be null)
	 * @param user
	 *            associated with the thread
	 * @param location
	 *            the thread was written at
	 */
	public ThreadModel(String comment, Bitmap image, UserModel user, UUID parentUUID, UUID topicUUID)
	{
		super();
		this.comment = comment;
		this.bitmapData.encode(image);
		this.user = user;
		this.title = null;
		
		this.timestamp = new Date();
		
		// every thread has a uniqueID, either topic or comment
		this.generateUniqueID();
		
		this.parentUUID = parentUUID;
		this.topicUUID = parentUUID;
	}

	public UUID getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(UUID parentUUID) {
		this.parentUUID = parentUUID;
	}

	public UUID getTopicUUID() {
		return topicUUID;
	}

	public void setTopicUUID(UUID topicUUID) {
		this.topicUUID = topicUUID;
	}

	/**
	 * Constructs the ThreadModel with appropriate parameters. Generic reply
	 * without a picture
	 * 
	 * @param comment
	 *            associated with the thread
	 * @param user
	 *            associated with the thread
	 * @param location
	 *            the thread was written at
	 */
	public ThreadModel(String comment, UserModel user)
	{
		super();
		this.comment = comment;
		this.user = user;
		this.title = null;
		this.bitmapData = new BitmapData(); //empty bitmap will be treated as no bitmap
		
		this.timestamp = new Date();
		
		// every thread has a uniqueID, either topic or comment
		this.generateUniqueID();
		this.parentUUID = parentUUID;
		this.topicUUID = parentUUID;
	}

	/**
	 * Constructs the ThreadModel with appropriate parameters. Top Level post
	 * with title and picture
	 * 
	 * @param comment
	 *            associated with the thread
	 * @param image
	 *            associated with the thread (may be null)
	 * @param user
	 *            associated with the thread
	 * @param location
	 *            the thread was written at
	 * @param title
	 *            of the thread
	 */
	public ThreadModel(String comment, Bitmap image, UserModel user,
			String title)
	{
		super();
		this.comment = comment;
		this.bitmapData.encode(image);
		this.user = user;
		this.title = title;
		
		this.timestamp = new Date();
		
		// every thread has a uniqueID, either topic or comment
		this.generateUniqueID();
		
	}

	/**
	 * Constructs the ThreadModel with appropriate parameters. Top Level post
	 * without picture
	 * 
	 * @param comment
	 *            associated with the thread
	 * @param user
	 *            associated with the thread
	 * @param location
	 *            the comment was written at
	 * @param title
	 *            of the thread
	 */
	public ThreadModel(String comment, UserModel user, String title)
	{
		super();
		this.comment = comment;
		this.user = user;
		this.title = title;
		this.bitmapData = new BitmapData(); //empty bitmap will be treated as no bitmap
		
		this.timestamp = new Date();
		
		// every thread has a uniqueID, either topic or comment
		this.generateUniqueID();
		
	}

	public boolean isTopic()
	{

		return (this.title != null);
	}

	public String getComment()
	{

		return comment;
	}

	public void setComment(String comment)
	{

		this.comment = comment;
		this.timestamp = new Date();

	}

	public Bitmap getImage()
	{

		return this.bitmapData.decode();
	}

	public void setImage(Bitmap image)
	{

		this.bitmapData.encode(image);
		this.timestamp = new Date();
	}

	public String getAuthorName()
	{

		return user.getName();

	}

	public void setAuthorName(String authorName)
	{

		this.user.setName(authorName);
		this.timestamp = new Date();

	}

	public String getAuthorUnique()
	{

		return user.getUniqueName();
	}

	public Date getTimestamp()
	{

		return timestamp;
	}

	public void resetTimestamp()
	{

		this.timestamp = new Date();
	}

	public Location getLocation()
	{

		return user.getLocation();
	}

	public void setLocation(Location location)
	{

		this.user.setLocation(location);
		this.timestamp = new Date();

	}

	public UUID getUniqueID()
	{

		return this.uniqueID;
	}

	public void setUniqueID(UUID uniqueID)
	{

		this.uniqueID = uniqueID;

		// not updating timestamp on purpose
	}

	public void generateUniqueID()
	{

		this.uniqueID = UUID.randomUUID();

		// not updating timestamp on purpose
	}

	public String getTitle()
	{

		return title;
	}

	public void setTitle(String title)
	{

		this.title = title;
		this.timestamp = new Date();
	}

	
	/**
	 * Class that stores a bitmap in json serializable form (a base64 encoded
	 * byte array)
	 * <p>
	 * See the following:
	 * <ul>
	 * <li>http://stackoverflow.com/questions/5871482</li>
	 * <li>http://mobile.cs.fsu.edu/converting-images-to-json-objects/</li>
	 * </ul>
	 */
	protected class BitmapData
	{

		private String data = null;

		/**
		 * Converts a bitmap to an array of bytes, then encodes the bytes as a
		 * base64 string
		 * <p>
		 * Accepts null as the parameter, in which case the stored string is
		 * cleared
		 * 
		 * @param image
		 *            a bitmap to be encoded
		 */
		public void encode(Bitmap image)
		{

			if (image == null)
			{
				data = null;
				return;
			}

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
			byte[] bytes = byteStream.toByteArray();
			data = Base64.encodeToString(bytes, Base64.DEFAULT);
		}

		/**
		 * Decodes the stored base64 string, then converts the bytes to a bitmap
		 * 
		 * @return a Java Bitmap corresponding to the base64 string stored , or
		 *         null if no base64 string is stored
		 */
		public Bitmap decode()
		{

			if (data == null)
				return null;

			byte[] bytes = Base64.decode(data, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
					bytes.length);
			return bitmap;
		}

	}
}
