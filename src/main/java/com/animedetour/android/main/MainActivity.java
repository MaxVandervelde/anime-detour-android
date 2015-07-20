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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import butterknife.Bind;
import butterknife.OnClick;
import com.animedetour.android.R;
import com.animedetour.android.framework.BaseActivity;
import com.animedetour.android.guest.GuestIndexFragment;
import com.animedetour.android.home.HomeFragment;
import com.animedetour.android.map.HotelMapFragment;
import com.animedetour.android.schedule.ScheduleFragment;
import com.animedetour.android.schedule.favorite.FavoritesFragment;
import com.animedetour.android.settings.SettingsFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
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
final public class MainActivity extends BaseActivity implements SpinnerOptionContainer
{
    /**
     * Storage of the current page title.
     *
     * This is used for when the drawer is opened/closed and the title is
     * swapped with the application title
     */
    @Icicle String pageTitle;

    /**
     * Storage of the current spinner selected option, if available.
     *
     * This is used to maintain state in the navigation spinner when the
     * application is paused in instances like rotation.
     */
    @Icicle
    int spinnerSelection;

    /**
     * View of the main sliding left drawer
     */
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.main_action_bar)
    Toolbar actionBar;

    @Bind(R.id.drawer_favorites)
    View favoritesOption;

    @Bind(R.id.spinner_nav)
    Spinner spinner;

    @Inject
    Bus applicationBus;

    @Inject
    SpinnerEventProxy eventProxy;

    @Inject
    DrawerControllerFactory drawerControllerFactory;

    private DrawerController drawerController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.showSystemBarBackround();
        this.setSupportActionBar(this.actionBar);
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
    protected void onResume()
    {
        super.onResume();
        this.applicationBus.register(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.applicationBus.unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        this.spinnerSelection = this.spinner.getSelectedItemPosition();
        this.pageTitle = this.drawerController.getPageTitle();
        super.onSaveInstanceState(outState);
    }

    /**
     * Opens the Landing page in the main view
     */
    @OnClick(R.id.drawer_home)
    protected void openLandingFragment()
    {
        this.drawerController.closeToPage(HomeFragment.class);
        this.contentFragmentTransaction(new HomeFragment());
    }

    /**
     * Opens the Schedule / Programming page in the main view
     */
    @OnClick(R.id.drawer_programming)
    protected void openScheduleFragment()
    {
        this.drawerController.closeToPage(ScheduleFragment.class);
        this.contentFragmentTransaction(new ScheduleFragment());
    }

    @OnClick(R.id.drawer_favorites)
    protected void openFavorites()
    {
        this.drawerController.closeToPage(FavoritesFragment.class);
        this.contentFragmentTransaction(new FavoritesFragment());
    }

    @OnClick(R.id.drawer_guests)
    protected void openGuests()
    {
        this.drawerController.closeToPage(GuestIndexFragment.class);
        this.contentFragmentTransaction(new GuestIndexFragment());
    }

    @OnClick(R.id.drawer_maps)
    protected void openMaps()
    {
        this.drawerController.closeToPage(HotelMapFragment.class);
        this.contentFragmentTransaction(new HotelMapFragment());
    }

    @OnClick(R.id.drawer_settings)
    protected void openSettings()
    {
        this.drawerController.closeToPage(SettingsFragment.class);
        this.contentFragmentTransaction(new SettingsFragment());
    }

    /**
     * Set up the spinner and display it when content is provided.
     *
     * This takes the previous state of the adapter/spinner and stores it.
     * After updating the contents of the spinner, we restore the previous
     * selection into the spinner (it will reset to item 0 after a new adapter
     * is set) This might mean restoring from the old activity state or the
     * previous spinner state; depending on what is available. This is to
     * prevent the spinner from reverting on events like rotation.
     *
     * @param event An event containing new content to display in the spinner.
     */
    @Subscribe
    public void onContentUpdate(NavigationSubContentUpdate event)
    {
        if (null == event.getOptions()) {
            this.spinner.setVisibility(View.GONE);
            return;
        }

        SpinnerAdapter previousAdapter = this.spinner.getAdapter();
        int previousItem = this.spinner.getSelectedItemPosition();
        this.drawerController.disableTitles();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.main_navigation_spinner_item, event.getOptionsArray());
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(this.eventProxy);
        this.spinner.setVisibility(View.VISIBLE);

        if (null == previousAdapter && this.spinnerSelection != 0) {
            this.spinner.setSelection(this.spinnerSelection);
        } else {
            this.spinner.setSelection(previousItem);
        }
    }

    /**
     * Transition to the new fragment, optionally adding to the back stack.
     *
     * @param newFragment The fragment to add to the main content view
     */
    protected void contentFragmentTransaction(Fragment newFragment)
    {
        this.spinner.setVisibility(View.GONE);
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

    @Override
    public String getSpinnerSelection()
    {
        Object selectedItem = this.spinner.getSelectedItem();
        if (null != selectedItem) {
            return (String) selectedItem;
        }

        return null;
    }
}
