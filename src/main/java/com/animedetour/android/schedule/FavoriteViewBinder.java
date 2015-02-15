/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.view.ViewGroup;
import com.inkapplications.android.widget.recyclerview.ItemViewBinder;

/**
 * Binds the events tied to a list of favorites into views.
 *
 * This extracts the event from a Favorite and delegates the view logic to the
 * Event's ViewBinder.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
class FavoriteViewBinder implements ItemViewBinder<PanelView, Favorite>
{
    final private EventViewBinder eventViewBinder;

    public FavoriteViewBinder(EventViewBinder eventViewBinder)
    {
        this.eventViewBinder = eventViewBinder;
    }

    @Override
    public PanelView createView(ViewGroup viewGroup, int i)
    {
        return this.eventViewBinder.createView(viewGroup, i);
    }

    @Override
    public void bindView(Favorite favorite, PanelView view)
    {
        this.eventViewBinder.bindView(favorite.getEvent(), view);
    }
}
