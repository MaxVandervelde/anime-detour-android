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
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import com.animedetour.android.R;
import com.animedetour.android.database.favorite.FavoriteRepository;
import org.apache.commons.logging.Log;
import prism.framework.DisplayName;

import java.sql.SQLException;

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
    final private Log logger;
    final private FavoriteRepository favoriteData;
    final private View favoritesOption;
    final private Toolbar toolbar;
    final private DrawerLayout layout;
    private String title;
    private boolean displayTitles = true;

    public DrawerController(
        Activity activity,
        Log logger,
        FavoriteRepository favoriteData,
        DrawerLayout drawerLayout,
        Toolbar toolbar,
        int openDrawerContentDescRes,
        int closeDrawerContentDescRes,
        View favoritesOption
    ) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);

        this.toolbar = toolbar;
        this.logger = logger;
        this.layout = drawerLayout;
        this.favoriteData = favoriteData;
        this.favoritesOption = favoritesOption;
        this.resources = activity.getResources();
    }

    @Override
    public void onDrawerClosed(View view)
    {
        super.onDrawerClosed(view);

        this.toolbar.setTitle(title);
        this.updateFavoritesVisibility();
    }

    @Override
    public void onDrawerOpened(View drawerView)
    {
        super.onDrawerOpened(drawerView);

        if (this.displayTitles) {
            this.toolbar.setTitle(R.string.app_name);
        }
        this.updateFavoritesVisibility();
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

        this.updateFavoritesVisibility();
    }

    private void updateFavoritesVisibility()
    {
        try {
            if (this.favoriteData.hasFavorites()) {
                this.favoritesOption.setVisibility(View.VISIBLE);
            } else {
                this.favoritesOption.setVisibility(View.GONE);
            }
        } catch (SQLException e) {
            this.logger.error("Error when checking if the user has favorites", e);
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
        this.displayTitles = true;
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
     * Close the drawer and update the title.
     *
     * @param titleId title of the page to be displayed in the toolbar.
     */
    public void closeToPage(String titleId)
    {
        this.closeDrawer();
        this.setTitle(titleId);
    }

    /**
     * Closes the drawer and updates the toolbar title to match the fragment.
     *
     * If there is a `DisplayName` attribute on the fragment, that will be used
     * as the title. Otherwise, it will just use the class name as the title
     * for a fallback.
     *
     * @param fragment The page class being displayed to base the title off of.
     */
    public void closeToPage(Class<? extends Fragment> fragment)
    {
        DisplayName displayName = fragment.getAnnotation(DisplayName.class);
        if (null == displayName) {
            this.closeToPage(fragment.getSimpleName());
            return;
        }

        this.closeToPage(displayName.value());
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

    /**
     * Hides the title of the page and prevents it from appearing when the
     * drawer is opened.
     */
    public void disableTitles()
    {
        this.setTitle("");
        this.displayTitles = false;
    }
}
