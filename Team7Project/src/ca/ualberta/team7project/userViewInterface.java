/**
 * Contains methods that are needed in all views associated with a UserModel
 * 
 * @author Michael Raypold
 */

package ca.ualberta.team7project;

import ca.ualberta.team7project.models.UserModel;


public interface userViewInterface
{

	public void UpdateUser(UserModel user);
	public void ToastUser();
	public void PromptIdentityAlertView();
}
