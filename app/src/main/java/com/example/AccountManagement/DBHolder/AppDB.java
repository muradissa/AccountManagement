package com.example.AccountManagement.DBHolder;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.AccountManagement.Transaction;


@Database(entities = {Transaction.class},version = 2)
public  abstract class AppDB extends RoomDatabase {
    private static AppDB instance;

    public static AppDB getInstance(Context context) {
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDB.class,"bank-database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract TransactionDB TransactionDB();
}
