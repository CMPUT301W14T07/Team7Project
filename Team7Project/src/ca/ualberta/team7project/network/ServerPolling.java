package ca.ualberta.team7project.network;

import android.os.Handler;
import android.os.Looper;


public class ServerPolling
{
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
     * Starts the periodical update routine (mStatusChecker 
     * adds the callback to the handler).
     */
    public synchronized void startUpdates(){
    	serverCheck.run();
    }

    /**
     * Stops the periodical update routine from running,
     * by removing the callback.
     */
    public synchronized void stopUpdates(){
    	handler.removeCallbacks(serverCheck);
    }
}
