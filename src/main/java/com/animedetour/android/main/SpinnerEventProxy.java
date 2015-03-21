/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.main;

import android.view.View;
import android.widget.AdapterView;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Listen for spinner selection events and proxy them into an event bus.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
final public class SpinnerEventProxy implements AdapterView.OnItemSelectedListener
{
    final private Bus eventBus;

    @Inject
    public SpinnerEventProxy(Bus eventBus)
    {
        this.eventBus = eventBus;
    }

    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id)
    {
        String selected = (String) parent.getItemAtPosition(position);
        this.eventBus.post(new SubNavigationSelectionChange(selected));
    }

    @Override public void onNothingSelected(AdapterView adapterView) {}
}
