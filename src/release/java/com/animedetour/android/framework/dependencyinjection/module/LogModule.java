/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.content.res.Resources;
import com.google.android.gms.analytics.Tracker;
import com.inkapplications.android.logger.CompositeLogger;
import com.inkapplications.android.logger.analytics.AnalyticsLogger;
import com.inkapplications.android.logger.console.ConsoleLogger;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.logging.Log;

import javax.inject.Singleton;

/**
 * This logger module provides a logger for production builds that will send
 * error reports to Google Analytics.
*
 * This won't be much help for debugging live, so this is only to be available
 * in non-debug builds.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Module(library = true, complete = false)
public class LogModule
{
    @Provides @Singleton Log provideLogger(
        Tracker analytics,
        Resources resources
    ) {
        CompositeLogger composite = new CompositeLogger();
        composite.addLogger(new AnalyticsLogger(analytics, resources));
        composite.addLogger(new ConsoleLogger(resources, false, false, "AnimeDetour"));

        return composite;
    }
}
