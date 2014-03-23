package ca.ualberta.team7project.alertviews;

import ca.ualberta.team7project.MainActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Displays and alert dialog to the screen prompting the user to enter an address.
 * <p>
 * The address is used to lookup a geolocation and set the location attribute in a UserModel
 * @see UserModel.java
 */
public class LocationLookupAlertView extends DialogFragment
{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflator = getActivity().getLayoutInflater();
		final View v = inflator.inflate(ca.ualberta.team7project.R.layout.location, null);
		builder.setView(v);

		builder.setMessage(ca.ualberta.team7project.R.string.set_location);
		
		builder.setPositiveButton(
				ca.ualberta.team7project.R.string.confirm,
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						EditText address = (EditText) v.findViewById(ca.ualberta.team7project.R.id.editAddress);
						MainActivity.positionListener.addressLookup(address.getText().toString());
					}
				});
		
		// Only close dialog in event of a cancel click
		builder.setNegativeButton(
				ca.ualberta.team7project.R.string.cancel,
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id){ }
				});
		
		return builder.create();
	}
	
	
}
