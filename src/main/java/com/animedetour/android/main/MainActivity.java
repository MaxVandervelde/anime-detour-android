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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import butterknife.InjectView;
import butterknife.OnClick;
import com.animedetour.android.R;
import com.animedetour.android.guest.GuestIndexFragment;
import com.animedetour.android.home.HomeFragment;
import com.animedetour.android.map.HotelMapFragment;
import com.animedetour.android.schedule.favorite.FavoritesFragment;
import com.animedetour.android.schedule.ScheduleFragment;
import com.animedetour.android.settings.SettingsFragment;
import icepick.Icicle;
import prism.framework.Layout;

import javax.inject.Inject;

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

    @InjectView(R.id.drawer_favorites)
    View favoritesOption;

    @Inject
    DrawerControllerFactory drawerControllerFactory;

    private DrawerController drawerController;

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

        this.drawerController = this.drawerControllerFactory.create(
            this.drawer,
            this.actionBar,
            R.string.drawer_open,
            R.string.drawer_close,
            this.pageTitle,
            this.favoritesOption
        );


        if (null == savedInstanceState) {
            this.openLandingFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        this.pageTitle = this.drawerController.getPageTitle();
        super.onSaveInstanceState(outState);
    }

    /**
     * Opens the Landing page in the main view
     */
    @OnClick(R.id.drawer_home)
    protected void openLandingFragment()
    {
        this.drawerController.closeToPage(R.string.home_title);
        this.contentFragmentTransaction(new HomeFragment());
    }

    /**
     * Opens the Schedule / Programming page in the main view
     */
    @OnClick(R.id.drawer_programming)
    protected void openScheduleFragment()
    {
        this.drawerController.closeToPage(R.string.schedule_title);
        this.contentFragmentTransaction(new ScheduleFragment());
    }

    @OnClick(R.id.drawer_favorites)
    protected void openFavorites()
    {
        this.drawerController.closeToPage(R.string.schedule_title);
        this.contentFragmentTransaction(new FavoritesFragment());
    }

    @OnClick(R.id.drawer_guests)
    protected void openGuests()
    {
        this.drawerController.closeDrawer();
        this.contentFragmentTransaction(new GuestIndexFragment());
    }

    @OnClick(R.id.drawer_maps)
    protected void openMaps()
    {
        this.drawerController.closeToPage(R.string.maps_title);
        this.contentFragmentTransaction(new HotelMapFragment());
    }

    @OnClick(R.id.drawer_settings)
    protected void openSettings()
    {
        this.drawerController.closeToPage(R.string.settings_title);
        this.contentFragmentTransaction(new SettingsFragment());
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

    @TargetApi(21)
    private void showSystemBarBackround()
    {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
}
