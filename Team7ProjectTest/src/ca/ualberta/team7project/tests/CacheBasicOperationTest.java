package ca.ualberta.team7project.tests;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.MemoryToFileOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;

public class CacheBasicOperationTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public CacheBasicOperationTest()
	{
		super(MainActivity.class);
	}
	
	//I 
	public void testSaveInFileWithPoolEmpty(){
		
		//populate pool with several examples
		//ThreadModel tm = new ThreadModel("some text", null, "some text too");
		
		MemoryToFileOperation tool = new MemoryToFileOperation(new MainActivity());
		tool.saveInFile();
		tool.loadFromFile();
		
		this.assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);
	}
	
	

}
