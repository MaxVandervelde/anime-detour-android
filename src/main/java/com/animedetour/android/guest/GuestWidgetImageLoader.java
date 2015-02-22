/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.guest;

import android.graphics.Bitmap;
import com.inkapplications.prism.imageloader.ImageCallback;
import com.inkapplications.prism.imageloader.LoadType;
import org.apache.commons.logging.Log;

/**
 * Callback for when a guest image has been loaded.
 *
 * This will set the image on a guest widget according to the bitmap fetched.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
final class GuestWidgetImageLoader implements ImageCallback
{
    final private Log log;
    final private GuestWidgetView view;
    final private String photo;

    /**
     * @param view The view to be updated with the loaded image.
     * @param log Logged to on a network error.
     */
    public GuestWidgetImageLoader(GuestWidgetView view, Log log, String photo)
    {
        this.view = view;
        this.log = log;
        this.photo = photo;
    }

    @Override
    public void onLoad(Bitmap source, LoadType loadType)
    {
        if (null == source) {
            this.view.showDefaultImage();
            return;
        }

        String expected = this.view.getGuest().getPhoto();
        if (false == expected.equals(this.photo)) {
            return;
        }

        this.view.setImage(source);
    }

    @Override
    public void onError(Exception e)
    {
        this.log.error("Error loading guest image", e);
    }
}
