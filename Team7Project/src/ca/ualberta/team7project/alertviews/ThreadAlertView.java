package ca.ualberta.team7project.alertviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.controllers.ThreadListController;
import ca.ualberta.team7project.views.ThreadListView;

/**
 * ThreadAlertView prompts the user to reply to a comment or create a new topic.
 * <p>
 * There exists two conditions in which this dialog is called.
 * <ul>
 * <li>The user is replying to a topic. Click event is called through a
 * ThreadListView item.
 * <li>The user is creating a new topic. Click event is called through an
 * ActionBarIcon.
 * </ul>
 * <p>
 * Some of the layout is defined in the builder, while the remainder is in
 * create_thread.xml All button clicks are handled with the ThreadListener in
 * ThreadListView
 * 
 * @see ThreadListView.java
 * @author raypold
 * 
 */
public class ThreadAlertView extends DialogFragment
{

	/*
	 * Reuse Statements
	 * https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse
	 * -Statements#threadalertview
	 */

	private Boolean replying;
	private Boolean editing;
	private ThreadListController controller;
	private long spinnerId;

	public interface ThreadAlertListener
	{
		public void insertImage();
		public void createThread(String title, String body, long spinnerId);
	}

	ThreadAlertListener listener;

	public ThreadAlertView()
	{

		super();
		Context mainContext = MainActivity.getMainContext();

		this.listener = ((ca.ualberta.team7project.MainActivity) mainContext)
				.getListController().getListView();

		controller = ((ca.ualberta.team7project.MainActivity) mainContext)
				.getListController();
		replying = ((ca.ualberta.team7project.MainActivity) mainContext)
				.getListController().getInTopic();
		editing = ((ca.ualberta.team7project.MainActivity) mainContext)
				.getListController().getEditingTopic();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{

		/*
		 * Create the builder, inflate the layout and set the view to the
		 * appropriate xml file
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflator = getActivity().getLayoutInflater();

		View v = inflator.inflate(
				ca.ualberta.team7project.R.layout.create_thread, null);
		builder.setView(v);

		final EditText titleInput = (EditText) v
				.findViewById(ca.ualberta.team7project.R.id.thread_title);
		final EditText bodyInput = (EditText) v
				.findViewById(ca.ualberta.team7project.R.id.thread_body);
		final Button insertImage = (Button) v
				.findViewById(ca.ualberta.team7project.R.id.thread_image);

		/*
		 * If the user is editing the topic, we need to add the option to keep
		 * the existing location in the spinner.
		 * 
		 * This is why the spinner is not populated with
		 * android:entries="@array/location_preferences_array" in the xml
		 */
		final Spinner spinner = (Spinner) v
				.findViewById(ca.ualberta.team7project.R.id.select_location);
		
		ArrayAdapter<CharSequence> adapter = null;
		
		if(editing)
		{
			adapter = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), 
							ca.ualberta.team7project.R.array.location_preferences_array_editing,
							android.R.layout.simple_spinner_item);
		}
		else
		{
			adapter = ArrayAdapter.createFromResource(this.getActivity().getBaseContext(), 
							ca.ualberta.team7project.R.array.location_preferences_array,
							android.R.layout.simple_spinner_item);
		}
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id)
			{
				spinnerId = parent.getItemIdAtPosition(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent){}	
			
		});

		/* User is replying to a topic */
		if (replying == true && editing == false)
		{
			builder.setMessage(ca.ualberta.team7project.R.string.reply_thread);

			/* Title is optional */
			titleInput
					.setHint(ca.ualberta.team7project.R.string.enter_title_optional);
			titleInput.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_NORMAL);

			// controller.setInTopic(false);

		}
		/* User is creating a new topic */
		else if (replying == false && editing == false)
		{
			builder.setMessage(ca.ualberta.team7project.R.string.create_thread);

			titleInput.setHint(ca.ualberta.team7project.R.string.enter_title);
			titleInput.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_NORMAL);

		}
		/* User is editing an existing thread or topic */
		else if (editing == true)
		{
			builder.setMessage(ca.ualberta.team7project.R.string.edit_thread);

			/* Show existing title */
			titleInput.setText(controller.getOpenThread().getTitle());
			titleInput.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_NORMAL);

			/* Show existing comment body */
			bodyInput.setText(controller.getOpenThread().getComment());
			bodyInput.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_NORMAL);

			// controller.setEditingTopic(false);

		}

		/*
		 * User wishes to insert an image. Show a new prompt with image
		 * selection options
		 */
		insertImage.setOnClickListener(new Button.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				/*
				 * This is going to be a tricky bit of code.
				 * 
				 * We will have to hide this dialog box, open up a new one to
				 * select the image and the reopen this current dialog box after
				 * the user has chosen the image.
				 */
				listener.insertImage();
			}

		});

		/* Exit out of prompt through cancel or confirm buttons */
		builder.setCancelable(true);
		builder.setPositiveButton(ca.ualberta.team7project.R.string.confirm,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{

						String title = titleInput.getText().toString();
						String body = bodyInput.getText().toString();
						
						listener.createThread(title, body, spinnerId);
					}
				});
		builder.setNegativeButton(ca.ualberta.team7project.R.string.cancel,
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
