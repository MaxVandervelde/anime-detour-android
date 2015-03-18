package com.animedetour.android.framework.dependencyinjection.module;

import android.app.Application;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Module(library = true, complete = false)
public class NetworkModule
{
    @Provides @Singleton OkHttpClient provideOkHttp(
        Application application
    ) {
        try {
            OkHttpClient client = new OkHttpClient();
            File cacheDir = new File(application.getCacheDir(), "http");
            long cacheSize = 80 * 1024 * 1024; // 80MB
            Cache cache =  new Cache(cacheDir, cacheSize);
            client.setCache(cache);
            client.networkInterceptors().add(new StethoInterceptor());

            return client;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
