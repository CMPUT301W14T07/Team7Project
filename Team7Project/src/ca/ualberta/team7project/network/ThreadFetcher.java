package ca.ualberta.team7project.network;

import java.util.ArrayList;
import java.util.UUID;

import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.models.ThreadModel;

/**
 * Fetches comments from the server
 * <p>
 * Prepares search strings for ElasticSearchOperation
 *
 */
public class ThreadFetcher
{
	void CacheToast()
	{
		//Toast.makeText(MainActivity.getMainContext(), "Pulling from cache", Toast.LENGTH_SHORT).show();
	}
	
	private ElasticSearchOperation search;
	private CacheOperation cache;
	private String listSize;
	double lat = 0;
	double lon = 0;
	
	boolean isPictureSort = false;
	
	private final String pictureFilterEntityString = "\"filter\":{\"exists\":{\"field\":\"innerBitmapData\"}}";
	
	private ConnectionDetector detector;
	
	/**
	 * Construct and set max size to the default (15)
	 */
	public ThreadFetcher()
	{
		super();
		listSize = "size=15";
		search = new ElasticSearchOperation();
		detector = new ConnectionDetector(MainActivity.getMainContext());
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
		detector = new ConnectionDetector(MainActivity.getMainContext());
	}
	
	/**
	 * Set the ThreadFetcher to fetch only comments with pictures
	 */
	public void EnablePictureSort()
	{
		isPictureSort = true;
	}
	
	/**
	 * Enumeration of methods used to get the "best/most relevant" topics
	 */
	public static enum SortMethod
	{
		NO_SORT, DATE, LOCATION
	}
	
	/**
	 * Insert the user's location for proximity sorting
	 * <p>
	 * This must be called if passing in SortMethod.LOCATION, else the user's location is treated as [0, 0]
	 * @param latitude
	 * @param longitude
	 */
	public void SetLocation(double latitude, double longitude)
	{
		this.lat = latitude;
		this.lon = longitude;
	}
	
	public void InitCacheOperation(SortMethod sort)
	{
		cache = new CacheOperation();
		
		cache.SetSortMethod(sort);
		cache.SetMaxResults(20);
		cache.SetFilterPicture(isPictureSort);
		cache.SetLocation(lat, lon);
	}
	
	/**
	 * Fetch comments that match a set of tags
	 * <p>
	 * Only comments with <i>all</i> the specified tags are fetched
	 * @param tags a list of tags
	 * @return list of comments/topics
	 */
	public ArrayList<ThreadModel> fetchTaggedComments(ArrayList<String> tags)
	{
		if(! detector.isConnectingToInternet())
		{
			InitCacheOperation(SortMethod.NO_SORT);
			CacheToast();
			return cache.searchTags(tags);
		}
		
		String sortString = "_search?sort=threadTimestamp:desc" + "&" + "size=40";
		String sortEntity = "{";

		sortEntity += "\"query\":{\"query_string\":{\"query\":\"innerTags:(";
		
		//insert space-seperated UUID's into the sortEntity (extra space at end is OK)
		for(String tag : tags)
		{
			sortEntity += tag + " ";
		}
		
		sortEntity += ")\"}}";
		
		if(isPictureSort)
			sortEntity += "," + pictureFilterEntityString;
		
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
		if(! detector.isConnectingToInternet())
		{
			InitCacheOperation(sort);
			CacheToast();
			return cache.searchAll();
		}
		
		String sortString = null;
		String sortEntity = "{";
		switch(sort)
		{
			case DATE:
				sortString = "_search?sort=threadTimestamp:desc" + "&" + listSize;
				break;
			case LOCATION:
				sortString = "_search" + "?" + listSize;
				sortEntity += "\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search" + "?" + listSize;
		}
		
		if(isPictureSort)
			sortEntity += pictureFilterEntityString;	
		
		sortEntity += "}";
		
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
		if(! detector.isConnectingToInternet())
		{
			InitCacheOperation(sort);
			CacheToast();
			return cache.searchChildren(parentID);
		}
		
		String sortString = null;
		String sortEntity = "{";
		
		switch(sort)
		{
			case DATE:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" +
						"sort=threadTimestamp:desc" + "&" + listSize;
				break;
			case LOCATION:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" + listSize;
				sortEntity += "\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search?q=parentUUID:" + parentID.toString() + "&" + listSize;
		}
		
		if(isPictureSort)
			sortEntity += ((sort == SortMethod.LOCATION) ? "," : "") + pictureFilterEntityString;	
		
		sortEntity += "}";
		
		return new ArrayList<ThreadModel>(search.searchThreads(sortString, sortEntity));
	}
	
	/**
	 * Fetch comments by a list of their own UUID's
	 * <p>
	 * Used to fetch the list of favorited comments from server
	 * @param favorites list of UUID's for favorited comments
	 * @param sort sorting method
	 * @return list of favorited comments
	 */
	public ArrayList<ThreadModel> fetchFavorites(ArrayList<UUID> favorites, SortMethod sort)
	{
		if(! detector.isConnectingToInternet())
		{
			InitCacheOperation(sort);
			CacheToast();
			return cache.searchFavorites(favorites);
		}
		
		String sortString = null;
		String sortEntity = null;
		String favoritesSize = "size=" + Integer.toString(favorites.size());
		
		sortEntity = "{";
		
		switch(sort)
		{
			case DATE:
				sortString = "_search?sort=threadTimestamp:desc" + "&" + favoritesSize;
				break;
			case LOCATION:
				sortString = "_search?" + favoritesSize;
			
				sortEntity += "\"sort\":{\"_geo_distance\":{\"user.locationModel.locationInner\":[";
				sortEntity += Double.toString(lat);
				sortEntity += ", ";
				sortEntity += Double.toString(lon);
				sortEntity += "],\"order\":\"asc\",\"unit\":\"km\"}}";
				break;
			case NO_SORT:
			default:
				sortString = "_search?" + favoritesSize;
		}
		
		sortEntity += "\"query\":{\"query_string\":{\"query\":\"uniqueID:(";
		
		//insert space-seperated UUID's into the sortEntity (extra space at end is OK)
		for(UUID fav : favorites)
		{
			sortEntity += fav.toString() + " ";
		}
		
		sortEntity += ")\"}}";
		
		if(isPictureSort)
			sortEntity += "," + pictureFilterEntityString;
		
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