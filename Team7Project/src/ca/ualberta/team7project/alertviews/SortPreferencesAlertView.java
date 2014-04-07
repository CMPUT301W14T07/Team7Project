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
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Allows the user to select how to sort/view threads
 */
public class SortPreferencesAlertView extends DialogFragment
{

	/* Reuse statement # 1 https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#sortpreferencesalertview */
	
	public static enum SortPreference
	{
		BY_DATE, FILTER_PICTURE, BY_LOCATION, UNFILTER_PICTURE, GLOBALLY, BY_PARENTS
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
		
		listener = MainActivity.getListController().getNavigation();
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
								switch(which)
								{
									case 0:
										listener.setSortPreferences(SortPreference.BY_DATE);
										break;
									case 1:
										listener.setSortPreferences(SortPreference.BY_LOCATION);
										break;								
										
									case 2:	
										listener.setSortPreferences(SortPreference.FILTER_PICTURE);
										break;
									
									case 3:
										listener.setSortPreferences(SortPreference.UNFILTER_PICTURE);									
										break;
										
									case 4:
										listener.setSortPreferences(SortPreference.BY_PARENTS);									
										break;
										
									case 5:
										listener.setSortPreferences(SortPreference.GLOBALLY);
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
						}
					});	
		return builder.create();
	}

}
