/*
 * This file is part of the Anime Detour Android application
 *
 * Copyright (c) 2015 Anime Twin Cities, Inc.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.animedetour.android.schedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.animedetour.android.database.FavoriteRepository;
import com.animedetour.android.framework.Application;
import org.apache.commons.logging.Log;
import prism.framework.PrismKernel;

import javax.inject.Inject;
import java.sql.SQLException;

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
    FavoriteRepository favoriteData;

    @Inject
    EventNotificationManager notificationManager;

    @Inject
    Log logger;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Application application = (Application) context.getApplicationContext();
        PrismKernel prismKernel = new PrismKernel(application);
        prismKernel.bootstrap(this);

        try {
            for (Favorite favorite : this.favoriteData.getAll()) {
                this.notificationManager.scheduleNotification(favorite.getEvent());
            }
        } catch (SQLException e) {
            this.logger.error("Could not look up events to schedule notifications.", e);
        }
    }
}
