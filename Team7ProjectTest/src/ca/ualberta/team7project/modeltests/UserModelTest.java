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

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.UserModel;

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

		assertFalse("uniqueName for two users with the same name is unique",
				userOne.getUniqueName().equals(userTwo.getUniqueName()));
	}

	/**
	 * Ensure that basic properties of UserModel are set
	 */
	public void testPrimitiveAttributes()
	{
		UserModel user = new UserModel("Jerry Maguire");
		
		assertNotNull("User is not null", user);
		assertEquals("User's name is properly set", "Jerry Maguire", user.getName());
		assertNotNull("User has a unique name", user.getUniqueName());
	}
	
	/**
	 * Ensure that the location is correct
	 */
	public void testUserLocation()
	{
		// TODO
	}
	
	
}
