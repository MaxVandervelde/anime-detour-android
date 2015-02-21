/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework;

import android.app.Activity;
import com.animedetour.android.framework.dependencyinjection.module.ActivityModule;
import com.animedetour.android.framework.dependencyinjection.module.ApplicationModule;
import com.inkapplications.prism.ApplicationCallback;
import com.inkapplications.prism.analytics.AutoLogger;
import dagger.ObjectGraph;
import org.apache.commons.logging.Log;
import prism.framework.GraphContext;
import prism.framework.KernelContext;
import prism.framework.LifecycleSubscriber;
import prism.framework.PrismKernel;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

final public class Application extends android.app.Application implements GraphContext, KernelContext
{
    private ObjectGraph applicationGraph;
    private PrismKernel kernel;

    @Inject
    Log logger;

    @Inject
    ApplicationCallback applicationCallback;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.applicationGraph = ObjectGraph.create(this.getApplicationModules());
        this.kernel = new PrismKernel(this);
        this.kernel.bootstrap(this);
        this.applicationCallback.onCreate(this);

        this.registerActivityLifecycleCallbacks(new LifecycleSubscriber(this));
        this.registerActivityLifecycleCallbacks(new ExtraActivityInjections());
        this.registerActivityLifecycleCallbacks(new AutoLogger(this.logger));
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
