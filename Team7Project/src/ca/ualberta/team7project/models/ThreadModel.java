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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Represents a single comment
 * <p>
 * Aggregates everything neccasary to completely identify the comment
 */
public class ThreadModel
{
	/* Reuse statements https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#threadmodel */
	
	//no spaces in the format, or Elastic Search will complain
	private String internalDateFormat = "MM/dd/yyyy|HH:mm:ss";
	
	private String title = null;
	private String comment = null;
	private BitmapData bitmapData = null; // bitmap stored as base64-encoded
											// byte array

	private ThreadTagModel tags;
	
	//do not change name to timestamp, Elastic Search will complain
	private String threadTimestamp = null; // time of last change of any kind
	private UUID uniqueID = null; // this is also the Elastic Search index

	private UUID parentUUID = null;
	private UUID topicUUID = null;
	private UserModel user;
	public static final String ROOT = "db352350-aa82-11e3-a5e2-0800200c9a66";

	/**
	 * Constructs the ThreadModel with appropriate parameters. Generic reply
	 * with a picture
	 * 
	 * @param comment associated with the thread
	 * @param image associated with the thread (may be null)
	 * @param user associated with the thread
	 * @param location the thread was written at
	 */
	public ThreadModel(String comment, Bitmap image, UserModel user,
			UUID parentUUID, UUID topicUUID)
	{

		super();
		this.comment = comment;
		this.bitmapData.encode(image);
		this.user = user;
		this.title = null;
		this.setTags(new ThreadTagModel());
		
		this.resetTimestamp();
		this.generateUniqueID();

		this.parentUUID = parentUUID;
		this.topicUUID = parentUUID;
	}

	/**
	 * Constructs the ThreadModel with appropriate parameters. Generic reply
	 * without a picture
	 * 
	 * @param comment associated with the thread
	 * @param user associated with the thread
	 * @param location the thread was written at
	 */
	public ThreadModel(String comment, UserModel user, UUID parentUUID,
			UUID topicUUID)
	{

		super();
		this.comment = comment;
		this.user = user;
		this.title = null;
		this.bitmapData = new BitmapData(); // empty bitmap will be treated as
											// no bitmap
		this.setTags(new ThreadTagModel());

		this.resetTimestamp();
		this.generateUniqueID();

		this.parentUUID = parentUUID;
		this.topicUUID = topicUUID;

	}

	/**
	 * Constructs the ThreadModel with appropriate parameters. Top Level post
	 * with title and picture
	 * 
	 * @param comment associated with the thread
	 * @param image associated with the thread (may be null)
	 * @param user associated with the thread
	 * @param location the thread was written at
	 * @param title of the thread
	 */
	public ThreadModel(String comment, Bitmap image, UserModel user,
			String title)
	{

		super();
		this.comment = comment;
		this.bitmapData.encode(image);
		this.user = user;
		this.title = title;
		this.setTags(new ThreadTagModel());
		
		this.resetTimestamp();
		this.generateUniqueID();
		
		this.topicUUID = this.uniqueID;

	}

	/**
	 * Constructs the ThreadModel with appropriate parameters. Top Level post
	 * without picture
	 * 
	 * @param comment associated with the thread
	 * @param user associated with the thread
	 * @param location the comment was written at
	 * @param title of the thread
	 */
	public ThreadModel(String comment, UserModel user, String title)
	{

		super();
		this.comment = comment;
		this.user = user;
		this.title = title;
		this.bitmapData = new BitmapData(); // empty bitmap will be treated as
											// no bitmap
		this.setTags(new ThreadTagModel());

		this.resetTimestamp();

		this.generateUniqueID();
		this.topicUUID = this.uniqueID;
		this.parentUUID = UUID.fromString(ROOT);

	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
		this.resetTimestamp();
	}

	public Bitmap getImage()
	{
		return this.bitmapData.decode();
	}

	public void setImage(Bitmap image)
	{
		this.bitmapData.encode(image);
		this.resetTimestamp();
	}

	public String getAuthorName()
	{
		return user.getName();
	}

	public String getAuthorUnique()
	{
		return user.getUniqueName();
	}

	@SuppressLint("SimpleDateFormat")
	public Date getTimestamp()
	{
		DateFormat df = new SimpleDateFormat(internalDateFormat);
		Date date = null;
		
		try
		{
			date = df.parse(this.threadTimestamp);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return date;
	}

	@SuppressLint("SimpleDateFormat")
	public void resetTimestamp()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(internalDateFormat);
		String dateStr = df.format(date);
				
		this.threadTimestamp = dateStr;
	}

	public LocationModel getLocation()
	{
		return user.getLocation();
	}
	
	public void setLocation(LocationModel location)
	{
		this.user.setLocation(location);
		this.resetTimestamp();
	}

	public UUID getUniqueID()
	{
		return this.uniqueID;
	}

	public void setUniqueID(UUID uniqueID)
	{
		this.uniqueID = uniqueID;
	}

	public void generateUniqueID()
	{
		this.uniqueID = UUID.randomUUID();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
		this.resetTimestamp();
	}

	public UUID getParentUUID()
	{
		return parentUUID;
	}

	public void setParentUUID(UUID parentUUID)
	{
		this.parentUUID = parentUUID;
	}

	public UUID getTopicUUID()
	{
		return topicUUID;
	}

	public void setTopicUUID(UUID topicUUID)
	{
		this.topicUUID = topicUUID;
	}

	
	public ThreadTagModel getTags()
	{

		return tags;
	}

	public void setTags(ThreadTagModel tags)
	{

		this.tags = tags;
	}

	/**
	 * Class that stores a bitmap in json serializable form (a base64 encoded
	 * byte array)
	 */
	protected class BitmapData
	{
		private String innerBitmapData = null; //verbose naming to make ES happy

		/**
		 * Converts a bitmap to an array of bytes, then encodes the bytes as a
		 * base64 string
		 * <p>
		 * Accepts null as the parameter, in which case the stored string is
		 * cleared
		 * 
		 * @param image a bitmap to be encoded
		 */
		public void encode(Bitmap image)
		{

			if (image == null)
			{
				innerBitmapData = null;
				return;
			}

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.PNG, 50, byteStream);
			byte[] bytes = byteStream.toByteArray();
			innerBitmapData = Base64.encodeToString(bytes, Base64.DEFAULT);
		}

		/**
		 * Decodes the stored base64 string, then converts the bytes to a bitmap
		 * 
		 * @return a Java Bitmap corresponding to the base64 string stored , or
		 *         null if no base64 string is stored
		 */
		public Bitmap decode()
		{

			if (innerBitmapData == null)
				return null;

			byte[] bytes = Base64.decode(innerBitmapData, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
					bytes.length);
			return bitmap;
		}

	}
	
}
