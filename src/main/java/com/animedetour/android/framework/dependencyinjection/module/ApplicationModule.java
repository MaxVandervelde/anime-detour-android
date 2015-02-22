/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.animedetour.android.BuildConfig;
import com.animedetour.android.R;
import com.animedetour.android.database.DataModule;
import com.animedetour.android.schedule.notification.NotificationScheduler;
import com.animedetour.android.volley.cache.LongImageCache;
import com.animedetour.api.ApiModule;
import com.circle.android.api.OkHttpStack;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.inkapplications.groundcontrol.SubscriptionManager;
import com.inkapplications.prism.imageloader.ImageLoader;
import com.inkapplications.prism.imageloader.adapter.volley.VolleyLoader;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.logging.Log;

import javax.inject.Singleton;

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

    @Provides @Singleton Context provideAppContext()
    {
        return this.application;
    }

    @Provides @Singleton com.android.volley.toolbox.ImageLoader provideVolleyImageLoader(
        Application context,
        OkHttpClient client
    ) {
        RequestQueue queue = Volley.newRequestQueue(context, new OkHttpStack(client));
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return new com.android.volley.toolbox.ImageLoader(queue, new LongImageCache(cacheSize));
    }

    @Provides @Singleton ImageLoader provideImageLoader(
        Application context,
        Log logger,
        com.android.volley.toolbox.ImageLoader volleyLoader
    ) {
        Resources resources = context.getResources();
        return new VolleyLoader(volleyLoader, resources, logger);
    }

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

    @Provides @Singleton SharedPreferences provideSharedPreferences(
        android.app.Application context
    ) {
        return context.getSharedPreferences("anime_detour", Context.MODE_PRIVATE);
    }

    @Provides @Singleton Resources provideResources(Application context)
    {
        return context.getResources();
    }
}
