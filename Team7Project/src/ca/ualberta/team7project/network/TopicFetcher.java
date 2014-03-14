package ca.ualberta.team7project.network;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.team7project.models.ThreadModel;


public class TopicFetcher
{
	public static enum SortMethod //methods for getting the "best/most relevant" topics
	{
		NO_SORT, DATE, LOCATION, DATE_LOCATION
	}
	
	/**
	 * Fetch topics (by location/date)
	 * @param sort sorting method
	 * @return list of topics
	 */
	public ArrayList<ThreadModel> fetchTopics(SortMethod sort)
	{
		return new ArrayList<ThreadModel>(ElasticSearchOperation.searchThreads(ThreadModel.ROOT));
	}
	
	/**
	 * Fetch comments by location/date
	 * @param sort sorting method
	 * @return list of comments/topics
	 */
	public ArrayList<ThreadModel> fetchComments(SortMethod sort)
	{
		return null;
	}
	
	/**
	 * Fetch comments by parent (and location/date)
	 * @param parentID UUID of parent
	 * @param sort sorting method
	 * @return list of comments (or topics if parentID = null)
	 */
	public ArrayList<ThreadModel> fetchChildComments(UUID parentID, SortMethod sort)
	{
		if(parentID == null)
			return fetchTopics(sort);
		
		return new ArrayList<ThreadModel>(ElasticSearchOperation.searchThreads(parentID.toString()));
	}
}
