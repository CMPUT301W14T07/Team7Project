/**
 * A procedurally built view for creating and replying to threads.
 */

package ca.ualberta.team7project.alertviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import ca.ualberta.team7project.controllers.ThreadController;


public class ThreadAlertView extends DialogFragment
{
	private Boolean replying = true;
	
	public interface ThreadAlertListener
	{
		public void createThread(String title, String comment);
		public void insertImage();
	}

	ThreadAlertListener listener;
		
	public ThreadAlertView(ThreadAlertListener listener)
	{
		super();
		this.listener = listener;
	}

	/*
	 * The context sensitive builder for ThreadAlertView
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		/*
		 * NOTES:
		 * Not fully implemented yet. Need to interact with the ThreadView through listeners still
		 * Also need to create a dialog to select images still
		 * (extra dialog for images not needed)
		 * 
		 */

		/* We need to determine which attributes the AlertDialoig will contain */
		this.replying = ThreadController.inTopic();

		/* Create the builder, inflate the layout and set the view to the appropriate xml file */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflator = getActivity().getLayoutInflater();
		View v = inflator.inflate(ca.ualberta.team7project.R.layout.create_thread, null);
		builder.setView(v);
		
		final EditText titleInput = (EditText) v.findViewById(ca.ualberta.team7project.R.id.thread_title);
		final EditText bodyInput = (EditText) v.findViewById(ca.ualberta.team7project.R.id.thread_body);

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
				/* This will be moved to xml layout when I find a nicer way of making the button */
				ca.ualberta.team7project.R.string.insert_image,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						// TODO. Interact with the listener/interface
						
						//TODO: make this not dismiss
					}
				});
		builder.setPositiveButton(
				ca.ualberta.team7project.R.string.confirm,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						String title = titleInput.getText().toString();
						String body = bodyInput.getText().toString();
						listener.createThread(title, body);
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
