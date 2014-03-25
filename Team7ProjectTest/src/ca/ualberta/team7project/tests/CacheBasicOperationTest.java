package ca.ualberta.team7project.tests;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.MemoryToFileOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
import ca.ualberta.team7project.models.ThreadModel;

public class CacheBasicOperationTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity ac;

	public CacheBasicOperationTest()
	{
		super(MainActivity.class);
	}
	
	
	public void testSaveInFileWithPoolEmpty(){
		
		ac = this.getActivity();
		MemoryToFileOperation tool = new MemoryToFileOperation(ac);
		tool.saveInFile();
		tool.loadFromFile();
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}

	
	public void testSaveInFilePupulated(){
		ac = this.getActivity();
		
		//populate pool with several examples
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		MemoryToFileOperation tool = new MemoryToFileOperation(ac);
		ThreadModelPool.threadModelPool.add(tm);
		
		tool.saveInFile();
		tool.loadFromFile();
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 1);
	}
	
	

}