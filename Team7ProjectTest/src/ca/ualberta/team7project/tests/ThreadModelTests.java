package ca.ualberta.team7project.tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		ThreadModel thread = new ThreadModel("With a Masterball!", user, "Caught Snorelax");
		
		assertEquals("Title should be set", "Caught Snorelax", thread.getTitle());
	}
	
	public void testDateSet()
	{
		/* The date must properly be set in the constructor */
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel thread = new ThreadModel("Caught Snorelax", user, null);
		
		thread.resetTimestamp();
				
		/* Now check that the date is updated when we update a thread */
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy|HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		Date date = calendar.getTime();
		String dateStr = df.format(date);
		
		try
		{
			date = df.parse(dateStr);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		
		thread.setComment("Check this out!");
		assertFalse("Date was updated on thread insertion", date.equals(thread.getTimestamp()));
	}
		
	/*
	 * Tests below test whether unique ID is working
	 */
	public void testUniqueID()
	{
		UserModel user = new UserModel("Ash Ketchum");
		ThreadModel threadOne = new ThreadModel("First post!", user, "Ash's Pokedex");
		ThreadModel threadTwo = new ThreadModel("First post!", user, "Ash's Pokedex");

		/* Even though threads have the same properties, the id should be unique */
		
		assertFalse("Thread ID is unique", threadOne.getUniqueID().equals(threadTwo.getUniqueID()));	
	}
	
}
