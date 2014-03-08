/**
 * A Series of tests to verify that CommentThreadModel is working correctly:
 * <ul>
 * <li> Determine if basic thread attributes are properly set.
 * <li> Determine if unique ID's for topics and threads is assigning unique ID's
 * <li>Location (longitude and latitude) is correct
 * </ul>
 * 
 * @author Michael Raypold
 */
package ModelTests;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.CommentModel;
import ca.ualberta.team7project.models.TopicModel;
import ca.ualberta.team7project.models.UserModel;

public class CommentModelTest extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	public CommentModelTest()
	{
		super(MainActivity.class);
	}

	/**
	 * Tests to determine if constructor overloading is working
	 */
	public void testOverload()
	{
		UserModel user = new UserModel("Ash Ketchum");
		CommentModel thread = new CommentModel("Caught Snorelax", user, null);
		
		assertEquals("Bitmap should be NULL", thread.getImage(), null);
	}
	
	/**
	 * Determines if the basic attributes of a thread are set
	 * <p>
	 * This includes authors name, unique names, Bitmap image and comment.
	 */
	public void testThreadCreate()
	{

		UserModel user = new UserModel("Ash Ketchum");
		CommentModel thread = new CommentModel("Caught Snorelax", null, user,
				null);

		assertEquals("Name of thread has been set properly", "Caught Snorelax",
				thread.getComment());
		assertNull("No Bitmap was inserted", thread.getImage());
		assertEquals("User name was set", "Ash Ketchum", thread.getAuthorName());
		assertNotNull("User's unique ID is attributed to the thread",
				thread.getAuthorUnique());

	}

	/**
	 * Test to determine if unique ID is correct when parent threads are
	 * inserted.
	 * <p>
	 * Three threads are inserted. Two parent threads and one child thread.
	 */
	public void testUniqeIDOnThreadInsert()
	{

		UserModel user = new UserModel("Ash Ketchum");

		TopicModel topic = new TopicModel("A listing of all my Pokemon", null,
				user, null, "Ash's pokedex");

		CommentModel threadOne = new CommentModel("Caught Snorelax", null,
				user, null);

		topic.setLastThreadUniqueID();
		threadOne.setUniqueID(topic.getLastThreadUniqueID());
		topic.addComment(threadOne);

		assertEquals("First thread should have unique ID of 1",
				threadOne.getUniqueID(), (Integer) 1);

		CommentModel threadTwo = new CommentModel("Caught Mew", null, user,
				null);

		topic.setLastThreadUniqueID();
		threadTwo.setUniqueID(topic.getLastThreadUniqueID());
		topic.addComment(threadTwo);

		assertEquals("Second thread should have a unique ID of 2",
				threadTwo.getUniqueID(), (Integer) 2);

		CommentModel threadThree = new CommentModel("Caught Jigglypuff", null,
				user, null);

		topic.setLastThreadUniqueID();
		threadThree.setUniqueID(topic.getLastThreadUniqueID());
		threadOne.addComment(threadThree);

		assertEquals("Third thread should have a unique ID of 3",
				threadThree.getUniqueID(), (Integer) 3);
	}

	/**
	 * Test that location has been properly set for the thread
	 */
	public void testThreadLocation()
	{

	}
}
