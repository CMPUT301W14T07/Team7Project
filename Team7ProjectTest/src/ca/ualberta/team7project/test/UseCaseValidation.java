package ca.ualberta.team7project.test;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.models.ThreadListModel;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.UserModel;


public class UseCaseValidation extends
		ActivityInstrumentationTestCase2<MainActivity>
{

	public UseCaseValidation()
	{

		super(MainActivity.class);
	}
	
	/*
	 * All use cases are referenced https://github.com/CMPUT301W14T07/Team7Project/wiki/Use-Cases
	 */
	
	public void testCreateItendity()
	{
		assertNotNull("User can create identity", new UserModel("Jerry Maguire"));
	}
	
	public void testAddToplevelComment()
	{
		UserModel katniss = new UserModel("Katniss Everdeen");
		UserModel peeta = new UserModel("Peeta Mellark");
		
		ThreadListModel threads = new ThreadListModel();
		
		ThreadModel thread = new ThreadModel("What about you?", katniss);
		ThreadModel reply = new ThreadModel("Nobody needs me.", peeta);

		threads.addTopic(reply);
		threads.insertFirstTopic(thread);

		assertEquals("User is able to add top level comment", threads.getFirstTopic(), thread);
	}
	
	public void testUserReply()
	{
		UserModel jerry = new UserModel("Jerry Maguire");
		UserModel dorothy = new UserModel("Dorothy");
			
		ThreadModel thread = new ThreadModel("You complete me.", jerry);
		ThreadModel reply = new ThreadModel("Just shutup; you had me at hello.", dorothy);
				
		thread.addComment(reply);
		
		assertEquals("User able to reply to threads", reply, thread.getComments().getLast());
	}
	
	
}
