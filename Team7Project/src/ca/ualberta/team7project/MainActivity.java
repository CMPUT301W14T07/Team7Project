/**
 * The main view associated with the application.
 * <p>
 * Contains a list of all topics or threads depending on user selections.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity
{
	
    /**
     * Creates the state of the application when the activity is initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.show();
    }

    // TODO Need an onResume()

    /**
     * Places all items for the action bar in the application menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles button clicks in the action bar
     * <p>
     * When a user clicks a button, actionBarController is called with the item,
     * where the request is handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        ActionBarController actionBarController = new ActionBarController(item);
        return actionBarController.getAction();
    }

}
