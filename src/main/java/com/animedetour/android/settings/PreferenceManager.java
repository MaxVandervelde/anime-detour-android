/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.settings;

import android.content.SharedPreferences;
import com.animedetour.android.analytics.EventFactory;
import org.apache.commons.logging.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Handles getting and putting user settings into shared preferences.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
final public class PreferenceManager
{
    final private SharedPreferences preferences;
    final private Log logger;

    @Inject
    public PreferenceManager(SharedPreferences preferences, Log logger)
    {
        this.preferences = preferences;
        this.logger = logger;
    }

    /**
     * Set whether the user should receive notifications for upcoming events.
     *
     * @param notify whether to show notifications for events.
     */
    public void setEventNotifications(boolean notify)
    {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean("notify_events", notify);
        editor.apply();
        this.logger.trace(EventFactory.notificationSetting(notify));
    }

    /**
     * @return Whether the user wants to see notifications about upcoming events.
     */
    public boolean receiveEventNotifications()
    {
        return this.preferences.getBoolean("notify_events", true);
    }

    /**
     * Developer setting for faking slow connections.
     *
     * @return Whether the app should pretend that the network is slow.
     */
    public boolean fakeSlowConnections()
    {
        return this.preferences.getBoolean("fake_slow_connections", false);
    }

    /**
     * Developer setting for faking slow connections.
     *
     * @param shouldSlowdown Whether the app should pretend that the network is slow.
     */
    public void setFakeSlowConnections(boolean shouldSlowdown)
    {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean("fake_slow_connections", shouldSlowdown);
        editor.apply();
    }

    /**
     * Enables or disables developer views and settings in the app.
     *
     * @param showDeveloperSettings Whether to show dev settings to the user.
     */
    public void setDeveloper(boolean showDeveloperSettings)
    {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean("developer", showDeveloperSettings);
        editor.apply();

        if (showDeveloperSettings) {
            this.logger.trace(EventFactory.developerEnabled());
        }
    }

    /**
     * @return Whether the user should be show developer settings and info.
     */
    public boolean isDeveloper()
    {
        return this.preferences.getBoolean("developer", false);
    }

    /**
     * Clear all stored shared preference data.
     */
    public void truncate()
    {
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.clear();
        editor.apply();
    }
}
