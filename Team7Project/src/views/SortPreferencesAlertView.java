/**
 * Creates an alert action dialog prompting the user to select sorting preferences
 * 
 * @author Michael Raypold
 */
package views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class SortPreferencesAlertView extends DialogFragment
{
	/**
	 * Build the action dialog with a list of sorting preferences.
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(ca.ualberta.team7project.R.string.create_identity)
	        .setPositiveButton(ca.ualberta.team7project.R.string.create_user_confirm, new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int id) {
	            	// TODO
	            }
	        })
	        .setNegativeButton(ca.ualberta.team7project.R.string.cancel, new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int id) {
	                // TODO
	            }
	        });
		
		return builder.create();
	}
}
