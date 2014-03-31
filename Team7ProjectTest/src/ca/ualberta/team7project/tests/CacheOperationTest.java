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
		tool.saveFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	//at this point, pool in the file only 0 threadmodel
	//the pool in the memory would be owning 2.
	public void testSaveInCache2(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		//load the pool from file to memory
		tool.loadFile(ac);
		//add one pool from memory
		tool.saveThread(tm);

		assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
		
		//this test clears the pool in our app and loads what has been saved previously
		tool.loadFile(ac);
		assertEquals("The threadModelPool should be emptied because the file is empty", false, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	//at this point, pool in the file has 0 threadModel
	public void testSaveInCache3(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		//load the pool from file to memory
		tool.loadFile(ac);
		//add one pool from memory
		tool.saveThread(tm);
		//make file synchronized to memory
		tool.saveFile(ac);
		
		tool.saveThread(tm2);

		//repeat of previous tests?
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 2);
		
	}
	
	//at this point, pool in the file has 1 threadModel
	public void testSaveInCache4(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.loadFile(ac);
		
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	//at this point, pool in the file has 1 threadModel
	public void testSaveInCache5(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.loadFile(ac);
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		
		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		collection.add(tm);
		collection.add(tm2);
		
		//add a collection of threadModel(which are 2 of them) to the cache in memory
		tool.saveCollection(collection);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 3);
		
		//synchronize File sytem with pool in the memory
		tool.saveFile(ac);
		
		//Synchronize pool in the memory with the pool in the file
		tool.loadFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 3);
			
	}
	
	//test for searching by UUID
	public void testSaveInCache6(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.loadFile(ac);
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		
		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		collection.add(tm);
		collection.add(tm2);
		
		//save into cache in memory
		tool.saveCollection(collection);
		
		//not entirely convinced these test hierarchy
		Collection<ThreadModel> c1 = tool.searchByUUID(UUID.fromString(ThreadModel.ROOT), CacheOperation.BY_PARENTID);
		Collection<ThreadModel> c2 = tool.searchByUUID(tm.getUniqueID(), CacheOperation.BY_ITSELFID);
		Collection<ThreadModel> c3 = tool.searchByUUID(tm2.getTopicUUID(), CacheOperation.BY_TOPICID);
		
		assertEquals(true, c1.size() == 5);
		assertEquals(true, c2.size() == 1);
		assertEquals(true, c3.size() == 1);

		Collection<ThreadModel> collection2 = new ArrayList<ThreadModel>();
		ThreadModel topBlue = new ThreadModel ("Blastoise", null, "Blue's team");
		ThreadModel midBlue1 = new ThreadModel("Wortortle", null, topBlue.getUniqueID(), topBlue.getUniqueID());
		ThreadModel midBlue2 = new ThreadModel("Poliwhorl", null, topBlue.getUniqueID(), topBlue.getUniqueID());
		ThreadModel midBlue3 = new ThreadModel("Vaporeon", null, topBlue.getUniqueID(), topBlue.getUniqueID());
		ThreadModel lowBlue = new ThreadModel("Squirtle", null, midBlue1.getUniqueID(), topBlue.getUniqueID());

		collection2.add(topBlue);
		collection2.add(midBlue3);
		collection2.add(midBlue2);
		collection2.add(midBlue1);
		collection2.add(lowBlue);
		
		tool.saveCollection(collection2);
		assertEquals("Should have 10 total now", 10, ThreadModelPool.threadModelPool.size());
		
		//search1: find all the threads with topBlue as the topic, should have 5
		Collection<ThreadModel> search1 = tool.searchByUUID(topBlue.getUniqueID(), CacheOperation.BY_TOPICID);
		assertEquals("Should have 5", 5, search1.size());
		for (ThreadModel element : search1)
		{
			assertEquals("Expecting every Pokemon to be here", true, collection2.contains(element));
		}
		
		//search2: find all the direct children of topBlue, should have 3
		Collection<ThreadModel> search2 = tool.searchByUUID(topBlue.getUniqueID(), CacheOperation.BY_PARENTID);
		assertEquals("Should have 3", 3, search2.size());
		for (ThreadModel element2 : search2)
		{
			assertEquals("Expecting mid tier Pokemon", true, collection2.contains(element2));
		}
		
		//search3: find lowBlue
		Collection<ThreadModel> search3 = tool.searchByUUID(lowBlue.getUniqueID(), CacheOperation.BY_ITSELFID);
		assertEquals("Should have 1", 1, search3.size());
		for (ThreadModel element3 : search3)
		{
			assertEquals("Expecting low tier", true, collection2.contains(element3));
		}
		
		
			
	}
	
	//clear the the file
	public void testSaveInCache8(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModelPool.threadModelPool.clear();
		//to make file empty
		tool.saveFile(ac);
		tool.loadFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	
	
	
	
}
