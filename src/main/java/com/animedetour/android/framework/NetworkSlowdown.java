/*
 * Copyright (c) 2015 Ink Applications, LLC.
 * Distributed under the MIT License (http://opensource.org/licenses/MIT)
 */
package com.animedetour.android.framework;

import com.animedetour.android.settings.PreferenceManager;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;
import monolog.Monolog;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.Random;

/**
 * Delays an http request for a random amount of time.
 *
 * This is intended for simulating slow connections when debugging, it simply
 * sleeps the thread in a network interceptor to simulate longer connection
 * times.
 *
 * To use this, add it to your OkHttpClient's network interceptors:
 *
 *     OkHttpClient client = new OkHttpClient();
 *     client.networkInterceptors().add(slowdownInterceptor);
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class NetworkSlowdown implements Interceptor
{
    private final Monolog log;
    private final PreferenceManager preferences;
    private final Random random = new Random();

    @Inject
    public NetworkSlowdown(Monolog log, PreferenceManager preferences)
    {
        this.log = log;
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        if (false == this.preferences.fakeSlowConnections()) {
            this.log.info("Preference disabled. Not slowing network.");
            return chain.proceed(chain.request());
        }

        this.sleepRandom();
        this.log.info("Network slowdown done. Proceeding chain");

        return chain.proceed(chain.request());
    }

    /**
     * Sleep the thread anywhere between 5 and 10 seconds.
     */
    private void sleepRandom()
    {
        try {
            int delay = this.getRandomTime(5, 20);
            this.log.info("Slowing request down by " + delay + " seconds");
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            this.log.error(e);
        }
    }

    /**
     * Get a random integr with a min/max value.
     */
    private int getRandomTime(int min, int max)
    {
        return this.random.nextInt((max - min) + 1) + min;
    }
}
