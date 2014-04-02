package ca.ualberta.team7project.location;

import java.util.ArrayList;
import java.util.Collections;

import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Handles the geolocation sorting of threads
 * 
 * @author michael
 *
 */
public class GeolocationSorting
{

	public ThreadListModel locationSort(ThreadListModel threadList)
	{		
		ArrayList<ThreadModel> threads = threadList.getTopics();
		
		Collections.sort(threads, new LocationComparator());
		
		threadList.setTopics(threads);
		
		return threadList;
	}

	
}
