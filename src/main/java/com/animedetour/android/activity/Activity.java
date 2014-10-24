/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;
import com.inkapplications.android.eventdispatcher.event.activity.*;
import com.inkapplications.android.standard.BaseApplication;
import com.animedetour.android.dependencyinjection.module.ActivityModule;
import com.inkapplications.android.standard.dependencyinjection.module.StandardApplicationModule;
import com.squareup.otto.Bus;
import dagger.ObjectGraph;
import icepick.Icepick;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

abstract public class Activity extends ActionBarActivity
{
    /**
     * Application-wide Event Bus
     */
    @Inject
    Bus applicationBus;

    /**
     * Get Modules
     *
     * Gets all of the dagger modules to load into the application object graph.
     * These modules are loaded for all Activities, but not the entire
     * application.
     * Method may be overridden to include additional modules.
     *
     * @return The modules to include in the object graph
     */
    public List<Object> getModules()
    {
        ActivityModule activityModule = new ActivityModule(this);
        BaseApplication baseApplication = (BaseApplication) this.getApplication();
        StandardApplicationModule applicationModule = new StandardApplicationModule(baseApplication);

        ArrayList<Object> modules = new ArrayList<Object>();
        modules.add(applicationModule);
        modules.add(activityModule);

        return modules;
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setupContentView();
        this.injectSelf();
        Icepick.restoreInstanceState(this, savedInstanceState);

        this.applicationBus.post(new OnCreateEvent(this, savedInstanceState));
    }

    abstract protected void setupContentView();

    @Override protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        this.applicationBus.post(new OnStartEvent(this));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.applicationBus.post(new OnResumeEvent(this));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        this.applicationBus.post(new OnPauseEvent(this));
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        this.applicationBus.post(new OnStartEvent(this));
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        this.applicationBus.post(new OnRestartEvent(this));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        this.applicationBus.post(new OnDestroyEvent(this));
    }

    /**
     * Inject Self
     *
     * Injects the current activity using both butterknife inject and dagger
     */
    private void injectSelf()
    {
        ButterKnife.inject(this);
        this.injectDagger();
    }

    /**
     * Inject Dagger
     *
     * Uses Dagger to inject the current class
     */
    private void injectDagger()
    {
        ObjectGraph graph = ObjectGraph.create(this.getModules().toArray());

        graph.inject(this);
        graph.injectStatics();
    }
}
