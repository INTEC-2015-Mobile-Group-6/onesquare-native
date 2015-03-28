package com.example.onesquare;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.ResourceBundle;


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

            Resources resources = getResources();
            String allCheckInsLabel = resources.getString(R.string.tab_all_check_ins);
            String allCheckInsTabId = resources.getResourceName(R.id.all_check_ins_tab);
            String favoriteCheckInsLabel = resources.getString(R.string.tab_fav_check_ins);
            String favoriteCheckInsTabId = resources.getResourceName(R.id.fav_check_ins_tab);

            TabHost.TabSpec allCheckInsTabSpec = tabHost.newTabSpec(allCheckInsTabId);
            TabHost.TabSpec favoriteCheckInsTabSpec = tabHost.newTabSpec(favoriteCheckInsTabId);

            allCheckInsTabSpec.setIndicator(allCheckInsLabel);
            allCheckInsTabSpec.setContent(R.id.all_check_ins_tab);

            favoriteCheckInsTabSpec.setIndicator(favoriteCheckInsLabel);
            favoriteCheckInsTabSpec.setContent(R.id.fav_check_ins_tab);

            tabHost.addTab(allCheckInsTabSpec);
            tabHost.addTab(favoriteCheckInsTabSpec);
        }
    }
}
