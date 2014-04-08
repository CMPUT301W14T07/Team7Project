package ca.ualberta.team7project.network;

import ca.ualberta.team7project.MainActivity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

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
    ConnectionDetector connection;
    
    //fresh every 20 sec
	private int UPDATE_INTERVAL = 20000;
	private boolean connected = true;

    public ServerPolling(final Runnable refreshWarn) 
    {	
    	connection = new ConnectionDetector(MainActivity.getMainContext());
    	handler = new Handler(Looper.getMainLooper());
    	
    	serverCheck = new Runnable() {
            @Override
            public void run() {
            	
            	if(connection.isConnectingToInternet()){
            	
            		if(connected){
            			//connected all the time
            			refreshWarn.run();
            		}
            		else{
            			//recover the connection
            			connected = true;
            			Toast.makeText(MainActivity.getMainContext(), "connected to the internet", Toast.LENGTH_SHORT).show();            			
            			refreshWarn.run();	
            		}
            	}
            	else{
            		
            		if(connected){
            			//the transition from connected to unconnected 
            			Toast.makeText(MainActivity.getMainContext(), "Lose connection", Toast.LENGTH_SHORT).show();
            		}
            		connected = false;
            	}
            	
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
