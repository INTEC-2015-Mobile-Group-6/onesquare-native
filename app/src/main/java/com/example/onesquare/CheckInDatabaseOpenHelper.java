package com.example.onesquare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onesquare.model.CheckInContract.CheckInEntry;

/**
 * Created by francis on 04/05/15.
 */
public class CheckInDatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_CHECK_IN_TABLE =
            "CREATE TABLE " + CheckInEntry.TABLE_NAME +
            "(" +
                CheckInEntry._ID + " INTEGER PRIMARY KEY, " +
                CheckInEntry.DATE + " DATE NOT NULL, " +
                CheckInEntry.PLACE + " TEXT NOT NULL, " +
                CheckInEntry.ADDRESS + " TEXT NOT NULL, " +
                CheckInEntry.URL + " TEXT NOT NULL, " +
                CheckInEntry.PICTURE_URL + " TEXT NOT NULL, " +
                CheckInEntry.IS_FAVORITE + " BOOLEAN" +
            ")";
    private static final String SQL_DROP_CHECK_IN_TABLE = "DROP IF EXISTS " + CheckInEntry.TABLE_NAME;
    public static final String DATABASE_NAME = "onesquare.db";
    public static final int DATABASE_VERSION = 1;

    public CheckInDatabaseOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CHECK_IN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_CHECK_IN_TABLE);

        onCreate(db);
    }
}
