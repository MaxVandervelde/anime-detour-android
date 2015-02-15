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

    @Inject
    public PreferenceManager(SharedPreferences preferences)
    {
        this.preferences = preferences;
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
    }

    /**
     * @return Whether the user wants to see notifications about upcoming events.
     */
    public boolean receiveEventNotifications()
    {
        return this.preferences.getBoolean("notify_events", true);
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
    }

    /**
     * @return Whether the user should be show developer settings and info.
     */
    public boolean isDeveloper()
    {
        return this.preferences.getBoolean("developer", false);
    }
}
