/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.InjectViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.animedetour.android.R;
import com.animedetour.android.framework.Fragment;

/**
 * Settings page for the application.
 *
 * Gives the user options to enable/disable things like notifications.
 *
 * Also includes developer options which will be displayed after tapping the
 * version number ten times.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class SettingsFragment extends Fragment
{
    /**
     * Keeps track of clicks on the version number so we can display dev settings.
     */
    private int versionClicks = 0;

    /**
     * Settings that should only be displayed to developers.
     */
    @InjectViews({R.id.settings_event_generate})
    View[] developerViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.settings, container, false);

        return view;
    }

    /**
     * Change whether the user wants to receive notifications for events.
     */
    @OnCheckedChanged(R.id.settings_event_notification_switch)
    public void toggleNotifications(boolean checked)
    {
        String verb = checked ? "Activate!" : "Disable!";
        Toast.makeText(this.getActivity(), "Notifications " + verb, Toast.LENGTH_SHORT).show();
    }

    /**
     * Click on the version number, enable dev settings if over 10 clicks.
     */
    @OnClick(R.id.settings_version)
    public void trackDeveloperClick()
    {
        ++versionClicks;
        if (versionClicks > 10) {
            this.versionClicks = 0;
            Toast.makeText(this.getActivity(), "Developer Unlocked!", Toast.LENGTH_SHORT).show();
            for (View view : this.developerViews) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Developer setting: Generate a new upcoming event.
     */
    @OnClick(R.id.settings_event_generate_button)
    public void generateEvent()
    {
        Toast.makeText(this.getActivity(), "Generate Event", Toast.LENGTH_SHORT).show();
    }
}
