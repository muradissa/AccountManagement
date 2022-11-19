package com.example.AccountManagement;

import static java.lang.System.exit;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.example.AccountManagement.BrodcastHolder.BroadcastReceiverListner;
import com.example.AccountManagement.serviceHolder.ForegroundService;


public class MainActivity extends AppCompatActivity implements TransactionListFrag.TransactionListFragListener {
    private static MainActivity main;
    BroadcastReceiver br = new BroadcastReceiverListner();
    IntentFilter filter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main= (MainActivity) this;
    /************************************************ define service **********************************************************/

        String[] permissions = { Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS, Manifest.permission.ACCESS_NETWORK_STATE };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,permissions,1);
            }
        } else {
        }
        //filter.addAction(Intent.CONNECTIVITY_CHANGE);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(br, filter);
       /**        old       **/
        if(!checkServiceRunning(ForegroundService.class)){

            Intent serviceIntent = new Intent(this, ForegroundService.class);
            startForegroundService(serviceIntent);
        }
    }

    private boolean checkServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

/************************************************ define menu **********************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settingBotton:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content ,new MyPreferences()).addToBackStack(null)
                        .commit();
                break;
            case R.id.exitBotton:
                exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    public static MainActivity getactiv() {
        return main;
    }


    /************************************************ define Preferences  **********************************************************/

    public static class MyPreferences extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.mypreferencescreen, rootKey);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            view.setBackgroundColor(Color.WHITE);
            super.onViewCreated(view, savedInstanceState);
        }
    }

    /************************************************ talking with frag **********************************************************/

    @Override
    public void DoInEditEvent(Transaction transaction) {
        Edittransactionfrag edittransactionfrag = new Edittransactionfrag();
        edittransactionfrag.EditSelectedTransaction(transaction);
        MainActivity.getactiv().getSupportFragmentManager().beginTransaction().
                add(R.id.fragContainer, edittransactionfrag).//add on top of the static fragment
                addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                commit();
        MainActivity.getactiv().getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void DoInViewEvent(Transaction transaction) {
        itemViewrFrag viewerFrag = new itemViewrFrag();
        viewerFrag.EditSelectedTask(transaction);
        MainActivity.getactiv().getSupportFragmentManager().beginTransaction().
                add(R.id.fragContainer, viewerFrag).//add on top of the static fragment
                addToBackStack("BBB").//cause the back button scrolling through the loaded fragments
                commit();
        MainActivity.getactiv().getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        filter.addAction("com.TaskRemainder.CUSTOM_INTENT");
        registerReceiver(br, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(br);
    }

}