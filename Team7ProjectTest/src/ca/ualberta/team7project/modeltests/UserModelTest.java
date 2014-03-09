/**
 * A series of tests to determine that UserModel is working correctly:
 * <ul>
 * <li>Confirm that user's UniqueID's are unique.
 * <li>Location (longitude and latitude) is correct
 * </ul>
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project.modeltests;

import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.UserModel;
import android.test.ActivityInstrumentationTestCase2;

public class UserModelTest extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	public UserModelTest()
	{

		super(MainActivity.class);
	}

	/**
	 * Ensure that two users with the same name have different UniqueID
	 */
	public void testUniqueID()
	{

		UserModel userOne = new UserModel("Ash Ketchum");
		UserModel userTwo = new UserModel("Ash Ketchum");

		assertFalse("UniqueID for two users with the same name is unique",
				userOne.getUniqueName().equals(userTwo.getUniqueName()));
	}

	/**
	 * Ensure that the location is correct
	 */
	public void testUserLocation()
	{

	}
}
