package com.animedetour.android.framework.dependencyinjection.module;

import com.animedetour.android.framework.Application;
import com.inkapplications.prism.EmptyApplicationCallback;
import com.inkapplications.prism.ApplicationCallback;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public class DebugModule
{
    @Provides @Singleton ApplicationCallback provideDebugCallbacks() {
        return new EmptyApplicationCallback();
    }
}
