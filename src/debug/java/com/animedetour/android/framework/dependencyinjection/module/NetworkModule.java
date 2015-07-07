package com.animedetour.android.framework.dependencyinjection.module;

import android.app.Application;
import com.animedetour.android.framework.NetworkSlowdown;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;

@Module(library = true, complete = false)
@SuppressWarnings("UnusedDeclaration")
public class NetworkModule
{
    @Provides @Singleton OkHttpClient provideOkHttp(
        Application application,
        NetworkSlowdown slowdownInterceptor
    ) {
        try {
            OkHttpClient client = new OkHttpClient();
            File cacheDir = new File(application.getCacheDir(), "http");
            long cacheSize = 80 * 1024 * 1024; // 80MB
            Cache cache =  new Cache(cacheDir, cacheSize);
            client.setCache(cache);
            client.networkInterceptors().add(new StethoInterceptor());
            client.networkInterceptors().add(slowdownInterceptor);
            return client;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
