package ca.ualberta.team7project.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * reference http://www.androidhive.info/2012/07/android-detect-internet-connection-status/
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
