/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.main;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import com.animedetour.android.R;

/**
 * Manages actions on the application drawer.
 *
 * This is responsible for what actions to take when the drawer is opened and
 * closed. It keeps the action bar title in sync when this is opened/closed
 * and provides an API for restoring the state on events like rotation.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class DrawerController extends ActionBarDrawerToggle
{
    final private Resources resources;
    final private Toolbar toolbar;
    final private DrawerLayout layout;
    private String title;

    public DrawerController(
        Activity activity,
        DrawerLayout drawerLayout,
        Toolbar toolbar,
        int openDrawerContentDescRes,
        int closeDrawerContentDescRes
    ) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);

        this.toolbar = toolbar;
        this.layout = drawerLayout;
        this.resources = activity.getResources();
    }

    @Override
    public void onDrawerClosed(View view)
    {
        super.onDrawerClosed(view);

        this.toolbar.setTitle(title);
    }

    @Override
    public void onDrawerOpened(View drawerView)
    {
        super.onDrawerOpened(drawerView);

        this.toolbar.setTitle(R.string.app_name);
    }

    @Override
    public void syncState()
    {
        super.syncState();

        if (this.layout.isDrawerOpen(Gravity.START)) {
            this.toolbar.setTitle(R.string.app_name);
        } else {
            this.toolbar.setTitle(this.title);
        }
    }

    /**
     * Set the page title, then synchronize the state of the drawer.
     *
     * @param pageTitle The title to be used when the drawer is closed.
     */
    public void syncState(String pageTitle)
    {
        this.title = pageTitle;
        this.syncState();
    }


    /**
     * Set the title of the toolbar.
     *
     * @param title the string to display in the toolbar.
     */
    public void setTitle(String title)
    {
        this.title = title;
        this.toolbar.setTitle(title);
    }

    /**
     * Set the title of the toolbar.
     *
     * @param titleId The Android resource ID of the string to lookup and display.
     */
    public void setTitle(@StringRes int titleId)
    {
        String title = this.resources.getString(titleId);
        this.setTitle(title);
    }

    /**
     * Close the drawer and update the title.
     *
     * @param titleId The Android resource ID of the string to lookup and
     *                display in the toolbar.
     */
    public void closeToPage(@StringRes int titleId)
    {
        this.closeDrawer();
        this.setTitle(titleId);
    }

    /**
     * Get the current page title.
     *
     * This provides the title for use when saving the application state, to
     * be restored later.
     *
     * @see #syncState(String) to restore the title.
     * @return The page title that is be displayed if the drawer is closed.
     */
    public String getPageTitle()
    {
        return this.title;
    }

    /**
     * Closes the drawer.
     */
    public void closeDrawer()
    {
        this.layout.closeDrawer(Gravity.START);
    }
}
