package ca.ualberta.team7project.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import android.content.Context;
import ca.ualberta.team7project.location.LocationComparator;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.network.ThreadFetcher.SortMethod;


/**
 * Helper class, Cache programming interface<p>
 * MemorySyncWithFS(context) probably would be the first function to call,
 * if you want the threadModelPool in the memory to be loaded.
 * <p>
 * this class provides the essential operation for the cache,
 * like searching by uuid in the cache in the memory, 
 * or adding threadModel to cache(this is, these threadModels are cached)
 * <p>
 * If you worry your memory would lose, like user shutting down the app
 * you could call FSSyncWithMemory(context), which make file to be consistent with memory
 * <p>
 * if you want to have direct access to ThreadModelPool in the Main Memory,
 * I have made it to be singleton, which is static and would last in the app as app last,(I guess)
 * @author wzhong3
 *
 */
public class CacheOperation {
	
	public static int BY_PARENTID = 1;
	public static int BY_ITSELFID = 2;
	public static int BY_TOPICID =3;

	private SortMethod sortMethod = SortMethod.DATE;
	private boolean isFilterPicture = false;
	private double lat = 0;
	private double lon = 0;
	
	private Integer maxResults = 20;
	
	//Various methods for setting up the CacheOperation for a pull
	
	public void SetLocation(double lat, double lon)
	{
		this.lat = lat;
		this.lon = lon;
	}
	
	public void SetSortMethod(SortMethod sortMethod)
	{
		this.sortMethod = sortMethod;
	}
	
	public void SetFilterPicture(boolean isFilterPicture)
	{
		this.isFilterPicture = isFilterPicture;
	}
	
	public void SetMaxResults(Integer maxResults)
	{
		this.maxResults = maxResults;
	}
	
	/**
	 * For testing purposes only
	 * <p>
	 * Reset the cache operation to its default state
	 */
	public void RestoreDefaults()
	{
		lat = 0;
		lon = 0;
		isFilterPicture = false;
		sortMethod = SortMethod.DATE;
	}
	
	// SEARCH STRATEGY
	// filter first
	// then perform the search
	// then do the sort
	// then return the top some number of threads
	
	/**
	 * Retrieves a <i>copy</i> of the current cache pool and returns it after applying the picture filter
	 * @return filtered list of comments that were in the pool
	 */
	private ArrayList<ThreadModel> grabCurrentPool()
	{
		ArrayList<ThreadModel> pool = new ArrayList<ThreadModel>(ThreadModelPool.threadModelPool);
		
		if(isFilterPicture)
		{
			ArrayList<ThreadModel> filteredPool = new ArrayList<ThreadModel>();
			
			for(ThreadModel thread : pool)
			{
				if(thread.getImage() != null)
					filteredPool.add(thread);
			}
			
			return filteredPool;
		}
		
		return pool;
	}
	
	/**
	 * Sort a pool of threads by the current sorting method
	 * <p>
	 * The passed pool should be post-filter and post-search
	 * @param unsorted pool
	 * @return sorted pool
	 */
	private ArrayList<ThreadModel> sortPool(ArrayList<ThreadModel> pool)
	{	
		ArrayList<ThreadModel> poolCopy = new ArrayList<ThreadModel>(pool);
		
		switch(sortMethod)
		{		
			/*
			 * @@@@@@@@@@@@@ PUT SORTING STUFF HERE @@@@@@@@@@@@@
			 * @@@@@@@@@@@@@ PUT SORTING STUFF HERE @@@@@@@@@@@@@
			 * @@@@@@@@@@@@@ PUT SORTING STUFF HERE @@@@@@@@@@@@@
			 * 
			 */
			case DATE:
				//TODO: date sort the poolCopy
				
				break;
			case LOCATION:
				//use the geocalc
				//LocationComparator is in the location package
				//override the comparator
				Collections.sort(poolCopy, new LocationComparator(lat, lon));
				
				break;
			case NO_SORT:
			default:
				//do nothing (just return the argument)
				break;
		}
		
		return poolCopy;
	}
	
	private ArrayList<ThreadModel> getTop(ArrayList<ThreadModel> pool)
	{
		//TODO: get the top <maxResults> threads
		
		return pool;
	}
	
	/**
	 * Pull favorited comments from the cache
	 * @param favorites list of favorite UUID's
	 * @return list of favorited comments
	 */
	public ArrayList<ThreadModel> searchFavorites(ArrayList<UUID> favorites)
	{
		ArrayList<ThreadModel> pool = grabCurrentPool();
		
		ArrayList<ThreadModel> favoritePool = new ArrayList<ThreadModel>();
		
		for(ThreadModel thread : pool)
		{
			if(favorites.contains(thread.getUniqueID()))
				favoritePool.add(thread);
		}
		
		favoritePool = sortPool(favoritePool);
		
		return favoritePool; //return all in the case of favorites only
	}
	
	/**
	 * Pull child comments from the cache
	 * @param parent
	 * @return list of child comments
	 */
	public ArrayList<ThreadModel> searchChildren(UUID parent)
	{
		ArrayList<ThreadModel> pool = grabCurrentPool();
		
		ArrayList<ThreadModel> childPool = new ArrayList<ThreadModel>();
		
		for(ThreadModel thread : pool)
		{
			if(thread.getParentUUID().equals(parent))
				childPool.add(thread);
		}
		
		childPool = sortPool(childPool);
		
		return getTop(childPool);
	}
	
	/**
	 * Pull comments globally from the cache
	 * @return list of comments
	 */
	public ArrayList<ThreadModel> searchAll()
	{
		ArrayList<ThreadModel> pool = grabCurrentPool();
		pool = sortPool(pool);
		
		return getTop(pool);
	}
	
	/**
	 * Pull comments matching a set of tags from the cache
	 * <p>
	 * Pulls only comments that contain <i>all</i> the specified tags
	 * @param tags list of tags to match
	 * @return list of comments matching the passed tags
	 */
	public ArrayList<ThreadModel> searchTags(ArrayList<String> tags)
	{
		ArrayList<ThreadModel> pool = grabCurrentPool();
		
		//TODO: perform the tag search
		
		
		pool = sortPool(pool);
		
		return getTop(pool);
	}
	
	/**
	 * Make ThreadModelPool in the memory synchronized to the pool in the File System<p>
	 * Technically, if there is no network connected, this is the first function to call to set the cache up
	 * @param context
	 */
	public void loadFile(Context context)
	{
		MemoryToFileOperation transferTool = new MemoryToFileOperation(context);
		transferTool.loadFromFile();	
	}
	
	/**
	 * Make threadModelPool in the file synchronized to the pool in the current memory<p>
	 * Call this when you want ThreadModelPool in file system to be consistent with the one in memory
	 * @param context
	 */
	public void saveFile(Context context)
	{
		MemoryToFileOperation transferTool = new MemoryToFileOperation(context);
		transferTool.saveInFile();
	}
	
	
	/**
	 * Search the threadModelPool by UUID and mode<p>
	 * it has three modes, by_parentid, by_itselfid, and by_topicid.
	 * And these mode can be referred by the class name, since they are static
	 * return Collection of threadModel
	 * @param uuid
	 * @param mode
	 * @return
	 */
	public Collection<ThreadModel> searchByUUID(UUID uuid, int mode)
	{
		
		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		
		if(mode == BY_PARENTID){
			for(ThreadModel threadModel: ThreadModelPool.threadModelPool){
				if(threadModel.getParentUUID().equals(uuid)){
					collection.add(threadModel);
				}
			}
		}
		else if(mode == BY_ITSELFID){
			for(ThreadModel threadModel: ThreadModelPool.threadModelPool){
				if(threadModel.getUniqueID().equals(uuid)){
					collection.add(threadModel);
				}
			}
		}
		else if(mode == BY_TOPICID){
			for(ThreadModel threadModel: ThreadModelPool.threadModelPool){
				if(threadModel.getTopicUUID().equals(uuid)){
					collection.add(threadModel);
				}
			}
		}
		return collection;
	}
	
	/**
	 * Insert a single threadModel to the pool in the memory<p>
	 * don't worry about inserting a duplicated threadModel in the pool.
	 * it would check the UUID, and prevent from inserting a duplicated threadModel.
	 * @param threadModel
	 */
	public void saveThread(ThreadModel threadModel)
	{	
		if(threadModel == null)
			return;
		
		//we can assume the inserted threadModel is always the latest model
		
		//if an older/identical version of the thread already exists in pool, remove it
		for(ThreadModel thread : ThreadModelPool.threadModelPool)
		{
			if(threadModel.getUniqueID().equals(thread.getUniqueID()))
			{
				ThreadModelPool.threadModelPool.remove(thread);
				break;
			}
		}
		
		//add the up-to-date version of the thread to the pool
		ThreadModelPool.threadModelPool.add(threadModel);
	}
	
	/**
	 * Inset a collection of threadModels to the pool in the memory<p>
	 * the same as inserting the single one, except for collection this time
	 * @param collection
	 */
	public void saveCollection(Collection<ThreadModel> collection)
	{
		if(collection != null)
		{
			for(ThreadModel threadModel: collection)
			{
				saveThread(threadModel);
			}
		}
	}
}
