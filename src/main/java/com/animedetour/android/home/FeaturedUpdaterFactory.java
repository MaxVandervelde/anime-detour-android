/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.home;

import com.animedetour.android.view.scrim.ImageScrim;
import com.animedetour.android.view.scrim.ScrimLoader;
import org.apache.commons.logging.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Creates new Updaters for the featured events as soon as the views are created.
 *
 * Since we can't properly inject these until the view has been created, we'll
 * use a factory to create the new instances.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class FeaturedUpdaterFactory
{
    final private Log logger;
    final private FeaturedControllerFactory controllerFactory;
    final private ScrimLoader imageLoader;

    @Inject
    public FeaturedUpdaterFactory(
        Log logger,
        FeaturedControllerFactory controllerFactory,
        ScrimLoader imageLoader
    ) {
        this.logger = logger;
        this.controllerFactory = controllerFactory;
        this.imageLoader = imageLoader;
    }

    public FeaturedUpdater create(ImageScrim preview)
    {
        return new FeaturedUpdater(this.logger, this.controllerFactory, this.imageLoader, preview);
    }
}
