package ca.ualberta.team7project.alertviews;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import ca.ualberta.team7project.OpenThreadInstance;
import ca.ualberta.team7project.models.ThreadModel;
import ca.ualberta.team7project.models.ThreadTagModel;

import com.google.gson.Gson;

/**
 * Displays a selectable listview of ThreadTagModels for appending to a ThreadModel.
 * @author michael
 *
 */
public class TagDeleteAlertView extends DialogFragment
{
	/**
	 * Create a new TagAlertView with a ThreadModel passed in.
	 * <p>
	 * A JSON object of the class is used instead of using the preferred method of Android parcelable.
	 * This is simply for functionality over elegance, and may be looked at latter should time permit. 
	 * @param thread to be passed to TagAlertView
	 * @return A functioning TagAlertView
	 */
    public static TagDeleteAlertView newInstance(ThreadModel thread) {
    	TagDeleteAlertView tagAlert = new TagDeleteAlertView();

        Bundle bundle = new Bundle();
        bundle.putString("ThreadModel", new Gson().toJson(thread));
        tagAlert.setArguments(bundle);

        return tagAlert;
    }

	private String[] getList(ThreadTagModel tags)
	{
	    ArrayList<String> tagsList = new ArrayList<String>(tags.getTags());
		return tagsList.toArray(new String[tagsList.size()]);
	}
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{		
		String json = getArguments().getString("ThreadModel");
		final ThreadModel thread = new Gson().fromJson(json, ThreadModel.class);
		final ThreadTagModel threadTags = thread.getTags();
		
		final String[] tags = getList(threadTags);
		final ArrayList<String> toDelete = new ArrayList<String>();
				
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(ca.ualberta.team7project.R.string.append_tag);
		builder.setMultiChoiceItems(tags, null,  new DialogInterface.OnMultiChoiceClickListener(){

			/* Save which tags to delete, but do not delete them immediately incase the user changes their mind */
			@Override
			public void onClick(DialogInterface dialog, int which,
					boolean isChecked)
			{
				if(isChecked == true)
					toDelete.add(tags[which]);
				if(isChecked == false)
					toDelete.remove(tags[which]);
			}
			
		});
		builder.setPositiveButton(ca.ualberta.team7project.R.string.confirm,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						OpenThreadInstance openedThread = OpenThreadInstance.getInstance();
						ThreadModel threadModel = openedThread.getThread();
						
						ThreadTagModel threadTags = threadModel.getTags();

						for(String tag : toDelete)
							threadTags.removeTag(tag);

						threadModel.setTags(threadTags);
					}
				});

		builder.setNegativeButton(ca.ualberta.team7project.R.string.cancel,
				new DialogInterface.OnClickListener()
				{

					public void onClick(DialogInterface dialog, int id)
					{
						// Do nothing on cancel
					}
				});
			
		return builder.create();
	}
}
