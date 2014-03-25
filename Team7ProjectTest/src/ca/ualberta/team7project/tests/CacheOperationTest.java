package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
import ca.ualberta.team7project.models.ThreadModel;

public class CacheOperationTest extends ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity ac;
	
	public CacheOperationTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	public void testSaveInCache1(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//to make file empty
		tool.fsSyncWithMemory(ac);
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	//at this point, pool in the file only 0 threadmodel
	//the pool in the memory would be owning 2.
	public void testSaveInCache2(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		//load the pool from file to memory
		tool.memorySyncWithFS(ac);
		//add one pool from memory
		tool.SaveInCache(tm);
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	//at this point, pool in the file has 0 threadModel
	public void testSaveInCache3(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		//load the pool from file to memory
		tool.memorySyncWithFS(ac);
		//add one pool from memory
		tool.SaveInCache(tm);
		//make file synchronized to memory
		tool.fsSyncWithMemory(ac);
		
		tool.SaveInCache(tm2);
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 2);
	}
	
	//at this point, pool in the file has 1 threadModel
	public void testSaveInCache4(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.memorySyncWithFS(ac);
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	//at this point, pool in the file has 1 threadModel
	public void testSaveInCache5(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.memorySyncWithFS(ac);
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		
		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		collection.add(tm);
		collection.add(tm2);
		
		//add a collection of threadModel(which are 2 of them) to the cache in memory
		tool.SaveInCache(collection);
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 3);
		
		//synchronize File sytem with pool in the memory
		tool.fsSyncWithMemory(ac);
		
		//Synchronize pool in the memory with the pool in the file
		tool.memorySyncWithFS(ac);
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 3);
			
	}
	
	//test for searching by UUID
	public void testSaveInCache6(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.memorySyncWithFS(ac);
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		
		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		collection.add(tm);
		collection.add(tm2);
		
		//save into cache in memory
		tool.SaveInCache(collection);
		
		Collection<ThreadModel> c1 = tool.searchByUUID(UUID.fromString(ThreadModel.ROOT), CacheOperation.BY_PARENTID);
		Collection<ThreadModel> c2 = tool.searchByUUID(tm.getUniqueID(), CacheOperation.BY_ITSELFID);
		Collection<ThreadModel> c3 = tool.searchByUUID(tm2.getTopicUUID(), CacheOperation.BY_TOPICID);
		
		this.assertEquals(true, c1.size() == 5);
		this.assertEquals(true, c2.size() == 1);
		this.assertEquals(true, c3.size() == 1);
			
	}
	
	//clear the the file
	public void testSaveInCache8(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModelPool.threadModelPool.clear();
		//to make file empty
		tool.fsSyncWithMemory(ac);
		tool.memorySyncWithFS(ac);
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	
	
	
	
}
