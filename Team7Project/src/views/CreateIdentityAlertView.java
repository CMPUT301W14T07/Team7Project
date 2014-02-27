/**
 * Creates an alert action dialog promting the user to enter a new user name.
 * 
 * @author Michael Raypold
 */
package views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class CreateIdentityAlertView extends DialogFragment
{

	/**
	 * Build the action dialog with text entry and confirmation buttons
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
