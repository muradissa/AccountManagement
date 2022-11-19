package com.example.AccountManagement.DBHolder;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.AccountManagement.Transaction;

import java.util.List;

@Dao
public interface TransactionDB {

    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAll();

    @Delete
    void delete(Transaction transaction);


    @Insert(onConflict = REPLACE)
    public void insert(Transaction transaction);

    @Query("SELECT * FROM `Transaction` where year=:year1 And day =:day1 And month=:month1  And hour=:hour1 And minute=:minute1")
    List<Transaction> getClosetTask(int day1, int month1 , int year1, int hour1, int minute1);



}
