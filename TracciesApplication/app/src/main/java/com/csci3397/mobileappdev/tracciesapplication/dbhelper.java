package com.csci3397.mobileappdev.tracciesapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class dbhelper extends SQLiteOpenHelper {
    Context context;
    public dbhelper(@Nullable Context context) {
        super(context, "userData.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Users(username TEXT primary key unique, password TEXT)");
        db.execSQL("create Table JobList(ListID INTEGER primary key AUTOINCREMENT, username TEXT, ListName TEXT)");
        db.execSQL("create Table Job(JobID INTEGER primary key AUTOINCREMENT, JobListID INTEGER REFERENCES JobList(ListID), " +
                "JobName TEXT, UserProgress TEXT, ApplyLink TEXT, LogoImage TEXT, CompanyName TEXT, Location TEXT, Description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists Users");
        db.execSQL("drop Table if exists JobList");
        db.execSQL("drop Table if exists Job");
    }

    public Boolean createList (String username, String ListName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("ListID", 1);
        contentValues.put("username", username);
        contentValues.put("ListName", ListName);

        long result = db.insert("JobList", null, contentValues);
        /* Database insert results*/

        Toast.makeText(context, "List Successfully Created", Toast.LENGTH_SHORT).show();
        if(result == -1) {
            Toast.makeText(context, "Could not add list to database", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;

    }

    public Boolean createJob (String JobListID, String JobName, String UserProgress, String ApplyLink, String LogoImage ,
                              String CompanyName , String Location, String Description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("JobListID", JobListID);
        contentValues.put("JobName", JobName);
        contentValues.put("UserProgress", UserProgress);
        contentValues.put("ApplyLink", ApplyLink);
        contentValues.put("LogoImage", LogoImage);
        contentValues.put("CompanyName", CompanyName);
        contentValues.put("Location", Location);
        contentValues.put("Description", Description);

        long result = db.insert("Job", null, contentValues);

        /* Database insert results*/
        Toast.makeText(context, "Job Successfully Added", Toast.LENGTH_SHORT).show();
        if(result == -1) {
            Toast.makeText(context, "Could Not Add Job to List", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;

    }

    public Cursor getJobListID(String username, String ListName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT ListID FROM JobList WHERE TRIM(JobList.username) = '"+username.trim()+"' and TRIM(JobList.ListName)= '"+ListName.trim()+"'", null);
        cursor.moveToFirst();
        return cursor;
    }


    public Boolean createUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = db.insert("Users", null, contentValues);
        /* Database insert results*/
        Toast.makeText(context, "User Successfully Created", Toast.LENGTH_SHORT).show();
        if(result == -1) {
            Toast.makeText(context, "Could Not Create User", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Users", null);
        return cursor;
    }

    public Cursor getListNames(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select ListName from JobList WHERE TRIM(JobList.username)= '"+username+"'", null);
        return cursor;
    }

    public Cursor getJobTitles(String ListID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT JobName FROM Job WHERE TRIM(Job.JobListID) = '"+ListID.trim()+"'", null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getJobListData(String ListID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT JobName, CompanyName, Location FROM Job WHERE TRIM(Job.JobListID) = '"+ListID.trim()+"'", null);
        return cursor;
    }

    public Cursor getJobFromList(String ListID, String JobName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT JobName, CompanyName, Location, ApplyLink, Description FROM Job WHERE TRIM(Job.JobListID) = '"+ListID.trim()+"' and TRIM(Job.JobName) = '"+JobName.trim()+"'", null);
        cursor.moveToFirst();
        return cursor;
    }

}
