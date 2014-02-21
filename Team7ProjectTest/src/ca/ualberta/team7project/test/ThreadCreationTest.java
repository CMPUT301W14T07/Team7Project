/**
 * A Series of tests to verify that ThreadModel is working correctly:
 * <ul>
 * <li> Determine if basic thread attributes are properly set.
 * <li> Determine if unique ID's for topics and threads is assigning unique ID's
 * </ul>
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.test;

import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.ThreadModel;
import ca.ualberta.team7project.TopicModel;
import ca.ualberta.team7project.UserModel;
import android.test.ActivityInstrumentationTestCase2;

public class ThreadCreationTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	/**
	 * Default constructor.
	 */
	public ThreadCreationTest() {
		super(MainActivity.class);
	}


	/**
	 * Determines if the basic attributes of a thread are set
	 * <p>
	 * This includes authors name, unique ID, Bitmap image and comment.
	 */
	public void testThreadCreate() {
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", null, user.getName(), user.getUniqueID());

		assertEquals("Name of thread has been set properly", "Caught Snorelax", thread.getComment());
		assertNull("No Bitmap was inserted", thread.getImage());
		assertEquals("User name was set", "Ash Ketchum", thread.getAuthorName());
		assertNotNull("User's unique ID is attributed to the thread", thread.getAuthorUnique());
		
	}
	
	/**
	 * Test to determine if unique ID is correct when parent threads are inserted.
	 * <p>
	 * Three threads are inserted. Two parent threads and one child thread.
	 */
	public void testUniqeIDOnThreadInsert() {
		UserModel user = new UserModel("Ash Ketchum");
		TopicModel topic = new TopicModel("Pokedex", user.getName(), user.getUniqueID());
		
		// Create a new thread to add to the topic
		ThreadModel thread1 = new ThreadModel("Caught Snorelax", null, user.getName(), user.getUniqueID());
		
		// Before adding the thread to the topic we must set the unique ID
		topic.setLastThreadUniqueID();
		Integer uniqueID = topic.getLastThreadUniqueID();
		thread1.setUniqueID(uniqueID);
		
		topic.addThread(thread1);
		
		assertEquals("First thread should have unique ID of 1", thread1.getUniqueID(), (Integer)1);
		
		// Create a second thread to add to the topic (Repeat above steps)
		ThreadModel thread2 = new ThreadModel("Caught Mew", null, user.getName(), user.getUniqueID());
		
		topic.setLastThreadUniqueID();
		Integer uniqueID2 = topic.getLastThreadUniqueID();
		thread2.setUniqueID(uniqueID2);
		
		topic.addThread(thread2);
		
		assertEquals("Second thread should have a unique ID of 2", thread2.getUniqueID(), (Integer)2);
				
		// Add a child thread to thread 1
		ThreadModel thread3 = new ThreadModel("Caught Jigglypuff", null, user.getName(), user.getUniqueID());
		
		topic.setLastThreadUniqueID();
		Integer uniqueID3 = topic.getLastThreadUniqueID();
		thread3.setUniqueID(uniqueID3);
		
		thread1.addThread(thread3);
		
		assertEquals("Third thread should have a unique ID of 3", thread3.getUniqueID(), (Integer)3);
	}

}
