package ca.ualberta.team7project.alertviews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.OpenThreadInstance;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.ThreadTagModel;

import com.google.gson.Gson;

/**
 * Displays a text view for the user to insert new thread tags
 * @author michael
 *
 */
public class TagInsertAlertView extends DialogFragment
{
	/**
	 * Create a new TagAlertView with a ThreadModel passed in.
	 * <p>
	 * A JSON object of the class is used instead of using the preferred method of Android parcelable.
	 * This is simply for functionality over elegance, and may be looked at latter should time permit. 
	 * @param thread to be passed to TagAlertView
	 * @return A functioning TagAlertView
	 */
    public static TagInsertAlertView newInstance(ThreadModel thread) {
    	TagInsertAlertView tagAlert = new TagInsertAlertView();

        Bundle bundle = new Bundle();
        bundle.putString("ThreadModel", new Gson().toJson(thread));
        tagAlert.setArguments(bundle);

        return tagAlert;
    }
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{		
		String json = getArguments().getString("ThreadModel");
		final ThreadModel thread = new Gson().fromJson(json, ThreadModel.class);
		
		OpenThreadInstance openThread = OpenThreadInstance.getInstance();
		openThread.setThread(thread);		
				
		LayoutInflater inflator = getActivity().getLayoutInflater();
		final View view = inflator.inflate(ca.ualberta.team7project.R.layout.tag_select, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(ca.ualberta.team7project.R.string.append_tag);
		builder.setView(view);
		
		Button deleteTags = (Button) view.findViewById(ca.ualberta.team7project.R.id.button_delete_tag);
		deleteTags.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v)
			{

				TagDeleteAlertView tagAlert = TagDeleteAlertView.newInstance(thread);
				tagAlert.setCancelable(false);
				tagAlert.show(((ca.ualberta.team7project.MainActivity)MainActivity.getMainContext())
						.getFragmentManager(), "New Tag Alert");
				
			}
			
		});
		
		builder.setPositiveButton(ca.ualberta.team7project.R.string.confirm,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						EditText tags = (EditText) view.findViewById(ca.ualberta.team7project.R.id.text_add_tag);
						String strTags = tags.getText().toString();
						
						OpenThreadInstance openedThread = OpenThreadInstance.getInstance();
						ThreadModel threadModel = openedThread.getThread();
						
						ThreadTagModel threadTags = threadModel.getTags();
						threadTags.parseAndAppend(strTags);
						threadModel.setTags(threadTags);

						MainActivity.getListController().InsertThread(threadModel);
					}
				});

		builder.setNegativeButton(ca.ualberta.team7project.R.string.cancel,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						OpenThreadInstance openedThread = OpenThreadInstance.getInstance();
						ThreadModel threadModel = openedThread.getThread();
						MainActivity.getListController().InsertThread(threadModel);
					}
				});
			
		return builder.create();
	}
}
