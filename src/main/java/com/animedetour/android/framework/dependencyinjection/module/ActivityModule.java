/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.app.Activity;
import com.animedetour.android.guest.GuestDetailActivity;
import com.animedetour.android.schedule.EventActivity;
import com.animedetour.android.guest.GuestIndexFragment;
import com.animedetour.android.home.HomeFragment;
import com.animedetour.android.main.MainActivity;
import com.animedetour.android.schedule.DayFragment;
import com.animedetour.android.schedule.ScheduleFragment;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(
    includes = {
    },
    injects = {
        MainActivity.class,
        EventActivity.class,
        GuestDetailActivity.class,

        HomeFragment.class,
        DayFragment.class,
        ScheduleFragment.class,
        GuestIndexFragment.class,
    },
    complete = false,
    library = true
)
final public class ActivityModule
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
