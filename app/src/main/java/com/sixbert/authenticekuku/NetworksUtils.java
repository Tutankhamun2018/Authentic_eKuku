package com.sixbert.authenticekuku;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworksUtils {
    public static boolean isNetworkAvailable (Context context){
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnectedOrConnecting();

    }

}
