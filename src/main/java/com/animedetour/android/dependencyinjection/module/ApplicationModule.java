/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.dependencyinjection.module;

import com.inkapplications.android.logger.ConsoleLogger;
import com.inkapplications.android.standard.dependencyinjection.module.StandardApplicationModule;
import com.animedetour.android.BuildConfig;
import com.animedetour.android.Application;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.logging.Log;

import javax.inject.Singleton;

@Module(
    includes = {

    },
    injects = {
        Application.class
    },
    addsTo = StandardApplicationModule.class,
    complete = false,
    library = true
)
public class ApplicationModule
{
    @Provides @Singleton Log provideLogger()
    {
        return new ConsoleLogger(BuildConfig.DEBUG, "application");
    }
}
