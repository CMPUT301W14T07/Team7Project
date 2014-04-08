package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
import ca.ualberta.team7project.models.ThreadModel;

public class CacheOpeartionSaveAndLoadTest extends ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity ac;
	
	public CacheOpeartionSaveAndLoadTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	
	//Test if cacheOperation save and load right
	public void testSaveAndLoadCache(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//clear the file
		ThreadModelPool.threadModelPool.clear();
		//to make file empty
		tool.saveFile(ac);
		tool.loadFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
		
		
		//at this point, pool in the file only 0 threadmodel
		//the pool in the memory would be owning 2.
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		//load the pool from file to memory
		tool.loadFile(ac);
		//add one pool from memory
		tool.saveThread(tm);

		assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
		
		//this test clears the pool in our app and loads what has been saved previously
		tool.loadFile(ac);
		assertEquals("The threadModelPool should be emptied because the file is empty", false, ThreadModelPool.threadModelPool.size() == 1);
		
		//at this point, pool in the file has 0 threadModel

		ThreadModel tm1= new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");
		//load the pool from file to memory
		tool.loadFile(ac);
		//add one pool from memory
		tool.saveThread(tm1);
		//make file synchronized to memory
		tool.saveFile(ac);
		
		tool.saveThread(tm2);

		//repeat of previous tests?
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 2);
		
		//at this point, pool in the file has 1 threadModel
		//load the pool from file to memory
		tool.loadFile(ac);
		
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
		
		//at this point, pool in the file has 1 threadModel
		//load the pool from file to memory
		tool.loadFile(ac);
		ThreadModel tm3 = new ThreadModel("some text", null, "some text too");
		ThreadModel tm4 = new ThreadModel("haha", null, "haha");
				
		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		collection.add(tm3);
		collection.add(tm4);
				
		//add a collection of threadModel(which are 2 of them) to the cache in memory
		tool.saveCollection(collection);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 3);
		
		//synchronize File sytem with pool in the memory
		tool.saveFile(ac);
		
		//Synchronize pool in the memory with the pool in the file
		tool.loadFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 3);
		
		
		
	}	
	
	
	
	
	
	
}
