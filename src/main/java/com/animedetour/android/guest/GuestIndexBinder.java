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
import android.content.Intent;
import android.view.View;
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
    public void bindView(final Guest guest, GuestWidgetView view)
    {
        view.setName(guest.getFullName());
        view.showDefaultImage();
        this.imageLoader.get(
            guest.getPhoto(),
            new GuestWidgetImageLoader(view, this.log)
        );
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(context, GuestDetailActivity.class);
                intent.putExtra(GuestDetailActivity.ARG_GUEST, guest);
                context.startActivity(intent);
            }
        });
    }
}
