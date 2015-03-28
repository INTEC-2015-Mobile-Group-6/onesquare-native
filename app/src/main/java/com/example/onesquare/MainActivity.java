package com.example.onesquare;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CheckInListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CheckInListFragment extends Fragment {

        private static final String TAB_ALL_CHECK_INS = "all_check_ins_tab";
        private static final String TAB_FAV_CHECK_INS = "fav_check_ins_tab";

        public CheckInListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            setUpTabs(rootView);

            return rootView;
        }

        private void setUpTabs(View rootView) {
            TabHost tabHost = (TabHost) rootView.findViewById(R.id.tab_host);
            tabHost.setup();

            TabHost.TabSpec allCheckInsTabSpec = tabHost.newTabSpec(TAB_ALL_CHECK_INS);
            TabHost.TabSpec favoriteCheckInsTabSpec = tabHost.newTabSpec(TAB_FAV_CHECK_INS);

            String allCheckInsLabel = getResources()
                    .getString(R.string.tab_all_check_ins);
            allCheckInsTabSpec.setIndicator(allCheckInsLabel);
            allCheckInsTabSpec.setContent(R.id.all_check_ins_tab);

            String favoriteCheckInsLabel = getResources()
                    .getString(R.string.tab_fav_check_ins);
            favoriteCheckInsTabSpec.setIndicator(favoriteCheckInsLabel);
            favoriteCheckInsTabSpec.setContent(R.id.fav_check_ins_tab);

            tabHost.addTab(allCheckInsTabSpec);
            tabHost.addTab(favoriteCheckInsTabSpec);
        }
    }
}
