/**
 * @author	Michael Raypold
 */
package ca.ualberta.team7project.test;

import java.util.Date;
import ca.ualberta.team7project.MainActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;

public class useCaseValidation extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public useCaseValidation(Class<MainActivity> name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test CreateIdentity use case
	 * @return	void
	 */
	public void createIdentityTest() {
		UserPersistenceModel user = new UserPersistenceMode();
		UserModel newUser = null;
		
		if(user.exists() == false) {
			newUser = new UserModel("Bob");
		}
		else {
			newUser = user.getUser();
		}
		
		assertNotNull("User should be created or already exist", newUser);
	}
	
	/**
	 * Test SetLocation use case
	 * @return	void
	 */
	public void setLocationTest() {
		UserPersistenceModel user = new UserPersistenceMode();
		UserModel newUser = user.getUser();

		// TODO https://developer.android.com/training/location/location-testing.html
		newUser.setCurrentLocation();
		
		assertNotNull("Users location should now be set", newUser.getCurrentLocation());
	}
	
	/**
	 * Test AddTopLevelComment use case
	 * @return	void
	 */
	public void addTopLevelCommentTest() {
		TopicModel topic = new TopicModel();
		ThreadModel thread = new ThreadModel();
		UserModel user = new UserModel();
		
		// TODO Maybe have a helper class so we aren't creating a new user for each Test.
		user.setName("Bob");
		
		thread.setComment("This is my comment");
		thread.setLocation();
		thread.setAuthor(CommentAuthor author = new CommentAuthor(user));
		topic.addThread(thread);
		
		assertTrue("Comment is top level", topic.threads.contains(thread));
	}
	
	/**
	 * Test sortByScore use case
	 * @return	void
	 */
	public void sortByScoreTest() {
		// For the actual test, this topic needs to be populated.
		TopicModel topic = new TopicModel();
		Boolean ordered = true;
		Integer lastScore = null;
		
		topic.sortThreadsByRelevance();
		lastScore = topic.threads.get(0).getVotes();
		
		for(Thread t : topic.getThreads()) {
			if(lastScore < t.getVotes()) {
				ordered = false;
				break;
			}
			
			lastScore = t.getVotes();
		}
	
		assertTrue("Comments should be ordered by vote count", ordered);
	}
		
	/**
	 * Test DefaultCommentOrder use case
	 * <p>
	 * Comments are ordered based off date by default if the user has never seen the topic before
	 * @return	void
	 */
	public void defaultCommentOrderTest() {
		// For the actual test, this topic needs to be populated.
		TopicModel topic = new TopicModel();
		Boolean ordered = true;
		Date lastDate = null;
		
		lastDate = topic.threads.get(0).getDate();
		
		for(Thread t : topic.getThreads()) {
			if(lastDate.compareTo(t.getDate()) < 0 ) {
				ordered = false;
				break;
			}

			lastDate = t.getDate();
		}
		
		assertTrue("Threads ordered by date", ordered);
	}
	
	/**
	 * Test AddFavorite use case
	 * @return void
	 */
	public void addFavoriteTest() {
		// For the actual test, this topic needs to be populated.
		TopicModel topic = new TopicModel();
		UserModel user = new UserModel();
		
		user.setName("bob");
		
		// Bob is going to favorite the parent thread
		user.addFavoriteThread(topic.getThreads().get(0));

		assertEqual("Users most recent favorite should be the same as the thread",
				user.getFavorites().getLast(), topic.getThreads().get(0));
	}
	
	/**
	 * Test EditComment use case
	 * @return void 
	 */
	public void editCommentTest() {
		// Initial test is the same as addTopLevelCommentTest()
		TopicModel topic = new TopicModel();
		ThreadModel thread = new ThreadModel();
		UserModel user = new UserModel();
		
		// TODO Maybe have a helper class so we aren't creating a new user for each Test.
		user.setName("Bob");
		
		thread.setComment("This is my comment");
		thread.setLocation();
		thread.setAuthor(CommentAuthor author = new CommentAuthor(user));
		topic.addThread(thread);
		
		assertTrue("Comment is top level for edit comment", topic.threads.contains(thread));
		
		thread = topic.getThreads().getLast();
		thread.setComment("Updated comment");
				
		assertEquals("Edited thread should represent the new comment",
				"updated comment",
				topic.getThreads().getLast().getComment());
		
	}
}
