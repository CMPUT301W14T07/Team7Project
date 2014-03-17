/**
 * Contains methods that are needed in all views associated with a UserModel
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project.interfaces;

import ca.ualberta.team7project.models.LocationModel;
import ca.ualberta.team7project.models.PreferenceModel;

/**
 * Listener for the UserView
 */
public interface UserListener
{

	public void updateViews(PreferenceModel user);
	public void toastUser(String userName);
	public void promptIdentityAlertView();
	public void locationUpdated(double longitude, double latitude);
	public void locationModelUpdate(LocationModel location);
	public void toastLocation(LocationModel location);
	public void updateLocationFailure();
	public void invalidEditPermissions();
	
}
