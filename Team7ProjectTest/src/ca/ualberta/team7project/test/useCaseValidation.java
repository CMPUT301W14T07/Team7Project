/**a
 * @author	Michael Raypold
 * 
 */
package ca.ualberta.team7project.test;

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
	 * Test to check use case 1 (CreateIdentity)
	 */
	public void createIdentityTest() {
		UserPersistenceModel user = new UserPersistenceMode();
		UserModel newUser = null;
		
		if(user.exists() == false) {
			newUser = new UserModel("Bob");
		}
		else {
			// Retrieve the user tied to the application
			newUser = user.getUser();
		}
		
		assertNotNull("User should be created or already exist", newUser);
	}
	
	/**
	 * Test to check use case 2 (SetLocation)
	 */
	public void setLocationTest() {
		UserPersistenceModel user = new UserPersistenceMode();
		UserModel newUser = user.getUser();

		// TODO https://developer.android.com/training/location/location-testing.html
		newUser.setCurrentLocation();
		
		assertNotNull("Users location should now be set", newUser.getCurrentLocation());
	}
	
	/**
	 * Test to check use case 11 (AddTopLevelComment)
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
	 *  
	 */
	
	
}
