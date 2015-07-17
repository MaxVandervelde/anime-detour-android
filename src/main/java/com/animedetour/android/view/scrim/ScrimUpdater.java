/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.scrim;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import monolog.Monolog;

/**
 * Callback for volley that puts the returned bitmap into the view.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ScrimUpdater implements ImageLoader.ImageListener
{
    final private Monolog logger;
    final private ImageScrim scrim;

    /**
     * @param scrim Image view to control.
     */
    public ScrimUpdater(Monolog logger, ImageScrim scrim)
    {
        this.logger = logger;
        this.scrim = scrim;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b)
    {
        this.scrim.setImage(imageContainer.getBitmap());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError)
    {
        this.logger.error("Error loading scrim image", volleyError);
    }
}
