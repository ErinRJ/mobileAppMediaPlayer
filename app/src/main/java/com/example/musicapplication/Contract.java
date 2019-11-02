package com.example.musicapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

public final class Contract extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "music";
    public static final String COLUMN_NAME = "track_name";
    public static final String COLUMN_URL = "track_url";
    private static final String TAG = "database";


    public Contract(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME
                + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT,"
                + COLUMN_URL + " TEXT)";
        db.execSQL(createTable);
        Log.d(TAG, "'music' database created in onCreate() function (in Contract.java)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name, String url) {
        Log.d(TAG, "Reached the addData() function in the Contract.java class");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_URL, url);

        Log.d(TAG, "Adding new item to the database");
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //get all rows in the database
    public Cursor getData(){
        //read from the database
        SQLiteDatabase db = this.getReadableDatabase();
        //query to select all rows
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //find the url for the track, given the track name
    public String getTrack(String name){
        //assuming no duplicate names are in the database
        Log.d(TAG, "reached the getTrack() method (Contract.java)");
        SQLiteDatabase db = this.getReadableDatabase();
        //query the database for the url
        String query = "SELECT " + COLUMN_URL + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{name});
        //put the url in a string and return it
        int url_index = cursor.getColumnIndex(COLUMN_URL);
        cursor.moveToFirst();
        String url = cursor.getString(url_index);
        Log.d(TAG, "found the url: " + url);
        return url;
    }

    //delete the row from the database
    public boolean delete(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[] {name});
        Log.d(TAG, name + " has been deleted from the database");
        //ensure the track has been properly removed
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
