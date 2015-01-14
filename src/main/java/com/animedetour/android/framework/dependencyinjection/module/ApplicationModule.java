/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.animedetour.android.BuildConfig;
import com.animedetour.android.R;
import com.animedetour.android.database.DataModule;
import com.animedetour.android.volley.cache.LongImageCache;
import com.animedetour.api.sched.api.dependencyinjection.ApiModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
    includes = {
        DataModule.class,
        ApiModule.class,
        LogModule.class,
    },
    complete = false,
    library = true
)
final public class ApplicationModule
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

    @Provides @Singleton ImageLoader provideImageLoader(Application context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return new ImageLoader(queue, new LongImageCache(cacheSize));
    }

    @Provides @Singleton Tracker provideAnalytics(Application context)
    {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
        Tracker tracker = analytics.newTracker(R.xml.google_analytics);
        tracker.setAppVersion(BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);

        return tracker;
    }
}
