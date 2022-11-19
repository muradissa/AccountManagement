package com.example.AccountManagement.BrodcastHolder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.example.AccountManagement.ItemsRecycleAdapter;


public class BroadcastReceiverListner extends BroadcastReceiver {

    private static final String TAG = BroadcastReceiverListner.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

//        if (intent.getAction().equals("com.TaskRemainder.CUSTOM_INTENT")) {
//            ItemsRecycleAdapter.listner.UpdateTaskList();
//            Log.d( "Intent"," Detected.");
//        }
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
                );
                if (noConnectivity) {
                    Toast.makeText(context, "Network is off", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Network is on", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

}
