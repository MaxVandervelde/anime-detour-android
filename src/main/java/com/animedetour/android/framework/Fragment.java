/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework;

import android.os.Bundle;
import butterknife.ButterKnife;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.otto.Bus;
import icepick.Icepick;
import org.apache.commons.logging.Log;
import prism.framework.PrismFacade;

import javax.inject.Inject;

public class Fragment extends android.support.v4.app.Fragment
{
    @Inject
    Log logger;

    @Inject
    Bus applicationBus;

    @Inject
    RefWatcher refWatcher;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        PrismFacade.bootstrap(this);
        ButterKnife.inject(this, this.getView());
        this.logger.trace(this);
        this.refWatcher.watch(this);
    }

    @Override public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.applicationBus.register(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        this.applicationBus.unregister(this);
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
