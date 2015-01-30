/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc. All rights Reserved.
 */
package com.animedetour.android.schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.animedetour.api.sched.api.model.Event;

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
        long futureInMillis = event.getStartDateTime().minusMinutes(15).getMillis();
        alarmManager.set(AlarmManager.RTC, futureInMillis, pendingIntent);
    }
}
