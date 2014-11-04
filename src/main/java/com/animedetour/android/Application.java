/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android;

import android.app.Activity;
import com.animedetour.android.dependencyinjection.module.ActivityModule;
import com.animedetour.android.dependencyinjection.module.ApplicationModule;
import com.animedetour.android.framework.ExtraActivityInjections;
import dagger.ObjectGraph;
import prism.framework.ActivityInjector;
import prism.framework.GraphContext;

import java.util.ArrayList;
import java.util.List;

public class Application extends android.app.Application implements GraphContext
{
    private ObjectGraph applicationGraph;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.applicationGraph = ObjectGraph.create(this.getApplicationModules().toArray());

        this.registerActivityLifecycleCallbacks(new ActivityInjector(this));
        this.registerActivityLifecycleCallbacks(new ExtraActivityInjections());
    }

    @Override
    public ObjectGraph getApplicationGraph()
    {
        return this.applicationGraph;
    }

    @Override
    public Object[] getActivityModules(Activity activity)
    {
        return new Object[]{
            new ActivityModule(activity),
        };
    }

    private List<Object> getApplicationModules()
    {
        List<Object> modules = new ArrayList<Object>();

        modules.add(new ApplicationModule(this));

        return modules;
    }
}
