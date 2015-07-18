/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import android.content.Context;
import android.view.ViewGroup;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.android.widget.recyclerview.ItemViewBinder;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Creates and binds new views for the guest widgets.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class GuestIndexBinder implements ItemViewBinder<GuestWidgetView, Guest>
{
    final private Context context;
    final private GuestControllerFactory controllerFactory;

    @Inject
    public GuestIndexBinder(Context context, GuestControllerFactory controllerFactory)
    {
        this.context = context;
        this.controllerFactory = controllerFactory;
    }

    @Override
    public GuestWidgetView createView(ViewGroup viewGroup, int i)
    {
        return new GuestWidgetView(context);
    }

    @Override
    public void bindView(Guest guest, GuestWidgetView view)
    {
        view.bindGuest(guest);
        GuestWidgetController controller = this.controllerFactory.create(guest);
        view.setOnClickListener(controller);
    }
}
