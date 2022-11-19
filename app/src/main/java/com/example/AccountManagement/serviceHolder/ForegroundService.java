package com.example.AccountManagement.serviceHolder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;


import com.example.AccountManagement.DBHolder.AppDB;
import com.example.AccountManagement.R;
import com.example.AccountManagement.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ForegroundService extends Service {
    String CHANNEL_ID = "CHANNEL_SAMPLE";
    AppDB DataBase;
    Notification.Builder NotifyBuilder;
    NotificationManager notificationManager;
    ZonedDateTime time;
    LocalDate lt;
    Intent Brodcastintent = new Intent();
    @Override
    public void onCreate() {
        super.onCreate();
        CreateNotificationChannel();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //send notefication to the user
        startForeground(1,sendNotification("No Reminders"));
        //get the sp value of saveBox
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

       new Thread(new Runnable() {
           @Override
           public void run() {

               while (true) {
                   boolean save_removed_tasks = app_preferences.getBoolean("SaveBox",true);

                   /*********/
                   lt = LocalDate.now();
                   time = ZonedDateTime.now();
                   ZonedDateTime IsraelDateTime = time.withZoneSameInstant(ZoneId.of("Asia/Jerusalem"));
                   Log.d("time now",IsraelDateTime.getHour() + ":" + IsraelDateTime.getMinute());
                   /*********/

                   DataBase = AppDB.getInstance(getApplicationContext());
                   //getting all task that their time is now !
                   List<Transaction> colsestTransaction = DataBase.TransactionDB().getClosetTask(lt.getDayOfMonth(),lt.getMonthValue(),lt.getYear(),IsraelDateTime.getHour(),IsraelDateTime.getMinute());
                   for(Transaction temp : colsestTransaction) {
                       sendNotification(temp.getTitle());
                       //check if the user want to delete removed task , according to that delete it or not
                       if(save_removed_tasks) { // save task
//                           temp.setStatus();
                           DataBase.TransactionDB().insert(temp);
                           Brodcastintent.setAction("com.TaskRemainder.CUSTOM_INTENT");
                           sendBroadcast(Brodcastintent);
                       } else{ //delete task
                           DataBase.TransactionDB().delete(temp);
                           Brodcastintent.setAction("com.TaskRemainder.CUSTOM_INTENT");
                           sendBroadcast(Brodcastintent);
                       }
                   }

                   // the service always check if there is any task that their date is already pass
                   List<Transaction> allTransaction = DataBase.TransactionDB().getAll();
                   for(Transaction temp : allTransaction) {
                       LocalDate myDate2 = LocalDate.of(temp.getYear(), temp.getMonth(), temp.getDay());
                       int compareValue = lt.compareTo(myDate2);
//                       if(temp.getStatus().equals("yet") && compareValue>0) {
//                           temp.setStatus();
//                           DataBase.TransactionDB().insert(temp);
//                           Brodcastintent.setAction("com.TaskRemainder.CUSTOM_INTENT");
//                           sendBroadcast(Brodcastintent);
//                       }
                   }
               }
           }
       }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    public void CreateNotificationChannel(){
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

    }

    public Notification sendNotification(String title) {
        NotifyBuilder = new Notification.Builder(this,CHANNEL_ID)
                .setAutoCancel(true)
                .setContentText("Test service")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Don't Forget " + title);

        Notification notification = NotifyBuilder.build();
        notificationManager.notify(0, notification);
        return notification;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
