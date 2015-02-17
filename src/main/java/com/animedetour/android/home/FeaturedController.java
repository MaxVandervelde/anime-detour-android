/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.animedetour.android.schedule.EventActivity;
import com.animedetour.android.view.ImageScrim;
import com.animedetour.api.sched.api.model.Event;
import org.apache.commons.logging.Log;

/**
 * Reacts to events on the home pages "featured" event banners.
 *
 * Handles the click events and image updates for the banner views.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class FeaturedController implements ImageListener, OnClickListener
{
    /** Activity containing the views for starting a new activity. */
    final private Context context;

    /** Logger to log networking issues with image loading. */
    final private Log logger;

    /** The featured event view that this controller is reacting to. */
    final private ImageScrim subject;

    /** An event bound to the view and displayed in it. */
    final private Event event;

    /**
     * @param context Activity containing the views for starting a new activity.
     * @param logger Logger to log networking issues with image loading.
     * @param preview The featured event view that this controller is reacting to.
     * @param event An event bound to the view and displayed in it.
     */
    public FeaturedController(Context context, Log logger, ImageScrim preview, Event event)
    {
        this.context = context;
        this.logger = logger;
        this.subject = preview;
        this.event = event;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b)
    {
        this.subject.setImage(imageContainer.getBitmap());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError)
    {
        this.logger.error("Error loading featured image", volleyError);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(this.context, EventActivity.class);
        intent.putExtra(EventActivity.EXTRA_EVENT, this.event);
        this.context.startActivity(intent);
    }
}
