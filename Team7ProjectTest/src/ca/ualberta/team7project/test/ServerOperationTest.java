package ca.ualberta.team7project.test;

import java.util.Collection;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.network.ElasticSearchOperation;
import ca.ualberta.team7project.network.TopicFetcher;

public class ServerOperationTest extends
		ActivityInstrumentationTestCase2<MainActivity> {



	public ServerOperationTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	/*
	public void testPush(){
		ElasticSearchOperation.pushThreadModel(new ThreadModel("ass",null,"asdf"));
		assertEquals(true, true);
	}
	*/

}
