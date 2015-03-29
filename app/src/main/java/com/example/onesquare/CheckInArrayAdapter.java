package com.example.onesquare;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.onesquare.model.CheckIn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by francis on 03/29/15.
 */
public class CheckInArrayAdapter extends ArrayAdapter<CheckIn> {
    private final LayoutInflater mInflater;
    private final ArrayList<CheckIn> mCheckInList;

    public CheckInArrayAdapter(FragmentActivity activity, int layoutId, int textId) {
        this(activity, layoutId, textId, new ArrayList<CheckIn>());
    }

    public CheckInArrayAdapter(FragmentActivity activity, int layoutId, int textId,
                               ArrayList<CheckIn> items) {
        super(activity, layoutId, textId, items);

        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCheckInList = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckIn checkIn = mCheckInList.get(position);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.check_in_item, null);
        }

        TextView checkInPlaceText = (TextView) convertView.findViewById(R.id.check_in_place);
        TextView checkInDateText = (TextView) convertView.findViewById(R.id.check_in_date);

        String placeName = checkIn.getPlace();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(checkIn.getDate().getTime());

        checkInPlaceText.setText(placeName);
        checkInDateText.setText(timeAgo);

        return convertView;
    }
}
