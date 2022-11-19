package com.example.AccountManagement;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"title","description","day","month","year","hour","minute"})
public class Transaction {

    @NonNull
    @ColumnInfo(name = "title")
    String title;

    @NonNull
    @ColumnInfo(name = "description")
    String description;
    
    @ColumnInfo(name = "year")
    int  year;

    @ColumnInfo(name = "month")
    int  month;

    @ColumnInfo(name = "day")
    int  day;

    @ColumnInfo(name = "hour")
    int hour;

    @ColumnInfo(name = "minute")
    int minute;

    public Transaction(String title, String description, int day, int month, int year, int hour, int minute) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount='" + title + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                '}';
    }
}
