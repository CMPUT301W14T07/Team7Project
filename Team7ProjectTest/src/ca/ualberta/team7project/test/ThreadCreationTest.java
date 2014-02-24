/**
 * A Series of tests to verify that ThreadModel is working correctly:
 * <ul>
 * <li> Determine if basic thread attributes are properly set.
 * <li> Determine if unique ID's for topics and threads is assigning unique ID's
 * <li>Location (longitude and latitude) is correct
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
        ActivityInstrumentationTestCase2<MainActivity>
{

    /**
     * Default constructor.
     */
    public ThreadCreationTest()
    {

        super(MainActivity.class);
    }

    /**
     * Determines if the basic attributes of a thread are set
     * <p>
     * This includes authors name, unique ID, Bitmap image and comment.
     */
    public void testThreadCreate()
    {

        UserModel user = new UserModel("Ash Ketchum");
        ThreadModel thread = new ThreadModel("Caught Snorelax", null,
                user.getName(), user.getUniqueID());

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
        TopicModel topic = new TopicModel("Pokedex", user.getName(),
                user.getUniqueID());

        ThreadModel threadOne = new ThreadModel("Caught Snorelax", null,
                user.getName(), user.getUniqueID());
        topic.setLastThreadUniqueID();
        threadOne.setUniqueID(topic.getLastThreadUniqueID());
        topic.addThread(threadOne);

        assertEquals("First thread should have unique ID of 1",
                threadOne.getUniqueID(), (Integer) 1);

        ThreadModel threadTwo = new ThreadModel("Caught Mew", null,
                user.getName(), user.getUniqueID());
        topic.setLastThreadUniqueID();
        threadTwo.setUniqueID(topic.getLastThreadUniqueID());
        topic.addThread(threadTwo);

        assertEquals("Second thread should have a unique ID of 2",
                threadTwo.getUniqueID(), (Integer) 2);

        ThreadModel threadThree = new ThreadModel("Caught Jigglypuff", null,
                user.getName(), user.getUniqueID());
        topic.setLastThreadUniqueID();
        threadThree.setUniqueID(topic.getLastThreadUniqueID());
        threadOne.addThread(threadThree);

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
