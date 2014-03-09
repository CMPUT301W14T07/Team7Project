package ca.ualberta.team7project.modeltests;

import java.util.LinkedList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;


public class ThreadListModelTests extends ActivityInstrumentationTestCase2<MainActivity>
{

	public ThreadListModelTests()
	{
		super(MainActivity.class);
	}

	// Why is this failing?
	public void testAlternateConstructor()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		ThreadModel threadOne = new ThreadModel("Caught Charmander", user, null);
		ThreadModel threadTwo = new ThreadModel("Caught Pidgeo", user, null);
		
		LinkedList<ThreadModel> threads = new LinkedList<ThreadModel>();
		threads.add(thread);
		threads.add(threadOne);
		threads.add(threadTwo);

		ThreadListModel threadsList = new ThreadListModel(threads);
		ThreadListModel threadsListTwo = new ThreadListModel();
		
		threadsListTwo.setTopics(threads);

		assertEquals("The models should be the same", threadsList, threadsListTwo);
	}
}
