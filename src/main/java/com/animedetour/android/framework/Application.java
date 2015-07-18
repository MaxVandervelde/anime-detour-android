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
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.inkapplications.prism.ApplicationCallback;
import com.squareup.okhttp.OkHttpClient;
import dagger.ObjectGraph;
import prism.framework.GraphContext;
import prism.framework.KernelContext;
import prism.framework.PrismKernel;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

final public class Application extends android.app.Application implements GraphContext, KernelContext
{
    private PrismKernel kernel;

    @Inject
    ApplicationCallback applicationCallback;

    @Inject
    OkHttpClient okHttpClient;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.kernel = new PrismKernel(this);
        this.kernel.bootstrap(this);
        this.applicationCallback.onCreate(this);

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(
            this,
            this.okHttpClient
        ).build();
        Fresco.initialize(this, config);
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
            new prism.module.ActivityModule(activity),
            new ActivityModule(),
        };
    }

    @Override
    public ObjectGraph getApplicationGraph()
    {
        return ObjectGraph.create(
            new prism.module.ApplicationModule(this),
            new ApplicationModule()
        );
    }

    @Override
    public PrismKernel getKernel()
    {
        return this.kernel;
    }
}
