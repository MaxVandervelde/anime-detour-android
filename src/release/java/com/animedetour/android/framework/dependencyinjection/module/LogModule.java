/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import com.animedetour.android.framework.AnalyticsLogger;
import com.google.android.gms.analytics.Tracker;
import com.inkapplications.android.logger.ConsoleLogger;
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
