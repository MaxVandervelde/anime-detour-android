/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.fragment;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.animedetour.android.activity.Activity;
import dagger.ObjectGraph;
import icepick.Icepick;

import java.util.List;

public class Fragment extends android.support.v4.app.Fragment
{
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.injectDagger();
        ButterKnife.inject(this, this.getView());
    }

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * Inject Dagger
     *
     * Uses Dagger to inject the current class with the base activity
     * dependencies.
     * This method may not be overridden, as fragments do not need their own
     * dependency graph.
     */
    private void injectDagger()
    {
        Activity parentActivity = (Activity) this.getActivity();
        List<Object> parentModules = parentActivity.getModules();

        ObjectGraph graph = ObjectGraph.create(parentModules.toArray());

        graph.inject(this);
        graph.injectStatics();
    }
}
