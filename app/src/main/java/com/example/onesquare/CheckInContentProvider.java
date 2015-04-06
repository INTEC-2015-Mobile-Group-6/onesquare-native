package com.example.onesquare;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.onesquare.model.CheckInContract.CheckInEntry;

public class CheckInContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CHECK_INS = 100;
    private static final int CHECK_IN = 101;

    static {
        sUriMatcher.addURI(CheckInEntry.CONTENT_AUTHORITY,
                CheckInEntry.PATH_CHECK_IN, CHECK_INS);
        sUriMatcher.addURI(CheckInEntry.CONTENT_AUTHORITY,
                CheckInEntry.PATH_CHECK_IN + "/#", CHECK_IN);
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
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
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
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case CHECK_INS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_ID ASC";
                }
            case CHECK_IN:
                String idString = uri.getLastPathSegment();

                if (selection != null && !selection.toUpperCase().contains("_ID")) {
                    selection += " _ID = ?";

                    List<String> selectionArgsList = Arrays.asList(selectionArgs);

                    selectionArgsList.add(idString);

                    selectionArgs = (String[]) selectionArgsList.toArray();
                }
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
