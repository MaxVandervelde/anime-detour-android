/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.scrim;

import android.content.res.Resources;
import android.graphics.Bitmap;
import com.animedetour.android.view.FadeInDrawable;
import com.inkapplications.prism.imageloader.ImageCallback;
import com.inkapplications.prism.imageloader.LoadType;
import org.apache.commons.logging.Log;

/**
 * Callback for volley that puts the returned bitmap into the view.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class ScrimUpdater implements ImageCallback
{
    final private Resources resources;
    final private Log logger;
    final private ImageScrim scrim;

    /**
     * @param resources Android resources.
     * @param scrim Image view to control.
     */
    public ScrimUpdater(Resources resources, Log logger, ImageScrim scrim)
    {
        this.resources = resources;
        this.logger = logger;
        this.scrim = scrim;
    }

    @Override
    public void onLoad(Bitmap source, LoadType loadType)
    {
        if (null == source) {
            return;
        }

        if (loadType.isCache()) {
            this.scrim.setImage(source);
            return;
        }

        FadeInDrawable drawable = new FadeInDrawable(this.resources, source);
        this.scrim.setDrawable(drawable);
        drawable.startDefaultTransition();
    }

    @Override
    public void onError(Exception e)
    {
        this.logger.error("Error loading scrim image", e);
    }
}
