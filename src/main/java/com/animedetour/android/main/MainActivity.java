/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.main;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import butterknife.InjectView;
import butterknife.OnClick;
import com.animedetour.android.R;
import com.animedetour.android.guest.GuestIndexFragment;
import com.animedetour.android.home.HomeFragment;
import com.animedetour.android.map.HotelMapFragment;
import com.animedetour.android.schedule.FavoritesFragment;
import com.animedetour.android.schedule.ScheduleFragment;
import icepick.Icicle;
import prism.framework.Layout;

/**
 * Main containing Activity
 *
 * This is the default activity of the application.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Layout(R.layout.main)
final public class MainActivity extends ActionBarActivity
{
    /**
     * Drawer Toggle handler for the main sliding left drawer
     */
    private ActionBarDrawerToggle drawerToggle;

    /**
     * Storage of the current page title.
     *
     * This is used for when the drawer is opened/closed and the title is
     * swapped with the application title
     */
    @Icicle String pageTitle;

    /**
     * View of the main sliding left drawer
     */
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;

    @InjectView(R.id.main_action_bar)
    Toolbar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.showSystemBarBackround();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        if (null == savedInstanceState) {
            this.openLandingFragment();
        } else {
            MainActivity.this.actionBar.setTitle(this.pageTitle);
        }

        this.setupNavigation();
        this.drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        this.drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (this.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the navigation action bar for the activity
     *
     * Creates a drawer toggle listener and binds it to the action bar as well
     * as configuring the proper display for the home icon.
     */
    protected void setupNavigation()
    {
        this.drawerToggle = new ActionBarDrawerToggle(
            this,
            this.drawer,
            this.actionBar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            @Override public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                MainActivity.this.actionBar.setTitle(MainActivity.this.pageTitle);
            }
            @Override public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                MainActivity.this.actionBar.setTitle(R.string.app_name);
            }
        };

        this.drawer.setDrawerListener(this.drawerToggle);
    }

    /**
     * Opens the Landing page in the main view
     */
    @OnClick(R.id.drawer_home)
    protected void openLandingFragment()
    {
        this.setPageTitle(R.string.home_title);
        this.drawer.closeDrawer(Gravity.START);
        this.contentFragmentTransaction(new HomeFragment());
    }

    /**
     * Opens the Schedule / Programming page in the main view
     */
    @OnClick(R.id.drawer_programming)
    protected void openScheduleFragment()
    {
        this.setPageTitle(R.string.schedule_title);
        this.drawer.closeDrawer(Gravity.START);
        this.contentFragmentTransaction(new ScheduleFragment());
    }

    @OnClick(R.id.drawer_favorites)
    protected void openFavorites()
    {
        this.setPageTitle(R.string.schedule_title);
        this.drawer.closeDrawer(Gravity.START);
        this.contentFragmentTransaction(new FavoritesFragment());
    }

    @OnClick(R.id.drawer_guests)
    protected void openGuests()
    {
        this.drawer.closeDrawer(Gravity.START);
        this.contentFragmentTransaction(new GuestIndexFragment());
    }

    @OnClick(R.id.drawer_maps)
    protected void openMaps()
    {
        this.setPageTitle(R.string.maps_title);
        this.drawer.closeDrawer(Gravity.START);
        this.getFragmentManager().beginTransaction().replace(
            R.id.content_frame,
            new HotelMapFragment()
        ).setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom).commit();
    }

    /**
     * Transition to the new fragment, optionally adding to the back stack.
     *
     * @param newFragment The fragment to add to the main content view
     */
    protected void contentFragmentTransaction(Fragment newFragment)
    {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        transaction.replace(R.id.content_frame, newFragment);
        transaction.commit();
    }

    /**
     * Sets the page title to appear in the action bar when the drawer is closed
     *
     * @param resourceId The current page title
     */
    protected void setPageTitle(int resourceId)
    {
        this.pageTitle = this.getString(resourceId);
        this.actionBar.setTitle(resourceId);
    }

    @TargetApi(21)
    private void showSystemBarBackround()
    {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
}
