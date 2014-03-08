/**
 * A procedurally built view for creating and replying to threads.
 */

package ca.ualberta.team7project.alertviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.widget.EditText;
import ca.ualberta.team7project.controllers.ThreadController;


public class ThreadAlertView extends DialogFragment
{
	/*
	 * This view does not use XML files since much of the content is context
	 * sensitive and depends on the models/controllers state.
	 */
	
	private Boolean replying = true;
	
	public interface ThreadAlertListener
	{
		public void createThread(DialogFragment dialog, String title, String comment);
		public void insertImage(DialogFragment dialog);
	}

	ThreadAlertListener listener;
		
	/*
	 * The context sensitive builder for ThreadAlertView
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		/*
		 * NOTES:
		 * Not fully implemented yet. Need to interact with the ThreadView through listeners still
		 * Also need to create a dialog to select images still.
		 * 
		 */
		
		final EditText titleInput = new EditText(getActivity());
		final EditText bodyInput = new EditText(getActivity());
				
		/* We need to determine which attributes the AlertDialoig will contain */
		this.replying = ThreadController.inTopic();

		/* Create the builder, inflate the layout and set the view to the appropriate xml file */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflator = getActivity().getLayoutInflater();
		builder.setView(inflator.inflate(ca.ualberta.team7project.R.layout.create_thread, null));

		/* Set the properties of the user input text box */
		if(replying == true)
		{
			builder.setMessage(ca.ualberta.team7project.R.string.reply_thread);
			
			/* Title is optional */
			titleInput.setHint(ca.ualberta.team7project.R.string.enter_title_optional);
			titleInput.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_NORMAL);
			
			//TextView title = (TextView)getDialog().findViewById(ca.ualberta.team7project.R.id.thread_title);
			//title.setText("stuff");
		}
		else
		{
			builder.setMessage(ca.ualberta.team7project.R.string.create_thread);

			titleInput.setHint(ca.ualberta.team7project.R.string.enter_title);
			titleInput.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_NORMAL);

			//TextView title = (TextView)getDialog().findViewById(ca.ualberta.team7project.R.id.thread_title);
			//title.setText("stuff");
		}

		builder.setCancelable(true);
		builder.setNeutralButton(
				ca.ualberta.team7project.R.string.insert_image,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						// TODO. Interact with the listener/interface
					}
				});
		builder.setPositiveButton(
				ca.ualberta.team7project.R.string.confirm,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						// TODO. Interact with the listener/interface
					}
				});
		builder.setNegativeButton(
				ca.ualberta.team7project.R.string.cancel,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						// TODO

					}
				});		
		return builder.create();
	}
}
