package ca.ualberta.team7project.models;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Responsible for handling the application states.
 * @author michael
 *
 */
public class ApplicationStateModel {

	private Context context;
	
	public ApplicationStateModel(Context context)
	{
		this.context = context;
	}
	
	/**
	 * A simple check to determine if the application has ever been run on this phone.
	 * <p>
	 * This is necessary to set the user if this is the first time the application 
	 * has launched.
	 * @return Boolean True if application has run before.
	 */
	public boolean firstRun()
	{
		SharedPreferences persistence = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
		return persistence.getBoolean("firstRun", true);
	}

	/**
	 * Create a setting to represent that the application has now run for the
	 * first time.
	 */
	public void setFirstRun()
	{
		
		SharedPreferences persistence = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = persistence.edit();

		editor.putBoolean("firstRun", false);
		editor.commit();
	}

}
