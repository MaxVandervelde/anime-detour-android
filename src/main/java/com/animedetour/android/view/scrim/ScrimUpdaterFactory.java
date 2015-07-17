/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.scrim;

import monolog.Monolog;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Creates a loader class at runtime based on the image view.
 *
 * Since the updater relies on a view, these must be created at runtime, this
 * service injects dependencies into new instances.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class ScrimUpdaterFactory
{
    final private Monolog logger;

    @Inject
    public ScrimUpdaterFactory(Monolog logger)
    {
        this.logger = logger;
    }

    /**
     * @param scrim View that will be bound to the loader class.
     * @return
     */
    public ScrimUpdater create(ImageScrim scrim)
    {
        return new ScrimUpdater(this.logger, scrim);
    }
}
