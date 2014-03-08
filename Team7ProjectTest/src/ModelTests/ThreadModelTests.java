package ModelTests;

import java.util.Date;
import java.util.LinkedList;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;

/**
 * Basic tests to determine whether ThreadModel is functioning
 * 
 * @author Michael Raypold
 *
 */
public class ThreadModelTests extends
	ActivityInstrumentationTestCase2<MainActivity>
{

	public ThreadModelTests()
	{
		super(MainActivity.class);
	}

	/*
	 * Note: Location values are null for now until functionality is added in milestone 3
	 */
	
	/*
	 * The below test will test the constructors, since there exists overloading in the model class
	 */
	

	public void testNoImageNoTitle()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		
		assertEquals("Title should be null", null, thread.getTitle());
		assertEquals("Image should be null", null, thread.getImage());
	}
	
	public void testWithTitle()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("With a Masterball!", user, null, "Caught Snorelax");
		
		assertEquals("Title should be set", "Caught Snorelax", thread.getTitle());
	}
	
	public void testDateSet()
	{
		/* The date must properly be set in the constructor */
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		
		Date date = new Date();
		
		assertEquals("Date is set", thread.getTimestamp(), date);
		
		/* Now check that the date is updated when we update a thread */
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		thread.setTitle("Check this out!");
		assertFalse("Date was updated", date.equals(thread.getTimestamp()));
	}
	
	/*
	 * The below tests will test whether insertion of child comments is working
	 */
	
	public void testChildThread()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel parent = new ThreadModel("First post!", user, null, "Ash's Pokedex");
		ThreadModel child = new ThreadModel("Obtained Charmander", user, null);
		
		LinkedList<ThreadModel> children = new LinkedList<ThreadModel>();
		children.add(child);
		
		parent.addComment(child);
		
		assertEquals("Child comments are inserted", parent.getComments(), children);		
	}
	
	/*
	 * Tests below test whether unique ID is working
	 */

	// FAILS RIGHT NOW!
	public void testUniqueID()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel threadOne = new ThreadModel("First post!", user, null, "Ash's Pokedex");
		ThreadModel threadTwo = new ThreadModel("First post!", user, null, "Ash's Pokedex");

		/* Even though threads have the same properties, the id should be unique */
		
		assertFalse("Thread ID is unique", threadOne.getUniqueID().equals(threadTwo.getUniqueID()));	
	}
	
	/*
	 * Tests the ability to store a thread as a JSON object
	 */
	public void testThreadJSON() 
	{
		// TODO
	}
	
}
