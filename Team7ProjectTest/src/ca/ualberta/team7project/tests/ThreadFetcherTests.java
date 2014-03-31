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
//parent and topic. From the ES server, the comments are "Moltres", "Flareon" and 
//Ponyta. By using known values that have been created previously, can eliminate 
//need to create new test cases for the test everytime

public class ThreadFetcherTests extends ActivityInstrumentationTestCase2<MainActivity> 
{


	public UUID topSelf = UUID.fromString("dc42282b-7667-4cb4-ac9e-6ebb5a159d84");
	public UUID topParent = UUID.fromString("db352350-aa82-11e3-a5e2-0800200c9a66"); //root
	public UUID topTopic = UUID.fromString("dc42282b-7667-4cb4-ac9e-6ebb5a159d84");
	
	public UUID midSelf = UUID.fromString("0730a8e9-9610-48db-855f-0e20948117b9");
	public UUID midParent = UUID.fromString("dc42282b-7667-4cb4-ac9e-6ebb5a159d84");
	public UUID midTopic = UUID.fromString("dc42282b-7667-4cb4-ac9e-6ebb5a159d84");
	
	public UUID lowSelf = UUID.fromString("e649ad98-c369-45c9-ad2b-9fa105ec3287");
	public UUID lowParent = UUID.fromString("0730a8e9-9610-48db-855f-0e20948117b9");
	public UUID lowTopic = UUID.fromString("dc42282b-7667-4cb4-ac9e-6ebb5a159d84");
	public ThreadFetcherTests()
	{
		super(MainActivity.class);
	}
	
	public void testFetchChildComments()
	{
		ThreadFetcher fetcher = new ThreadFetcher();
		
		//should have no children
		ArrayList<ThreadModel> threads = fetcher.fetchChildComments(lowSelf, SortMethod.DATE);				
		assertEquals("Should be empty", 0, threads.size());

		//should only have one child
		ArrayList<ThreadModel> threads2 = fetcher.fetchChildComments(topSelf, SortMethod.DATE);
		assertEquals("Should have only 1", 1, threads2.size());
	}
	
	public void testFetchComments()
	{
		ThreadFetcher fetcher = new ThreadFetcher();

		ArrayList<ThreadModel> search = fetcher.fetchComments(topSelf, SortMethod.DATE);
		assertEquals("Should have 1", 1, search.size());
		for (ThreadModel tm : search)
		{
			assertEquals("Should have the same UUID", topSelf, tm.getUniqueID());
		}

		
	}
}
