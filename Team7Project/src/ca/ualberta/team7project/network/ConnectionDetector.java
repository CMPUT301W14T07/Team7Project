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
public class ConnectionDetector {
	private Context context;
    
    public ConnectionDetector(Context context){
        this.context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
