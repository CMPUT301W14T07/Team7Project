package ca.ualberta.team7project.tests;

import android.app.DialogFragment;
import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import ca.ualberta.team7project.MainActivity;

public class ActionBarViewTests extends
		ActivityInstrumentationTestCase2<MainActivity>
{
	/*
	 * Reuse statements
	 * https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#actionbarviewtests
	 */
	
	private int buttonId;
	private MainActivity activity;

	public ActionBarViewTests()
	{

		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		activity = getActivity();
	}

	// Still looking up how to properly perform ui tests...
	public void testSortPreferencesClick()
	{
		Button view = (Button) activity.findViewById(ca.ualberta.team7project.R.id.action_sort);
		assertNotNull("Button is not null", view);
		
		view.performClick();
		
		Fragment dialog = getActivity().getFragmentManager().findFragmentByTag("Sort Preference Alert");
		assertTrue(((DialogFragment) dialog).getShowsDialog());

	}
}
