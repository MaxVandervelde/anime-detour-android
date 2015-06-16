/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2014-2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.framework.dependencyinjection.module;

import android.content.res.Resources;
import com.inkapplications.android.logger.console.ConsoleLogger;
import dagger.Module;
import dagger.Provides;
import org.apache.commons.logging.Log;

import javax.inject.Singleton;

/**
 * This Log Module provides the console (logcat) logger used in debug builds.
 *
 * This is not to be made available in a production build. A different
 * logger can be found in the release folder.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Module(library = true, complete = false)
public class LogModule
{
    @Provides @Singleton Log provideLogger(
        Resources resources
    ) {
        return new ConsoleLogger(true, true, "AnimeDetour");
    }
}
