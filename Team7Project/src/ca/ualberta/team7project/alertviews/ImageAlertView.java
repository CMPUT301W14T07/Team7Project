package ca.ualberta.team7project.alertviews;

import ca.ualberta.team7project.models.ThreadModel;

import com.google.gson.Gson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


public class ImageAlertView extends DialogFragment
{

	/**
	 * Create a new ImageAlertView with a ThreadModel passed in.
	 * <p>
	 * A JSON object of the class is used instead of using the preferred method of Android parcelable.
	 * This is simply for functionality over elegance, and may be looked at latter should time permit. 
	 * @param thread to be passed to TagAlertView
	 * @return A functioning TagAlertView
	 */
    public static ImageAlertView newInstance(ThreadModel thread) {
    	ImageAlertView imageAlert = new ImageAlertView();

        Bundle bundle = new Bundle();
        bundle.putString("ThreadModel", new Gson().toJson(thread));
        imageAlert.setArguments(bundle);

        return imageAlert;
    }


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		String json = getArguments().getString("ThreadModel");
		final ThreadModel thread = new Gson().fromJson(json, ThreadModel.class);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflator = getActivity().getLayoutInflater();

		View v = inflator.inflate(ca.ualberta.team7project.R.layout.image_view, null);

		ImageView imageView = (ImageView) v.findViewById(ca.ualberta.team7project.R.id.thread_image_view);
		imageView.setImageBitmap(thread.getImage());

		builder.setTitle(thread.getTitle());
		builder.setView(v);

		return builder.create();			
	}

}
