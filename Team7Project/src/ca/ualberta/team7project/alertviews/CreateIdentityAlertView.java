/**
 * Creates an alert action dialog prompting the user to enter a new user name.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.alertviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import ca.ualberta.team7project.models.UserPersistenceModel;

public class CreateIdentityAlertView extends DialogFragment
{

	private Context context;

	public interface IdentityListener
	{
		public void onIdentityPositiveCLick(String userName);
	}

	IdentityListener listener;
	
	/**
	 * Allows activities to pass in context needed for UserPersistence
	 * <p>
	 * Referenced http://developer.android.com/guide/topics/ui/dialogs.html
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
	}

	/**
	 * Build the action dialog with text entry and confirmation buttons
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		final EditText textInput = new EditText(getActivity());

		/* Set the properties of the user input text box */
		textInput.setHint(ca.ualberta.team7project.R.string.create_user_hint);
		textInput.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_NORMAL);

		/* Build the dialog alert */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(ca.ualberta.team7project.R.string.create_identity)
				.setCancelable(false)
				.setView(textInput)
				.setPositiveButton(
						ca.ualberta.team7project.R.string.create_user_confirm,
						new DialogInterface.OnClickListener()
						{

							public void onClick(DialogInterface dialog, int id)
							{

								listener.onIdentityPositiveCLick(textInput
												.getText().toString());

							}
						});

		/*
		 * Only show a cancel button if there is a user already
		 * existing on the phone.
		 */
		UserPersistenceModel persistence = new UserPersistenceModel(context);
		
		if (persistence.getLastOpenUser() != null)
		{
			builder.setNegativeButton(ca.ualberta.team7project.R.string.cancel,
					new DialogInterface.OnClickListener()
					{

						public void onClick(DialogInterface dialog, int id)
						{

							// Do nothing on cancel
						}
					});
		}
		return builder.create();
	}
}
