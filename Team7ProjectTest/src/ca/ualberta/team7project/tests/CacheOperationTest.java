package ca.ualberta.team7project.tests;

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
		tool.fsSyncToMemory(ac);
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	//at this point, pool in the file only 0 threadmodel
	//the pool in the memory would be owning 2.
	public void testSaveInCache2(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		//load the pool from file to memory
		tool.memorySyncToFS(ac);
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
		tool.memorySyncToFS(ac);
		//add one pool from memory
		tool.SaveInCache(tm);
		//make file synchronized to memory
		tool.fsSyncToMemory(ac);
		
		tool.SaveInCache(tm2);
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 2);
	}
	
	//at this point, pool in the file has 1 threadModels
	public void testSaveInCache4(){
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//load the pool from file to memory
		tool.memorySyncToFS(ac);
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	
	
}
