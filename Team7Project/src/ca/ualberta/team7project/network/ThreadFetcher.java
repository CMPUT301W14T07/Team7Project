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
	double lat = 0;
	double lon = 0;
	
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
	 * Enumeration of methods used to get the "best/most relevant" topics
	 */
	public static enum SortMethod
	{
		NO_SORT, DATE, LOCATION
	}
	
	public void SetLocation(double latitude, double longitude)
	{
		this.lat = latitude;
		this.lon = longitude;
	}
	
	public ArrayList<ThreadModel> fetchTaggedComments(ArrayList<String> tags)
	{
		String sortString = "_search?sort=threadTimestamp:desc" + "&" + "size=40";
		String sortEntity = "{";

		sortEntity += "\"query\":{\"query_string\":{\"query\":\"innerTags:(";
		
		//insert space-seperated UUID's into the sortEntity (extra space at end is OK)
		for(String tag : tags)
		{
			sortEntity += tag + " ";
		}
		
		sortEntity += ")\"}}";
		sortEntity += "}";
		
		return new ArrayList<ThreadModel>(search.searchThreads(sortString, sortEntity));
	}
	
	/**
	 * Fetch comments globally (so not by parent) by location/date
	 * @param sort sorting method
	 * @return list of comments/topics
	 */
	public ArrayList<ThreadModel> fetchComments(SortMethod sort)
	{
		String sortString = null;
		String sortEntity = null;
		switch(sort)
		{
			case DATE:
				sortString = "_search?sort=threadTimestamp:desc" + "&" + listSize;
				sortEntity = null;
				break;
			case LOCATION:
				sortString = "_search" + "?" + listSize;
				sortEntity = "{\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search" + "?" + listSize;
				sortEntity = null;
		}
		return new ArrayList<ThreadModel>(search.searchThreads(sortString, sortEntity));
	}
	
	/**
	 * Fetch comments by parent (and location/date)
	 * <p>
	 * Can be used to fetch only direct children of a thread (pass parentID = ThreadModel.ROOT)
	 * @param parentID UUID of parent comment
	 * @param sort sorting method
	 * @return list of comments (or topics if parentID = null)
	 */
	public ArrayList<ThreadModel> fetchChildComments(UUID parentID, SortMethod sort)
	{
		String sortString = null;
		String sortEntity = null;
		switch(sort)
		{
			case DATE:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" +
						"sort=threadTimestamp:desc" + "&" + listSize;
				sortEntity = null;
				break;
			case LOCATION:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" + listSize;
				sortEntity = "{\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" + listSize;
				sortEntity = null;
		}
		return new ArrayList<ThreadModel>(search.searchThreads(sortString, sortEntity));
	}
	
	/**
	 * Fetch comments by a list of their own UUID's
	 * <p>
	 * Used to fetch the list of favorited comments from server
	 * 
	 * @param favorites list of UUID's for favorited comments
	 * @param sort sorting method
	 * @return list of favorited comments
	 */
	public ArrayList<ThreadModel> fetchFavorites(ArrayList<UUID> favorites, SortMethod sort)
	{
		String sortString = null;
		String sortEntity = null;
		String favoritesSize = "size=" + Integer.toString(favorites.size());
		
		switch(sort)
		{
			case DATE:
				sortString = "_search?sort=threadTimestamp:desc" + "&" + favoritesSize;
				sortEntity = "{";
				break;
			case LOCATION:
				sortString = "_search?" + favoritesSize;
				sortEntity = "{";
				
				sortEntity += "\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search?" + favoritesSize;
				sortEntity = "{";
		}
		
		sortEntity += "\"query\":{\"query_string\":{\"query\":\"uniqueID:(";
		
		//insert space-seperated UUID's into the sortEntity (extra space at end is OK)
		for(UUID fav : favorites)
		{
			sortEntity += fav.toString() + " ";
		}
		
		sortEntity += ")\"}}";
		sortEntity += "}";
		
		return new ArrayList<ThreadModel>(search.searchThreads(sortString, sortEntity));
	}
	
	/**
	 * This is the same code as above. By using the above method however, there 
	 * might be an issue with the default condition which wouldn't work for the 
	 * Unique ID. 
	 * @param Unique ID
	 * @param sort
	 * @return
	 */
	public ArrayList<ThreadModel> fetchByUnique(UUID uniqueID, SortMethod sort)
	{
		String sortString = null;
		String sortEntity = null;
		switch(sort)
		{
			case DATE:
				sortString = "_search?q=uniqueID:" + uniqueID.toString() + "&" +
						"sort=threadTimestamp:desc" + "&" + listSize;
				sortEntity = null;
				break;
			case LOCATION:
				sortString = "_search?q=uniqueID:" + uniqueID.toString() + "&" + listSize;
				sortEntity = "{\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search?q=uniqueID:" + uniqueID.toString() + "&" + listSize;
				sortEntity = null;
		}
		return new ArrayList<ThreadModel>(search.searchThreads(sortString, sortEntity));
	}
	
}