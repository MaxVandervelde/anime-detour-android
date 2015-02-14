/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.animedetour.api.sched.api.model.Event;
import org.joda.time.DateTime;

/**
 * Schedules a notification alarm for the start-time of an event.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class EventNotificationManager
{
    final private AlarmManager alarmManager;
    final private Context context;

    public EventNotificationManager(AlarmManager alarmManager, Context context)
    {
        this.alarmManager = alarmManager;
        this.context = context;
    }

    /**
     * Schedules an alarm 15 minutes before the specified event.
     *
     * @param event The event to schedule an alarm for.
     */
    public void scheduleNotification(Event event)
    {
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
