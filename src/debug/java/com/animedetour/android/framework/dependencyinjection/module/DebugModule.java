package com.animedetour.android.framework.dependencyinjection.module;

import com.animedetour.android.framework.InitialDataLoader;
import com.inkapplications.StethoCallback;
import com.inkapplications.prism.ApplicationCallback;
import com.inkapplications.prism.CompositeApplicationCallback;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Module(library = true, complete = false)
public class DebugModule
{
    @Provides @Singleton ApplicationCallback provideDebugCallbacks(
        InitialDataLoader initialDataLoader
    ) {
        List<ApplicationCallback> callbacks = new ArrayList<>();
        callbacks.add(new StethoCallback());
        callbacks.add(initialDataLoader);

        return new CompositeApplicationCallback(callbacks);
    }
}
