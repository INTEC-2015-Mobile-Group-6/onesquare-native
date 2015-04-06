package com.example.onesquare;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.onesquare.model.CheckIn;
import com.example.onesquare.model.CheckInContract;
import com.example.onesquare.model.CheckInContract.CheckInEntry;

import java.util.Comparator;
import java.util.Date;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CheckInListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(0, getArguments(), this);
    }

    private static final String ARG_IS_FILTERED_BY_FAVORITE = "is_filtered_by_favorite";
    private static final String TAG = CheckInListFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter mSimpleCheckInCursorAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CheckInListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSimpleCheckInCursorAdapter = new SimpleCheckInCursorAdapter(
                getActivity(),
                R.layout.check_in_item,
                null,
                new String[]{
                        CheckInEntry.PLACE,
                        CheckInEntry.DATE
                },
                new int[]{
                        R.id.check_in_place,
                        R.id.check_in_date
                },
                0
        );

        setListAdapter(mSimpleCheckInCursorAdapter);

        getLoaderManager()
                .initLoader(0, getArguments(), this);
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
            //

            Cursor c = getActivity().getContentResolver().query(CheckInContract.CheckInEntry.CONTENT_URI,null,null,null,null);
            c.moveToPosition(position);

            mListener.onFragmentInteraction(c.getInt(c.getColumnIndex(CheckInEntry._ID)));
        }
    }

    public static CheckInListFragment newInstance(boolean isFilteredByFavorite) {
        CheckInListFragment fragment = new CheckInListFragment();

        Bundle args = new Bundle();

        args.putBoolean(ARG_IS_FILTERED_BY_FAVORITE, isFilteredByFavorite);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        boolean filterByIsFavorite = args.getBoolean(ARG_IS_FILTERED_BY_FAVORITE);

        String[] projection = new String[] {
                CheckInEntry._ID,
                CheckInEntry.PLACE,
                CheckInEntry.DATE
        };

        String selection = null;
        String[] selectionArgs = null;

        if (filterByIsFavorite) {
            selection = CheckInEntry.IS_FAVORITE + " = ?";
            selectionArgs = new String[] {
                    "1"
            };
        }

        return new CursorLoader(
                getActivity(),
                CheckInEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSimpleCheckInCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSimpleCheckInCursorAdapter.swapCursor(null);
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
        void onFragmentInteraction(int id);
    }
}
