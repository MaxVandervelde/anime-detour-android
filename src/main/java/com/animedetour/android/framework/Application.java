/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework;

import android.app.Activity;
import com.animedetour.android.event.EventModule;
import com.animedetour.android.framework.dependencyinjection.module.ActivityModule;
import com.animedetour.android.framework.dependencyinjection.module.ApplicationModule;
import com.animedetour.android.main.MainModule;
import dagger.ObjectGraph;
import prism.framework.GraphContext;
import prism.framework.KernelContext;
import prism.framework.LifecycleSubscriber;
import prism.framework.PrismKernel;

import java.util.LinkedHashMap;
import java.util.Map;

final public class Application extends android.app.Application implements GraphContext, KernelContext
{
    private ObjectGraph applicationGraph;
    private PrismKernel kernel;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.applicationGraph = ObjectGraph.create(this.getApplicationModules());
        this.kernel = new PrismKernel(this);

        this.registerActivityLifecycleCallbacks(new LifecycleSubscriber(this));
        this.registerActivityLifecycleCallbacks(new ExtraActivityInjections());
    }

    @Override
    public ObjectGraph getApplicationGraph()
    {
        return this.applicationGraph;
    }

    @Override
    public Map<Class, Object> getScopeModules(Activity activity)
    {
        LinkedHashMap<Class, Object> definitions = new LinkedHashMap<>();
        definitions.put(MainModule.class, new MainModule());
        definitions.put(EventModule.class, new EventModule());

        return definitions;
    }

    @Override
    public Object[] getActivityModules(Activity activity)
    {
        return new Object[] {
            new ActivityModule(activity),
        };
    }

    private Object[] getApplicationModules()
    {
        return new Object[] {
            new ApplicationModule(this),
        };
    }

    @Override
    public PrismKernel getKernel()
    {
        return this.kernel;
    }
}
