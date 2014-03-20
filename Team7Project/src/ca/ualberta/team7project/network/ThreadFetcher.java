package ca.ualberta.team7project.network;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.team7project.models.ThreadModel;

/**
 * Fetches comments from the server
 * <p>
 * Prepares search strings for ElasticSearchOperation
 *
 */
public class ThreadFetcher
{
	private ElasticSearchOperation search;
	private String listSize;
	
	/**
	 * Construct and set max size to the default (15)
	 */
	public ThreadFetcher()
	{
		super();
		listSize = "size=15";
		search = new ElasticSearchOperation();
	}
	
	/**
	 * Construct with a custom max size
	 * @param maxItems the max number of comments to pull from the server
	 */
	public ThreadFetcher(int maxItems)
	{
		super();
		listSize = "size=" + Integer.toString(maxItems);
		search = new ElasticSearchOperation();
	}
	
	/**
<<<<<<< HEAD
	 * Enumeration of methods used to get the "best/most relevant" thread
=======
	 * Enumeration of methods used to get the "best/most relevant" topics
>>>>>>> 6d3378dc532510f93d67ee6cc660f58bcc7b3335
	 */
	public static enum SortMethod
	{
		NO_SORT, DATE, LOCATION, DATE_LOCATION
	}
	
	/**
	 * Fetch comments globally (so not by parent) by location/date
	 * @param sort sorting method
	 * @return list of comments/topics
	 */
	public ArrayList<ThreadModel> fetchComments(SortMethod sort)
	{
		String sortString = null;
		switch(sort)
		{
			case DATE:
				sortString = "_search?sort=threadTimestamp:desc" + "&" + listSize;
				break;
			case NO_SORT:
			default:
				sortString = "_search" + "?" + listSize;
		}
		return new ArrayList<ThreadModel>(search.searchThreads(sortString));
	}
	
	/**
	 * Fetch comments by parent (and location/date)
	 * <p>
	 * Can be used to fetch only topics (pass parentID = ThreadModel.ROOT)
	 * @param parentID UUID of parent comment
	 * @param sort sorting method
	 * @return list of comments (or topics if parentID = null)
	 */
	public ArrayList<ThreadModel> fetchChildComments(UUID parentID, SortMethod sort)
	{
		String sortString = null;
		switch(sort)
		{
			case DATE:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" +
						"sort=threadTimestamp:desc" + "&" + listSize;
				break;
			case NO_SORT:
			default:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" + listSize;
		}
		return new ArrayList<ThreadModel>(search.searchThreads(sortString));
	}
}