package ca.ualberta.team7project.tests;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.MemoryToFileOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
import ca.ualberta.team7project.models.ThreadModel;

public class MemoryToFileOperationTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity ac;

	public MemoryToFileOperationTest()
	{
		super(MainActivity.class);
	}
	
	public void testSaveInFileWithPoolEmpty(){
		
		ac = this.getActivity();
		MemoryToFileOperation tool = new MemoryToFileOperation(ac);
		//ThreadModelPool.threadModelPool.clear();
		tool.saveInFile();
		tool.loadFromFile();
		
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	public void testSaveInFilePopulated(){
		ac = this.getActivity();
		
		//populate pool with several examples
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		MemoryToFileOperation tool = new MemoryToFileOperation(ac);
		
		//check to see if pool is empty
		assertEquals("Starts empty", true, ThreadModelPool.threadModelPool.size() == 0);
		//add the ThreadModel
		ThreadModelPool.threadModelPool.add(tm);
		
		tool.saveInFile();
		//clear and check it's empty again
		ThreadModelPool.threadModelPool.clear();
		assertEquals("Pool should be cleared", true, ThreadModelPool.threadModelPool.size() == 0);
		
		//load from file and check that it is back
		tool.loadFromFile();
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	

}
