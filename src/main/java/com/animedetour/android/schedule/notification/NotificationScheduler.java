/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.animedetour.android.framework.DetourApplication;
import monolog.Monolog;
import prism.framework.PrismKernel;

import javax.inject.Inject;

/**
 * Schedules all of the user's favorited events when the device boots.
 *
 * Android does not persist these scheduled alarms when the device is powered
 * off, so we must re-schedule these at every boot.
 *
 * @author Maxwell Vandervelde (Max@MaxVandervelde.com)
 */
public class NotificationScheduler extends BroadcastReceiver
{
    @Inject
    EventNotificationManager notificationManager;

    @Inject
    Monolog logger;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        DetourApplication application = (DetourApplication) context.getApplicationContext();
        PrismKernel prismKernel = new PrismKernel(application);
        prismKernel.bootstrap(this);

        this.notificationManager.scheduleAllNotifications();
    }
}
