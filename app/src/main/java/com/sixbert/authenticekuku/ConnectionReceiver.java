package com.sixbert.authenticekuku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.Objects;

public class ConnectionReceiver extends BroadcastReceiver {
public static  ReceiverListener Listener;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)){
            if(NetworksUtils.isNetworkAvailable(context)){
                //Toast.makeText(context, "Mtanadao upo", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(context, "Hakuna mtandao", Toast.LENGTH_SHORT).show();
            }
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(Listener !=null){
            boolean isConnected =networkInfo !=null && networkInfo.isConnectedOrConnecting();
            Listener.onNetworkChange(isConnected);
        }
    }

    public interface ReceiverListener{
        void onNetworkChange(boolean isConnected);
    }
}
