package com.example.onesquare;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.onesquare.dummy.DummyContent;
import com.example.onesquare.model.CheckIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CheckInListFragment extends ListFragment {

    private static final String ARG_IS_FILTERED_BY_FAVORITE = "is_filtered_by_favorite";
    private static final CheckIn[] DUMMY_CHECK_INS = {
            new CheckIn(new Date(), "Santo Domingo", "Chef Pepper", null, null, true),
            new CheckIn(new Date(2015 - 1900, 0, 13), "Samaná", "Bahía de las aguilas", null, null, true),
            new CheckIn(new Date(2015 - 1900, 1, 7), "Santiago", "El monumento", null, null, false),
            new CheckIn(new Date(2015 - 1900, 2, 1), "Puerto Plata", "La boca", null, null, false)
    };
    private static final String TAG = CheckInListFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private ArrayAdapter<CheckIn> mCheckInArrayAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CheckInListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        boolean filterByFavorite = args.getBoolean(ARG_IS_FILTERED_BY_FAVORITE);

        setUpListAdapter(filterByFavorite);

        setListAdapter(mCheckInArrayAdapter);
    }

    private void setUpListAdapter(boolean filterByFavorite) {
        ArrayList<CheckIn> defaultCheckIns = new ArrayList<>(Arrays.asList(DUMMY_CHECK_INS));

        ArrayList<CheckIn> allCheckIns = new ArrayList<>();
        allCheckIns.addAll(defaultCheckIns);

        mCheckInArrayAdapter = new CheckInArrayAdapter(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );

        if (filterByFavorite) {
            ArrayList<CheckIn> favoriteCheckIns = new ArrayList<>();

            for (int i = 0; i < defaultCheckIns.size(); i++) {
                CheckIn checkIn = defaultCheckIns.get(i);

                if (checkIn.isFavorite()) {
                    favoriteCheckIns.add(checkIn);
                }
            }

            mCheckInArrayAdapter.addAll(favoriteCheckIns);
        } else {
            mCheckInArrayAdapter.addAll(allCheckIns);
        }

        mCheckInArrayAdapter.sort(new InverseCheckInDateComparator());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(mCheckInArrayAdapter.getItem(position).getId());
        }
    }

    public static CheckInListFragment newInstance(boolean isFilteredByFavorite) {
        CheckInListFragment fragment = new CheckInListFragment();

        Bundle args = new Bundle();

        args.putBoolean(ARG_IS_FILTERED_BY_FAVORITE, isFilteredByFavorite);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int id);
    }

    private class InverseCheckInDateComparator implements Comparator<CheckIn> {
        private static final int INVERSE_QUANTIFIER = -1;

        @Override
        public int compare(CheckIn c1, CheckIn c2) {
            Date checkInDate1 = c1.getDate();
            Date checkInDate2 = c2.getDate();

            return checkInDate1.compareTo(checkInDate2)
                    * INVERSE_QUANTIFIER;
        }
    }
}
