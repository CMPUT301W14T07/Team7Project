package ca.ualberta.team7project.test;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;

public class UseCaseValidation extends
		ActivityInstrumentationTestCase2<MainActivity>
{
	Activity activity;

	public UseCaseValidation()
	{

		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
	    this.activity = getActivity();
	}
	
	/*
	 * All use cases are referenced at
	 * https://github.com/CMPUT301W14T07/Team7Project/wiki/Use-Cases
	 */
	

	/*
	 * Tests whether the call to onIdentityPositiveCLick works in MainActivity
	 * and creates a new user successfully
	 */
	public void testCreateIdentity()
	{
		Context context = activity.getApplicationContext();
		FragmentManager fragment = activity.getFragmentManager();
		
		assertNotNull("Context is not null", context);
		assertNotNull("Fragment is not null", fragment);
		
		UserController userController = new UserController(context, fragment);
		UserModel user = new UserModel("Jerry Maguire");

		userController.setContext(context);
		userController.setFragment(null);
		userController.createNewUser(user.getName());
	}
/*
	/* Bad test, will create new one */
	public void testAddToplevelComment()
	{

		UserModel katniss = new UserModel("Katniss Everdeen");
		UserModel peeta = new UserModel("Peeta Mellark");

		ThreadListModel threads = new ThreadListModel();

		//ThreadModel thread = new ThreadModel("What about you?", katniss);
		//ThreadModel reply = new ThreadModel("Nobody needs me.", peeta);

		//threads.addTopic(reply);
		//threads.insertFirstTopic(thread);

		//assertEquals("User is able to add top level comment",
		//		threads.getFirstTopic(), thread);
	}
/*
	public void testUserReply()
	{

		UserModel jerry = new UserModel("Jerry Maguire");
		UserModel dorothy = new UserModel("Dorothy");

		ThreadModel thread = new ThreadModel("You complete me.", jerry);
		ThreadModel reply = new ThreadModel(
				"Just shutup; you had me at hello.", dorothy);

		thread.addComment(reply);

		assertEquals("User able to reply to threads", reply, thread
				.getComments().getLast());
	}
*/	

}
