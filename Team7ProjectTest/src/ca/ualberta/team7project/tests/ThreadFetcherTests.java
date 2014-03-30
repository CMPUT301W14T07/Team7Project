package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.network.ThreadFetcher;
import ca.ualberta.team7project.network.ThreadFetcher.SortMethod;

//There exists a top level comment, it's reply, and a reply to that reply. They
//will be denoted as top, mid and low respectively, and their variables are self
//parent and topic

public class ThreadFetcherTests extends ActivityInstrumentationTestCase2<MainActivity> 
{

	public UUID topSelf = UUID.fromString("62a0e338-4743-4b2e-83b6-570d097cccb8");
	public UUID topParent =UUID.fromString("db352350-aa82-11e3-a5e2-0800200c9a66"); //root
	public UUID topTopic = UUID.fromString("62a0e338-4743-4b2e-83b6-570d097cccb8");
	
	public UUID midSelf = UUID.fromString("56c6ee32-ce1e-4fb3-8b2c-532864b35e03");
	public UUID midParent = UUID.fromString("62a0e338-4743-4b2e-83b6-570d097cccb8");
	public UUID midTopic = UUID.fromString("56c6ee32-ce1e-4fb3-8b2c-532864b35e03");
	
	public UUID lowSelf = UUID.fromString("02f219bc-c6c8-45b6-86cf-8e59cae76ed3");
	public UUID lowParent = UUID.fromString("56c6ee32-ce1e-4fb3-8b2c-532864b35e03");
	public UUID lowTopic = UUID.fromString("02f219bc-c6c8-45b6-86cf-8e59cae76ed3");
	public ThreadFetcherTests()
	{
		super(MainActivity.class);
	}
	
	public void testFetchChildComments()
	{
		ThreadFetcher fetcher = new ThreadFetcher();
		
		ArrayList<ThreadModel> threads = fetcher.fetchChildComments(lowTopic, SortMethod.DATE);
		
		
		assertNotNull(threads);
		
	}
}
