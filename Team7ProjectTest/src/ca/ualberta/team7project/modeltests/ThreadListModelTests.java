package ca.ualberta.team7project.modeltests;

import java.util.ArrayList;

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
	
	public void testInsertThread()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadListModel threadsList = new ThreadListModel();
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		threadsList.addTopic(thread);
		
		ArrayList<ThreadModel> threads = threadsList.getTopics();
		
		assertTrue("LinkedList of thread should contain a thread", threads.contains(thread));
	}
	
	//after changing LinkedList to ArrayList, insertion order no longer exists
	/*
	public void testInsertionOrder()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadListModel threadsList = new ThreadListModel();
		
		ThreadModel threadFirst = new ThreadModel("Obtained bicycle!", user, null);
		ThreadModel threadLast = new ThreadModel("Obtained Helix Fossil!", user, null);
		
		threadsList.addTopic(threadFirst);
		threadsList.addTopic(threadLast);

		ArrayList<ThreadModel> threads = threadsList.getTopics();
		
		assertEquals("Last inserted topic in place", threads.getLast(), threadLast);
	}
	*/
	
	public void testSetTopic()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		ThreadModel threadOne = new ThreadModel("Caught Charmander", user, null);
		
		ArrayList<ThreadModel> threads = new ArrayList<ThreadModel>();
		threads.add(thread);
		threads.add(threadOne);

		ThreadListModel threadList = new ThreadListModel(threads);
		threadList.setTopics(threads);
		
		assertEquals("Topics has been properly set", threadList.getTopics(), threads);
		
		/* Now lets test setTopic for the alternate constructor */
		ThreadListModel threadListTwo = new ThreadListModel();
		threadListTwo.setTopics(threads);
		
		assertEquals("Topics for aleternate constructor properly set", threadListTwo.getTopics(),
				threads);
	}
	
	/* The model should be able to return an thread based off index position */
	public void testIndexRetrieve()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadListModel threadList = new ThreadListModel();
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		threadList.addTopic(thread);
		
		assertEquals("Thread retrieved by index correctly", thread, threadList.getThread(0));
	}
	
	public void testListSize()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadListModel threadList = new ThreadListModel();
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		threadList.addTopic(thread);
		
		assertEquals("Thread retrieved by index correctly", 1, threadList.getSize());
	}

}
