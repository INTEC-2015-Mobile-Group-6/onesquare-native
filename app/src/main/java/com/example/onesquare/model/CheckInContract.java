package com.example.onesquare.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by francis on 04/05/15.
 */
public final class CheckInContract {
    public static final class CheckInEntry implements BaseColumns {
        public static final String TABLE_NAME = "check_in";

        // Column names.
        public static final String DATE = "date";
        public static final String PLACE = "place";
        public static final String ADDRESS = "address";
        public static final String URL = "url";
        public static final String PICTURE_URL = "picture_url";
        public static final String IS_FAVORITE = "is_favorite";
        public static final String _ID = "_id";

        private static final String CONTENT_AUTHORITY = "com.example.onesquare";
        private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        private static final String PATH_CHECK_IN = "check-in";

        // For content providers.
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_CHECK_IN)
                .build();

        public static final String CONTENT_TYPE = "" +
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CHECK_IN;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CHECK_IN;

    }
}
