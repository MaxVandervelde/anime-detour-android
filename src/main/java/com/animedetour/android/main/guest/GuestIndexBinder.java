/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.main.guest;

import android.content.Context;
import com.android.volley.toolbox.ImageLoader;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.prism.widget.recyclerview.ItemViewBinder;
import org.apache.commons.logging.Log;

/**
 * Creates and binds new views for the guest widgets.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 * @todo Replace GuestLoader creation with a factory.
 */
public class GuestIndexBinder implements ItemViewBinder<GuestWidgetView, Guest>
{
    final private ImageLoader imageLoader;
    final private Log log;
    final private Context context;

    public GuestIndexBinder(ImageLoader imageLoader, Log log, Context context)
    {
        this.imageLoader = imageLoader;
        this.log = log;
        this.context = context;
    }

    @Override
    public GuestWidgetView createView()
    {
        return new GuestWidgetView(context);
    }

    @Override
    public void bindView(Guest guest, GuestWidgetView view)
    {
        view.setName(guest.getFirstName());
        view.showDefaultImage();
        this.imageLoader.get(
            guest.getPhoto(),
            new GuestImageLoader(view, this.log)
        );
    }
}
