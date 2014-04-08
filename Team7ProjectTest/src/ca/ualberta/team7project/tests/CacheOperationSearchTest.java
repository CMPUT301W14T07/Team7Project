package ca.ualberta.team7project.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.cache.CacheOperation;
import ca.ualberta.team7project.cache.ThreadModelPool;
import ca.ualberta.team7project.models.ThreadModel;

public class CacheOperationSearchTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity ac;

	public CacheOperationSearchTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	// test for searching by UUID
	public void testSearchInCache() {
		
		ac = this.getActivity();
		CacheOperation tool = new CacheOperation();
		
		//clear the file
		ThreadModelPool.threadModelPool.clear();
		//to make file empty
		tool.saveFile(ac);
		tool.loadFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);		
		

		// load the pool from file to memory
		tool.loadFile(ac);
		ThreadModel tm = new ThreadModel("some text", null, "some text too");
		ThreadModel tm2 = new ThreadModel("haha", null, "haha");

		Collection<ThreadModel> collection = new ArrayList<ThreadModel>();
		collection.add(tm);
		collection.add(tm2);

		// save into cache in memory
		tool.saveCollection(collection);

		// not entirely convinced these test hierarchy
		Collection<ThreadModel> c1 = tool.searchByUUID(
				UUID.fromString(ThreadModel.ROOT), CacheOperation.BY_PARENTID);
		Collection<ThreadModel> c2 = tool.searchByUUID(tm.getUniqueID(),
				CacheOperation.BY_ITSELFID);
		Collection<ThreadModel> c3 = tool.searchByUUID(tm2.getTopicUUID(),
				CacheOperation.BY_TOPICID);

		assertEquals(true, c1.size() == 2);
		assertEquals(true, c2.size() == 1);
		assertEquals(true, c3.size() == 1);

		Collection<ThreadModel> collection2 = new ArrayList<ThreadModel>();
		ThreadModel topBlue = new ThreadModel("Blastoise", null, "Blue's team");
		ThreadModel midBlue1 = new ThreadModel("Wortortle", null,
				topBlue.getUniqueID(), topBlue.getUniqueID());
		ThreadModel midBlue2 = new ThreadModel("Poliwhorl", null,
				topBlue.getUniqueID(), topBlue.getUniqueID());
		ThreadModel midBlue3 = new ThreadModel("Vaporeon", null,
				topBlue.getUniqueID(), topBlue.getUniqueID());
		ThreadModel lowBlue = new ThreadModel("Squirtle", null,
				midBlue1.getUniqueID(), topBlue.getUniqueID());

		collection2.add(topBlue);
		collection2.add(midBlue3);
		collection2.add(midBlue2);
		collection2.add(midBlue1);
		collection2.add(lowBlue);

		tool.saveCollection(collection2);
		assertEquals("Should have 10 total now", 7,
				ThreadModelPool.threadModelPool.size());

		// search1: find all the threads with topBlue as the topic, should have
		// 5
		Collection<ThreadModel> search1 = tool.searchByUUID(
				topBlue.getUniqueID(), CacheOperation.BY_TOPICID);
		assertEquals("Should have 5", 5, search1.size());
		for (ThreadModel element : search1) {
			assertEquals("Expecting every Pokemon to be here", true,
					collection2.contains(element));
		}

		// search2: find all the direct children of topBlue, should have 3
		Collection<ThreadModel> search2 = tool.searchByUUID(
				topBlue.getUniqueID(), CacheOperation.BY_PARENTID);
		assertEquals("Should have 3", 3, search2.size());
		for (ThreadModel element2 : search2) {
			assertEquals("Expecting mid tier Pokemon", true,
					collection2.contains(element2));
		}

		// search3: find lowBlue
		Collection<ThreadModel> search3 = tool.searchByUUID(
				lowBlue.getUniqueID(), CacheOperation.BY_ITSELFID);
		assertEquals("Should have 1", 1, search3.size());
		for (ThreadModel element3 : search3) {
			assertEquals("Expecting low tier", true,
					collection2.contains(element3));
		}
		
		//clear the file
		ThreadModelPool.threadModelPool.clear();
		//to make file empty
		tool.saveFile(ac);
		tool.loadFile(ac);
		assertEquals(true, ThreadModelPool.threadModelPool.size() == 0);		

	}
}
