package ca.ualberta.team7project.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import android.content.Context;
import ca.ualberta.team7project.models.ThreadModel;

public class CacheOperation {
	
	public static int BY_PARENTID = 1;
	public static int BY_ITSELFID = 2;
	public static int BY_TOPICID =3;

	/**
	 * Search the threadModelPool by UUID and mode<p>
	 * it has three modes, by_parentid, by_itselfid, and by_topicid.
	 * And these mode can be referred by the class name, since they are static
	 * return Collection of threadModel
	 * @param uuid
	 * @param mode
	 * @return
	 */
	public Collection<ThreadModel> loadByUUID(UUID uuid, int mode){
		
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
	public void SaveInCache(ThreadModel threadModel){	
		UUID uuid = threadModel.getUniqueID();
		//I assume the inserted threadModel is always the latest model
		//I don't know if this assumption is right or not
		int i;
		for(i=0; i<ThreadModelPool.threadModelPool.size();i++){
			UUID tempUUID = ThreadModelPool.threadModelPool.get(i).getUniqueID();
			if(tempUUID.equals(uuid)){
				ThreadModelPool.threadModelPool.set(i, threadModel);
				return;
			}
		}
		
		//otherwise, pool add one more threadModel
		ThreadModelPool.threadModelPool.add(threadModel);
	}
	
	/**
	 * Inset a collection of threadModels to the pool in the memory<p>
	 * the same as inserting the single one, except for collection this time
	 * @param collection
	 */
	public void SaveInCache(Collection<ThreadModel> collection){
		for(ThreadModel threadModel: collection){
			SaveInCache(threadModel);
		}
	}
	
	/**
	 * Make ThreadModelPool in the memory synchronized to the pool in the File System<p>
	 * Technically, if there is no network connected, this is the first function to call to set the cache up
	 * @param context
	 */
	public void MemorySyncToFS(Context context){
		MemoryToFileOperation transferTool = new MemoryToFileOperation(context);
		transferTool.loadFromFile();	
	}
	
	/**
	 * Make threadModelPool in the file synchronized to the pool in the current memory<p>
	 * Call this when you want ThreadModelPool in file system to be consistent with the one in memory
	 * @param context
	 */
	public void FSSyncToMemory(Context context){
		MemoryToFileOperation transferTool = new MemoryToFileOperation(context);
		transferTool.loadFromFile();
	}

}
