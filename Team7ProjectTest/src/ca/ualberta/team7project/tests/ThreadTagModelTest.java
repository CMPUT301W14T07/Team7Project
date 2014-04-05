package ca.ualberta.team7project.tests;

import java.util.ArrayList;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.ThreadTagModel;


public class ThreadTagModelTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	public ThreadTagModelTest()
	{

		super(MainActivity.class);
	}

	protected void setUp() throws Exception
	{

		super.setUp();
	}
	
	public void testParseAndAppend()
	{
		ThreadTagModel model = new ThreadTagModel();
		model.parseAndAppend("These are,working hooray");
		
		assertTrue("Model contains", model.contains("These"));
		assertTrue("Model contains", model.contains("are"));
		assertTrue("Model contains", model.contains("working"));
		assertTrue("Model contains", model.contains("hooray"));
		
		/* No whitespace or commas were added. Should only be 4 entries */
		assertEquals("Size is correct", model.tagCount(), 4);
	}
	
	/**
	 * ThreadTagModel needs to be able to add a string of tags, parse it and add it to the arraylist
	 */
	public void testParseAndSet()
	{
		ThreadTagModel model = new ThreadTagModel();
		
		/* Test a csv method */
		String tagsToSet = "tagOne,tagTwo,tagThree";
				
		model.parseAndSet(tagsToSet, ",");
		
		assertTrue("ArrayList was set properly", model.contains("tagOne"));
		assertTrue("ArrayList was set properly", model.contains("tagTwo"));
		assertTrue("ArrayList was set properly", model.contains("tagThree"));
		
		/* Test a space seperated method */
		tagsToSet = "tagOne tagTwo tagThree";
		
		model.parseAndSet(tagsToSet, " ");
		assertTrue("ArrayList was set properly", model.contains("tagOne"));
		assertTrue("ArrayList was set properly", model.contains("tagTwo"));
		assertTrue("ArrayList was set properly", model.contains("tagThree"));

	}
	
	/**
	 * Test alphabetical sort
	 */
	public void testAlphabet()
	{
		ThreadTagModel model = new ThreadTagModel();
		String tagsToSet = "AAA BBB CCC";

		model.parseAndSet(tagsToSet, " ");
		
		ArrayList<String> alpha = new ArrayList<String>();
		alpha.add("AAA");
		alpha.add("BBB");
		alpha.add("CCC");
		
		assertEquals("Sorted correctly", alpha, model.getTags());
	}
	
	/**
	 * ThreadTagModel should return a deliminated string of tags
	 */
	public void testStringDeliminate()
	{
		ThreadTagModel model = new ThreadTagModel();
		String tagsToSet = "AAA BBB CCC";
		
		model.parseAndSet(tagsToSet, " ");

		String returnedString = model.commaFormatTag();
		assertTrue("ArrayList was set properly", model.contains("AAA"));
		Log.e("debug", returnedString);
		
		assertEquals("Strings returned with space deliminate", returnedString, "AAA,BBB,CCC");
	}

}
