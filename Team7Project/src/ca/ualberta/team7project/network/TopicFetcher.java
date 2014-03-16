package ca.ualberta.team7project.network;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.team7project.models.ThreadModel;


public class TopicFetcher
{
	private ElasticSearchOperation search;
	private String listSize;
	
	public TopicFetcher()
	{
		listSize = "size=15";
		
		search = new ElasticSearchOperation();
	}
	
	public TopicFetcher(int maxItems)
	{
		listSize = "size=" + Integer.toString(maxItems);
	}
	
	public static enum SortMethod //methods for getting the "best/most relevant" topics
	{
		NO_SORT, DATE, LOCATION, DATE_LOCATION
	}
	
	/**
	 * Fetch comments by location/date
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
	 * Use this to fetch topics (pass parentID = ThreadModel.ROOT)
	 * @param parentID UUID of parent
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
