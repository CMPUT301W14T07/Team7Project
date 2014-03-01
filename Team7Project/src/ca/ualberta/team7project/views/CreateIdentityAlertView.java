/**
 * Creates an alert action dialog prompting the user to enter a new user name.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import ca.ualberta.team7project.UserPersistence;
import ca.ualberta.team7project.models.UserModel;


public class CreateIdentityAlertView extends DialogFragment
{
	    
	/**
	 * Build the action dialog with text entry and confirmation buttons
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		final EditText textInput = new EditText(getActivity()); 

		/* Set the properties of the user input text box */
		textInput.setHint(ca.ualberta.team7project.R.string.create_user_hint);
		textInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
	
		/* Build the dialog alert */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(ca.ualberta.team7project.R.string.create_identity)
        	.setView(textInput)
	        .setPositiveButton(ca.ualberta.team7project.R.string.create_user_confirm, new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int id) {
	            	/* Note: I think ideally we create an interface to listen for onClicks and pass this data
	            	 * back to the caller instead serializing here.
	            	 * Discuss in next meeting 
	            	 */
	            	
	            	/* Save the new user to the filesystem */
	            	UserModel user = new UserModel(textInput.getText().toString());
	        		UserPersistence persistence = new UserPersistence();
	        		persistence.serializeUser(user);
	        		
	            }
	        })
	        .setNegativeButton(ca.ualberta.team7project.R.string.cancel, new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int id) {
	            	// Do nothing, just close dialog box
	            }
	        });
		
		return builder.create();
	}
}
