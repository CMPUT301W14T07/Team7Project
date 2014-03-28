/**
 * Creates an alert action dialog prompting the user to select sorting preferences
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.alertviews;

import ca.ualberta.team7project.MainActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * User selects how to sort threads
 */
public class SortPreferencesAlertView extends DialogFragment
{

	/* Reuse statement # 1 https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#sortpreferencesalertview */
	
	public static enum SortPreference
	{
		BY_DATE, FILTER_PICTURE, BY_LOCATION
	}
	
	public interface SortPreferencesAlertListener
	{
		public void setSortPreferences(SortPreference newPreference);
	}
	
	MainActivity mainActivity;
	
	SortPreferencesAlertListener listener;

	public SortPreferencesAlertView()
	{
		super();
		
		mainActivity = (ca.ualberta.team7project.MainActivity)MainActivity.getMainContext();
		
		listener = mainActivity.getListController();
	}

	/**
	 * Build the action dialog with a list of sorting preferences.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(
				ca.ualberta.team7project.R.string.select_sorting_preferences)
				.setItems(ca.ualberta.team7project.R.array.sorting_preferences_array,
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								// Determine which sorting method was clicked
								// This isn't very readable or robust; should look into alternative
								// For instance which is index position....You have to dig into arrays.xml to find that out.
								switch(which)
								{
									case 0:
										// Sort threads by date
										Log.e(MainActivity.DEBUG, "sort by date");
										listener.setSortPreferences(SortPreference.BY_DATE);
										
										break;
									case 1:
										// Sort threads by picture										
										Log.e(MainActivity.DEBUG, "sort by picture");
										listener.setSortPreferences(SortPreference.FILTER_PICTURE);
										
										break;
									case 2:
										// Sort threads by proximity
										Log.e(MainActivity.DEBUG, "sort by rating");
										listener.setSortPreferences(SortPreference.BY_LOCATION);
										
										break;
								}
							}

						})
				.setNegativeButton( ca.ualberta.team7project.R.string.cancel,
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int id)
						{
							// Nothing needs to happen if user selects cancel.
							// Just for show.
						}
					});	
		return builder.create();
	}

}
