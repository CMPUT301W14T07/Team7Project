package ca.ualberta.team7project.locationtesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.location.GeolocationSorting;
import ca.ualberta.team7project.location.LocationComparator;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;


public class LocationSortingTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	private UserModel user;
	private int longitude = 50;
	private int latitude = 50;

	public LocationSortingTest()
	{
		super(MainActivity.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		this.user = new UserModel("Ash");
		this.user.setLocation(new LocationModel(longitude, latitude));
	}

	/**
	 * Compare distance between two threads.
	 */
	public void testDistanceDelta()
	{
		int longitude = 50;
		int latitude = 50;
		
		UserModel userModel = this.user;
		userModel.setLocation(new LocationModel(longitude, latitude));
		
		ThreadModel modelClose = new ThreadModel("Hello", userModel, null, null);
		
		userModel.setLocation(new LocationModel(++longitude, ++latitude));
		ThreadModel modelFar = new ThreadModel("Hello", userModel, null, null);
		
		LocationComparator comparator = new LocationComparator();
		double distance = comparator.distanceDelta(modelClose, modelFar);
		
		/* The distance between [50, 50] and [51, 51] is 131800 meters 
		 * For the test, this may not be accurate, so an approximate is used
		 */
		int intDistance = (int) Math.round(distance);
		
		assertEquals("Distance between two threads is correct", intDistance, 131800);
	}

	/**
	 * Tests the ability to sort an ThreadListModle by geolocation
	 */
	public void testFindClosestThreads()
	{
		ThreadListModel listModel = new ThreadListModel();

		/*
		 * Add a thread to threadlistmodel with a new and different location model.
		 */
		for(int i = 0; i < 10; i++)
		{
			user.setLocation(new LocationModel(longitude++, latitude++));
			listModel.addTopic(new ThreadModel("hello", user, null, null));
		}
		
		ArrayList<ThreadModel> oldSorted = listModel.getTopics();
		ArrayList<ThreadModel> shuffled = oldSorted;
		
		/* Shuffle the list of locations  so it is no longer in order*/
		Collections.shuffle(shuffled, new Random());
			
		GeolocationSorting sorting = new GeolocationSorting();
		
		ThreadListModel shuffledModel = new ThreadListModel(shuffled);
		ThreadListModel sortedLocations = sorting.locationSort(shuffledModel);

		/* The returned sortedLocations should be the same as oldSorted */
		assertEquals("ThreadListModel sorted by location", sortedLocations.getTopics(), oldSorted);
		
	}
	
	/**
	 * Tests the ability to take an unsorted list of threads and sort with the closest thread to current location first.
	 */
	public void testThreadSort()
	{
		
	}
	
}
