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
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.android.widget.recyclerview.ItemViewBinder;
import org.apache.commons.logging.Log;

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
    final private ImageLoader imageLoader;
    final private Log log;
    final private Context context;
    final private GuestControllerFactory controllerFactory;

    @Inject
    public GuestIndexBinder(
        ImageLoader imageLoader,
        Log log,
        Context context,
        GuestControllerFactory controllerFactory
    ) {
        this.imageLoader = imageLoader;
        this.log = log;
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
        view.showDefaultImage();
        view.bindGuest(guest);

        this.imageLoader.get(
            guest.getPhoto(),
            new GuestWidgetImageLoader(view, guest.getPhoto(), this.log)
        );

        GuestWidgetController controller = this.controllerFactory.create(guest);
        view.setOnClickListener(controller);
    }
}
