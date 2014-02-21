/**
 * A series of tests to determine that UserModel is working correctly:
 * <ul>
 * <li>Confirm that user's UniqueID's are unique.
 * </ul>
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project.test;

import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.UserModel;
import android.test.ActivityInstrumentationTestCase2;

public class UserModelTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	/**
	 * Default constructor
	 */
	public UserModelTest() {
		super(MainActivity.class);
	}
	
	/**
	 * Ensure that two users with the same name have different UniqueID
	 */
	public void testUniqueID() {
		UserModel userOne = new UserModel("Ash Ketchum");
		UserModel userTwo = new UserModel("Ash Ketchum");

		assertFalse("UniqueID for two users with the same name is unique", 
				userOne.getUniqueID().equals(userTwo.getUniqueID()));
	}

}
