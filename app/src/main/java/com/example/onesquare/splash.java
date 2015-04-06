package com.example.onesquare;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.*;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
/**
 * Created by night on 04/06/15.
 *
 * SPLASH: Aunque no pude quitarle la barra de arriba, Alguna idea de como hacerlo? Intente cambiarle el tema (solo al splash), pero explotaba.
 */
public class splash extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(splash.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 5000);
    }
}
