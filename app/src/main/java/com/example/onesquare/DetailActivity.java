package com.example.onesquare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.example.onesquare.model.CheckIn;
import com.example.onesquare.model.CheckInContract;

import org.w3c.dom.Text;


public class DetailActivity extends Activity {

    TextView details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        details = (TextView) findViewById(R.id.detailtext);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Bundle args = getIntent().getExtras();
        int checkInId = args.getInt("id");

        String selection = CheckInContract.CheckInEntry._ID + " = ?";
        String[] selectionArgs = new String[] {
                String.valueOf(checkInId)
        };

        Cursor detailCursor = getContentResolver()
                .query(
                        CheckInContract.CheckInEntry.CONTENT_URI, null,
                        selection,
                        selectionArgs,
                        null
                );

        detailCursor.moveToFirst();

        if(args != null)
        {


            if (detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.IS_FAVORITE)).toString() == "TRUE")
            {
                details.setText(detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.PLACE)).toString()
                                +"\n"+
                                detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.ADDRESS)).toString()
                                +"\n"+
                                detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.DATE)).toString()
                                +"\n"+
                                detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.URL)).toString()
                                +"\n"+
                                "Es favorito."
                );

            }

            else {
                details.setText(detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.PLACE)).toString()
                                +"\n"+
                                detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.ADDRESS)).toString()
                                +"\n"+
                                detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.DATE)).toString()
                                +"\n"+
                                detailCursor.getString(detailCursor.getColumnIndex(CheckInContract.CheckInEntry.URL)).toString()
                );

            }


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            Toast.makeText(
                    this,
                    R.string.message_check_in_invalid_date,
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
           // TextView detail = (TextView) rootView.findViewById(R.id.placename);
            return rootView;
        }


        }
}
