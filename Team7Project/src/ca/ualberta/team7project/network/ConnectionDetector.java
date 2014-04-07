package ca.ualberta.team7project.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Reuse statement: https://github.com/CMPUT301W14T07/Team7Project/wiki/Reuse-Statements#connection-detector
 * Helper class to check if there is network connection
 * @author wzhong3
 *
 */
public class ConnectionDetector
{
	private ConnectivityManager connectivity;

    public ConnectionDetector(Context context){
        this. connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
 
    public boolean isConnectingToInternet()
    {
          if (connectivity != null) 
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
    
}
