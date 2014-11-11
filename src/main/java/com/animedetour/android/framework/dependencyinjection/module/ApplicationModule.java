/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.animedetour.android.database.DataModule;
import com.animedetour.android.event.EventActivity;
import com.animedetour.android.landing.LandingActivity;
import com.animedetour.android.landing.home.HomeFragment;
import com.animedetour.android.landing.schedule.DayFragment;
import com.animedetour.android.volley.cache.LongImageCache;
import com.animedetour.sched.api.dependencyinjection.ApiModule;
import com.inkapplications.android.logger.ConsoleLogger;
import com.animedetour.android.BuildConfig;
import com.animedetour.android.framework.Application;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.logging.Log;

import javax.inject.Singleton;

@Module(
    includes = {
        DataModule.class,
        ApiModule.class,
    },
    injects = {
        Application.class,

        LandingActivity.class,
        EventActivity.class,

        HomeFragment.class,
        DayFragment.class,
    },
    complete = false,
    library = true
)
public class ApplicationModule
{
    private android.app.Application application;

    public ApplicationModule(android.app.Application application)
    {
        this.application = application;
    }

    @Provides @Singleton android.app.Application getApplication()
    {
        return this.application;
    }

    @Provides @Singleton Log provideLogger()
    {
        return new ConsoleLogger(BuildConfig.DEBUG, "application");
    }

    @Provides @Singleton
    ImageLoader provideImageLoader(android.app.Application context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return new ImageLoader(queue, new LongImageCache(cacheSize));
    }
}
