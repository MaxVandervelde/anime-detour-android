/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import butterknife.InjectView;
import butterknife.OnClick;
import com.animedetour.android.R;
import com.animedetour.android.fragment.Fragment;
import com.animedetour.android.fragment.LandingFragment;
import com.animedetour.android.fragment.ScheduleFragment;
import icepick.Icicle;

/**
 * Main containing Activity
 *
 * This is the default activity of the application.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class MainActivity extends Activity
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        this.setContentView(R.layout.main);
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState) {
            this.openLandingFragment();
        } else {
            MainActivity.this.getActionBar().setTitle(this.pageTitle);
        }

        this.setupNavigation();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
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
            R.drawable.ic_drawer,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                MainActivity.this.getActionBar().setTitle(MainActivity.this.pageTitle);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                MainActivity.this.getActionBar().setTitle(R.string.app_name);
            }
        };

        ActionBar actionBar = this.getActionBar();
        this.drawer.setDrawerListener(this.drawerToggle);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.flat_logo);
    }

    /**
     * Opens the Landing page in the main view
     */
    @OnClick(R.id.drawer_home)
    protected void openLandingFragment()
    {
        this.setPageTitle(R.string.home_title);
        this.drawer.closeDrawer(Gravity.START);
        this.contentFragmentTransaction(new LandingFragment(), false);
    }

    /**
     * Opens the Schedule / Programming page in the main view
     */
    @OnClick(R.id.drawer_programming)
    protected void openScheduleFragment()
    {
        this.setPageTitle(R.string.schedule_title);
        this.drawer.closeDrawer(Gravity.START);
        this.contentFragmentTransaction(new ScheduleFragment(), false);
    }

    /**
     * Transition to the new fragment, optionally adding to the back stack.
     *
     * @param newFragment The fragment to add to the main content view
     * @param addToBackStack Whether or not to add the fragment into the manager's backstack
     */
    protected void contentFragmentTransaction(Fragment newFragment, boolean addToBackStack)
    {
        FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, newFragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
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
        this.getActionBar().setTitle(resourceId);
    }
}
