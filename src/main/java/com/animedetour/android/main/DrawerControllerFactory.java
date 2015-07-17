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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.animedetour.android.database.favorite.FavoriteRepository;
import monolog.Monolog;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Creates new instances and configures the DrawerController at runtime.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class DrawerControllerFactory
{
    final private Activity context;
    final private FavoriteRepository favoriteData;
    final private Monolog logger;

    @Inject
    public DrawerControllerFactory(
        Activity context,
        FavoriteRepository favoriteData,
        Monolog logger
    ) {
        this.context = context;
        this.favoriteData = favoriteData;
        this.logger = logger;
    }

    /**
     * Creates a new Controller and binds it to the drawer and toolbar.
     *
     * @param drawerLayout The drawer to watch for events on.
     * @param toolbar Toolbar to sync the title with and use as a controller.
     * @param openContent Content description for opening the drawer.
     * @param closeContent Content description for closing the drawer.
     * @param pageTitle The initial page title to set the toolbar to.
     * @return A new and initialized instance of the controller.
     */
    public DrawerController create(
        DrawerLayout drawerLayout,
        Toolbar toolbar,
        int openContent,
        int closeContent,
        String pageTitle,
        View favoritesOption
    ) {
        DrawerController controller = new DrawerController(
            this.context,
            this.logger,
            this.favoriteData,
            drawerLayout,
            toolbar,
            openContent,
            closeContent,
            favoritesOption
        );

        drawerLayout.setDrawerListener(controller);
        controller.syncState(pageTitle);

        return controller;
    }
}
