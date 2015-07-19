/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.favorite;

import android.view.View;
import monolog.Monolog;
import rx.Observer;

import java.util.List;

/**
 * Subscriber used to listen for updates to the favorites list.
 *
 * When an update to the events list is detected, this will notify the fragment
 * of the new data and handle any errors.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FavoriteUpdateObserver implements Observer<List<Favorite>>
{
    private FavoritesFragment fragment;
    private View emptyView;
    private Monolog logger;

    public FavoriteUpdateObserver(
        FavoritesFragment fragment,
        View emptyView,
        Monolog logger
    ) {
        this.fragment = fragment;
        this.emptyView = emptyView;
        this.logger = logger;
    }

    @Override public void onCompleted() {}

    @Override
    public void onError(Throwable e)
    {
        this.logger.error("Error fetching schedule", e);
    }

    @Override
    public void onNext(List<Favorite> favorites)
    {
        if (favorites.size() == 0) {
            this.emptyView.setVisibility(View.VISIBLE);
        } else {
            this.emptyView.setVisibility(View.GONE);
        }

        this.fragment.updateEvents(favorites);
    }
}
