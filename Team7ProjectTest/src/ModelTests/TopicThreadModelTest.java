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

package ModelTests;

import java.util.LinkedList;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.CommentModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.TopicModel;
import ca.ualberta.team7project.models.UserModel;

public class TopicThreadModelTest extends
	ActivityInstrumentationTestCase2<MainActivity> {

    public TopicThreadModelTest() {

	super(MainActivity.class);
    }

    /**
     * Test to ensure that basic properties of the topic are correct.
     */
    public void testTopicCreate() {

	UserModel user = new UserModel("Ash Ketchum");
	TopicModel topic = new TopicModel("A listing of all my Pokemon", null,
		user, null, "Ash's pokedex");

	assertEquals("Topic title is correct", "Ash's pokedex",
		topic.getTitle());
	assertEquals("Author name is correct", "Ash Ketchum",
		topic.getAuthorName());
	assertEquals("Unique author name is the same as the users",
		topic.getAuthorUnique(), user.getUniqueName());
    }

    /**
     * Test to determine that chronological insertion into Topics is correct
     */
    public void testThreadsInsertedProperly() {

	UserModel user = new UserModel("Ash Ketchum");
	TopicModel topic = new TopicModel("A listing of all my Pokemon", null,
		user, null, "Ash's pokedex");

	CommentModel threadOne = new CommentModel("Caught Snorelax", null,
		user, null);

	topic.setLastThreadUniqueID();
	threadOne.setUniqueID(topic.getLastThreadUniqueID());
	topic.addComment(threadOne);

	CommentModel threadTwo = new CommentModel("Caught Mew", null, user,
		null);

	topic.setLastThreadUniqueID();
	threadTwo.setUniqueID(topic.getLastThreadUniqueID());
	topic.addComment(threadTwo);

	CommentModel threadThree = new CommentModel("Caught Jigglypuff", null,
		user, null);

	topic.setLastThreadUniqueID();
	threadThree.setUniqueID(topic.getLastThreadUniqueID());
	topic.addComment(threadThree);

	LinkedList<ThreadModel> pokedexThreads = new LinkedList<ThreadModel>();
	pokedexThreads.add(threadOne);
	pokedexThreads.add(threadTwo);
	pokedexThreads.add(threadThree);

	assertEquals("Threads have been correctly entered", pokedexThreads,
		topic.getComments());
    }

    /**
     * Test to determine that Location is properly set for the topic
     */
    public void testTopicLocation() {

	// TODO
    }
}
