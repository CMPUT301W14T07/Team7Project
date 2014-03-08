/**
 * Creates an alert action dialog prompting the user to select sorting preferences
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.alertviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SortPreferencesAlertView extends DialogFragment
{
	
	/*
	 * TODO. Left for a latter milestone
	 */

	/**
	 * Build the action dialog with a list of sorting preferences.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(
				ca.ualberta.team7project.R.string.select_sorting_preferences)
				.setItems(
						ca.ualberta.team7project.R.array.sorting_preferences_array,
						new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{

								// TODO switch statement for each item in
								// arrays.cml
								// TODO Auto-generated method stub

							}

						});

		return builder.create();
	}
}
