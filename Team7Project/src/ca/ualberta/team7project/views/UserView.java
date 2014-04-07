package ca.ualberta.team7project.views;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import ca.ualberta.team7project.MainActivity;
import ca.ualberta.team7project.alertviews.CreateIdentityAlertView;
import ca.ualberta.team7project.controllers.UserController;
import ca.ualberta.team7project.interfaces.UserListener;
import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;

/**
 * Creates toasts and manages alert views related to user and preferences
 */
public class UserView extends Activity implements UserListener
{

	private Context context;
	private FragmentManager fragment;
	
	public UserView(Context context, FragmentManager fragment)
	{
		super();
		this.context = context;
		this.fragment = fragment;
		
		MainActivity.userListener = this;
	}

	@Override
	public void onCreate(Bundle savedState)
	{
		super.onCreate(savedState);
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



	/* Only called when address location has been changed...Not GPS location */
	@Override
	public void toastLocation(LocationModel location)
	{
		Toast.makeText(this.context, ca.ualberta.team7project.R.string.location_update, 
				Toast.LENGTH_SHORT).show();					
	}

	@Override
	public void updateLocationFailure()
	{
		Toast.makeText(this.context, ca.ualberta.team7project.R.string.location_fail, 
				Toast.LENGTH_SHORT).show();					
	}

	@Override
	public void locationModelUpdate(LocationModel location)
	{
		UserController.updateLocationModel(location);
	}

	@Override
	public void invalidEditPermissions()
	{
		Toast.makeText(this.context, ca.ualberta.team7project.R.string.no_edit_permission, 
				Toast.LENGTH_SHORT).show();					
	}

	@Override
	public void toastAddress(String address)
	{
		String message = context.getString(ca.ualberta.team7project.R.string.set_address_toast);
		message = message + " " + address;
		
		Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();					
	}
	
	@Override
	public void editToast()
	{
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_edit, 
				Toast.LENGTH_SHORT).show();

	}
	
	@Override
	public void replyingToast()
	{
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_reply, 
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void newTopicToast()
	{
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_create, 
				Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void favoriteToast()
	{
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_favorite, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void cacheToast()
	{
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_cache, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void postFailToast() {
		
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_network_fail_push, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void pullNewToast() {
		// TODO Auto-generated method stub
		Toast.makeText(context, ca.ualberta.team7project.R.string.toast_pull_new, Toast.LENGTH_SHORT).show();

	}

}
