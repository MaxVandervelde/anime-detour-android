/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.framework;

import android.os.Bundle;
import butterknife.ButterKnife;
import icepick.Icepick;
import org.apache.commons.logging.Log;
import prism.framework.PrismFacade;

import javax.inject.Inject;

public class Fragment extends android.support.v4.app.Fragment
{
    @Inject
    Log logger;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        PrismFacade.bootstrap(this);
        ButterKnife.inject(this, this.getView());
        this.logger.trace(this);
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
