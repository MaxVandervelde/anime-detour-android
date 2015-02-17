/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.view.fader;

import android.content.res.Resources;
import android.view.View;
import android.widget.ScrollView;
import com.animedetour.android.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Standardizes creation of the toolbar fader utility.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class ToolbarFaderFactory
{
    final private Resources resources;

    @Inject
    public ToolbarFaderFactory(Resources resources)
    {
        this.resources = resources;
    }

    public ToolbarFader create(View reference, ScrollView container, View toolbar)
    {
        return new ToolbarFader(this.resources, R.color.primary, reference, container, toolbar);
    }
}
