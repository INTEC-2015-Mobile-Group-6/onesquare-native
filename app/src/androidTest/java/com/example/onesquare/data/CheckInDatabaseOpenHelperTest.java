package com.example.onesquare.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.example.onesquare.CheckInDatabaseOpenHelper;
import com.example.onesquare.model.CheckInContract.CheckInEntry;

import java.util.Date;

/**
 * Created by francis on 04/05/15.
 */
public class CheckInDatabaseOpenHelperTest extends AndroidTestCase {
    private SQLiteDatabase mDatabase;

    public void testCanCreateDatabase() {
        mContext.deleteDatabase(CheckInDatabaseOpenHelper.DATABASE_NAME);

        CheckInDatabaseOpenHelper dbHelper = new CheckInDatabaseOpenHelper(
                getContext(),
                CheckInDatabaseOpenHelper.DATABASE_NAME,
                null,
                CheckInDatabaseOpenHelper.DATABASE_VERSION
        );
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        assertTrue(db.isOpen());
    }

    public void testCanInsertCheckIn() {
        CheckInDatabaseOpenHelper dbHelper = new CheckInDatabaseOpenHelper(
                getContext(),
                CheckInDatabaseOpenHelper.DATABASE_NAME,
                null,
                CheckInDatabaseOpenHelper.DATABASE_VERSION
        );

        mDatabase = dbHelper.getWritableDatabase();

        String testPlace = "Chef Pepper";
        String testAddress = "Santo Domingo";
        String testDate = new Date().toString();
        String testUrl = "http://localhost";
        String testPictureUrl = "http://placekitten.com/500";
        boolean testIsFavorite = true;

        ContentValues values = new ContentValues();

        values.put(CheckInEntry.PLACE, testPlace);
        values.put(CheckInEntry.ADDRESS, testAddress);
        values.put(CheckInEntry.DATE, testDate);
        values.put(CheckInEntry.URL, testUrl);
        values.put(CheckInEntry.PICTURE_URL, testPictureUrl);
        values.put(CheckInEntry.IS_FAVORITE, testIsFavorite);

        long rowId = mDatabase.insert(
                CheckInEntry.TABLE_NAME,
                null,
                values
        );

        assertFalse(rowId == -1);

        Cursor cursor = mDatabase.query(
                CheckInEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String place = cursor.getString(cursor.getColumnIndex(CheckInEntry.PLACE));
            String address = cursor.getString(cursor.getColumnIndex(CheckInEntry.ADDRESS));
            String date = cursor.getString(cursor.getColumnIndex(CheckInEntry.DATE));
            String url = cursor.getString(cursor.getColumnIndex(CheckInEntry.URL));
            String pictureUrl = cursor.getString(cursor.getColumnIndex(CheckInEntry.PICTURE_URL));
            boolean isFavorite =
                    cursor.getInt(cursor.getColumnIndex(CheckInEntry.IS_FAVORITE)) == 1;

            assertEquals(testPlace, place);
            assertEquals(testAddress, address);
            assertEquals(testDate, date);
            assertEquals(testUrl, url);
            assertEquals(testPictureUrl, pictureUrl);
            assertEquals(testIsFavorite, isFavorite);
        }
    }

    public void testCanDeleteCheckIn() {
        CheckInDatabaseOpenHelper dbHelper = new CheckInDatabaseOpenHelper(
                getContext(),
                CheckInDatabaseOpenHelper.DATABASE_NAME,
                null,
                CheckInDatabaseOpenHelper.DATABASE_VERSION
        );

        mDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CheckInEntry.PLACE, "Chef Pepper");
        values.put(CheckInEntry.ADDRESS, "Santo Domingo");
        values.put(CheckInEntry.DATE, "4/29/2015");
        values.put(CheckInEntry.URL, "http://localhost");
        values.put(CheckInEntry.PICTURE_URL, "http://placeholder.com/500");
        values.put(CheckInEntry.IS_FAVORITE, true);

        mDatabase.insert(
                CheckInEntry.TABLE_NAME,
                null,
                values
        );

        String checkInId = "1";

        int rowsDeleted = mDatabase.delete(
                CheckInEntry.TABLE_NAME,
                "_ID = ?",
                new String[]{
                        checkInId
                }
        );

        assertEquals(rowsDeleted, 1);

        Cursor cursor = mDatabase.query(
                CheckInEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        int checkInCount = cursor.getCount();

        assertEquals(checkInCount, 0);
    }
}
