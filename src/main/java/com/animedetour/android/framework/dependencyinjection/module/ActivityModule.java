/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.app.Activity;
import com.animedetour.android.event.EventActivity;
import com.animedetour.android.landing.LandingActivity;
import com.animedetour.android.landing.home.HomeFragment;
import com.animedetour.android.landing.schedule.ScheduleFragment;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
    includes = {

    },
    injects = {
        LandingActivity.class,
        EventActivity.class,

        HomeFragment.class,
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
}
