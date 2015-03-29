package com.example.onesquare;

import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends ActionBarActivity
        implements ActionBar.TabListener, CheckInListFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    CheckInListPagerAdapter mCheckInListPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mCheckInListPagerAdapter =  new CheckInListPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCheckInListPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mCheckInListPagerAdapter.getCount(); i++) {
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(mCheckInListPagerAdapter.getPageTitle(i))
                    .setTabListener(this);

            actionBar.addTab(tab);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onFragmentInteraction(int id) {
        Log.d(TAG, "selected check in id: " + id);
    }

    private class CheckInListPagerAdapter extends FragmentPagerAdapter {
        private static final int POSITION_ALL_CHECK_INS = 0;
        private static final int POSITION_FAV_CHECK_INS = 1;

        public CheckInListPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String result = null;

            Resources resources = getResources();
            Locale locale = Locale.getDefault();

            switch (position) {
                case POSITION_ALL_CHECK_INS:
                    result = resources.getString(R.string.tab_all_check_ins);
                    break;
                case POSITION_FAV_CHECK_INS:
                    result = resources.getString(R.string.tab_fav_check_ins);
                    break;
            }

            return result != null ? result.toUpperCase(locale) : null;
        }

        @Override
        public Fragment getItem(int position) {
           CheckInListFragment result = null;

            switch (position) {
                case POSITION_ALL_CHECK_INS:
                    result = CheckInListFragment.newInstance(false);
                    break;
                case POSITION_FAV_CHECK_INS:
                    result = CheckInListFragment.newInstance(true);
            }

           return result;
        }
    }
}
