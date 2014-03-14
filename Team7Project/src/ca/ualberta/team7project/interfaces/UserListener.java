/**
 * Contains methods that are needed in all views associated with a UserModel
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project.interfaces;

import android.location.Location;
import ca.ualberta.team7project.models.PreferenceModel;

public interface UserListener
{

	public void updateViews(PreferenceModel user);
	public void toastUser(String userName);
	public void promptIdentityAlertView();
	public void locationUpdated(double longitude, double latitude);
	public void toastLocation(Location location);
}
