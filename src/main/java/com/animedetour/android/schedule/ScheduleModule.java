/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(complete = false, library = true)
public class ScheduleModule
{
    @Provides @Singleton EventViewBinder provideEventViewBinder(
        Context context,
        PanelViewController controller,
        EventPalette palette
    ) {
        return new EventViewBinder(context, palette, controller);
    }
}
