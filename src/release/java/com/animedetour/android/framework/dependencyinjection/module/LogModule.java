/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import com.inkapplications.prism.analytics.AnalyticsLogger;
import com.google.android.gms.analytics.Tracker;
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
        Tracker analytics
    ) {
        return new AnalyticsLogger(analytics);
    }
}
