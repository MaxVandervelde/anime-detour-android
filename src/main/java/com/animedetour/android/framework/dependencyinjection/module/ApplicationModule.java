/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import com.animedetour.android.BuildConfig;
import com.animedetour.android.R;
import com.animedetour.android.database.DataModule;
import com.animedetour.android.schedule.notification.NotificationScheduler;
import com.animedetour.api.ApiModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.inkapplications.groundcontrol.SubscriptionManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.okhttp.Cache;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Module(
    includes = {
        DebugModule.class,
        NetworkModule.class,
        DataModule.class,
        ApiModule.class,
        LogModule.class,
    },
    injects = {
        com.animedetour.android.framework.Application.class,
        NotificationScheduler.class,
    },
    staticInjections = { ApplicationModule.EagerSingletons.class },
    complete = false,
    library = true
)
final public class ApplicationModule
{
    @Provides @Singleton Tracker provideAnalytics(Application context)
    {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
        Tracker tracker = analytics.newTracker(R.xml.google_analytics);
        tracker.setAppVersion(BuildConfig.VERSION_NAME + "." + BuildConfig.VERSION_CODE);

        return tracker;
    }

    @Provides @Singleton AlarmManager provideAlarmManager(
        Application context
    ) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides @Singleton SubscriptionManager provideSubscriptionManager()
    {
        return new SubscriptionManager();
    }

    @Provides @Singleton Bus provideBus()
    {
        return new Bus();
    }

    @Provides @Singleton RefWatcher provideRefWatcher(Application application)
    {
        return LeakCanary.install(application);
    }

    @Provides @Singleton Cache provideCache(Application application)
    {
        try {
            File cacheDir = new File(application.getCacheDir(), "http");
            long cacheSize = 80 * 1024 * 1024; // 80MB
            return new Cache(cacheDir, cacheSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Services that are created immediately upon instantiation of the graph.
     *
     * DO NOT USE THESE SERVICES DIRECTLY!
     * This only exists as a workaround for dagger.
     */
    static class EagerSingletons
    {
        @Inject static RefWatcher refWatcher;
    }
}
