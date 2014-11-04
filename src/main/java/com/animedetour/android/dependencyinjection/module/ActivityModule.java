/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.dependencyinjection.module;

import android.app.Activity;
import com.animedetour.android.activity.EventActivity;
import com.animedetour.android.fragment.LandingFragment;
import com.animedetour.android.fragment.ScheduleFragment;
import com.animedetour.android.activity.MainActivity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
    includes = {

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
}
