/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.scrim;

import com.android.volley.toolbox.ImageLoader;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Facade to easily load an image into the specified view.
 *
 * This holds onto our factory and creates a new listener each time we request
 * to put an image into a view.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class ScrimLoader
{
    /**
     * Service for downloading images.
     */
    final private ImageLoader loader;

    /**
     * Service for generating new asynchronous callbacks.
     */
    final private ScrimUpdaterFactory scrimUpdaterFactory;

    /**
     * @param loader Service for downloading images.
     * @param scrimUpdaterFactory Service for generating new asynchronous callbacks.
     */
    @Inject
    public ScrimLoader(
        ImageLoader loader,
        ScrimUpdaterFactory scrimUpdaterFactory
    ) {
        this.loader = loader;
        this.scrimUpdaterFactory = scrimUpdaterFactory;
    }

    /**
     * Asynchronously loads an image into the specified view.
     *
     * @param scrim View to put the image into.
     * @param url The location of the image to download.
     */
    public void loadImage(ImageScrim scrim, String url)
    {
        this.loader.get(url, this.scrimUpdaterFactory.create(scrim));
    }
}
