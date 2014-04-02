package ca.ualberta.team7project.locationtesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.location.GeolocationSorting;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.ThreadModel;


public class LocationSortingTest extends ActivityInstrumentationTestCase2<MainActivity>
{

	public LocationSortingTest()
	{
		super(MainActivity.class);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
	}


	/**
	 * Compare two threads to find the 
	 */
	
	
	/**
	 * Tests the ability to sort an ArrayList of threads by location to a set LocationModel
	 */
	public void testFindClosestThreads()
	{
		ArrayList<LocationModel> locationModels = new ArrayList<LocationModel>();
		ArrayList<ThreadModel> threadModels = new ArrayList<ThreadModel>();
		
		int longitude = 50;
		int latitude = 50;
		
		LocationModel setLocation = new LocationModel(longitude, latitude);
		
		for(int i = 0; i < 10; i++)
		{
			LocationModel model = new LocationModel(longitude++, latitude++);
			locationModels.add(model);
			// add to threadModels...Should actually be ThreadListModel. Will fix tomorrow morning. Too late tonight
		}
		
		ArrayList<LocationModel> oldSorted = locationModels;
		
		/* Shuffle the list of locations  so it is no longer in order*/
		Collections.shuffle(locationModels, new Random());
			
		GeolocationSorting sorting = new GeolocationSorting();
		
		ArrayList<LocationModel> sortedLocations = sorting.locationSort(setLocation, locationModels);
		
		/* Locations should now be in order */
		assertEquals("sortArray returned a properly sorted array by location", sortedLocations, oldSorted);
		
		/* Further confirmation */
		assertEquals("First location is correct", sortedLocations.get(0), setLocation);
		
		setLocation = new LocationModel(longitude, latitude);
		
		assertEquals("Last location is correct", sortedLocations.get(sortedLocations.size()), setLocation);
	}
	
	/**
	 * Tests the ability to take an unsorted list of threads and sort with the closest thread to current location first.
	 */
	public void testThreadSort()
	{
		
	}
	
}
