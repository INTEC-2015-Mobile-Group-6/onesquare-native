package com.example.onesquare;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.onesquare.model.CheckInContract.CheckInEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by francis on 04/06/15.
 */
public class SimpleCheckInCursorAdapter extends SimpleCursorAdapter {

    private static final String TAG = SimpleCheckInCursorAdapter.class.getSimpleName();

    private final LayoutInflater mInflater;
    private final int mLayoutId;

    public SimpleCheckInCursorAdapter(Context context, int layout, Cursor cursor,
                                      String[] from, int[] to, int flags) {
        super(context, layout, cursor, from, to, flags);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutId = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, null);
        }

        TextView placeText = (TextView) convertView.findViewById(R.id.check_in_place);
        TextView dateText = (TextView) convertView.findViewById(R.id.check_in_date);

        mCursor.moveToPosition(position);

        String place = mCursor.getString(mCursor.getColumnIndex(CheckInEntry.PLACE));
        String dateString = mCursor.getString(mCursor.getColumnIndex(CheckInEntry.DATE));

        Date date = new Date(dateString);

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(date.getTime());

        placeText.setText(place);
        dateText.setText(timeAgo);

        return convertView;
    }
}
