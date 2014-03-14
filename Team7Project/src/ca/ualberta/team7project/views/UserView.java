package ca.ualberta.team7project.views;

import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.interfaces.UserListener;
import ca.ualberta.team7project.models.PreferenceModel;


public class UserView implements UserListener
{

	private Context context;
	private FragmentManager fragment;
	
	public UserView(Context context, FragmentManager fragment)
	{
		super();
		this.context = context;
		this.fragment = fragment;
	}

	@Override
	public void updateViews(PreferenceModel user)
	{
		toastUser(user.getUser().getName());
	}

	@Override
	public void toastUser(String userName)
	{
		Toast.makeText(this.context,
				"Logged in as " + userName, Toast.LENGTH_SHORT).show();			
	}

	@Override
	public void promptIdentityAlertView()
	{
		CreateIdentityAlertView userAlert = new CreateIdentityAlertView();
		userAlert.setCancelable(false);
		userAlert.show(this.fragment, "New User Name Alert");			
	}

	@Override
	public void locationUpdated(double longitude, double latitude)
	{
		Log.e("debug", "request to update user coordinates");
		UserController.updateLocationModel(longitude, latitude);
	}

	@Override
	public void toastLocation(Location location)
	{

		/* A variety of things could go wrong when the user selects a location.
		 * After location has been selected. A prompt should be shown displaying the
		 * results
		 */
		
	}

}
