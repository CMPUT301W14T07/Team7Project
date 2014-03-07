/**
 * Contains methods that are needed in all views associated with a UserModel
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project;

import ca.ualberta.team7project.models.UserModel;


public interface UserViewInterface
{

	public void updateViews(UserModel user);
	public void toastUser();
	public void promptIdentityAlertView();
}
