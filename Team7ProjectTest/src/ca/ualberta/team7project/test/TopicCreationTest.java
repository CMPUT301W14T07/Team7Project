/**
 * A series of tests to determine that TopicModel is working correctly:
 * <ul>
 * <li>Determines that the constructor is working properly
 * <li>Chronological insert into the threads linked list is working correctly
 * <li>Location (longitude and latitude) is correct
 * </ul>
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project.test;

import java.util.Date;
import java.util.LinkedList;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.ThreadModel;
import ca.ualberta.team7project.TopicModel;
import ca.ualberta.team7project.UserModel;

public class TopicCreationTest extends
        ActivityInstrumentationTestCase2<MainActivity>
{

    /**
     * Default constructor
     */
    public TopicCreationTest()
    {

        super(MainActivity.class);
    }

    /**
     * Test to ensure that basic properties of the topic are correct.
     */
    public void testTopicCreate()
    {

        UserModel user = new UserModel("Ash Ketchum");
        TopicModel topic = new TopicModel("Pokedex", user.getName(),
                user.getUniqueID());

        assertEquals("Topic name is correct", "Pokedex", topic.getTopicName());
        assertEquals("Author name isc correct", "Ash Ketchum",
                topic.getAuthorName());
        assertEquals("Unique author name is the same as the users",
                topic.getAuthorUnique(), user.getUniqueID());
    }

    /**
     * Test to determine that chronological insertion into Topics is correct
     */
    public void testThreadsInsertedProperly()
    {

        UserModel user = new UserModel("Ash Ketchum");
        TopicModel topic = new TopicModel("Pokedex", user.getName(),
                user.getUniqueID());

        ThreadModel threadOne = new ThreadModel("Caught Snorelax", null,
                user.getName(), user.getUniqueID());
        topic.setLastThreadUniqueID();
        threadOne.setUniqueID(topic.getLastThreadUniqueID());
        topic.addThread(threadOne);

        ThreadModel threadTwo = new ThreadModel("Caught Charmander", null,
                user.getName(), user.getUniqueID());
        topic.setLastThreadUniqueID();
        threadTwo.setUniqueID(topic.getLastThreadUniqueID());
        topic.addThread(threadTwo);

        ThreadModel threadThree = new ThreadModel("Caught Bulbasaur", null,
                user.getName(), user.getUniqueID());
        topic.setLastThreadUniqueID();
        threadThree.setUniqueID(topic.getLastThreadUniqueID());
        topic.addThread(threadThree);

        LinkedList<ThreadModel> pokedexThreads = new LinkedList<ThreadModel>();
        pokedexThreads.add(threadOne);
        pokedexThreads.add(threadTwo);
        pokedexThreads.add(threadThree);

        assertEquals("Threads have been correctly entered", pokedexThreads,
                topic.getThreads());
    }

    /**
     * Test to determine that Location is properly set for the topic
     */
    public void testTopicLocation()
    {

        // TODO
    }
}
