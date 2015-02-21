package com.animedetour.android.framework.dependencyinjection.module;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public class NetworkModule
{
    @Provides @Singleton OkHttpClient provideOkHttp()
    {
        OkHttpClient client = new OkHttpClient();

        return client;
    }
}
