/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import org.apache.commons.logging.Log;

/**
 * Callback for when a guest image has been loaded.
 *
 * This will set the image on a guest widget according to the bitmap fetched.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class GuestWidgetImageLoader implements ImageLoader.ImageListener
{
    final private GuestWidgetView view;
    final private String url;
    final private Log log;

    /**
     * @param view The view to be updated with the loaded image.
     * @param log Logged to on a network error.
     */
    public GuestWidgetImageLoader(GuestWidgetView view, String url, Log log)
    {
        this.view = view;
        this.url = url;
        this.log = log;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b)
    {
        if (this.isStale()) {
            return;
        }
        this.view.setImage(imageContainer.getBitmap());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError)
    {
        this.log.error("Error loading guest image", volleyError);
    }

    /**
     * Check whether this request listener is no longer relevant.
     *
     * If a view gets recycled, then this listener may become bound to a
     * different guest. In order to prevent this, we have an attached url
     * to this listener and we'll check that they still match before setting
     * the image.
     *
     * @todo This is a bad hack - We should be cancelling these requests instead.
     * @return Whether the attached view is out-of-sync with this request.
     */
    private boolean isStale()
    {
        String expectedUrl = view.getDisplayedGuest().getPhoto();

        return !expectedUrl.equals(this.url);
    }
}
