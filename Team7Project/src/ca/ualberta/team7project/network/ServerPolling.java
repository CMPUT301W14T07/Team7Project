package ca.ualberta.team7project.network;

import android.os.Handler;
import android.os.Looper;

/**
 * Initiates a server poll at set intervals
 */
public class ServerPolling
{
	/* Reuse statement 
	 * https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#serverpolling 
	 */
	
    private Handler handler;
    private Runnable serverCheck;
    
    private int UPDATE_INTERVAL = 30000;

    public ServerPolling(final Runnable refreshWarn) 
    {
    	handler = new Handler(Looper.getMainLooper());
    	
    	serverCheck = new Runnable() {
            @Override
            public void run() {
            	refreshWarn.run();
                handler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
    }

    /**
     * Start the handler to update every UPDATE_INTERVAL
     */
    public synchronized void startUpdates(){
    	serverCheck.run();
    }

    /**
     * Stop the handler from updating every UPDATE_INTERVAL
     */
    public synchronized void stopUpdates(){
    	handler.removeCallbacks(serverCheck);
    }
}
