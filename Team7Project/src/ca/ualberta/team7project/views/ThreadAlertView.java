/**
 * A procedurally built view for creating and replying to threads.
 */

package ca.ualberta.team7project.views;

import ca.ualberta.team7project.UserPersistence;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;


public class ThreadAlertView extends DialogFragment
{
	/*
	 * This view does not use XML files since much of the content is context
	 * sensitive and depends on the models/controllers state.
	 */
	
	private Context context;
	
	public interface IdentityListener
	{
		// TODO
	}

	IdentityListener listener;
	
	
	/*
	 * Allows activities to pass in context.
	 */
	@Override
	public void onAttach(Activity activity)
	{

		super.onAttach(activity);
		this.context = activity.getApplicationContext();

		try
		{
			listener = (IdentityListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ "must implement NoticeDialogListener");
		}
		
		/* We need to determine which attributes the AlertDialoig will contain */
		
	}
	
	/*
	 * The context sensitive builder for ThreadAlertView
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		final EditText titleInput = new EditText(getActivity());

		/* Set the properties of the user input text box */
		titleInput.setHint(ca.ualberta.team7project.R.string.create_user_hint);
		titleInput.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_NORMAL);

		/* Build the dialog alert */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		return builder.create();
	}
}
