/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.dependencyinjection.module;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.animedetour.android.activity.EventActivity;
import com.animedetour.android.fragment.LandingFragment;
import com.animedetour.android.fragment.ScheduleFragment;
import com.animedetour.android.volley.cache.LongImageCache;
import com.animedetour.sched.api.dependencyinjection.ApiModule;
import com.animedetour.android.activity.MainActivity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
    includes = {
        ApplicationModule.class,
        ApiModule.class,
        DataModule.class,
    },
    injects = {
        MainActivity.class,
        EventActivity.class,

        LandingFragment.class,
        ScheduleFragment.class
    },
    complete = false,
    library = true
)
public class ActivityModule
{
    final private Activity activity;

    public ActivityModule(Activity activity)
    {
        this.activity = activity;
    }

    @Provides @Singleton Activity provideActivity()
    {
        return activity;
    }

    @Provides @Singleton ImageLoader provideImageLoader(Activity context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return new ImageLoader(queue, new LongImageCache(cacheSize));
    }
}
