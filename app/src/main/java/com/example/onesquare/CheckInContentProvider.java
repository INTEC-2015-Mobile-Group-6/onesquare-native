package com.example.onesquare;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.onesquare.model.CheckInContract.CheckInEntry;

public class CheckInContentProvider extends ContentProvider {
    private static final String TAG = CheckInContentProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CHECK_INS = 100;
    private static final int CHECK_INS_FAVORITE = 101;

    private static final int CHECK_IN = 200;

    static {
        sUriMatcher.addURI(CheckInEntry.CONTENT_AUTHORITY,
                CheckInEntry.PATH_CHECK_IN, CHECK_INS);
        sUriMatcher.addURI(CheckInEntry.CONTENT_AUTHORITY,
                CheckInEntry.PATH_CHECK_IN + "/#", CHECK_IN);
        sUriMatcher.addURI(CheckInEntry.CONTENT_AUTHORITY,
                CheckInEntry.PATH_FAVORITE, CHECK_INS_FAVORITE);
    }

    private SQLiteDatabase mDatabase;
    private CheckInDatabaseOpenHelper mDatabaseHelper;

    public CheckInContentProvider() {
        mDatabaseHelper = new CheckInDatabaseOpenHelper(
                getContext(),
                CheckInDatabaseOpenHelper.DATABASE_NAME,
                null,
                CheckInDatabaseOpenHelper.DATABASE_VERSION
        );
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case CHECK_INS:
                return CheckInEntry.CONTENT_TYPE;
            case CHECK_IN:
                return CheckInEntry.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long checkInId = -1;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case CHECK_INS:
                mDatabase = mDatabaseHelper.getWritableDatabase();

                checkInId = mDatabase.insert(
                        CheckInEntry.TABLE_NAME,
                        null,
                        values
                );
        }

        return uri.withAppendedPath(uri, String.valueOf(checkInId));
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new CheckInDatabaseOpenHelper(
                getContext(),
                CheckInDatabaseOpenHelper.DATABASE_NAME,
                null,
                CheckInDatabaseOpenHelper.DATABASE_VERSION
        );

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "URI: " + uri);
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        // TODO-francisbrito: Update selection/sort-order based on match.
        switch (match) {
            case CHECK_INS:
                break;
            case CHECK_INS_FAVORITE:
                break;
            case CHECK_IN:
                break;
        }

        mDatabase = mDatabaseHelper.getReadableDatabase();
        cursor = mDatabase.query(
                CheckInEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
