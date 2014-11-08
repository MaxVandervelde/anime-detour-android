/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework;

import android.os.Bundle;
import butterknife.ButterKnife;
import icepick.Icepick;
import prism.framework.GraphContext;
import prism.framework.InjectionFacade;

public class Fragment extends android.support.v4.app.Fragment
{
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        GraphContext context = (GraphContext) this.getActivity().getApplication();
        InjectionFacade.inject(context, this.getActivity(), this);

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
}
