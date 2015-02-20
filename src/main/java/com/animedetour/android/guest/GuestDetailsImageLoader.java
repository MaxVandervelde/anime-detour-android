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
import com.animedetour.android.view.scrim.ImageScrim;
import org.apache.commons.logging.Log;

/**
 * Callback for when a guest image has been loaded.
 *
 * This will set the image on the guest details page when the image has been
 * loaded from the server. It can be used for both the high and low res images.
 *
 * @todo Can we combine this with the widget loader?
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class GuestDetailsImageLoader implements ImageLoader.ImageListener
{
    final private ImageScrim view;
    final private Log log;

    /**
     * @param view The view to be updated with the loaded image.
     * @param log Logged to on a network error.
     */
    public GuestDetailsImageLoader(ImageScrim view, Log log)
    {
        this.view = view;
        this.log = log;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b)
    {
        if (null == imageContainer.getBitmap()) {
            return;
        }
        this.view.setImage(imageContainer.getBitmap());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError)
    {
        this.log.error("Error loading guest image", volleyError);
    }
}
