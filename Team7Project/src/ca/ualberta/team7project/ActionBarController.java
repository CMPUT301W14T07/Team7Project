/**
 * Handles all functions related to the buttons the user clicks on the action bar
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project;

import android.view.MenuItem;

public class ActionBarController
{

    private MenuItem item;

    /**
     * Required constructor.
     * <p>
     * Sets the MenuItem, which is the button that was clicked on the action
     * bar.
     * 
     * @param item The ID associated with the action bar item.
     */
    public ActionBarController(MenuItem item)
    {

        this.item = item;
    }

    /**
     * Takes an item id and calls the associated method to provide button
     * functionality. to the action bar.
     * 
     * @return Boolean representing whether a method has been called associated
     *         with button.
     */
    public boolean getAction()
    {

        switch (this.item.getItemId())
        {
            case R.id.action_home:
                topicsHome();
                return true;
            case R.id.action_geo_location:
                geolocationPreferences();
                return true;
            case R.id.action_user_preferences:
                userPreferences();
                return true;
            case R.id.action_add_favorite:
                addTopicToFavorites();
                return true;
            case R.id.action_create_topic:
                createTopic();
                return true;
            case R.id.action_refresh:
                refreshView();
                return true;
            case R.id.action_sort:
                sortPreferences();
                return true;
        }
        return false;
    }

    /**
     * Handles the user's request to return to main topic listings
     */
    private void topicsHome()
    {

        // TODO
    }

    /**
     * Handles the user's request to set geolocation preferences
     */
    private void geolocationPreferences()
    {

        // TODO
    }

    /**
     * Handles the user's request to set user preferences
     */
    private void userPreferences()
    {

        // TODO
    }

    /**
     * Handles the user's request to add current topic to favorites
     */
    private void addTopicToFavorites()
    {

        // TODO
    }

    /**
     * Handles the user's request to create a new topic
     */
    private void createTopic()
    {

        // TODO
    }

    /**
     * Handles the user's request to refresh the view
     */
    private void refreshView()
    {

        // TODO
    }

    /**
     * Handles the user's request to change sort preferences
     */
    private void sortPreferences()
    {

        // TODO
    }

    /**
     * Provides the caller with which button was clicked on the action bar.
     * 
     * @return item The id button that was clicked on the action bar.
     */
    public MenuItem getItem()
    {

        return item;
    }

}
