/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.animedetour.android.settings.PreferenceManager;
import com.animedetour.api.sched.api.model.Event;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Schedules a notification alarm for the start-time of an event.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
@Singleton
public class EventNotificationManager
{
    final private PreferenceManager preferenceManager;
    final private AlarmManager alarmManager;
    final private Context context;

    @Inject
    public EventNotificationManager(
        PreferenceManager preferenceManager,
        AlarmManager alarmManager,
        Context context
    ) {
        this.preferenceManager = preferenceManager;
        this.alarmManager = alarmManager;
        this.context = context;
    }

    /**
     * Schedules an alarm 15 minutes before the specified event.
     *
     * This will NOT schedule an alarm if the user has the notification
     * preference turned off.
     *
     * @param event The event to schedule an alarm for.
     */
    public void scheduleNotification(Event event)
    {
        if (false == this.preferenceManager.receiveEventNotifications()) {
            return;
        }

        DateTime notificationTime = event.getStartDateTime().minusMinutes(15);
        if (notificationTime.isBeforeNow()) {
            return;
        }

        Intent alarmIntent = new Intent(this.context, UpcomingEventReciever.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(UpcomingEventReciever.EXTRA_EVENT, event);
        alarmIntent.putExtra(UpcomingEventReciever.EXTRA_BUNDLE, bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
            this.context,
            event.getId().hashCode(),
            alarmIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        );

        alarmManager.set(AlarmManager.RTC, notificationTime.getMillis(), pendingIntent);
    }
}
